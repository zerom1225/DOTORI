package com.pcwk.ehr.product_board.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.MessageVO;
import com.pcwk.ehr.cmn.productBoard_Cmn;
import com.pcwk.ehr.product_board.domain.Product_BoardVO;
import com.pcwk.ehr.product_board.service.Product_BoardService;
import com.pcwk.ehr.user.domain.UserVO;
import com.pcwk.ehr.user.service.UserService;

@Controller
public class Product_BoardController {

	final Logger log = LogManager.getLogger(getClass());

	@Qualifier("product_BoardServiceImpl")
	@Autowired
	private Product_BoardService product_BoardService;

	@Qualifier("userServiceImpl")
	@Autowired
	private UserService userService;

	public Product_BoardController() {
		super();
		log.debug("┌───────────────────────────────────────┐");
		log.debug("│    **product_BoardService() 생성**     │");
		log.debug("└───────────────────────────────────────┘");
	}

	// 게시글 등록
	@RequestMapping(value = "/product_board/post.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String post_Product_Board(HttpSession session, @ModelAttribute Product_BoardVO pbVO,
			@RequestParam("board_Images") MultipartFile[] board_Images) {

		log.debug("post_Product_Board 호출");

		String message = "";
		int flag = 0;

		UserVO user = (UserVO) session.getAttribute("user");

		try {

			List<String> fileNames = new ArrayList<String>();
			// 1. 파일 저장 경로 설정
			String uploadDir = "C:/resources/board_image/"; // 유저 이미지 파일 저장 경로

			for (MultipartFile file : board_Images) {

				// 고유 파일명_원본파일명 으로 저장(중복 방지)
				String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

				File dest = new File(uploadDir + fileName); // 실제 저장 경로 지정

				// 2. 파일 저장
				file.transferTo(dest);

				fileNames.add(fileName);

			}

			log.debug("fileNames 리스트 : {}", fileNames.toString());

			// 3. DB에 업데이트
			pbVO.setSeller_Id(user.getUser_Id());
			pbVO.setBoard_Image(fileNames);

			log.debug("pbVO : {}", pbVO.toString());
			flag = product_BoardService.doSave(pbVO); // DB 업데이트 호출

			if (flag == 1) {
				message = "게시글 등록 성공";
			} else {
				message = "DB에 게시글 등록 중 오류가 발생하였습니다.";
			}

		} catch (IOException e) {
			return new Gson().toJson(new MessageVO(0, "서버에 파일 저장 중 오류가 발생하였습니다."));

		} catch (Exception e) {
			log.debug("예외 발생");
			return new Gson().toJson(new MessageVO(0, "게시글 등록 중 오류 발생"));
		}

		log.debug("예외 발생 안했는데?");
		return new Gson().toJson(new MessageVO(flag, message));

	}

	// 이미지 업데이트
	@RequestMapping(value = "/product_board/uploadBoard_Images.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String uploadBoard_Images(HttpSession session, @RequestParam("board_Images") MultipartFile[] board_Images) {

		log.debug("uploadBoard_Images 호출");

		String message = "";
		int flag = 0;

		UserVO user = (UserVO) session.getAttribute("user");

		Product_BoardVO pbVO = new Product_BoardVO();

		try {

			List<String> fileNames = new ArrayList<String>();
			// 1. 파일 저장 경로 설정
			String uploadDir = "C:/resources/board_image/"; // 유저 이미지 파일 저장 경로

			for (MultipartFile file : board_Images) {

				// 고유 파일명_원본파일명 으로 저장(중복 방지)
				String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

				File dest = new File(uploadDir + fileName); // 실제 저장 경로 지정

				// 2. 파일 저장
				file.transferTo(dest);

				fileNames.add(fileName);

			}

			log.debug("fileNames 리스트 : {}", fileNames.toString());

			// 3. DB에 업데이트
			pbVO.setBoard_Id(1);
			pbVO.setBoard_Image(fileNames);
			flag = product_BoardService.update_Board_Images(pbVO); // DB 업데이트 호출

			if (flag == 1) {
				message = "이미지 업데이트 성공";
			} else {
				message = "DB에 파일 이미지 업데이트 중 오류가 발생하였습니다.";
			}

		} catch (IOException e) {
			return new Gson().toJson(new MessageVO(0, "서버에 파일 저장 중 오류가 발생하였습니다."));

		} catch (Exception e) {
			log.debug("예외 발생");
			return new Gson().toJson(new MessageVO(0, "이미지 업데이트 중 오류 발생"));
		}

		log.debug("예외 발생 안했는데?");
		return new Gson().toJson(new MessageVO(flag, message));

	}

