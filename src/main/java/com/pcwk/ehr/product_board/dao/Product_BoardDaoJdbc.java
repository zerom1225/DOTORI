package com.pcwk.ehr.product_board.dao;

import java.io.Reader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pcwk.ehr.product_board.domain.Product_BoardVO;
import com.pcwk.ehr.user.dao.UserDaoJdbc;

@Repository
public class Product_BoardDaoJdbc implements Product_BoardDao {

	final Logger log = LogManager.getLogger(UserDaoJdbc.class);

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Product_BoardDaoJdbc() {
		super();
	}

	private static void mapBoardImage(ResultSet rs, Product_BoardVO outVO) {

		try {

			// CLOB 데이터 읽은 후 String 으로 변환
			Clob board_Image = rs.getClob("board_image");
			String jsonString = clobToString(board_Image);

			// Gson 을 사용하여 json 문자열을 List<String> 으로 변환
			Gson gson = new Gson();

			List<String> board_ImageList = gson.fromJson(jsonString, new TypeToken<List<String>>() {
			}.getType());

			// VO에 매핑
			outVO.setBoard_Image(board_ImageList);
		} catch (Exception e) {
			System.out.println("mapBoardImage 게시글 이미지 jsonString -> List 맵핑 예외 발생 ");
			e.printStackTrace();
		}

	}

	private static String clobToString(Clob clob) {

		if (clob == null)
			return "";

		StringBuilder sb = new StringBuilder();

		try (Reader reader = clob.getCharacterStream()) {
			char[] buffer = new char[1024]; // 1024 개의 문자를 읽을 수 있는 버퍼
			int len;
			while ((len = reader.read(buffer)) != -1) { // 문자 스트림에서 최대 1024자씩 데이터를 읽어옴
														// 더 이상 읽을 데이터가 없다면 반복문 종료
				sb.append(buffer, 0, len); // buffer의 0번 인덱스부터 len 길이만큼 데이터를 추가
			}
			return sb.toString();
		} catch (Exception e) {
			System.out.println(" CLOB -> String 변환 예외 발생 ");
			e.printStackTrace();
			return "";
		}

	}

	private static String convertMunic_Address(String trade_Address) {

		String[] splitSpace = trade_Address.split(" ");

		return splitSpace[0] + " " + splitSpace[1];
	}

	private static String preview_Board_Title(String board_Title, int board_Status) {

		if (board_Status == 0) {
			return board_Title;
		} else {
			return "[거래완료]" + board_Title;
		}

	}

