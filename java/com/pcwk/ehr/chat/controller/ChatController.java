package com.pcwk.ehr.chat.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pcwk.ehr.chat.domain.ChatDTO;
import com.pcwk.ehr.chat.domain.ChatVO;
import com.pcwk.ehr.chat.service.ChatService;
import com.pcwk.ehr.chat_message.domain.Chat_MessageVO;
import com.pcwk.ehr.chat_message.service.Chat_MessageService;
import com.pcwk.ehr.cmn.MessageVO;
import com.pcwk.ehr.product_board.service.Product_BoardService;
import com.pcwk.ehr.user.domain.UserVO;
import com.pcwk.ehr.user.service.UserService;

@Controller
public class ChatController {

	final Logger log = LogManager.getLogger(getClass());

	@Qualifier("product_BoardServiceImpl")
	@Autowired
	private Product_BoardService product_BoardService;

	@Qualifier("userServiceImpl")
	@Autowired
	private UserService userService;

	@Qualifier("chatServiceImpl")
	@Autowired
	private ChatService chatService;

	@Qualifier("chat_MessageServiceImpl")
	@Autowired
	private Chat_MessageService chat_MessageService;

	public ChatController() {
		super();
		log.debug("┌───────────────────────────────────────┐");
		log.debug("│    **ChatController() 생성**           │");
		log.debug("└───────────────────────────────────────┘");
	}

	// 채팅방 리스트를 보여주는 컨트롤러
	@RequestMapping(value = "/chat/chatList.do", method = RequestMethod.GET)
	public String show_ChatList(HttpSession session, Model model) {

		List<ChatDTO> chatList = new ArrayList<ChatDTO>();
		UserVO user = (UserVO) session.getAttribute("user");

		if (user == null) {
			return "/user/login/login";
		}

		try {
			chatList = chatService.getActiveChatList(user);
		} catch (DataAccessException | SQLException e) {
			e.printStackTrace();
		}

		model.addAttribute("current_User", new Gson().toJson(user));
		model.addAttribute("chatList", new Gson().toJson(chatList));

		return "/chat/chatList";

	}

	// 채팅방 화면을 띄울 때, 해당 채팅방의 이전 메세지들도 함께 불러오기
	@RequestMapping(value = "/chat/{chat_Id}.do", method = RequestMethod.GET)
	public String show_ChatRoom(@PathVariable int chat_Id, Model model, HttpSession session) {

		List<Chat_MessageVO> chat_Messages = new ArrayList<Chat_MessageVO>();
		Gson gson = new Gson();

		UserVO user = (UserVO) session.getAttribute("user");
		ChatDTO chatDTO;
		ChatVO chatVO;

		if (user == null) {
			return "/main";
		}

		try {
			chatVO = chatService.doSelectOne_ChatVO(chat_Id);

			if (chatVO == null) {
				log.debug("채팅 없음");
				return "/main";
			}

		} catch (DataAccessException | SQLException e2) {
			model.addAttribute("error", "DB 예외");
			return "/main";
		}

		try {
			chatDTO = chatService.doSelectOne(chat_Id);
			if (chatDTO == null) {
				model.addAttribute("error", "채팅방을 찾을 수 없습니다.");
				return "/main";
			}
		} catch (DataAccessException | SQLException e1) {
			e1.printStackTrace();
			return "/main";
		}

		try {
			chat_Messages = chat_MessageService.getChat_Messages(chat_Id);
		} catch (DataAccessException | SQLException e) {
			e.printStackTrace();
			return "/main";
		}

		if (user.getUser_Id() == chatDTO.getSeller().getUser_Id()) {
			model.addAttribute("nickname", chatDTO.getBuyer().getNickname());
		} else if (user.getUser_Id() == chatDTO.getBuyer().getUser_Id()) {
			model.addAttribute("nickname", chatDTO.getSeller().getNickname());
		}

		model.addAttribute("chat_Id", chat_Id);
		model.addAttribute("chat_Messages", gson.toJson(chat_Messages));
		model.addAttribute("chatDTO", chatDTO);
		model.addAttribute("board_Image", chatDTO.getProduct_Board().getBoard_Image().get(0));
		model.addAttribute("current_User", gson.toJson(user));

		return "/chat/chatRoom";
	}

	@RequestMapping(value = "/chat/create_chat/{board_Id}.do", method = RequestMethod.GET)
	public String create_Chat(@PathVariable int board_Id, HttpSession session, HttpServletRequest request) {

		UserVO user = (UserVO) session.getAttribute("user");

		int chat_Id;
		try {
			chat_Id = chatService.create_Chat(board_Id, user.getUser_Id());
		} catch (DataAccessException | SQLException e) {
			return "/main";
		}
		if (chat_Id == -1) {
			return "redirect:" + request.getHeader("Referer");
		}

		return "redirect:/chat/" + chat_Id + ".do"; // 새로 생성된 채팅방으로 리디렉션

	}

	@RequestMapping(value = "/chat/check_login_exist.do", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String checkChat(HttpSession session, @RequestParam("board_Id") int board_Id) {

		UserVO user = (UserVO) session.getAttribute("user");
		int is_Exist_Chat;
		int chat_Id;

		if (user == null) {
			return new Gson().toJson(new MessageVO(3, "로그인 후 이용해주세요."));
		}

		try {
			is_Exist_Chat = chatService.is_Exist_Chat(board_Id, user.getUser_Id());
		} catch (DataAccessException | SQLException e) {
			return new Gson().toJson(new MessageVO(0, "서버에서 오류가 발생하였습니다."));
		}

		if (is_Exist_Chat == 1) {
			try {
				chat_Id = chatService.doSelect_Chat_Id(board_Id, user.getUser_Id());
			} catch (DataAccessException | SQLException e) {
				return new Gson().toJson(new MessageVO(0, "서버에서 오류가 발생하였습니다."));
			}
			return new Gson().toJson(new MessageVO(1, String.valueOf(chat_Id)));
		} else {
			return new Gson().toJson(new MessageVO(2, "채팅방 생성"));
		}

	}

	@RequestMapping(value = "/chat/get_buyerlist.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getBuyerList(@RequestParam("board_Id") int board_Id) {

		log.debug("getBuyerList 접근");

		Map<String, Object> response = new HashMap<String, Object>();
		List<UserVO> buyers;

		try {
			buyers = chatService.doSelect_buyers(board_Id);
		} catch (DataAccessException | SQLException e) {
			response.put("error", "DB 예외 발생");
			return response;
		}

		for (UserVO buyer : buyers) {
			log.debug("후보 구매자 정보 : {}", buyer.toString());
		}

		response.put("buyers", new Gson().toJson(buyers));

		return response;

	}

}
