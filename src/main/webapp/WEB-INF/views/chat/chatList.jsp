<%@ page session="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html lang="ko">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>채팅 목록</title>
            <script src="/ehr/resources/assets/js/jquery_3_7_1.js"></script>
            <link rel="stylesheet" href="/ehr/resources/assets/css/chat.css" />
        </head>

        <body>
            <div class="container">
                <header>
                    <div class="back-button" onclick="location.href='/ehr/main.do'" >◀</div>
                    <div class="title">채팅 목록</div>
                </header>

                <div class="chat-list" id="chat-list">
                </div>
            </div>


            <script>
                const chat_List = JSON.parse(`${chatList}`);
                const current_User = JSON.parse(`${current_User}`);


                document.addEventListener("DOMContentLoaded", function () {

                    const chatListContainer = document.getElementById("chat-list");

                    chat_List.forEach(chatDTO => {

                        const chat = chatDTO.chat;
                        const board = chatDTO.product_Board;
                        const seller = chatDTO.seller;
                        const buyer = chatDTO.buyer;

                        const chatItem = document.createElement("div");
                        chatItem.className = "chat-item";

                        if (parseInt(current_User.user_Id) === parseInt(chat.seller_Id)) {

                            chatItem.innerHTML = `
                           <div class="profile">
                                <img src="/ehr/user_image/\${buyer.user_Image}" alt="profile">
                           </div>
                           <div class="chat-details">
                                <div class="user-name">\${buyer.nickname}</div>
                                <div class="chat-title">\${board.board_Title}</div>
                           </div>               
                        `;
                        } else if (parseInt(current_User.user_Id) === parseInt(chat.buyer_Id)) {

                            chatItem.innerHTML = `
                           <div class="profile">
                                <img src="/ehr/user_image/\${seller.user_Image}" alt="profile">
                           </div>
                          <div class="chat-details">
                                <div class="user-name">\${seller.nickname}</div>
                                <div class="chat-title">\${board.board_Title}</div>
                          </div>              
                        `;
                        }

                        chatItem.setAttribute("data-id", `\${chat.chat_Id}`);
                        chatListContainer.appendChild(chatItem);

                    });


                    // 채팅 클릭 시 채팅방 이동 이벤트
                    $(document).on("click", ".chat-item", function (event) {
                        event.preventDefault();
                        const chat_Id = $(this).data("id"); // 클릭한 채팅의 ID 가져오기
                        console.log("선택한 채팅 id", chat_Id);

                        window.location.href = "/ehr/chat/" + chat_Id + ".do";


                    }); // 채팅 클릭 시 채팅방 이동 이벤트 -- END


                    // 뒤로가기 감지 이벤트 설정
                    window.addEventListener("popstate", function (event) {
                        console.log("뒤로가기 감지됨");
                        // 특정 페이지로 리다이렉트
                        window.location.replace("/ehr/main.do");
                    });


                });



            </script>

        </body>

        </html>