	@RequestMapping(value = "/product_board/check_poster.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> check_Poster(@RequestParam("board_Id") int board_Id, HttpSession session) {

		Map<String, Object> response = new HashMap<String, Object>();

		UserVO user = (UserVO) session.getAttribute("user");

		Product_BoardVO pbVO;

		try {
			// 단건 조회
			pbVO = product_BoardService.doSelectOne(board_Id);
		} catch (DataAccessException | SQLException e) {
			response.put("error", "DB 예외 발생");
			return response;
		}

		if (pbVO == null) {
			response.put("error", "상품을 찾을 수 없습니다");
			return response;
		}

		// 현재 세션이 유저와 게시자 비교
		boolean isOwner = user != null && (pbVO.getSeller_Id() == user.getUser_Id());

		response.put("isOwner", isOwner); // 현재 세션이 유저와 게시자 비교값
		response.put("board_Id", board_Id); // 게시글 ID

		return response;

	}

	@RequestMapping(value = "/product_board/edit={board_Id}.do", method = RequestMethod.GET)
	public String edit_Post(@PathVariable int board_Id, Model model) {

		Product_BoardVO pbVO;
		String compare_Now;
		int view_Count;

		try {
			pbVO = product_BoardService.doSelectOne(board_Id);
		} catch (DataAccessException | SQLException e) {
			log.debug("게시글 수정 DB 접근 중 오류 발생");
			model.addAttribute("error", "DB 접근 중 오류 발생");
			return "/main";
		}

		if (pbVO == null) {
			log.debug("일치하는 게시글이 없음");
			model.addAttribute("error", "해당 게시글을 찾을 수 없습니다.");
			return "/main";
		}
		
		compare_Now = productBoard_Cmn.compare_Now(pbVO.getMod_Date());
		view_Count = pbVO.getView_Count();

		model.addAttribute("viewCount_And_Time", "조회수 " + view_Count + " · " + compare_Now);
		model.addAttribute("productBoard", pbVO);

		return "/product_board/edit";
	}

	// 게시글 수정
	@RequestMapping(value = "/product_board/update.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String update_Product_Board(HttpSession session, @ModelAttribute Product_BoardVO pbVO,
			@RequestParam(value = "board_Images", required = false) MultipartFile[] board_Images) {

		log.debug("update_Product_Board 호출");

		String message = "";
		int flag = 0;

		UserVO user = (UserVO) session.getAttribute("user");

		try {

			if (board_Images != null) {

				List<String> fileNames = new ArrayList<String>();
				// 1. 파일 저장 경로 설정
				String uploadDir = "C:/resources/board_image/"; // 게시글 이미지 파일 저장 경로

				for (MultipartFile file : board_Images) {

					// 고유 파일명_원본파일명 으로 저장(중복 방지)
					String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

					File dest = new File(uploadDir + fileName); // 실제 저장 경로 지정

					// 2. 파일 저장
					file.transferTo(dest);

					fileNames.add(fileName);

				}

				log.debug("fileNames 리스트 : {}", fileNames.toString());

				pbVO.setBoard_Image(fileNames);

			} // if -- END

			// 3. DB에 업데이트
			log.debug("pbVO : {}", pbVO.toString());

			flag = product_BoardService.update_Product_Board(pbVO); // DB 업데이트 호출

			if (flag == 1) {
				message = "게시글 수정 성공";
			} else {
				message = "DB에 게시글 수정 중 오류가 발생하였습니다.";
			}

		} catch (IOException e) {
			return new Gson().toJson(new MessageVO(0, "서버에 파일 저장 중 오류가 발생하였습니다."));

		} catch (Exception e) {
			log.debug("예외 발생");
			return new Gson().toJson(new MessageVO(0, "게시글 수정 중 오류 발생"));
		}

		log.debug("예외 발생 안했는데?");
		return new Gson().toJson(new MessageVO(flag, message));

	}