	private RowMapper<Product_BoardVO> product_BoardMapper = new RowMapper<Product_BoardVO>() {

		@Override
		public Product_BoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
			Product_BoardVO outVO = new Product_BoardVO();
			// no
			outVO.setBoard_Id(rs.getInt("board_id"));
			outVO.setSeller_Id(rs.getInt("seller_id"));
			outVO.setBuyer_Id(rs.getInt("buyer_id"));
			outVO.setReg_Date(rs.getTimestamp("reg_date").toLocalDateTime());
			outVO.setMod_Date(rs.getTimestamp("mod_date").toLocalDateTime());
			outVO.setView_Count(rs.getInt("view_count"));
			outVO.setBoard_Title(rs.getString("board_title"));
			mapBoardImage(rs, outVO); // board_image CLOB => List 매핑
			outVO.setCategory_Id(rs.getInt("category_id"));
			outVO.setPrice(rs.getInt("price"));
			outVO.setBoard_Content(clobToString(rs.getClob("board_content")));
			outVO.setHow_Trade(rs.getInt("how_trade"));
			outVO.setTrade_Address(rs.getString("trade_address"));
			outVO.setBoard_Status(rs.getInt("board_status"));
			outVO.setMunic_Address(convertMunic_Address(outVO.getTrade_Address()));
			outVO.setPreview_Board_Title(preview_Board_Title(rs.getString("board_title"), rs.getInt("board_status")));

			log.debug("2.mapRow outVO : " + outVO.toString());
			return outVO;
		}

	};

	@Override
	public int doSave(Product_BoardVO pbVO) throws SQLException {
		int flag = 0;

		StringBuilder sb = new StringBuilder(150);

		String board_ImagesJson = new Gson().toJson(pbVO.getBoard_Image()); // 파일명 목록을 JSON 형식으로 변환

		sb.append("INSERT INTO product_board ( \n");
		sb.append("            seller_id,      \n");
		sb.append("			   board_title,    \n");
		sb.append("			   board_image,    \n");
		sb.append("			   category_id,    \n");
		sb.append("			   price,          \n");
		sb.append("			   board_content,  \n");
		sb.append("			   how_trade,      \n");
		sb.append("			   trade_address ) \n");
		sb.append("   VALUES ( ?,              \n");
		sb.append("            ?,              \n");
		sb.append("			   ?,              \n");
		sb.append("			   ?,              \n");
		sb.append("			   ?,              \n");
		sb.append("		       ?,              \n");
		sb.append("			   ?,              \n");
		sb.append("			   ? )               ");

		Object[] args = { pbVO.getSeller_Id(), pbVO.getBoard_Title(), board_ImagesJson, pbVO.getCategory_Id(),
				pbVO.getPrice(), pbVO.getBoard_Content(), pbVO.getHow_Trade(), pbVO.getTrade_Address() };

		log.debug("1.param:");
		int i = 0;
		for (Object obj : args) {
			log.debug(++i + "." + obj.toString());
		}

		flag = this.jdbcTemplate.update(sb.toString(), args);
		log.debug("2.flag:{}", flag);

		return flag;
	}

	@Override
	public int update_Board_Images(Product_BoardVO pbVO) throws SQLException {

		int flag;

		StringBuilder sb = new StringBuilder(100);

		String board_ImagesJson = new Gson().toJson(pbVO.getBoard_Image()); // 파일명 목록을 JSON 형식으로 변환

		sb.append("UPDATE product_board   \n");
		sb.append("   SET board_image = ? \n");
		sb.append(" WHERE board_id = ?      ");

		Object[] args = { board_ImagesJson, pbVO.getBoard_Id() };

		log.debug("1.param:");
		int i = 0;
		for (Object obj : args) {
			log.debug(++i + "." + obj.toString());
		}

		flag = this.jdbcTemplate.update(sb.toString(), args);
		log.debug("2.flag:{}", flag);

		return flag;
	}

	@Override
	public List<Product_BoardVO> getOnsaleProducts_Mypage(int user_Id, int page) throws SQLException {

		List<Product_BoardVO> productList = new ArrayList<Product_BoardVO>();

		StringBuilder sb = new StringBuilder(500);

		sb.append(" SELECT p2.rnum,                                                \n");
		sb.append("        p2.*                                                    \n");
		sb.append("  FROM(                                                         \n");
		sb.append("        SELECT ROW_NUMBER() OVER (ORDER BY mod_date DESC) rnum, \n");
		sb.append("	              p1.*                                             \n");
		sb.append("	         FROM product_board p1                                 \n");
		sb.append("	        WHERE p1.seller_id = ? AND p1.board_status = 0         \n");
		sb.append("	     ) p2                                                      \n");
		sb.append("  WHERE p2.rnum BETWEEN (12 * (? - 1)+ 1) AND (12 * ?)          \n");

		Object[] args = { user_Id, page, page };

		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		productList = this.jdbcTemplate.query(sb.toString(), args, product_BoardMapper);

		return productList;
	}

	@Override
	public int getOnsaleProductsCount_Mypage(int user_Id) throws SQLException {

		int count = 0;

		StringBuilder sb = new StringBuilder(100);

		sb.append("	SELECT count(*)                           \n");
		sb.append("   FROM product_board                      \n");
		sb.append("  WHERE seller_id = ? AND board_status = 0   ");

		Object[] args = { user_Id };

		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		count = this.jdbcTemplate.queryForObject(sb.toString(), args, Integer.class);

		return count;
	}

	@Override
	public List<Product_BoardVO> getSoldOutProducts_Mypage(int user_Id, int page) throws SQLException {
		List<Product_BoardVO> productList = new ArrayList<Product_BoardVO>();

		StringBuilder sb = new StringBuilder(500);

		sb.append(" SELECT p2.rnum,                                                \n");
		sb.append("        p2.*                                                    \n");
		sb.append("  FROM(                                                         \n");
		sb.append("        SELECT ROW_NUMBER() OVER (ORDER BY mod_date DESC) rnum, \n");
		sb.append("	              p1.*                                             \n");
		sb.append("	         FROM product_board p1                                 \n");
		sb.append("	        WHERE p1.seller_id = ? AND p1.board_status = 1         \n");
		sb.append("	     ) p2                                                      \n");
		sb.append("  WHERE p2.rnum BETWEEN (12 * (? - 1)+ 1) AND (12 * ?)          \n");

		Object[] args = { user_Id, page, page };

		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		productList = this.jdbcTemplate.query(sb.toString(), args, product_BoardMapper);

		return productList;
	}

	@Override
	public int getSoldOutProductsCount_Mypage(int user_Id) throws SQLException {
		int count = 0;

		StringBuilder sb = new StringBuilder(100);

		sb.append("	SELECT count(*)                           \n");
		sb.append("   FROM product_board                      \n");
		sb.append("  WHERE seller_id = ? AND board_status = 1   ");

		Object[] args = { user_Id };

		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		count = this.jdbcTemplate.queryForObject(sb.toString(), args, Integer.class);

		return count;
	}

	@Override
	public List<Product_BoardVO> getPurchaseProducts_Mypage(int user_Id, int page) throws SQLException {
		List<Product_BoardVO> productList = new ArrayList<Product_BoardVO>();

		StringBuilder sb = new StringBuilder(500);

		sb.append(" SELECT p2.rnum,                                                \n");
		sb.append("        p2.*                                                    \n");
		sb.append("  FROM(                                                         \n");
		sb.append("        SELECT ROW_NUMBER() OVER (ORDER BY mod_date DESC) rnum, \n");
		sb.append("	              p1.*                                             \n");
		sb.append("	         FROM product_board p1                                 \n");
		sb.append("	        WHERE p1.buyer_id = ? AND p1.board_status = 1         \n");
		sb.append("	     ) p2                                                      \n");
		sb.append("  WHERE p2.rnum BETWEEN (12 * (? - 1)+ 1) AND (12 * ?)          \n");

		Object[] args = { user_Id, page, page };

		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		productList = this.jdbcTemplate.query(sb.toString(), args, product_BoardMapper);

		return productList;
	}

	@Override
	public int getPurchaseProductsCount_Mypage(int user_Id) throws SQLException {
		int count = 0;

		StringBuilder sb = new StringBuilder(100);

		sb.append("	SELECT count(*)                           \n");
		sb.append("   FROM product_board                      \n");
		sb.append("  WHERE buyer_id = ? AND board_status = 1   ");

		Object[] args = { user_Id };

		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		count = this.jdbcTemplate.queryForObject(sb.toString(), args, Integer.class);

		return count;
	}

	@Override
	public Product_BoardVO doSelectOne(int board_Id) throws SQLException, DataAccessException, NullPointerException {

		Product_BoardVO outVO;

		StringBuilder sb = new StringBuilder(100);

		sb.append("SELECT *             \n");
		sb.append("  FROM product_board \n");
		sb.append(" WHERE board_id = ?  \n");

		Object[] args = { board_Id };

		outVO = this.jdbcTemplate.queryForObject(sb.toString(), args, product_BoardMapper);

		// 조회 데이터가 없는 경우
		if (null == outVO) {
			throw new NullPointerException(board_Id + "(게시글 ID)를 확인 하세요.");
		}

		return outVO;
	}

	@Override
	public int update_Product_Board_withoutImages(Product_BoardVO pbVO) throws SQLException {

		int flag = 0;

		StringBuilder sb = new StringBuilder(150);

		sb.append("UPDATE product_board       \n");
		sb.append("   SET board_title = ?,    \n");
		sb.append("       category_id = ?,    \n");
		sb.append("       price = ?,          \n");
		sb.append("       board_content = ?,  \n");
		sb.append("       how_trade = ?,      \n");
		sb.append("       trade_address = ?,  \n");
		sb.append("       mod_date = sysdate  \n");
		sb.append(" WHERE board_id = ?          ");

		Object[] args = { pbVO.getBoard_Title(), pbVO.getCategory_Id(), pbVO.getPrice(), pbVO.getBoard_Content(),
				pbVO.getHow_Trade(), pbVO.getTrade_Address(), pbVO.getBoard_Id() };

		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		flag = this.jdbcTemplate.update(sb.toString(), args);

		return flag;
	}

	@Override
	public int update_Product_Board_withImages(Product_BoardVO pbVO) throws SQLException {

		int flag = 0;

		StringBuilder sb = new StringBuilder(150);

		String board_ImagesJson = new Gson().toJson(pbVO.getBoard_Image()); // 파일명 목록을 JSON 형식으로 변환

		sb.append("UPDATE product_board       \n");
		sb.append("   SET board_title = ?,    \n");
		sb.append("       category_id = ?,    \n");
		sb.append("       price = ?,          \n");
		sb.append("       board_content = ?,  \n");
		sb.append("       how_trade = ?,      \n");
		sb.append("       trade_address = ?,  \n");
		sb.append("       board_image = ?,    \n");
		sb.append("       mod_date = sysdate  \n");
		sb.append(" WHERE board_id = ?          ");

		Object[] args = { pbVO.getBoard_Title(), pbVO.getCategory_Id(), pbVO.getPrice(), pbVO.getBoard_Content(),
				pbVO.getHow_Trade(), pbVO.getTrade_Address(), board_ImagesJson, pbVO.getBoard_Id() };

		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		flag = this.jdbcTemplate.update(sb.toString(), args);

		return flag;

	}

	@Override
	public int bumpUp_Post(int board_Id) throws SQLException {
		int flag = 0;

		StringBuilder sb = new StringBuilder(70);

		sb.append("UPDATE product_board      \n");
		sb.append("   SET mod_date = sysdate \n");
		sb.append(" WHERE board_id = ?         ");

		Object[] args = { board_Id };

		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		flag = this.jdbcTemplate.update(sb.toString(), args);

		return flag;
	}

	@Override
	public int delete_Post(int board_Id) throws SQLException {
		int flag = 0;

		StringBuilder sb = new StringBuilder(70);

		sb.append("DELETE               \n");
		sb.append("  FROM product_board \n");
		sb.append(" WHERE board_id = ?    ");

		Object[] args = { board_Id };

		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		flag = this.jdbcTemplate.update(sb.toString(), args);

		return flag;
	}

	@Override
	public int increase_View_Count(int board_Id) throws SQLException, DataAccessException {
		int flag = 0;

		StringBuilder sb = new StringBuilder(150);

		sb.append("UPDATE product_board               \n");
		sb.append("   SET view_count = view_count + 1 \n");
		sb.append(" WHERE board_id = ?                  ");

		Object[] args = { board_Id };

		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		flag = this.jdbcTemplate.update(sb.toString(), args);

		return flag;
	}

	@Override
	public List<Product_BoardVO> search_GetProducts(String searchWord, int page) throws SQLException {
		List<Product_BoardVO> productList = new ArrayList<Product_BoardVO>();

		StringBuilder sb = new StringBuilder(500);

		String searchWord_With_Percent = "%" + searchWord + "%";

		sb.append("SELECT p2.*                                             \n");
		sb.append(" FROM(                                                  \n");
		sb.append("       SELECT ROW_NUMBER() OVER (ORDER BY               \n");
		sb.append("              mod_date DESC) rnum,                      \n");
		sb.append("              p1.*                                      \n");
		sb.append("         FROM product_board p1                          \n");
		sb.append("        WHERE (p1.board_title LIKE ?                    \n");
		sb.append("           OR p1.board_content LIKE ?)                  \n");
		sb.append("     ) p2                                               \n");
		sb.append(" WHERE p2.rnum BETWEEN (20 * (? - 1)+ 1) AND (20 * ?)   \n");

		Object[] args = { searchWord_With_Percent, searchWord_With_Percent, page, page };

		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		productList = this.jdbcTemplate.query(sb.toString(), args, product_BoardMapper);

		return productList;
	}

	@Override
	public int search_GetProductsCount(String searchWord) throws SQLException {

		int count = 0;

		String searchWord_With_Percent = "%" + searchWord + "%";

		StringBuilder sb = new StringBuilder(100);

		sb.append("SELECT count(*)                  \n");
		sb.append("  FROM product_board p1          \n");
		sb.append(" WHERE (p1.board_title LIKE ?    \n");
		sb.append("     OR p1.board_content LIKE ?)   ");

		Object[] args = { searchWord_With_Percent, searchWord_With_Percent };

		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		count = this.jdbcTemplate.queryForObject(sb.toString(), args, Integer.class);

		return count;

	}

	@Override
	public List<Product_BoardVO> search_GetProducts_WithFiltering(String order, String searchWord, int board_Status,
			String trade_Address, int category_Id, int page) throws SQLException {

		List<Product_BoardVO> productList = new ArrayList<Product_BoardVO>();

		List<Object> args = new ArrayList<Object>();

		StringBuilder sb = new StringBuilder(500);

		String searchWord_With_Percent = "%" + searchWord + "%";

		sb.append("SELECT p2.*                                           \n");
		sb.append(" FROM(                                                \n");
		sb.append("       SELECT ROW_NUMBER() OVER (ORDER BY             \n");

		// 정렬순서 조건별 쿼리
		if (order.equals("mod_date_DESC")) {

			sb.append("              mod_date DESC) rnum,                    \n");

		} else if (order.equals("price_DESC")) {

			sb.append("              price DESC, mod_date DESC) rnum,                       \n");

		} else if (order.equals("price_ASC")) {

			sb.append("              price ASC, mod_date DESC) rnum,    			         \n");

		}

		sb.append("              p1.*                                    \n");
		sb.append("         FROM product_board p1                        \n");
		sb.append("        WHERE (p1.board_title LIKE ?                  \n");
		sb.append("           OR p1.board_content LIKE ?)                \n");

		args.add(searchWord_With_Percent);
		args.add(searchWord_With_Percent);

		if (board_Status == 0 || board_Status == 1) {

			sb.append("           AND board_status = ?                       \n");
			args.add(board_Status);

		}

		if (trade_Address != "") {

			sb.append("           AND trade_address LIKE ?                   \n");
			args.add(trade_Address + "%");

		}

		if (category_Id != -1) {

			sb.append("           AND category_id = ?		                 \n");
			args.add(category_Id);

		}

		sb.append("     ) p2                                             \n");
		sb.append(" WHERE p2.rnum BETWEEN (20 * (? - 1)+ 1) AND (20 * ?) \n");

		args.add(page);
		args.add(page);

		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		productList = this.jdbcTemplate.query(sb.toString(), args.toArray(), product_BoardMapper);

		return productList;
	}

	@Override
	public int search_GetProductsCount_WithFiltering(String searchWord, int board_Status, String trade_Address,
			int category_Id) throws SQLException {
		int count = 0;

		String searchWord_With_Percent = "%" + searchWord + "%";

		StringBuilder sb = new StringBuilder(100);

		List<Object> args = new ArrayList<Object>();

		sb.append("SELECT count(*)                                           \n");
		sb.append(" FROM product_board p1                                    \n");
		sb.append(" WHERE (p1.board_title LIKE ?                             \n");
		sb.append("        OR p1.board_content LIKE ?)                       \n");

		args.add(searchWord_With_Percent);
		args.add(searchWord_With_Percent);

		if (board_Status == 0 || board_Status == 1) {

			sb.append("           AND board_status = ?                       \n");
			args.add(board_Status);

		}

		if (trade_Address != "") {

			sb.append("           AND trade_address LIKE ?                   \n");
			args.add(trade_Address + "%");

		}

		if (category_Id != -1) {

			sb.append("           AND category_id = ?		                 \n");
			args.add(category_Id);

		}

		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		count = this.jdbcTemplate.queryForObject(sb.toString(), args.toArray(), Integer.class);

		return count;
	}

	@Override
	public int changeBoard_Status(int board_Id, int selected_Buyer_Id) throws SQLException {

		List<Object> args = new ArrayList<Object>();

		StringBuilder sb = new StringBuilder(500);
		
		sb.append("UPDATE product_board    \n");
		sb.append("   SET board_status = 1 \n");
		
		if(selected_Buyer_Id != -1) {
			
		sb.append("       , buyer_id = ?   \n");
		args.add(selected_Buyer_Id);
		
		}
		
		sb.append(" WHERE board_Id = ?     \n");
		args.add(board_Id);

		log.debug("1.param:");
		for (Object obj : args) {
			log.debug(obj.toString());
		}

		int flag = this.jdbcTemplate.update(sb.toString(), args.toArray());

		return flag;
	}

}
