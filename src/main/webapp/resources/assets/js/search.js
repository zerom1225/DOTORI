

const itemsPerpage = 20;



// 거래 가능만 보기 체크박스 값 가져오기
function getBoard_Status_CheckedValue() {
  const checkbox = document.querySelector(".checkboxW input[type='checkbox']");
  let checkedValue = 0;

  if (checkbox.checked) {
    checkedValue = 1;
  }

  return checkedValue;
}

// 라디오 버튼에서 선택된 값 가져오기기
function getSelectedRadioValue(radioName) {
  const selectedRadio = document.querySelector(`input[name="${radioName}"]:checked`);
  return selectedRadio ? selectedRadio.value : null; // 선택되지 않았으면 null 반환 
}

function filtering_Reset() {

  // 체크박스 초기화
  const checkbox = document.querySelector(".checkboxW input[type='checkbox']");
  checkbox.checked = false;

  const radioBtns = document.querySelectorAll("input[type='radio']");
  radioBtns.forEach((radioBtn) => {
    radioBtn.checked = false;
  });

}

function getCategory_Name(category_Id) {

  if (category_Id == 100) {
    return "디지털기기"
  } else if (category_Id == 101) {
    return "생활가전"
  } else if (category_Id == 102) {
    return "가구/인테리어"
  } else if (category_Id == 103) {
    return "생활/주방"
  } else if (category_Id == 104) {
    return "유아동"
  } else if (category_Id == 105) {
    return "여성패션/잡화"
  } else if (category_Id == 106) {
    return "남성패션/잡화"
  } else if (category_Id == 107) {
    return "뷰티/미용"
  } else if (category_Id == 108) {
    return "스포츠/레저"
  } else if (category_Id == 109) {
    return "취미/게임/음반"
  } else if (category_Id == 110) {
    return "도서"
  } else if (category_Id == 111) {
    return "티켓/교환권"
  } else if (category_Id == 112) {
    return "가공식품"
  } else if (category_Id == 113) {
    return "건강기능식품"
  } else if (category_Id == 114) {
    return "반려동물용품"
  } else if (category_Id == 115) {
    return "식물"
  } else if (category_Id == 116) {
    return "기타 중고 물품"
  } else {
    return null;
  }
}

function getOrder_Name(order) {

  if (order === "mod_date_DESC") {
    return "최신순";
  } else if (order === "price_DESC") {
    return "고가순";
  } else if (order === "price_ASC") {
    return "저가순";
  } else {
    return null;
  }

}


// 상품 렌더링 함수
function renderProductItems(productGrid, products) {
  products.forEach(product => {
    const productElement = document.createElement("div");
    const formattedPrice = product.price.toLocaleString();
    productElement.className = "product";
    productElement.innerHTML = `
            <img src="/ehr/board_image/${product.board_Image?.[0] || 'default.png'}" alt="${product.board_Title}">
            <h3>${product.board_Title}</h3>
            <h2>${formattedPrice}원</h2>
            <p>${product.munic_Address}</p>                        
        `;
    productElement.setAttribute("data-id", product.board_Id);
    console.log(product);
    productGrid.appendChild(productElement);
  });
} // 상품 렌더링 함수 -- END

// 페이지 네이션 렌더링 함수
function renderPagination(pagination, totalItems, page) {
  const totalPages = Math.ceil(totalItems / itemsPerpage);
  const rangeSize = 10; // 한 번에 보여줄 페이지 번호 개수
  const currentRangeStart = Math.floor((page - 1) / rangeSize) * rangeSize + 1; // 현재 범위의 시작 페이지 번호
  const currentRangeEnd = Math.min(
    currentRangeStart + rangeSize - 1,
    totalPages
  ); // 현재 범위의 끝 페이지 번호

  pagination.innerHTML = "";

  // 왼쪽 화살표 버튼
  if (currentRangeStart > 1) {
    const leftArrow = document.createElement("button");
    leftArrow.textContent = "←";
    leftArrow.className = "arrow";
    leftArrow.addEventListener("click", function () {
      renderPagination(
        pagination,
        totalItems,
        currentRangeStart - rangeSize
      );
    });
    pagination.appendChild(leftArrow);
  }

  // 페이지 번호 버튼
  for (let i = currentRangeStart; i <= currentRangeEnd; i++) {
    const button = document.createElement("button");
    button.type = "button"; // 기본적으로 버튼은 submit 동작하지 않음
    button.textContent = i;
    button.className = page === i ? "active" : "";
    button.addEventListener("click", function (event) {
      event.preventDefault();
      renderProductsWithPage(i);
    });
    button.setAttribute("data-page", i); // 페이지 번호 저장
    pagination.appendChild(button);
  }

  // 오른쪽 화살표 버튼
  if (currentRangeEnd < totalPages) {
    const rightArrow = document.createElement("button");
    rightArrow.textContent = "→";
    rightArrow.className = "arrow";
    rightArrow.addEventListener("click", function () {
      renderPagination(pagination, totalItems, currentRangeEnd + 1);
    });
    pagination.appendChild(rightArrow);
  }

} // 페이지 네이션 렌더링 함수 -- NED


