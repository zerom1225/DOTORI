package com.pcwk.ehr.user.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import com.google.gson.Gson;
import com.pcwk.ehr.cmn.MessageVO;
import com.pcwk.ehr.email.domain.EmailVO;
import com.pcwk.ehr.email.service.EmailService;
import com.pcwk.ehr.product_board.domain.Product_BoardVO;
import com.pcwk.ehr.product_board.service.Product_BoardService;
import com.pcwk.ehr.user.domain.UserVO;
import com.pcwk.ehr.user.service.UserService;

@Controller
public class UserController {

	final Logger log = LogManager.getLogger(getClass());

//	@Qualifier("resourceHttpRequestHandler")
//	@Autowired
//	private ResourceHttpRequestHandler resourceHttpRequestHandler;

	@Qualifier("userServiceImpl")
	@Autowired
	private UserService userService;

	@Qualifier("emailServiceImpl")
	@Autowired
	private EmailService emailService;

	@Qualifier("product_BoardServiceImpl")
	@Autowired
	private Product_BoardService product_BoardService;

	public UserController() {
		super();
		log.debug("┌───────────────────────────────────────┐");
		log.debug("│    **UserController() 생성**           │");
		log.debug("└───────────────────────────────────────┘");

	}

	// 이메일 업데이트
	@RequestMapping(value = "/user/emailupdate.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String emailUpdate(HttpSession session, @RequestBody Map<String, String> jsonData) throws SQLException {

		log.debug("doUpdate() 실행 ");

		String message;
		String new_Email = jsonData.get("new_Email"); // 새 이메일
		String auth_code = jsonData.get("auth_code"); // 인증코드
		UserVO user = (UserVO) session.getAttribute("user"); // 현재 세션의 유저 객체
		String old_Email = user.getEmail(); // 기존 이메일
		int flag;
		EmailVO emailVO;

		log.debug(user.toString());
		log.debug(new_Email);
		log.debug(auth_code);

		// 1. 새 이메일 중복 체크
		if (userService.isExistsEmail(new_Email) == 1) {
			return new Gson().toJson(new MessageVO(0, "이미 사용 중인 이메일 입니다."));
		}

		// 2.1 email_auth 테이블에서 이메일 검색
		try {
			emailVO = emailService.doSelectOne(new EmailVO(new_Email));
		} catch (DataAccessException e) {
			message = "인증 코드를 획득하세요.";
			return new Gson().toJson(new MessageVO(0, message));
		}

		// 2.2 인증번호 검증
		// 입력 받은 인증코드와 발송한 인증코드 비교 검증
		if (!auth_code.equals(emailVO.getAuth_code())) {
			message = "인증 코드가 올바르지 않습니다.";
			return new Gson().toJson(new MessageVO(0, message));
		}

		user.setNew_Email(new_Email);

		flag = userService.doUpdate_Email(user);

		if (flag == 1) {
			message = "이메일이 성공적으로 변경되었습니다.";
			user.changeEmail();
			emailService.doUpdate_Is_Registered_1(user.getEmail());
			emailService.doUpdate_Is_Registered_0(old_Email);
			log.debug("변경된 user : {}", user.toString());
			session.setAttribute("user", user);
		} else {
			message = "이메일 변경 중 오류가 발생하였습니다.";
		}

		return new Gson().toJson(new MessageVO(flag, message));

	}

	// 회원 정보 수정
	@RequestMapping(value = "/otherUpdate.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String otherUpdateSave(@RequestBody UserVO param, HttpSession session) throws SQLException {

		log.debug("otherUpdate.do 실행");

		String message = "";
		int flag_phone;
		int flag_Update;
		UserVO user = (UserVO) session.getAttribute("user");

		// 1. 전화번호 중복 체크
		if (!param.getPhone().equals(user.getPhone())) {
			flag_phone = userService.isExistsPhone(param.getPhone());
			if (flag_phone != 0) {
				message = "이미 사용 중인 전화번호 입니다.";
				return new Gson().toJson(new MessageVO(0, message));
			}

		}

		// 2. 회원 수정

		user.setName(param.getName()); // 이름 업데이트
		user.setPhone(param.getPhone()); // 전화번호 업데이트
		user.setNickname(param.getNickname()); // 닉네임 업데이트
		user.setAddress(param.getAddress()); // 주소 업데이트

		flag_Update = userService.doUpdate_Other(user);
		if (flag_Update == 1) {
			message = "회원 정보가 수정되었습니다.";
			log.debug("업데이트 후 회원정보 : {}", user.toString());
			session.setAttribute("user", user);
		} else {
			message = "회원 정보 수정 중 에러가 발생했습니다.";
		}

		return new Gson().toJson(new MessageVO(flag_Update, message));
	}

	// 회원 이미지 업데이트
	@RequestMapping(value = "/user/imageupdate.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String user_Image_Update(HttpSession session, @RequestParam("user_Image") MultipartFile user_Image) {

		String message;
		int flag;
		UserVO user = (UserVO) session.getAttribute("user");

		if (user == null) {
			return new Gson().toJson(new MessageVO(0, "로그인이 필요합니다."));
		}

		try {

			// 1. 파일 저장 경로 설정
			String uploadDir = "C:/resources/user_image/"; // 유저 이미지 파일 저장 경로
			String fileName = UUID.randomUUID().toString() + "_" + user_Image.getOriginalFilename(); // 고유 파일명_원본 파일명 으로
																										// 저장(중복 방지)
			File dest = new File(uploadDir + fileName); // 실제 저장 경로 지정

			// 2. 파일 저장
			user_Image.transferTo(dest);

			// 3. DB에 업데이트
			user.setUser_Image(fileName); // user에 프로필 이미지 경로 저장
			flag = userService.doUpdate_User_Image(user); // DB 업데이트 호출

			session.setAttribute("user", user);
			if (flag == 1) {
				message = "이미지 업데이트 성공";
			} else {
				message = "DB에 파일 이미지 업데이트 중 오류가 발생하였습니다.";
			}

		} catch (IOException e) {
			return new Gson().toJson(new MessageVO(0, "서버에 파일 저장 중 오류가 발생하였습니다."));

		} catch (Exception e) {
			return new Gson().toJson(new MessageVO(0, "이미지 업데이트 중 오류 발생"));
		}

		return new Gson().toJson(new MessageVO(1, message));
	}

