<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<!DOCTYPE html>
		<html lang="en">

		<head>
			<meta charset="UTF-8" />
			<meta name="viewport" content="width=device-width, initial-scale=1.0" />
			<title>HTML 기본 문서</title>
			<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css">
			<link rel="stylesheet" href="/ehr/resources/assets/css/detail.css" />
			<link rel="stylesheet" href="/ehr/resources/assets/css/report.css" />
			<link rel="stylesheet"
				href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
			<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet" />
			<script>
				window.board_Id = ${ productBoard.board_Id }
			</script>
			<script src="/ehr/resources/assets/js/jquery_3_7_1.js"></script>
			<script src="/ehr/resources/assets/js/detail.js"></script>
			<script src="/ehr/resources/assets/js/report.js"></script>
			<script src="/ehr/resources/assets/js/logout_Search.js"></script>
		</head>

		<body>
			<!-- 헤더 시작 -->
			<header>
				<!-- 상단 배너 -->
				<div class="topBannerContainer">
					<div class="topBannerContent">
						<img src="/ehr/resources/assets/images/headerBanner.png" class="headerBanner" alt="Top Banner" />
					</div>
				</div>

				<!-- 헤더 영역 -->
				<div id="header">
					<!-- 로고 -->
					<img src="/ehr/resources/assets/images/logo.svg" class="headerImg" alt="로고"
						onclick="location.href='/ehr/main.do'" />

					<!-- 검색창 -->
					<div class="mainSector">
						<form>
							<div class="dropdown">
								<button id="dropdownBtn">
									<span class="dropdownText">중고거래</span> <span
										class="material-symbols-outlined dropdownArrow">arrow_drop_down</span>
								</button>
								<ul class="dropdownMenu" id="dropdownMenu">
									<li>중고거래</li>
								</ul>
								<div class="row-hr"></div>
								<input type="text" id="search" placeholder="검색어를 입력해주세요" /> <span id="searchBtn"
									class="material-symbols-outlined search-img">search</span>
							</div>



							<div class="max">
								<!-- 인기 검색어 -->
								<p class="keyword">
									인기 검색어 <a href="/ehr/product_board/search.do?searchWord=아이폰">아이폰</a>
									<a href="/ehr/product_board/search.do?searchWord=의자">의자</a> <a
										href="/ehr/product_board/search.do?searchWord=자전거">자전거</a> <a
										href="/ehr/product_board/search.do?searchWord=컴퓨터">컴퓨터</a> <a
										href="/ehr/product_board/search.do?searchWord=책상">책상</a> <a
										href="/ehr/product_board/search.do?searchWord=소파">소파</a> <a
										href="/ehr/product_board/search.do?searchWord=원피스">원피스</a> <a
										href="/ehr/product_board/search.do?searchWord=전기자전거">전기자전거</a> <a
										href="/ehr/product_board/search.do?searchWord=식탁">식탁</a> <a
										href="/ehr/product_board/search.do?searchWord=모니터">모니터</a>
								</p>
							</div>
						</form>
					</div>

					<!-- 버튼 영역 -->
					<div class="headerButtons">
						<button class="headerBtn" onclick="location.href='/ehr/chat/chatList.do'">
							<span class="material-symbols-outlined">mark_chat_unread</span>
							채팅하기
						</button>
						<div class="row-hr"></div>
						<button class="headerBtn" onclick="location.href='/ehr/product_board/post.do'">
							<span class="material-symbols-outlined">add_circle</span> 상품 등록
						</button>
						<div class="row-hr"></div>
						<button class="headerBtn" onclick="location.href='/ehr/user/mypage.do'">
							<span class="material-symbols-outlined">account_circle</span> 마이페이지
						</button>
						<div class="row-hr"></div>
						<button class="headerBtn" id="loginBtn" onclick="location.href='/ehr/user/login/login.do'">
							<span class="material-symbols-outlined">person</span> 로그인/회원가입
						</button>
						<button class="headerBtn hidden" id="logoutBtn">
							<span class="material-symbols-outlined">logout</span> 로그아웃
						</button>
					</div>
				</div>
			</header>
			<!-- 헤더 끝 -->


			<div class="container">
				<div class="area">

					<div class="right-area">
						<label for="">상품 이미지</label>
						<div id="previewImages"></div>
						<div class="user">
							<img src="/ehr/user_image/${user.user_Image}" alt="유저 프로필 이미지" />
							<div class="user-info">
								<p id="user_Nickname">${user_Nickname}</p>
								<p id="user_Address">${user_Munic_Address}</p>
							</div>
						</div>
						<div class="file-input-wrapper">
							<input type="file" id="board_images" name="board_images" class="hidden"
								data-images="${productBoard.board_Image}" accept="image/*" multiple />
						</div>

					</div>
					<div>
						<div class="right-input">
							<label for="board_title" class="board_title"></label>
							<h1 id="board_title">${productBoard.board_Title}</h1>
							<div class="right-input">
								<label for="category_And_Time"></label>
								<p id="category_And_Time">${category_And_Time}</p>
							</div>
						</div>

						<div class="right-input">
							<label for="price"></label>
							<!-- 가격 -->
							<h1 id="price">
								<span id="price">${productBoard.price}</span>
							</h1>
						</div>

						<div class="right-input">
							<label for="board_content"></label>
							<!-- 설명글 -->
							<p id="board_content">${productBoard.board_Content}</p>
						</div>

						<div class="right-input">
							<label for=""></label>
							<!-- 거래방식 -->
							<p class="tag">${how_Trade_String}</p>
							<!-- 거래지역 -->
							<p class="tag">${productBoard.trade_Address}</p>
						</div>

						<div class="right-input">
							<div class="chat-view-container">
								<p id="chat_And_ViewCount">${chat_And_ViewCount}</p>
								<div>
									<button id="reportBtn">
										<i class="fas fa-exclamation-circle"></i> 신고하기
									</button>
								</div>
							</div>
						</div>

						<div class="chatBtn">
							<button id="chatBtn">채팅하기</button>
						</div>
					</div>




					<!-- 신고하기 창  -->
					<div class="modalReport">
						<div class="report_body">
							<h1>
								신고하기 <a class="closeReportBtn"><i class="fa-solid fa-xmark"></i></a>
							</h1>
							<h3>게시글 제목 : ${productBoard.board_Title}</h3>
							<div>
								<select id="reportCategory" required>
									<option value="0" disabled hidden selected>신고 사유를 선택하세요</option>
									<option value="1">광고성 콘텐츠예요</option>
									<option value="2">거래 금지 품목으로 판단돼요</option>
									<option value="3">사기가 의심돼요</option>
									<option value="4">콘텐츠 내용이 불쾌해요</option>
								</select>
							</div>

							<div>
								<textarea id="reportReason" rows="15px" placeholder="신고 사유를 자세히 작성해주세요.&#10;자세하게 적어주시면 신고처리에 큰 도움이 됩니다."
									maxlength="1000" required></textarea>
							</div>

							<div>
								<button type="button" class="doReportBtn">신고</button>
							</div>
						</div>
					</div>
				</div>
			</div>


			<!-- 푸터 -->
			<div class="contents">
				<footer class="footer">
					<div class="footerTop">
						<div class="companyInfo">
							<p>
								<strong>(주)도토리 마켓 사업자 정보</strong>
							</p>
							<p>대표자: 홍길동 | 사업자 등록번호: 123-45-67890 | 통신판매업 신고번호:
								제2024-서울마포-1234호 | 주소: 서울 마포구 양화로 122 3층, 4층 | 대표전화: 02-1234-5678</p>
							<p>
								이메일: <a href="mailto:abc@email.com">abc@email.com | 호스팅제공자:
									주식회사 도토리</a>
							</p>
						</div>
						<div class="contactLinks">
							<a href="#">Contact</a> | <a href="#">신고센터</a> | <a href="#">광고
								문의</a>
						</div>
					</div>
					<div class="footerMiddle">
						<div class="policyLinks">
							<a href="#">이용약관</a> | <a href="#">개인정보처리방침</a> | <a href="#">청소년
								보호정책</a> | <a href="#">사업자 정보 확인</a> | <a href="#">게시물 수집 및 이용 안내</a>
						</div>
					</div>
					<div class="footerBottom">
						<div class="notice">
							<p>"도토리 마켓"은 통신판매중개자로 거래 당사자가 아니며, 회원 간 거래에 대해 책임을 지지 않습니다.</p>
						</div>
						<div class="socialLinks">
							<a href="#" class="socialIcon"><i class="fab fa-facebook-f"></i></a>
							<a href="#" class="socialIcon"><i class="fab fa-instagram"></i></a>
							<a href="#" class="socialIcon"><i class="fab fa-youtube"></i></a>
						</div>
					</div>
				</footer>
			</div>
			<script>

				document.addEventListener("DOMContentLoaded", function () {

					const priceElement = document.getElementById("price");
					let current_Status = ${ productBoard.board_Status };
					const chatBtnElement = document.getElementById("chatBtn");

					const rawPrice = ${ productBoard.price };
					const LocalePrice = rawPrice.toLocaleString();

					priceElement.textContent = `\${LocalePrice}원`;

					console.log("current_Status", current_Status);
					if (current_Status === 1) {
						chatBtnElement.disabled = true;
						chatBtnElement.textContent = "거래 완료된 상품입니다";
					}

				});

			</script>
		</body>

		</html>