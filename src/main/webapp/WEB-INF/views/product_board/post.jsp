<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <title>상품등록</title>
            <link rel="stylesheet" href="/ehr/resources/assets/css/post.css" />
            <!-- Material Icons -->
            <link rel="stylesheet"
                href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
            <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
                rel="stylesheet" />
            <script src="/ehr/resources/assets/js/common.js"></script>
            <script src="/ehr/resources/assets/js/jquery_3_7_1.js"></script>
            <script src="/ehr/resources/assets/js/post.js"></script>
            <script src="/ehr/resources/assets/js/logout_Search.js"></script>

        </head>

        <body>

            <!-- 헤더 시작 -->
            <header>
                <!-- 상단 배너 -->
                <div class="topBannerContainer">
                    <div class="topBannerContent">
                        <img src="/ehr/resources/assets/images/headerBanner.png" class="headerBanner"
                            alt="Top Banner" />
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

            <div class="container">
                <div class="area">

                    <div class="right-area">
                        <label for="">상품 이미지</label>
                        <div id="previewImages"></div>
                        <div class="file-input-wrapper">
                            <label for="board_images">상품 사진(최대 6장)</label>
                            <button type="button">사진 업로드</button>
                            <input type="file" id="board_images" name="board_images" accept="image/*" multiple />
                        </div>
                    </div>

                    <div>
                        <div class="right-input">
                            <label for="board_title">제목 : </label> <input type="text" id="board_title" maxlength="30"
                                name="board_title">
                        </div>
                        <div class="right-input">
                            <label for="category_id">카테고리 선택 : </label> <select id="category_id" name="category_id">
                                <option value="100">디지털기기</option>
                                <option value="101">생활가전</option>
                                <option value="102">가구/인테리어</option>
                                <option value="103">생활/주방</option>
                                <option value="104">유아동</option>
                                <option value="105">여성패션/잡화</option>
                                <option value="106">남성패션/잡화</option>
                                <option value="107">뷰티/미용</option>
                                <option value="108">스포츠/레저</option>
                                <option value="109">취미/게임/음반</option>
                                <option value="110">도서</option>
                                <option value="111">티켓/교환권</option>
                                <option value="112">가공식품</option>
                                <option value="113">건강기능식품</option>
                                <option value="114">반려동물용품</option>
                                <option value="115">식물</option>
                                <option value="116">기타 중고 물품</option>
                            </select>
                        </div>

                        <div class="right-input">
                            <label for="price">가격 : </label> <input type="text" id="price" name="price" maxlength="10">
                        </div>

                        <div class="right-input">
                            <label for="board_content">설명글</label>
                            <textarea name="board_content" id="board_content" maxlength="2000"></textarea>
                        </div>

                        <div class="right-input">
                            <label for="">거래 방식</label>
                            <div class="trade_radio">
                                <div class="radio-group">
                                    <div class="radio">
                                        <input type="radio" id="trade_0" name="how_trade" value="0">
                                        <label for="trade_0">직거래</label>
                                    </div>
                                    <div class="radio">
                                        <input type="radio" id="trade_1" name="how_trade" value="1">
                                        <label for="trade_1">택배거래</label>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="right-input">
                            <label for="address">주소</label>
                            <div class="input-group">
                                <input type="text" id="trade_address" name="trade_address" placeholder="주소를 입력하세요."
                                    readonly required> <input class="" type="button"
                                    onclick="sample5_execDaumPostcode()" value="주소 검색" id="addressBtn">
                            </div>
                        </div>

                        <button id="postBtn">등록하기</button>
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
            <!-- 카카오 맵 주소 API -->
            <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
            <script
                src="//dapi.kakao.com/v2/maps/sdk.js?appkey=6494369aebd01baf926e57a1affa1288&libraries=services"></script>




            <script>
                function sample5_execDaumPostcode() {
                    new daum.Postcode({
                        oncomplete: function (data) {
                            var addr = data.address; // 최종 주소 변수

                            // 주소 정보를 해당 필드에 넣는다.
                            document.getElementById("trade_address").value = addr;

                        }
                    }).open();
                }


                function formatNumberWithComma(value) {
                    // 숫자가 아닌 문자를 제거하고 숫자 형태로 변환
                    const numericValue = value.replace(/\D/g, '');
                    // 3자리마다 콤마 추가
                    return numericValue.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
                }

                // 가격 입력 필드에 이벤트 리스너 추가
                document.getElementById('price').addEventListener('input', function (event) {
                    // 현재 입력값을 포맷팅
                    const formattedValue = formatNumberWithComma(event.target.value);
                    // 포맷팅된 값으로 필드 업데이트
                    event.target.value = formattedValue;
                });


                // 게시글 등록 ajax 요청
                $('#postBtn').on("click", function (event) {

                    event.preventDefault();

                    const maxFileSize = 5 * 1024 * 1024; // 최대 파일 크기 5MB (5MB = 5 * 1024 * 1024 bytes)
                    const files = document.getElementById('board_images').files;


                    const board_Title1 = $('#board_title').val();

                    const price1 = $('#price').val().replace(/,/g, '');

                    console.log("가격 : ", price1)

                    const board_Content1 = $('#board_content').val();

                    const address1 = $('#trade_address').val();

                    if (board_Title1 === "") {
                        alert("제목을 입력해주세요.");
                        return;
                    }

                    if (board_Content1 === "") {
                        alert("내용을 입력해주세요.");
                        return;
                    }

                    // 가격
                    if (!window.common.validateInput("price", price1)) {
                        alert("잘못된 가격 형식 입니다.");
                        return;
                    }

                    if(address1 === ""){
                        alert("주소를 입력해주세요.");
                        return;
                    }



                    const formData = new FormData();

                    // 라디오 버튼에서 선택된 값 가져오기
                    const howTrade = $('input[name="how_trade"]:checked').val();

                    if(howTrade != 0 && howTrade != 1){
                        alert("거래 방식을 선택해주세요");
                        return;
                    }

                    if (files.length === 0 || files.length > 6) {
                        alert("이미지를 선택해주세요.");
                        return;
                    }

                    // 여러 이미지를 서버에 전송
                    Array.from(files).forEach((file) => {
                        if (file.size > maxFileSize) {
                            alert("파일 크기는 최대 5MB 이하입니다.")
                            return;
                        }
                        formData.append("board_Images", file) // 이미지 파일을 FormDate에 추가
                    });

                    formData.append("board_Title", $('#board_title').val());
                    formData.append("category_Id", $('#category_id').val());
                    formData.append("price", price1);
                    formData.append("board_Content", $('#board_content').val());
                    formData.append("how_Trade", howTrade);
                    formData.append("trade_Address", $('#trade_address').val());

                    $.ajax({
                        url: '/ehr/product_board/post.do',
                        method: 'POST',
                        processData: false,
                        contentType: false,
                        data: formData,
                        success: function (response) {
                            console.log(response.messageId + response.message);
                            if (response.messageId === 1) {
                                alert(response.message);
                                window.location.href = '/ehr/main.do' // 페이지 새로고침
                            } else {
                                alert(response.message);
                            }
                        },
                        error: function (xhr, status, error) {
                            console.error("Error Status: ", xhr.status);
                            console.error("Error Response: ", xhr.responseText);
                            alert('게시글 등록 중 오류가 발생하였습니다.');
                        }
                    }); // ajax 요청 -- END

                }); // 이미지 업데이트 ajax 요청 -- END

            </script>


        </body>

        </html>