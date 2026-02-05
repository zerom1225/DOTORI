package com.pcwk.ehr.product_board.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.pcwk.ehr.product_board.dao.Product_BoardDao;
import com.pcwk.ehr.product_board.domain.Product_BoardVO;

@Service
public class Product_BoardServiceImpl implements Product_BoardService {

	final Logger log = LogManager.getLogger(getClass());

	@Autowired
	Product_BoardDao product_BoardDao;

	public Product_BoardServiceImpl() {
		super();
	}

	@Override
	public int update_Board_Images(Product_BoardVO pbVO) throws SQLException {
		return product_BoardDao.update_Board_Images(pbVO);
	}

	@Override
	public int doSave(Product_BoardVO pbVO) throws SQLException {

		return product_BoardDao.doSave(pbVO);
	}

	@Override
	public List<Product_BoardVO> getOnsaleProducts_Mypage(int user_Id, int page) throws SQLException {

		return product_BoardDao.getOnsaleProducts_Mypage(user_Id, page);
	}

	@Override
	public int getOnsaleProductsCount_Mypage(int user_Id) throws SQLException {
		return product_BoardDao.getOnsaleProductsCount_Mypage(user_Id);
	}

	@Override
	public List<Product_BoardVO> getSoldOutProducts_Mypage(int user_Id, int page) throws SQLException {
		return product_BoardDao.getSoldOutProducts_Mypage(user_Id, page);
	}

	@Override
	public int getSoldOutProductsCount_Mypage(int user_Id) throws SQLException {
		return product_BoardDao.getSoldOutProductsCount_Mypage(user_Id);
	}

	@Override
	public List<Product_BoardVO> getPurchaseProducts_Mypage(int user_Id, int page) throws SQLException {
		return product_BoardDao.getPurchaseProducts_Mypage(user_Id, page);
	}

	@Override
	public int getPurchaseProductsCount_Mypage(int user_Id) throws SQLException {
		return product_BoardDao.getPurchaseProductsCount_Mypage(user_Id);
	}

	@Override
	public Product_BoardVO doSelectOne(int board_Id) throws SQLException, DataAccessException, NullPointerException {
		return product_BoardDao.doSelectOne(board_Id);
	}

	@Override
	public int update_Product_Board(Product_BoardVO pbVO) throws SQLException {

		if (pbVO.getBoard_Image() == null) {
			
			return product_BoardDao.update_Product_Board_withoutImages(pbVO);
			
		} else {
			
			return product_BoardDao.update_Product_Board_withImages(pbVO);
			
		}

	}

	@Override
	public int bumpUp_Post(int board_Id) throws SQLException {
		
		return product_BoardDao.bumpUp_Post(board_Id);
	}

	@Override
	public int delete_Post(int board_Id) throws SQLException {
		
		return product_BoardDao.delete_Post(board_Id);
	}

	@Override
	public int increase_View_Count(int board_Id) throws SQLException, DataAccessException {
		return product_BoardDao.increase_View_Count(board_Id);
	}

	@Override
	public List<Product_BoardVO> search_GetProducts(String searchWord, int page) throws SQLException {
		// TODO Auto-generated method stub
		return product_BoardDao.search_GetProducts(searchWord, page);
	}

	@Override
	public int search_GetProductsCount(String searchWord) throws SQLException {
		return product_BoardDao.search_GetProductsCount(searchWord);
	}

	@Override
	public List<Product_BoardVO> search_GetProducts_WithFiltering(String order, String searchWord, int board_Status,
			String trade_Address, int category_Id, int page) throws SQLException {
		return product_BoardDao.search_GetProducts_WithFiltering(order, searchWord, board_Status, trade_Address, category_Id, page);
	}

	@Override
	public int search_GetProductsCount_WithFiltering(String searchWord, int board_Status, String trade_Address,
			int category_Id) throws SQLException {
		return product_BoardDao.search_GetProductsCount_WithFiltering(searchWord, board_Status, trade_Address, category_Id);
	}

	@Override
	public int changeBoard_Status(int board_Id, int selected_Buyer_Id) throws SQLException {
		return product_BoardDao.changeBoard_Status(board_Id, selected_Buyer_Id);
	}

}
