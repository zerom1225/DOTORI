<%@ page session="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <html>

        <head>
            <meta charset="UTF-8" />
            <title>1:1 채팅</title>
            <script src="/ehr/resources/assets/js/jquery_3_7_1.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1.5.1/dist/sockjs.min.js"></script>
            <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
                rel="stylesheet">
            <link rel="stylesheet" href="/ehr/resources/assets/css/chat_talk.css" />
        </head>

        <body>


            <div class="container">
                <header>
                    <div class="back-button" onclick="history.back();">
                        <i class="fas fa-arrow-left"></i>
                    </div>
                    <div class="title">${nickname}</div>
                    <div class="exit-button" id="exit-button">나가기</div>
                </header>

                <!-- 채팅방 정보 -->
                <div class="chat-info">
                    <img src="/ehr/board_image/${board_Image}" alt="프로필 이미지" class="chat-image">
                    <div class="chat-details">
                        <div class="chat-title">${chatDTO.product_Board.board_Title}</div>
                        <div class="chat-price">${chatDTO.product_Board.price}원</div>
                    </div>
                </div>

                <!-- 채팅 내용 -->
                <div class="chat-room" id="chat-room">
                    <!-- <div class="message received">
                        <div class="bubble">안녕하세요!</div>
                        <div class="timestamp">오후 3:30</div>
                    </div>
                    <div class="message sent">
                        <div class="timestamp">오후 3:31</div>
                        <div class="bubble">안녕하세요! 반갑습니다.</div>
                    </div> -->

                </div>

                <!-- 입력창 -->
                <footer class="chat-input">
                    <label for="message"></label>
                    <input type="text" id="message" placeholder="메시지를 입력하세요" maxlength="1000">
                    <button id="sendMessageButton">전송</button>
                </footer>
            </div>

            <script>

                const chat_Messages = JSON.parse(`${chat_Messages}`);
                const current_User = JSON.parse(`${current_User}`);
                const chat_Id1 = ${ chat_Id };
                const user_Id = current_User.user_Id;
                const path = "/ehr/chatRoom?chat_Id=" + chat_Id1 + "&user_Id=" + user_Id;

                let sock;


                // 현재 시간을 가져와서 "YYYY/MM/DD HH:mm" 형식으로 출력
                function getCurrentFormattedTime() {
                    const now = new Date();

                    // 연도, 월, 일, 시간, 분 가져오기
                    const year = now.getFullYear();
                    const month = String(now.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1 필요
                    const date = String(now.getDate()).padStart(2, '0');
                    const hours = String(now.getHours()).padStart(2, '0');
                    const minutes = String(now.getMinutes()).padStart(2, '0');

                    // 포맷팅
                    return `\${year}/\${month}/\${date} \${hours}:\${minutes}`;
                }

                // 메시지를 전송하는 함수
                function sendMessage() {
                    let messageContent = $("#message").val();
                    if (messageContent) {

                        // 메시지를 서버로 전송
                        sock.send(messageContent);

                        // 자신의 메시지를 화면에 출력
                        const chatRoomContainer = document.getElementById("chat-room");
                        const chat_MessageItem = document.createElement("div");
                        const current_Time = getCurrentFormattedTime();

                        console.log("발신 시간 : ", current_Time);

                        chat_MessageItem.className = "message sent";
                        chat_MessageItem.innerHTML = `
                        <div class="timestamp">\${current_Time}</div>
                        <div class="bubble">\${messageContent}</div>                            
                        `;
                        chatRoomContainer.appendChild(chat_MessageItem);

                        $("#message").val('');
                    } else {
                        alert("메시지를 입력해주세요.");
                    }
                }

                // 웹소켓 연결전 초기화 함수
                function resetWebSocket() {
                    if (sock) {
                        if (sock.readyState === WebSocket.OPEN) {
                            sock.close();
                        }
                        sock = null; // WebSocket 객체 초기화
                    }
                }


                function connectWebSocket() {
                    if (sock && sock.readyState !== WebSocket.CLOSED) {
                        console.log("기존 WebSocket 연결이 여전히 활성 상태입니다. 새로운 연결을 생성하지 않습니다.");
                        return;
                    }

                    sock = new WebSocket(path);  // WebSocket 연결

                    sock.onopen = function () {
                        console.log("WebSocket 연결 성공");
                    };

                    $("#sendMessageButton").click(function () {
                        sendMessage();
                    });

                    $("#message").keypress(function (event) {
                        if (event.keyCode == 13) {  // 엔터키
                            event.preventDefault();
                            sendMessage();
                        }
                    });

                    // 상대로부터 메시지를 받을때 실행되는 함수수
                    sock.onmessage = function (e) {
                        console.log("e.data : ", e.data)

                        if(e.data === "상대가 채팅방을 나갔습니다.") {
                            alert(e.data);
                            history.replaceState(null, null, "/ehr/main.do");
                            window.location.href = "/ehr/main.do";
                            return;
                        }

                        // 서버에서 온 메시지, 상대방 메시지로 표시
                        const chatRoomContainer = document.getElementById("chat-room");
                        const chat_MessageItem = document.createElement("div");
                        const current_Time = getCurrentFormattedTime();

                        console.log("수신 시간 : ", current_Time);

                        chat_MessageItem.className = "message received";
                        chat_MessageItem.innerHTML = `
                        <div class="bubble">\${e.data}</div>   
                        <div class="timestamp">\${current_Time}</div>                         
                        `;
                        chatRoomContainer.appendChild(chat_MessageItem);
                    };

                    // 소켓이 닫혔을 때 실행되는 함수
                    sock.onclose = function () {
                        console.warn("WebSocket 연결이 종료되었습니다.");
                    };

                    // 웹 소켓 연결의 오류가 발생하였을때 실행되는 함수수
                    sock.onerror = function (error) {
                        console.error("WebSocket 에러 발생:", error);
                    };

                }

                // 뒤로가기 OR 새로 고침 시 소켓 연결 종료
                window.addEventListener("beforeunload", function () {
                    if (sock && sock.readyState === WebSocket.OPEN) {
                        sock.close();
                    }
                });


                document.addEventListener("DOMContentLoaded", function () {

                    const chatRoomContainer = document.getElementById("chat-room");

                    chat_Messages.forEach(chat_Message => {

                        const chat_MessageItem = document.createElement("div");

                        if (parseInt(chat_Message.sender_Id) === parseInt(current_User.user_Id)) {

                            chat_MessageItem.className = "message sent";
                            chat_MessageItem.innerHTML = `
                            <div class="timestamp">\${chat_Message.formattedDate}</div>
                            <div class="bubble">\${chat_Message.chat_Content}</div>                            
                            `;



                        } else if (parseInt(chat_Message.sender_Id) !== parseInt(current_User.user_Id)) {
                            chat_MessageItem.className = "message received";
                            chat_MessageItem.innerHTML = `
                            <div class="bubble">\${chat_Message.chat_Content}</div> 
                            <div class="timestamp">\${chat_Message.formattedDate}</div>
                            `;
                        }

                        chatRoomContainer.appendChild(chat_MessageItem);

                    });

                    resetWebSocket();
                    connectWebSocket();


                    $("#exit-button").on("click", function (event) {

                        event.preventDefault();

                        const checkDelete = confirm("채팅방을 나가시겠습니까?");

                        if (!checkDelete) {
                            return;
                        }

                        // 나가기 버튼 클릭 시 웹소켓을 통해 나가기 요청 메세지를 전송
                        if(sock && sock.readyState === WebSocket.OPEN) {
                            sock.send("exit#dotori#" + chat_Id1);
                            history.replaceState(null, null, "/ehr/main.do");
                            window.location.href = "/ehr/main.do";
                        }

                    });

                });
            </script>
        </body>

        </html>