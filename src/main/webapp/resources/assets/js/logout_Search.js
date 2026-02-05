

function performSearch(searchWord) {
  

  $.ajax({
    url: "/ehr/product_board/search.do",
    method: "GET",
    data: { searchWord: searchWord },
    success: function () {
      window.location.href = `/ehr/product_board/search.do?searchWord=${searchWord}`;
    },
  });
}


document.addEventListener("DOMContentLoaded", () => {

  const searchInput = document.getElementById("search");
  const loginBtn = document.getElementById("loginBtn");
  const logoutBtn = document.getElementById("logoutBtn");

  // 세션 체크 http 요청
  $.ajax({
    url: '/ehr/user/checksession.do',
    method: 'GET',
    success: function (response) {

      if(response.messageId == 0) {
        loginBtn.classList.remove("hidden");
        logoutBtn.classList.add("hidden")
      } else if(response.messageId == 1){
        loginBtn.classList.add("hidden");
        logoutBtn.classList.remove("hidden");
      }      
    },
    error: function () {
      alert("세션 체크 오류");
    }
  }); // http 요청 -- end





  // 로그 아웃 
  $('#logoutBtn').on('click', function (event) {

    event.preventDefault();

    // http 요청
    $.ajax({
      url: '/ehr/user/login/logout.do',
      method: 'POST',
      contentType: 'application/json',
      success: function (response) {
        alert(response.message);
        window.location.href = "/ehr/main.do"
      },
      error: function () {
        $('#loginMessage').text(response.message);
      }
    }); // http 요청 -- end

  }); // 로그아웃 -- end

  // 검색 버튼 이벤트
  $('#searchBtn').on('click', function (event) {


    const search_Word_Main = searchInput.value.replace(/\s+/g, '');
    console.log("검색어 : ", search_Word_Main);

    event.preventDefault();


    performSearch(search_Word_Main);

  }); // 검색 버튼 이벤트 --END

  // 입력창 엔터 검색 이벤트
  searchInput.addEventListener("keydown", function (event) {

    
    const search_Word_Main = searchInput.value.replace(/\s+/g, '');



    if (event.key == "Enter") {
      event.preventDefault();

      performSearch(search_Word_Main);
    }

  }); // 입력창 엔터 검색 이벤트 -- END


}); // DOMContentLoaded 이벤트 -- END

