document.addEventListener("DOMContentLoaded", function () {


  const statusBtn = document.getElementById('statusBtn');
  const userCategory = document.querySelector('#userCategory');

  let buyers;

  // 선택 가능한 구매자 리스트 받아오기
  $.ajax({
    url: '/ehr/chat/get_buyerlist.do',  
    method: 'POST',
    data: {
      board_Id: board_Id,
    },
    success: function (response) {

      buyers = JSON.parse(response.buyers);
      console.log("buyers : ", buyers);

      buyers.forEach(buyer => {
        const option = document.createElement("option");
        option.value = `${buyer.user_Id}`;
        option.text = `${buyer.nickname}`;

        console.log("구매자 ID : ", `${buyer.user_Id}`);
        console.log("구매자 닉네임 : ", `${buyer.nickname}`);

        userCategory.appendChild(option);
      });

    },
    error: function () {
      alert("구매자 정보를 받아오는 중 서버 오류가 발생했습니다.");
    }
  });

  
  statusBtn.addEventListener("click", (event) => {
    event.preventDefault();

    if (userCategory.selectedIndex === 0) {
      alert("구매자를 선택해주세요.");
      return;
    }

    const checkReport = confirm("판매 완료 상태 변경 후 되돌릴 수 없습니다.\n 판매 완료 상태로 변경하시겠습니까?");

    if (!checkReport) {
      return;
    }

    $.ajax({
      url: '/ehr/product_board/change_board_status.do',
      type: 'POST',
      data: {
        board_Id: board_Id,
        selected_Buyer_Id: userCategory.value
      },
      success: function (response) {
        if (response.messageId === 1) {
          alert(response.message);
          window.location.reload();
        } else {
          alert(response.message);
        }
      },
      error: function () {
        alert("판매 완료 상태로 변경 중 서버 오류가 발생했습니다.");
      }
    });
  });

});