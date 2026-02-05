const itemsPerpage = 12; // 페이지 당 아이템 개수 12개(4 X 3)

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
    productGrid.appendChild(productElement);
  });
} // 상품 렌더링 함수 -- END

// 페이지 네이션 렌더링 함수
function renderPagination(pagination, tabIndex, totalItems, page) {
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
        tabIndex,
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
      renderProductsWithPage(tabIndex, i);
    });
    button.setAttribute("data-page", i); // 페이지 번호 저장
    button.setAttribute("data-tabindex", tabIndex); // 탭 인덱스 저장
    pagination.appendChild(button);
  }

  // 오른쪽 화살표 버튼
  if (currentRangeEnd < totalPages) {
    const rightArrow = document.createElement("button");
    rightArrow.textContent = "→";
    rightArrow.className = "arrow";
    rightArrow.addEventListener("click", function () {
      renderPagination(pagination, tabIndex, totalItems, currentRangeEnd + 1);
    });
    pagination.appendChild(rightArrow);
  }
} // 페이지 네이션 렌더링 함수 -- NED





// 서버에서 상품 목록을 받아와서 렌더링 하는 함수
function loadProducts(tabIndex, page) {
  const productGrid = document.getElementById("product-grid-" + tabIndex);
  const pagination = document.getElementById("pagination-" + tabIndex);

  // 요소 존재 여부 확인
  if (!productGrid || !pagination) {
    console.error(`Elements with IDs product-grid-${tabIndex} or pagination-${tabIndex} not found.`);
    return;
  }

  productGrid.innerHTML = "";
  pagination.innerHTML = "";

  // 서버에 상품 목록을 받아오는 AJAX 요청
  $.ajax({
    url: `/ehr/mypage/get_products${tabIndex}.do`,
    method: 'GET',
    data: { page: page },
    success: function (data) {
      console.log("data" + data)
      const products = data.products;
      const totalItems = data.totalItems;
      const page = data.page;


      // 상품 렌더링 함수
      renderProductItems(productGrid, products);

      // 페이지 네이션 렌더링 함수
      renderPagination(pagination, tabIndex, totalItems, page);

    },
    error: function (error) {
      console.error("상품 데이터 가져오기 실패 : ", error)
    }
  }); // AJAX 요청 -- END

} // 서버에서 상품 목록을 받아와서 렌더링 하는 함수 -- END


// 탭 클릭시 상품 및 페이지 네이션 렌더링
function showTab(tabIndex) {
  console.log(`Tab index: ${tabIndex}`);
  const tabLinks = document.querySelectorAll('.tabs ul li') // 탭
  const tabs = document.querySelectorAll('.tab-content'); // 해당 탭의 상품
  tabs.forEach((tab, i) => {
    tabLinks[i].classList.toggle('active', i === tabIndex);
    tab.classList.toggle('active', i === tabIndex);
  });

  renderProductsInitial(tabIndex);

} // 탭 클릭시 상품 및 페이지 네이션 렌더링 -- END

function renderProductsInitial(tabIndex) {
  loadProducts(tabIndex, 1);
}

function renderProductsWithPage(tabIndex, page) {
  loadProducts(tabIndex, page);
}

document.addEventListener("DOMContentLoaded", () => {


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


  showTab(0);
});