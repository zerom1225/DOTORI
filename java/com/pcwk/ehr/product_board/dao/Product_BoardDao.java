package com.pcwk.ehr.product_board.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.pcwk.ehr.product_board.domain.Product_BoardVO;

public interface Product_BoardDao {

	int update_Product_Board_withoutImages(Product_BoardVO pbVO) throws SQLException;

	int update_Product_Board_withImages(Product_BoardVO pbVO) throws SQLException;

	int update_Board_Images(Product_BoardVO pbVO) throws SQLException;

	int doSave(Product_BoardVO pbVO) throws SQLException;

	List<Product_BoardVO> getOnsaleProducts_Mypage(int user_Id, int page) throws SQLException;

	int getOnsaleProductsCount_Mypage(int user_Id) throws SQLException;

	List<Product_BoardVO> getSoldOutProducts_Mypage(int user_Id, int page) throws SQLException;

	int getSoldOutProductsCount_Mypage(int user_Id) throws SQLException;

	List<Product_BoardVO> getPurchaseProducts_Mypage(int user_Id, int page) throws SQLException;

	int getPurchaseProductsCount_Mypage(int user_Id) throws SQLException;

	Product_BoardVO doSelectOne(int board_Id) throws SQLException, DataAccessException, NullPointerException;

	int bumpUp_Post(int board_Id) throws SQLException;

	int delete_Post(int board_Id) throws SQLException;

	int increase_View_Count(int board_Id) throws SQLException, DataAccessException;

	List<Product_BoardVO> search_GetProducts(String searchWord, int page) throws SQLException;

	int search_GetProductsCount(String searchWord) throws SQLException;

	List<Product_BoardVO> search_GetProducts_WithFiltering(String order, String searchWord, int board_Status, String trade_Address, int category_Id , int page) throws SQLException;

	int search_GetProductsCount_WithFiltering(String searchWord, int board_Status, String trade_Address, int category_Id) throws SQLException;
	
	int changeBoard_Status(int board_Id, int selected_Buyer_Id) throws SQLException;

}
