<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<!DOCTYPE html>
		<html lang="en">

		<head>
			<meta charset="UTF-8" />
			<meta name="viewport" content="width=device-width, initial-scale=1.0" />
			<title>상품 목록</title>
			<link rel="stylesheet" href="/ehr/resources/assets/css/search.css" />
			<link rel="stylesheet"
				href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
			<script src="/ehr/resources/assets/js/jquery_3_7_1.js"></script>
			<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet" />

			<script>
				window.search_Word = "${searchWord}".replace(/\s+/g, '');
				window.category_Id_Main = ${category_Id};
				console.log(search_Word);
				console.log(category_Id_Main);
			</script>
			<script src="/ehr/resources/assets/js/search.js"></script>
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
                            <span class="material-symbols-outlined">mark_chat_unread</span> 채팅하기
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

            <div id="navbar" class="navbar" ></div>
			<div class="container">
				<!-- 왼쪽 패널 시작 -->
				<div class="left-panel">
					<div class="profile-box">
						<div class="profile-image">
							<input type="file" id="profile-input" style="display: none;" accept="image/*">
						</div>

						<div class="filter">
							<h3>
								<span class="filter-title">필터</span>
								<button id="filtering_ResetBtn">초기화</button>
							</h3>
							<div class="checkboxW">
								<input type="checkbox" class="custom-checkbox" value="0"> 거래 가능만 보기
							</div>
							<hr class="hr">
						</div>


						<div class="area">
							<!-- 위치 시작 -->
							<div class="address">
								<h3>위치</h3>
								<div class="radio-margin">
									<input type="radio" name="location" value="서울" class="custom-radio5"> 서울특별시
								</div>

								<div class="radio-margin">
									<input type="radio" name="location" value="부산" class="custom-radio5"> 부산광역시
								</div>

								<div class="radio-margin">
									<input type="radio" name="location" value="대구" class="custom-radio5"> 대구광역시
								</div>

								<div class="radio-margin">
									<input type="radio" name="location" value="인천" class="custom-radio5"> 인천광역시
								</div>

								<div class="radio-margin">
									<input type="radio" name="location" value="광주" class="custom-radio5"> 광주광역시
								</div>

								<div class="radio-margin">
									<input type="radio" name="location" value="대전" class="custom-radio5"> 대전광역시
								</div>

								<div class="radio-margin">
									<input type="radio" name="location" value="울산" class="custom-radio5"> 울산광역시
								</div>

								<div class="radio-margin">
									<input type="radio" name="location" value="세종특별자치시" class="custom-radio5 custom-radio7"> 세종특별자치시
								</div>

								<div class="radio-margin">
									<input type="radio" name="location" value="경기" class="custom-radio5 custom-radio3"> 경기도
								</div>

								<div class="radio-margin">
									<input type="radio" name="location" value="강원특별자치도" class="custom-radio5 custom-radio7"> 강원특별자치도
								</div>

								<div class="radio-margin">
									<input type="radio" name="location" value="충북" class="custom-radio5 custom-radio4"> 충청북도
								</div>

								<div class="radio-margin">
									<input type="radio" name="location" value="충남" class="custom-radio5 custom-radio4"> 충청남도
								</div>

								<div class="radio-margin">
									<input type="radio" name="location" value="전북특별자치도" class="custom-radio5 custom-radio7"> 전북특별자치도
								</div>

								<div class="radio-margin">
									<input type="radio" name="location" value="전남" class="custom-radio5 custom-radio4"> 전라남도
								</div>

								<div class="radio-margin">
									<input type="radio" name="location" value="경북" class="custom-radio5 custom-radio4"> 경상북도
								</div>

								<div class="radio-margin">
									<input type="radio" name="location" value="경남" class="custom-radio5 custom-radio4"> 경상남도
								</div>

								<div class="radio-margin">
									<input type="radio" name="location" value="제주특별자치도" class="custom-radio5 custom-radio7"> 제주특별자치도
								</div>
							</div>

							<hr class="hr">
							<!-- 위치 끝 -->

							<!-- 카테고리 시작 -->
							<div class="kategory">
								<h3>카테고리</h3>
								<div class="radio-margin">
									<input type="radio" name="category" value="100" class="custom-radio5"> 디지털기기
								</div>

								<div class="radio-margin">
									<input type="radio" name="category" value="101" class="custom-radio5 custom-radio4"> 생활가전
								</div>

								<div class="radio-margin">
									<input type="radio" name="category" value="102" class="custom-radio5 custom-radio7-5"> 가구/인테리어
								</div>

								<div class="radio-margin">
									<input type="radio" name="category" value="103" class="custom-radio5 custom-radio5-5"> 생활/주방
								</div>

								<div class="radio-margin">
									<input type="radio" name="category" value="104" class="custom-radio5 custom-radio3"> 유아동
								</div>

								<div class="radio-margin">
									<input type="radio" name="category" value="105" class="custom-radio5 custom-radio7-5"> 여성패션/잡화
								</div>

								<div class="radio-margin">
									<input type="radio" name="category" value="106" class="custom-radio5 custom-radio7-5"> 남성패션/잡화
								</div>

								<div class="radio-margin">
									<input type="radio" name="category" value="107" class="custom-radio5 custom-radio5-5"> 뷰티/미용
								</div>

								<div class="radio-margin">
									<input type="radio" name="category" value="108" class="custom-radio5 custom-radio6-5"> 스포츠/레저
								</div>

								<div class="radio-margin">
									<input type="radio" name="category" value="109" class="custom-radio5 custom-radio8"> 취미/게임/음반
								</div>

								<div class="radio-margin">
									<input type="radio" name="category" value="110" class="custom-radio5 custom-radio2"> 도서
								</div>

								<div class="radio-margin">
									<input type="radio" name="category" value="111" class="custom-radio5 custom-radio6-5"> 티켓/교환권
								</div>

								<div class="radio-margin">
									<input type="radio" name="category" value="112" class="custom-radio5 custom-radio4-5"> 가공식품
								</div>

								<div class="radio-margin">
									<input type="radio" name="category" value="113" class="custom-radio5 custom-radio6"> 건강기능식품
								</div>

								<div class="radio-margin">
									<input type="radio" name="category" value="114" class="custom-radio5 custom-radio6"> 반려동물용품
								</div>

								<div class="radio-margin">
									<input type="radio" name="category" value="115" class="custom-radio5 custom-radio2"> 식물
								</div>

								<div class="radio-margin">
									<input type="radio" name="category" value="116" class="custom-radio5 custom-radio8-5"> 기타 중고 물품
								</div>

								<hr class="hr">
							</div>
							<!-- 카테고리 끝 -->
							<!-- 정렬렬 시작 -->
							<div class="area">
								<h3>정렬</h3>
								<div class="radio-margin">
									<input type="radio" name="order" value="mod_date_DESC" class="custom-radio5 custom-radio3"> 최신순
								</div>
								<div class="radio-margin">
									<input type="radio" name="order" value="price_DESC" class="custom-radio5 custom-radio3"> 고가순
								</div>

								<div class="radio-margin">
									<input type="radio" name="order" value="price_ASC" class="custom-radio5 custom-radio3"> 저가순
								</div>
								<hr class="hr">
							</div>

							<button id="filteringBtn" type="button">적용</button>

						</div>
					</div>
				</div>
				<!-- 왼쪽 패널 끝 -->

				<!-- 상품 정보 그리드 시작 -->
				<div class="right-panel">
					<div class="tab-content active" id="tab">
						<div class="product-grid" id="product-grid"></div>
						<div class="pagination" id="pagination"></div>
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

		</body>



		</html>