// 필터링된 검색 결과 페이지 네이션 렌더링 함수
function renderPagination_WithFiltering(pagination, totalItems, page) {
  const totalPages = Math.ceil(totalItems / itemsPerpage);
  const rangeSize = 10; // 한 번에 보여줄 페이지 번호 개수
  const currentRangeStart = Math.floor((page - 1) / rangeSize) * rangeSize + 1; // 현재 범위의 시작 페이지 번호
  const currentRangeEnd = Math.min(
    currentRangeStart + rangeSize - 1,
    totalPages
  ); // 현재 범위의 끝 페이지 번호

  pagination.innerHTML = "";

  // 왼쪽 화살표 버튼
  if (currentRangeStart > 1) {
    const leftArrow = document.createElement("button");
    leftArrow.textContent = "←";
    leftArrow.className = "arrow";
    leftArrow.addEventListener("click", function () {
      renderPagination_WithFiltering(
        pagination,
        totalItems,
        currentRangeStart - rangeSize
      );
    });
    pagination.appendChild(leftArrow);
  }

  // 페이지 번호 버튼
  for (let i = currentRangeStart; i <= currentRangeEnd; i++) {
    const button = document.createElement("button");
    button.type = "button"; // 기본적으로 버튼은 submit 동작하지 않음
    button.textContent = i;
    button.className = page === i ? "active" : "";
    button.addEventListener("click", function (event) {
      event.preventDefault();
      renderProductsWithPage_WithFiltering(i);
    });
    button.setAttribute("data-page", i); // 페이지 번호 저장
    pagination.appendChild(button);
  }

  // 오른쪽 화살표 버튼
  if (currentRangeEnd < totalPages) {
    const rightArrow = document.createElement("button");
    rightArrow.textContent = "→";
    rightArrow.className = "arrow";
    rightArrow.addEventListener("click", function () {
      renderPagination_WithFiltering(pagination, totalItems, currentRangeEnd + 1);
    });
    pagination.appendChild(rightArrow);
  }

} // 페이지 네이션 렌더링 함수 -- NED



// 서버에서 상품 목록을 받아와서 렌더링 하는 함수
function loadProducts(page) {
  const productGrid = document.getElementById("product-grid");
  const pagination = document.getElementById("pagination");
  const navbar = document.getElementById("navbar");

  // 요소 존재 여부 확인
  if (!productGrid || !pagination) {
    console.error(`Elements with IDs product-grid or pagination not found.`);
    return;
  }

  productGrid.innerHTML = "";
  pagination.innerHTML = "";
  navbar.innerHTML = "";

  const navbarElement = document.createElement("div");
  navbarElement.className = "searchedResult";
  if (search_Word.trim()) {
    navbarElement.innerHTML = `
       <h1>"${search_Word}" 검색 결과</h1>
  `;
  } else {
    navbarElement.innerHTML = `
    <h1>전체 검색 결과</h1>
  `;
  }
  navbar.appendChild(navbarElement);

  // 서버에 상품 목록을 받아오는 AJAX 요청
  $.ajax({
    url: "/ehr/product_board/search/getproducts.do",
    method: 'GET',
    data: {
      page: page,
      searchWord: search_Word
    },
    success: function (data) {
      console.log("data" + data)
      const products = data.products;
      const totalItems = data.totalItems;
      const page = data.page;


      // 상품 렌더링 함수
      renderProductItems(productGrid, products);

      // 페이지 네이션 렌더링 함수
      renderPagination(pagination, totalItems, page);

    },
    error: function (error) {
      console.error("상품 데이터 가져오기 실패 : ", error)
    }
  }); // AJAX 요청 -- END

} // 서버에서 상품 목록을 받아와서 렌더링 하는 함수 -- END


