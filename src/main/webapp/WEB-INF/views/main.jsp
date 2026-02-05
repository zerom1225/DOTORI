<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
      <meta charset="UTF-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <title>당신 근처의 도토리</title>
      <link rel="stylesheet" href="/ehr/resources/assets/css/main.css" />
      <!-- Material Icons -->
      <link rel="stylesheet"
        href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
      <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet" />

      <!-- jQuery -->
      <script src="/ehr/resources/assets/js/jquery_3_7_1.js"></script>
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

      <div class="contents">
        <!-- 배너 영역 -->
        <div class="banner">
          <img src="/ehr/resources/assets/images/banner.png" alt="배너 이미지" />
        </div>

        <!-- 카테고리 -->
        <section class="categories">
          <h2 class="categoriesTitle">카테고리</h2>
          <div class="categoriesWrapper">
            <!-- 왼쪽 화살표 -->
            <button class="arrowButton leftArrow" id="prevBtn">&lt;</button>

            <div class="categoriesViewport">
              <div class="categoriesContainer" id="categoriesContainer">
                <!-- 카테고리 1 -->
                <div class="categoryItem"
                  onclick="location.href='/ehr/product_board/search.do?searchWord=&category_Id=100'">
                  <img src="/ehr/resources/assets/images/camera.png" alt="디지털기기" class="categoryIcon">
                  <p class="categoryName">디지털기기</p>
                </div>
                <!-- 카테고리 2 -->
                <div class="categoryItem"
                  onclick="location.href='/ehr/product_board/search.do?searchWord=&category_Id=101'">
                  <img src="/ehr/resources/assets/images/microwave.png" alt="생활가전" class="categoryIcon">
                  <p class="categoryName">생활가전</p>
                </div>
                <!-- 카테고리 3 -->
                <div class="categoryItem"
                  onclick="location.href='/ehr/product_board/search.do?searchWord=&category_Id=102'">
                  <img src="/ehr/resources/assets/images/chair.png" alt="가구/인테리어" class="categoryIcon">
                  <p class="categoryName">가구/인테리어</p>
                </div>
                <!-- 카테고리 4 -->
                <div class="categoryItem"
                  onclick="location.href='/ehr/product_board/search.do?searchWord=&category_Id=103'">
                  <img src="/ehr/resources/assets/images/pot.png" alt="생활/주방" class="categoryIcon">
                  <p class="categoryName">생활/주방</p>
                </div>
                <!-- 카테고리 5 -->
                <div class="categoryItem"
                  onclick="location.href='/ehr/product_board/search.do?searchWord=&category_Id=104'">
                  <img src="/ehr/resources/assets/images/toy.png" alt="유아동" class="categoryIcon">
                  <p class="categoryName">유아동</p>
                </div>


                <!-- 추가 카테고리 -->
                <div class="categoryItem"
                  onclick="location.href='/ehr/product_board/search.do?searchWord=&category_Id=105'">
                  <img src="/ehr/resources/assets/images/fclothes.png" alt="여성패션/잡화" class="categoryIcon">
                  <p class="categoryName">여성패션/잡화</p>
                </div>
                <div class="categoryItem"
                  onclick="location.href='/ehr/product_board/search.do?searchWord=&category_Id=106'">
                  <img src="/ehr/resources/assets/images/mclothes.png" alt="남성패션/잡화" class="categoryIcon">
                  <p class="categoryName">남성패션/잡화</p>
                </div>
                <div class="categoryItem"
                  onclick="location.href='/ehr/product_board/search.do?searchWord=&category_Id=107'">
                  <img src="/ehr/resources/assets/images/beauty.png" alt="뷰티/미용" class="categoryIcon">
                  <p class="categoryName">뷰티/미용</p>
                </div>
                <div class="categoryItem"
                  onclick="location.href='/ehr/product_board/search.do?searchWord=&category_Id=108'">
                  <img src="/ehr/resources/assets/images/sports.png" alt="스포츠/레저" class="categoryIcon">
                  <p class="categoryName">스포츠/레저</p>
                </div>
                <div class="categoryItem"
                  onclick="location.href='/ehr/product_board/search.do?searchWord=&category_Id=109'">
                  <img src="/ehr/resources/assets/images/hobby.png" alt="취미/게임/음반" class="categoryIcon">
                  <p class="categoryName">취미/게임/음반</p>
                </div>


                <!-- 추가 카테고리 -->
                <div class="categoryItem"
                  onclick="location.href='/ehr/product_board/search.do?searchWord=&category_Id=110'">
                  <img src="/ehr/resources/assets/images/book.png" alt="도서" class="categoryIcon">
                  <p class="categoryName">도서</p>
                </div>
                <div class="categoryItem"
                  onclick="location.href='/ehr/product_board/search.do?searchWord=&category_Id=111'">
                  <img src="/ehr/resources/assets/images/ticket.png" alt="티켓/교환권" class="categoryIcon">
                  <p class="categoryName">티켓/교환권</p>
                </div>
                <div class="categoryItem"
                  onclick="location.href='/ehr/product_board/search.do?searchWord=&category_Id=112'">
                  <img src="/ehr/resources/assets/images/food.png" alt="가공식품" class="categoryIcon">
                  <p class="categoryName">가공식품</p>
                </div>
                <div class="categoryItem"
                  onclick="location.href='/ehr/product_board/search.do?searchWord=&category_Id=113'">
                  <img src="/ehr/resources/assets/images/health.png" alt="건강기능식품" class="categoryIcon">
                  <p class="categoryName">건강기능식품</p>
                </div>
                <div class="categoryItem"
                  onclick="location.href='/ehr/product_board/search.do?searchWord=&category_Id=114'">
                  <img src="/ehr/resources/assets/images/pet.png" alt="반려동물용품" class="categoryIcon">
                  <p class="categoryName"> 반려동물용품</p>
                </div>

                <!-- 추가 카테고리 -->
                <div class="categoryItem"
                  onclick="location.href='/ehr/product_board/search.do?searchWord=&category_Id=115'">
                  <img src="/ehr/resources/assets/images/plant.png" alt="식물" class="categoryIcon">
                  <p class="categoryName">식물</p>
                </div>
                <div class="categoryItem"
                  onclick="location.href='/ehr/product_board/search.do?searchWord=&category_Id=116'">
                  <img src="/ehr/resources/assets/images/etc.png" alt="기타 중고 물품" class="categoryIcon">
                  <p class="categoryName">기타 중고 물품</p>
                </div>

              </div>
            </div>

            <!-- 오른쪽 화살표 -->
            <button class="arrowButton rightArrow" id="nextBtn">&gt;</button>
          </div>
        </section>

        <!-- 하단 배너 영역 -->
        <div class="banner_bottom">
          <img src="/ehr/resources/assets/images/banner2.png" alt="배너 이미지" />
        </div>



        <!-- 푸터 -->
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



      <!-- JS -->
      <script src="/ehr/resources/assets/js/main.js"></script>
      <script src="/ehr/resources/assets/js/logout_Search.js"></script>
    </body>

    </html>