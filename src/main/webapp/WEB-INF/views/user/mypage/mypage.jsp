<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<!DOCTYPE html>
		<html lang="ko">

		<head>
			<meta charset="UTF-8">
			<meta name="viewport" content="width=device-width, initial-scale=1.0">
			<title>도토리마켓 마이페이지</title>
			<link rel="stylesheet" href="/ehr/resources/assets/css/mypage.css" />
			<link rel="stylesheet"
				href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
			<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet" />
			<script src="/ehr/resources/assets/js/common.js"></script>
			<script src="/ehr/resources/assets/js/jquery_3_7_1.js"></script>
			<script src="/ehr/resources/assets/js/mypage.js"></script>
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
				<div class="left-panel">
					<div class="profile-box">
						<div class="profile-image">
							<img src="/ehr/user_image/${sessionScope.user.user_Image}" alt="Profile Image" id="profile-img"> <input
								type="file" id="profile-input" style="display: none;" accept="image/*">
							<button class="edit-icon" onclick="document.getElementById('profile-input').click();">✎</button>
						</div>

						<form id="UpdateForm">

							<!-- 이메일 입력 및 중복 확인 -->
							<div class="profform">
								<label for="auth-code">이메일(아이디)</label> <input type="text" id="email" name="email"
									value=${sessionScope.user.email } minlength="3" maxlength="320" required disabled>
							</div>

							<div id="autu_code_Form" class="form hidden">
								<label for="auth-code">인증코드</label>
								<div class="input-group">
									<input type="text2" id="auth-code" name="auth_code" placeholder="인증코드" minlength="6" maxlength="6"
										disabled />
									<button type="button" id="sendAuthBtn">발송</button>
								</div>
							</div>

							<div>
								<button id="editEmailBtn">이메일 수정하기</button>
								<button id="saveEmailBtn" class="hidden">이메일 수정완료</button>
							</div>


							<!-- 나머지 회원가입 정보 입력 -->
							<div class="profform">
								<label for="name">이름</label> <input type="text" id="name" name="name" value="${sessionScope.user.name}"
									minlength="2" maxlength="7" required disabled>
							</div>

							<div class="profform">
								<label for="auth-code">전화번호</label> <input type="text" id="phone" name="phone"
									value="${sessionScope.user.phone}" minlength="8" maxlength="15" required disabled> <span
									id="phoneMessage"></span>
							</div>

							<div class="profform">
								<label for="auth-code">닉네임</label> <input type="text" id="nickname" name="nickname"
									value="${sessionScope.user.nickname}" minlength="2" maxlength="12" required disabled>
							</div>

							<div class="profform">
								<label for="address">주소</label>
								<div class="input-group">
									<input type="text" id="address" name="address" value="<c:out value='${sessionScope.user.address}'/>"
										readonly required disabled> <input class="hidden" type="button" onclick="sample5_execDaumPostcode()"
										value="주소 검색" id="addressBtn">
								</div>
							</div>

							<div>
								<button id="otherUpdateBtn" class="button_own">정보 수정</button>
								<button id="otherSaveBtn" class="button_own hidden">정보수정
									완료</button>
								<button id="otherCancelBtn" class="button_own hidden">취소</button>
							</div>


						</form>

					</div>
				</div>

				<div class="right-panel">
					<div class="tabs">
						<ul>
							<li class="active" onclick="showTab(0)">판매중</li>
							<li onclick="showTab(1)">판매 완료</li>
							<li onclick="showTab(2)">구매 내역</li>
						</ul>
					</div>
					<div class="tab-content active" id="tab-0">
						<div class="product-grid" id="product-grid-0"></div>
						<div class="pagination" id="pagination-0"></div>
					</div>
					<div class="tab-content" id="tab-1">
						<div class="product-grid" id="product-grid-1"></div>
						<div class="pagination" id="pagination-1"></div>
					</div>
					<div class="tab-content" id="tab-2">
						<div class="product-grid" id="product-grid-2"></div>
						<div class="pagination" id="pagination-2"></div>
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
				// 프로필 사진 업로드 ajax 요청
				document.getElementById('profile-input').addEventListener('change', function (event) {

					event.preventDefault();

					const maxFileSize = 5 * 1024 * 1024; // 최대 파일 크기 5MB (5MB = 5 * 1024 * 1024 bytes)

					const file = event.target.files[0];
					const formData = new FormData();
					formData.append('user_Image', file);

					if (file.size > maxFileSize) {

						alert("파일 크기는 최대 5MB 이하입니다.")
						return;
					}

					$.ajax({
						url: '/ehr/user/imageupdate.do',
						method: 'POST',
						processData: false,
						contentType: false,
						data: formData,
						success: function (response) {
							if (response.messageId === 1) {
								alert(response.message);
								window.location.href = "/ehr/user/mypage.do"
							} else {
								alert(response.message);
							}
						},
						error: function () {
							alert('프로필 사진 업로드 중 오류가 발생했습니다.');
						}
					});



				}); 
			</script>

			<!-- 마이페이지 상품 및 페이지 네이션 렌더링 -->
			<script>




			</script>

			<script>
				// 버튼 제어
				document.addEventListener("DOMContentLoaded", () => {
					const emailInput = document.getElementById("email");
					const nameInput = document.getElementById("name");
					const phonelInput = document.getElementById("phone");
					const addressInput = document.getElementById("address");
					const nicknameInput = document.getElementById("nickname");

					// 이메일 버튼 요소 가져오기
					const autu_code_Form = document.getElementById("autu_code_Form");
					const editEmailBtn = document.getElementById("editEmailBtn");
					const saveEmailBtn = document.getElementById("saveEmailBtn");

					// 기타 정보 수정 버튼 요소 가져오기
					const otherUpdateBtn = document.getElementById("otherUpdateBtn");
					const otherSaveBtn = document.getElementById("otherSaveBtn");
					const otherCancelBtn = document.getElementById("otherCancelBtn");
					const addressBtn = document.getElementById("addressBtn");


					editEmailBtn.addEventListener("click", (event) => {
						event.preventDefault();
						emailInput.disabled = false; // 이메일 입력 활성화
						emailInput.value = "${sessionScope.user.email}";
						autu_code_Form.classList.remove("hidden"); // 인증 번호 폼 보이기
						editEmailBtn.classList.add("hidden"); // 이메일 수정하기 버튼 감추기
						saveEmailBtn.classList.remove("hidden"); // 이메일 수정완료하기 버튼 보이기
					});


					// 정보 수정 버튼 제어
					otherUpdateBtn.addEventListener("click", (event) => {
						event.preventDefault();
						nameInput.disabled = false; // 이름 입력 활성화
						phonelInput.disabled = false; // 전화번호 입력 활성화
						addressInput.disabled = false; // 주소 입력 활성화
						nicknameInput.disabled = false; // 닉네임 입력 활성화

						// 세션 값으로 초기화화
						nameInput.value = "${sessionScope.user.name}";
						phonelInput.value = "${sessionScope.user.phone}";
						addressInput.value = "<c:out value='${sessionScope.user.address}'/>";
						nicknameInput.value = "${sessionScope.user.nickname}";

						otherUpdateBtn.classList.add("hidden"); // 정보 수정 버튼 숨기기
						otherSaveBtn.classList.remove("hidden"); // 정보 수정 완료 버튼 보이기
						otherCancelBtn.classList.remove("hidden"); // 정보 수정취소  버튼 보이기
						addressBtn.classList.remove("hidden"); // 주소 수정 버튼 보이기

					});


					// 정보 수정 취소 버튼 제어
					otherCancelBtn.addEventListener("click", (event) => {
						event.preventDefault();
						nameInput.disabled = true; // 이름 입력 비활성화
						phonelInput.disabled = true; // 전화번호 입력 비활성화
						addressInput.disabled = true; // 주소 입력 비활성화
						nicknameInput.disabled = true; // 닉네임 입력 비활성화

						// 세션 값으로 초기화화
						nameInput.value = "${sessionScope.user.name}";
						phonelInput.value = "${sessionScope.user.phone}";
						addressInput.value = "<c:out value='${sessionScope.user.address}'/>";
						nicknameInput.value = "${sessionScope.user.nickname}";

						otherUpdateBtn.classList.remove("hidden"); // 정보 수정 버튼 보이기
						otherSaveBtn.classList.add("hidden"); // 정보 수정 완료 버튼 숨기기
						otherCancelBtn.classList.add("hidden"); // 정보 수정 취소 버튼 숨기기
						addressBtn.classList.add("hidden"); // 주소 수정 버튼 숨기기기

					});





					// 회원 정보 수정 요청
					$('#otherSaveBtn').on('click', function (event) {

						event.preventDefault();

						const formData = {
							name: $('#name').val(),
							phone: $('#phone').val(),
							nickname: $('#nickname').val(),
							address: $('#address').val()
						};

						// 이름
						if (!window.common.validateInput("name", formData.name)) {
							alert("잘못된 이름 형식 입니다.");
							return;
						}

						// 전화 번호
						if (!window.common.validateInput("phone", formData.phone)) {
							alert("잘못된 전화번호 형식 입니다.");
							return;
						}

						// 닉네임
						if (!window.common.validateInput("nickname", formData.nickname)) {
							alert("잘못된 닉네임 형식 입니다.");
							return;
						}



						// http 요청
						$.ajax({
							url: '/ehr/otherUpdate.do',
							method: 'POST',
							contentType: 'application/json',
							data: JSON.stringify(formData),
							success: function (response) {
								if (response.messageId === 1) {
									alert(response.message);
									window.location.href = "/ehr/user/mypage.do"
								} else {
									alert(response.message);
								}
							},
							error: function () {
								alert('회원 정보 수정 중 에러가 발생했습니다.');
							}
						}); // http 요청 -- end
					}); // 회원 정보 수정 요청 -- end


					// 인증 번호 요청
					$('#sendAuthBtn').on('click', function () {

						const email = $('#email').val();

						// 이메일
						if (!window.common.validateInput("email", email)) {
							alert("잘못된 이메일 형식 입니다.");
							return;
						}

						// http 요청
						$.ajax({
							url: '/ehr/send_auth/register.do',
							method: 'POST',
							contentType: 'application/json',
							data: JSON.stringify({ email: email }),
							success: function (response) {
								if (response.messageId === 0) {
									alert(response.message)
									$('#auth-code').prop('disabled', false);
								} else {
									alert(response.message)
								}
							},
							error: function () {
								alert('인증 번호 요청 중 에러가 발생했습니다.');
							}
						}); // http 요청 -- end
					}); // 인증번호 요청 -- end

					// 이메일 수정 요청
					$('#saveEmailBtn').on('click', function (event) {

						event.preventDefault();

						const email = $('#email').val();
						const auth_code = $('#auth-code').val();

						// 이메일
						if (!window.common.validateInput("email", email)) {
							alert("잘못된 이메일 형식 입니다.");
							return;
						}

						// 인증코드
						if (!window.common.validateInput("auth_code", auth_code)) {
							alert("잘못된 인증코드 형식 입니다.");
							return;
						}

						// http 요청
						$.ajax({
							url: '/ehr/user/emailupdate.do',
							method: 'POST',
							contentType: 'application/json',
							data: JSON.stringify({
								new_Email: email,
								auth_code: auth_code
							}),
							success: function (response) {
								if (response.messageId === 1) {
									alert(response.message);
									window.location.href = "/ehr/user/mypage.do";
								} else {
									alert(response.message);
								}
							},
							error: function () {
								alert('인증 번호 요청 중 에러가 발생했습니다.');
							}
						}); // http 요청 -- end
					}); // 이메일 수정 요청 -- end




				}); // 버튼 제어 -- end
			</script>


			<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
			<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=6494369aebd01baf926e57a1affa1288&libraries=services"></script>
			<script>


				function sample5_execDaumPostcode() {
					new daum.Postcode({
						oncomplete: function (data) {
							var addr = data.address; // 최종 주소 변수

							// 주소 정보를 해당 필드에 넣는다.
							document.getElementById("address").value = addr;

						}
					}).open();
				}


			</script>

		</body>

		</html>