// 서버에서 필터링된 상품 목록을 받아와서 렌더링 하는 함수
function loadProducts_WithFiltering(page) {
  const productGrid = document.getElementById("product-grid");
  const pagination = document.getElementById("pagination");
  const navbar = document.getElementById("navbar");
  
  const order = getSelectedRadioValue("order");
  const board_Status = getBoard_Status_CheckedValue();
  const trade_Address = getSelectedRadioValue("location");
  const category_Id = getSelectedRadioValue("category");
  const category_Name = getCategory_Name(category_Id);
  const category_Main_Name = getCategory_Name(category_Id_Main);
  const order_Name = getOrder_Name(order);

  if (!order && !board_Status && !trade_Address && !category_Id) {
    alert("필터링 조건을 선택해주세요.");
    return;
  }


  // 요소 존재 여부 확인
  if (!productGrid || !pagination) {
    console.error(`Elements with IDs product-grid or pagination not found.`);
    return;
  }

  productGrid.innerHTML = "";
  pagination.innerHTML = "";
  navbar.innerHTML = "";

  const navbarElement = document.createElement("div");
  navbarElement.className = "searchedResult";

  if (search_Word.trim() && !trade_Address) {
    navbarElement.innerHTML += `
    <h1>"${search_Word}" 검색 결과</h1>
   `;
  } else if (search_Word.trim() && trade_Address) {
    navbarElement.innerHTML += `
      <h1>${trade_Address} 지역 "${search_Word}" 검색 결과</h1>
     `;
  } else if (!search_Word && category_Id_Main !== -1 && !trade_Address && !category_Id) {
    navbarElement.innerHTML += `
     <h1>카테고리 "${category_Main_Name}" 검색 결과</h1>
    `;
  } else if (!search_Word && category_Id_Main !== -1 && trade_Address && !category_Id) {
    navbarElement.innerHTML += `
    <h1>${trade_Address} 지역 카테고리 "${category_Main_Name}" 검색 결과</h1>
   `;
  } else if (!search_Word && category_Id_Main !== -1 && !trade_Address && category_Id) {
    navbarElement.innerHTML += `
     <h1>카테고리 "${category_Name}" 검색 결과</h1>
    `;
  } else if (!search_Word && category_Id_Main !== -1 && trade_Address && category_Id) {
    navbarElement.innerHTML += `
    <h1>${trade_Address} 지역 카테고리 "${category_Name}" 검색 결과</h1>
   `;
  } else if (!search_Word && category_Id_Main === -1 && trade_Address) {
    navbarElement.innerHTML += `
    <h1>${trade_Address} 지역 전체 검색 결과</h1>
   `;
  } else {
    navbarElement.innerHTML += `
    <h1>전체 검색 결과</h1>
   `;
  }

  // order_Name과 category_Name을 한 줄로 출력
  if (order_Name && category_Name) {
    navbarElement.innerHTML += `<p>${category_Name} / ${order_Name}</p>`;
  } else if (order_Name) {
    navbarElement.innerHTML += `<p>${order_Name}</p>`;
  } else if (category_Name) {
    navbarElement.innerHTML += `<p>${category_Name}</p>`;
  }

  navbar.appendChild(navbarElement);


  // 서버에 필터링된 상품 목록을 받아오는 AJAX 요청
  $.ajax({
    url: "/ehr/product_board/search_with_filtering/getproducts.do",
    method: 'GET',
    data: {
      order: order,
      searchWord: search_Word,
      board_Status: board_Status,
      trade_Address: trade_Address,
      category_Id: category_Id,
      page: page
    },
    success: function (data) {
      console.log("data" + data)
      const products = data.products;
      const totalItems = data.totalItems;
      const page = data.page;


      // 상품 렌더링 함수
      renderProductItems(productGrid, products);

      // 페이지 네이션 렌더링 함수
      renderPagination_WithFiltering(pagination, totalItems, page);

    },
    error: function (error) {
      console.error("상품 데이터 가져오기 실패 : ", error)
    }
  }); // AJAX 요청 -- END

} // 서버에서 상품 목록을 받아와서 렌더링 하는 함수 -- END


function renderProductsInitial() {
  loadProducts(1);
}

function renderProductsWithPage(page) {
  loadProducts(page);
}

function renderProductsInitial_WithFiltering() {
  loadProducts_WithFiltering(1);
}

function renderProductsWithPage_WithFiltering(page) {
  loadProducts_WithFiltering(page);
}





document.addEventListener("DOMContentLoaded", () => {

  if (category_Id_Main !== -1) {
    const selectedRadioBtn_Category = document.querySelector(`input[name="category"][value="${category_Id_Main}"]`);

    if (selectedRadioBtn_Category) {
      selectedRadioBtn_Category.checked = true;
    }

  }

  // 필터링 초기화 버튼 이벤트
  $("#filtering_ResetBtn").on("click", filtering_Reset);



  // 필터링 적용 버튼 이벤트
  $("#filteringBtn").on("click", function (event) {

    event.preventDefault();

    renderProductsInitial_WithFiltering();


  });  // 필터링 적용 버튼 이벤트 -- END




  // 상품 클릭 시 수정 OR 상세 페이지 이동 이벤트
  $(document).on("click", ".product", function (event) {
    event.preventDefault();
    const board_Id = $(this).data("id"); // 클릭한 상품의 ID 가져오기
    console.log("data-id(board_Id)" + board_Id)

    // 서버로 게시자와 현재 유저 비교 요청
    $.ajax({
      url: "/ehr/product_board/check_poster.do",
      method: "POST",
      data: { board_Id: board_Id },
      success: function (response) {
        if (response.error) {
          alert(response.error);
          return;
        }

        // 게시자와 현재 유저를 비교하여 true면 수정, false면 상세 페이지로 이동
        if (response.isOwner) {
          window.location.href = `/ehr/product_board/edit=${response.board_Id}.do`;
        } else {
          window.location.href = `/ehr/product_board/detail=${response.board_Id}.do`;
        }
      },
      error: function () {
        alert("서버 요청 중 오류가 발생하였습니다.")
      }

    }); // 서버로 게시자와 현재 유저 비교 요청 -- END

  }); // 상품 클릭 시 수정 OR 상세 페이지 이동 이벤트 -- END


  if (category_Id_Main === -1) {

    renderProductsInitial();

  } else {

    renderProductsInitial_WithFiltering();

  }



});