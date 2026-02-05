document.addEventListener("DOMContentLoaded", function () {

  const modalReport = document.querySelector('.modalReport');
  const reportBtn = document.getElementById('reportBtn');
  const closeReportBtn = document.querySelector('.closeReportBtn');
  const reportCategory = document.querySelector('#reportCategory');
  const reportReason = document.querySelector('#reportReason');
  const doReportBtn = document.querySelector('.doReportBtn');

  reportBtn.addEventListener("click", (event) => {
    event.preventDefault();
    modalReport.style.display = "flex";
  });

  function closeModal() {
    modalReport.style.display = "none";
    reportCategory.selectedIndex = 0;
    reportReason.value = '';
  }

  closeReportBtn.addEventListener("click", (event) => {
    event.preventDefault();
    closeModal();
  });

  window.addEventListener("click", (event) => {
    event.preventDefault();
    if (event.target === modalReport) {
      closeModal();
    }
  });

  doReportBtn.addEventListener("click", (event) => {
    event.preventDefault();
    
    if (reportCategory.selectedIndex === 0) {
      alert("신고 사유를 선택해주세요.");
      return;
    }
    if (reportReason.value.trim() === '') {
      alert("상세 설명란을 작성해주세요.");
      return;
    }

    const checkReport = confirm("신고 내용은 도토리 마켓 이용약관 및 정책에 의해서 처리되며,\n허위신고 시 도토리 마켓 이용이 제한될 수 있습니다.");

    if(!checkReport){
      return;
    }

    // 신고
    $.ajax({
      url: '/ehr/report/product_Board.do',  
      type: 'POST',
      contentType: 'application/json',
      data: JSON.stringify({
        board_Id: board_Id,
        report_Category: reportCategory.value,
        report_Reason: reportReason.value
      }),
      success: function (response) {
        if (response.messageId === 1) {
          alert(response.message);
          closeModal();  // Close the modal after successful submission
        } else if (response.messageId === 2) {
          const checkLogin = confirm("로그인이 필요합니다. 로그인하시겠습니까?");

          if(checkLogin) {
          window.location.href = "/ehr/user/login/login.do"
          }
           
        } else {
          alert(response.message);
        }
      },
      error: function () {
        alert("신고 접수 중 서버 오류가 발생했습니다.");
      }
    });
  });

});