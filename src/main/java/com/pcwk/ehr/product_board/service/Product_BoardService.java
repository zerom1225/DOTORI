package com.pcwk.ehr.product_board.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.pcwk.ehr.product_board.domain.Product_BoardVO;

public interface Product_BoardService {

	/**
	 * 게시글 이미지 업데이트
	 * 
	 * @param pbVO
	 * @return 1(성공) / 0(실패)
	 * @throws SQLException
	 */
	int update_Board_Images(Product_BoardVO pbVO) throws SQLException;

	/**
	 * 게시글 등록
	 * 
	 * @param pbVO
	 * @return 1(성공) / 0(실패)
	 * @throws SQLException
	 */
	int doSave(Product_BoardVO pbVO) throws SQLException;
	
	/**
	 * 게시글 수정
	 * 이미지가 NULL => 이미지를 제외한 정보 업데이트
	 * 이미지가 NOT NULL => 이미지를 포함한 정보 업데이트
	 * @param pbVO
	 * @return
	 * @throws SQLException
	 */
	int update_Product_Board(Product_BoardVO pbVO) throws SQLException;
	
	/**
	 * 게시글 끌어올리기 기능
	 * @param board_Id
	 * @return 1(성공) / 0(실패)
	 * @throws SQLException
	 */
	int bumpUp_Post(int board_Id) throws SQLException;
	
	/**
	 * 게시글 삭제 기능
	 * @param board_Id
	 * @return 1(성공) / 0(실패)
	 * @throws SQLException
	 */
	int delete_Post(int board_Id) throws SQLException;

	/**
	 * 판매 중인 상품 조회
	 * 
	 * @param user_Id
	 * @param page
	 * @return List<Product_BoardVO>
	 * @throws SQLException
	 */
	List<Product_BoardVO> getOnsaleProducts_Mypage(int user_Id, int page) throws SQLException;

	/**
	 * 판매 중인 상품 총 갯수 조회
	 * 
	 * @param user_Id
	 * @return count
	 * @throws SQLException
	 */
	int getOnsaleProductsCount_Mypage(int user_Id) throws SQLException;

	/**
	 * 판매 완료된 상품 조회
	 * 
	 * @param user_Id
	 * @param page
	 * @return List<Product_BoardVO>
	 * @throws SQLException
	 */
	List<Product_BoardVO> getSoldOutProducts_Mypage(int user_Id, int page) throws SQLException;

	/**
	 * 판매 완료된 상품 총 갯수 조회
	 * 
	 * @param user_Id
	 * @return count
	 * @throws SQLException
	 */
	int getSoldOutProductsCount_Mypage(int user_Id) throws SQLException;

	/**
	 * 구매 완료된 상품 조회
	 * 
	 * @param user_Id
	 * @param page
	 * @return List<Product_BoardVO>
	 * @throws SQLException
	 */
	List<Product_BoardVO> getPurchaseProducts_Mypage(int user_Id, int page) throws SQLException;

	/**
	 * 구매 완료된 상품 총 갯수 조회
	 * 
	 * @param user_Id
	 * @return count
	 * @throws SQLException
	 */
	int getPurchaseProductsCount_Mypage(int user_Id) throws SQLException;
	
	/**
	 * 검색어로 상품 조회
	 * @param searchWord
	 * @param page
	 * @return List<Product_BoardVO>
	 * @throws SQLException
	 */
	List<Product_BoardVO> search_GetProducts(String searchWord, int page) throws SQLException; 
	
	/**
	 * 검색어로 조회된 상품 총 갯수 조회
	 * @param searchWord
	 * @return count
	 * @throws SQLException
	 */
	int search_GetProductsCount(String searchWord) throws SQLException;
	
	/**
	 * 필터링과 함게 검색어로 상품 조회
	 * @param order
	 * @param searchWord
	 * @param board_Status
	 * @param trade_Address
	 * @param category_Id
	 * @param page
	 * @return List<Product_BoardVO>
	 * @throws SQLException
	 */
	List<Product_BoardVO> search_GetProducts_WithFiltering(String order, String searchWord, int board_Status, String trade_Address, int category_Id , int page) throws SQLException;

	/**
	 * 필터링과 함게 검색어로 조회된 상품 총 갯수 조회
	 * @param searchWord
	 * @param board_Status
	 * @param trade_Address
	 * @param category_Id
	 * @return count
	 * @throws SQLException
	 */
	int search_GetProductsCount_WithFiltering(String searchWord, int board_Status, String trade_Address, int category_Id) throws SQLException;
	
	/**
	 * board_Id로 단건 조회
	 * @param board_Id
	 * @return Product_BoardVO
	 * @throws SQLException
	 * @throws DataAccessException
	 */
	Product_BoardVO doSelectOne(int board_Id) throws SQLException, DataAccessException, NullPointerException;
	
	/**
	 * board_Id로 조회수 +1 갱신
	 * @param board_Id
	 * @return 1(성공) / 0(실패)
	 * @throws SQLException
	 * @throws DataAccessException
	 */
	int increase_View_Count(int board_Id) throws SQLException, DataAccessException;
	
	/**
	 * 판매 완료 상태로 변경
	 * @param board_Id
	 * @param selected_Buyer_Id
	 * @return 1(성공)/0(실패)
	 * @throws SQLException
	 */
	int changeBoard_Status(int board_Id, int selected_Buyer_Id) throws SQLException;

}