	@RequestMapping(value = "/product_board/bump_up.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String bumpUp_Post(@RequestParam("board_Id") int board_Id) {

		String message;
		int flag;

		try {

			flag = product_BoardService.bumpUp_Post(board_Id);
			message = "끌어올리기 성공!";

		} catch (Exception e) {
			message = "DB 접근 중 예외 발생";
			flag = 0;
		}

		return new Gson().toJson(new MessageVO(flag, message));

	}

	@RequestMapping(value = "/product_board/delete.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String delete_Post(@RequestParam("board_Id") int board_Id) {

		String message;
		int flag;

		try {

			flag = product_BoardService.delete_Post(board_Id);
			message = "삭제가 완료되었습니다";

		} catch (Exception e) {
			message = "DB 접근 중 예외 발생";
			flag = 0;
		}

		return new Gson().toJson(new MessageVO(flag, message));

	}

	@RequestMapping(value = "/product_board/detail={board_Id}.do", method = RequestMethod.GET)
	public String detail_Post(@PathVariable int board_Id, Model model) {

		Product_BoardVO pbVO; // 게시글 객체
		UserVO user; // 판매자 객체
		String category_Name; // 카테고리 이름
		String compare_Now; //
		String how_Trade_String;
		String user_Munic_Address;
		int chat_room_Count = 0;
		int increased_View_Count;

		// 게시글 조회
		try {
			pbVO = product_BoardService.doSelectOne(board_Id);

		} catch (DataAccessException | SQLException e) {
			log.debug("게시글 검색 DB 접근 중 오류 발생");
			model.addAttribute("error", " 게시글 검색 DB 접근 중 오류 발생");
			return "/main";
		} catch (NullPointerException e) {
			log.debug("일치하는 게시글이 없음");
			model.addAttribute("error", "해당 게시글을 찾을 수 없습니다.");
			return "/main";
		}

		// 판매자 검색
		try {
			user = userService.doSelectOne_With_User_Id(pbVO.getSeller_Id());
		} catch (DataAccessException | SQLException e) {
			log.debug("판매자 검색 DB 접근 중 오류 발생");
			model.addAttribute("error", "판매자 검색 DB 접근 중 오류 발생");
			return "/main";
		}

		try {
			int flag = product_BoardService.increase_View_Count(board_Id);

			if (flag != 1) {
				log.debug("조회수 갱신 실패");
				model.addAttribute("error", "조회수 갱신 실패");
				return "/main";
			}

		} catch (DataAccessException | SQLException e) {
			log.debug("조회수 갱신  DB 접근 중 오류 발생");
			model.addAttribute("error", "조회수 갱신 DB 접근 중 오류 발생");
			return "/main";
		}

		category_Name = productBoard_Cmn.Catecory_Id_To_Name(pbVO.getCategory_Id());
		compare_Now = productBoard_Cmn.compare_Now(pbVO.getMod_Date());
		how_Trade_String = productBoard_Cmn.how_Trade_ToString(pbVO.getHow_Trade());
		user_Munic_Address = productBoard_Cmn.convertMunic_Address(user.getAddress());
		increased_View_Count = pbVO.getView_Count() + 1;

		model.addAttribute("productBoard", pbVO);
		model.addAttribute("user", user);
		model.addAttribute("category_And_Time", category_Name + " · " + compare_Now);
		model.addAttribute("how_Trade_String", how_Trade_String);
		model.addAttribute("chat_And_ViewCount", "채팅 " + chat_room_Count + " · " + "조회수 " + increased_View_Count);
		model.addAttribute("user_Nickname", user.getNickname() + "#" + user.getUser_Id());
		model.addAttribute("user_Munic_Address", user_Munic_Address);

		return "/product_board/detail";
	}