	@RequestMapping(value = "/mypage/get_products{tabIndex}.do", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String get_Mypage_Products(@PathVariable int tabIndex,
			@RequestParam(value = "page", defaultValue = "1") int page, HttpSession session) {

		UserVO user = (UserVO) session.getAttribute("user");

		int user_Id = user.getUser_Id();

		List<Product_BoardVO> productList = new ArrayList<Product_BoardVO>();

		int totalItems = 0;

		try {

			switch (tabIndex) {
			case 0: // 판매중인 상품 탭 목록
				productList = product_BoardService.getOnsaleProducts_Mypage(user_Id, page);
				totalItems = product_BoardService.getOnsaleProductsCount_Mypage(user_Id);
				break;
			case 1: // 판매완료 된 상품 탭 목록
				productList = product_BoardService.getSoldOutProducts_Mypage(user_Id, page);
				totalItems = product_BoardService.getSoldOutProductsCount_Mypage(user_Id);
				break;
			case 2: // 구매완료 된 상품 탭 목록
				productList = product_BoardService.getPurchaseProducts_Mypage(user_Id, page);
				totalItems = product_BoardService.getOnsaleProductsCount_Mypage(user_Id);
				break;
			}
		} catch (SQLException e) {
			
		} 

		Map<String, Object> response = new HashMap<String, Object>();
		
		response.put("products", productList);
		response.put("totalItems", totalItems);
		response.put("page", page);
		
		Gson gson = new Gson();
		
		return gson.toJson(response);
	}

	// 회원가입
	@RequestMapping(value = "/register.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String RegisterSave(@RequestBody UserVO param) throws SQLException {

		log.debug("**회원가입 컨트롤러 실행**");

		String message = "";
		int flag_email;
		int flag_phone;
		int flag_doSave;
		EmailVO emailVO;

		// 1. 이메일 체크
		flag_email = userService.isExistsEmail(param.getEmail());
		if (flag_email != 0) {
			message = "이미 사용 중인 이메일 입니다.";
			return new Gson().toJson(new MessageVO(0, message));
		}

		// 2.1 인증번호 검색
		try {
			emailVO = emailService.doSelectOne(new EmailVO(param.getEmail()));
		} catch (NullPointerException e) {
			message = "인증 코드를 획득하세요.";
			return new Gson().toJson(new MessageVO(0, message));
		}

		// 2.2 인증번호 검증
		if (!param.getAuth_code().equals(emailVO.getAuth_code())) {
			message = "인증 코드가 올바르지 않습니다.";
			return new Gson().toJson(new MessageVO(0, message));
		}

		// 3. 전화 번호 체크
		flag_phone = userService.isExistsPhone(param.getPhone());
		if (flag_phone != 0) {
			message = "이미 사용 중인 전화번호 입니다.";
			return new Gson().toJson(new MessageVO(0, message));
		}

		// 4. 회원 등록
		flag_doSave = userService.doSave(param);
		if (flag_doSave == 1) {
			emailService.doUpdate_Is_Registered_1(param.getEmail());
			message = "회원가입이 완료되었습니다.";
		} else {
			message = "회원가입 중 에러가 발생했습니다.";
		}

		return new Gson().toJson(new MessageVO(flag_doSave, message));
	}

	// 로그인
	@RequestMapping(value = "/login.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String doLogin(@RequestBody UserVO param, HttpServletRequest req) throws SQLException {

		UserVO user;
		EmailVO emailVO;
		String message;

		// 1. 이메일 존재 여부 검사
		if (userService.isExistsEmail(param.getEmail()) != 1) {
			return new Gson().toJson(new MessageVO(0, "존재하지 않는 이메일 입니다."));
		}

		// 2.1 인증번호 검색
		try {
			emailVO = emailService.doSelectOne(new EmailVO(param.getEmail()));
		} catch (NullPointerException e) {
			message = "인증 코드를 획득하세요.";
			return new Gson().toJson(new MessageVO(0, message));
		}

		// 2.2 인증번호 검증
		if (!param.getAuth_code().equals(emailVO.getAuth_code())) {
			message = "인증 코드가 올바르지 않습니다.";
			return new Gson().toJson(new MessageVO(0, message));
		}

		try {

			user = userService.doSelectOne(param);
			HttpSession session = req.getSession();
			session.setAttribute("user", user);

		} catch (NullPointerException e) {
			return new Gson().toJson(new MessageVO(0, "회원 조회 중 오류가 발생하였습니다."));
		}

		return new Gson().toJson(new MessageVO(1, "로그인 성공"));
	}

	// 전화번호 & 이름 으로 이메일 찾기
	@RequestMapping(value = "/find_email.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String findEmail(@RequestBody UserVO param) throws SQLException {

		String email;

		log.debug("이메일 찾기 컨트롤러 실행");
		log.debug("이름 : {}", param.getName());
		log.debug("전화번호 : {}", param.getPhone());

		try {

			email = userService.doSelectEmail_By_Name_Phone(param);

		} catch (DataAccessException e) {

			return new Gson().toJson(new MessageVO(0, "일치하는 이메일이 없습니다."));
		}

		return new Gson().toJson(new MessageVO(1, email));
	}

}