	@RequestMapping(value = "/product_board/search/getproducts.do", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String search_GetProducts(@RequestParam("searchWord") String searchWord,
			@RequestParam(value = "page", defaultValue = "1") int page) {

		Map<String, Object> response = new HashMap<String, Object>();

		List<Product_BoardVO> productList = new ArrayList<Product_BoardVO>();

		int totalItems = 0;

		try {

			productList = product_BoardService.search_GetProducts(searchWord, page);
			totalItems = product_BoardService.search_GetProductsCount(searchWord);

		} catch (SQLException e) {
			response.put("error", "검색어 기반 상품 조회 중 DB 예외 발생");
		} catch (Exception e) {
			response.put("error", "검색어 기반 상품 조회 중 예기치 않은 오류 발생");
		}

		response.put("products", productList);
		response.put("totalItems", totalItems);
		response.put("page", page);

		return new Gson().toJson(response);

	}

	@RequestMapping(value = "/product_board/search_with_filtering/getproducts.do", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String search_GetProducts_WithFiltering(
			@RequestParam(value = "order", defaultValue = "mod_date_DESC") String order,
			@RequestParam("searchWord") String searchWord,
			@RequestParam(value = "board_Status", defaultValue = "-1") int board_Status,
			@RequestParam(value = "trade_Address", required = false) String trade_Address,
			@RequestParam(value = "category_Id", defaultValue = "-1") int category_Id,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		
		log.debug("search_GetProducts_WithFiltering() 실행");
		log.debug("order : {}", order );
		log.debug("searchWord : {}", searchWord );
		log.debug("board_Status : {}", board_Status );
	    log.debug("trade_Address : {}", trade_Address );			
		log.debug("category_Id : {}", category_Id );
		log.debug("page : {}", page );

		Map<String, Object> response = new HashMap<String, Object>();

		List<Product_BoardVO> productList = new ArrayList<Product_BoardVO>();

		int totalItems = 0;

		try {

			productList = product_BoardService.search_GetProducts_WithFiltering(order, searchWord, board_Status, trade_Address, category_Id, page);
			totalItems = product_BoardService.search_GetProductsCount_WithFiltering(searchWord, board_Status, trade_Address, category_Id);

		} catch (SQLException e) {
			response.put("error", "필터링 검색어 기반 상품 조회 중 DB 예외 발생");
		} catch (Exception e) {
			response.put("error", "필터링 검색어 기반 상품 조회 중 예기치 않은 오류 발생");
		}

		response.put("products", productList);
		response.put("totalItems", totalItems);
		response.put("page", page);

		return new Gson().toJson(response);

	}
	
	@RequestMapping(value = "/product_board/change_board_status.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String changeBoard_Status(@RequestParam("board_Id") int board_Id, @RequestParam("selected_Buyer_Id") int selected_Buyer_Id) {
		
		log.debug("changeBoard_Status 접근");
		log.debug("board_Id : {}", board_Id);
		log.debug("selected_Buyer_Id : {}", selected_Buyer_Id);
		
		int flag;
		
		try {
			flag = product_BoardService.changeBoard_Status(board_Id, selected_Buyer_Id);
		} catch (SQLException e) {
			return new Gson().toJson(new MessageVO(0, "판매 완료 상태로 변경 중 DB 예외 발생"));
		}
		
		return new Gson().toJson(new MessageVO(flag, "판매 완료 상태로 변경되었습니다."));		
		
	}

}
