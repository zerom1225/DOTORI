document.addEventListener("DOMContentLoaded", function () {

    const previewContainer = document.getElementById("previewImages");
    const imageInput = document.getElementById("board_images");
    const dotsContainer = document.createElement("div");
    dotsContainer.classList.add("dots-container");

    let images = [];
    let currentIndex = 0;
    let isDragging = false;
    let startX = 0;
    let currentTranslate = 0;
    let prevTranslate = 0;

    const rawImageNames = imageInput.getAttribute("data-images");

    let imageNames = rawImageNames
        .replace(/^\[|\]$/g, "") // 대괄호 제거
        .split(",") // 쉼표로 나누기
        .map(name => name.trim()); // 공백 제거

    const imageBasePath = "/ehr/board_image/";
    images = imageNames.map(name => imageBasePath + name);


    renderImages();
    renderDots();
    updateDots();

    // 이미지 렌더링
    function renderImages() {
        previewContainer.innerHTML = ""; // 초기화
        previewContainer.style.position = "relative";
        previewContainer.style.overflow = "hidden";

        images.forEach((src, index) => {
            const img = document.createElement("img");
            img.src = src;
            img.style.width = "100%";
            img.style.height = "100%";
            img.style.objectFit = "contain";
            img.style.position = "absolute";
            img.style.left = `${index * 100}%`;
            img.style.transition = "transform 0.3s ease";
            img.dataset.index = index;
            previewContainer.appendChild(img);
        });

        previewContainer.appendChild(dotsContainer);
    }

    // 점(dot) 네비게이션 렌더링
    function renderDots() {
        dotsContainer.innerHTML = ""; // 초기화
        images.forEach((_, index) => {
            const dot = document.createElement("span");
            dot.classList.add("dot");
            if (index === currentIndex) dot.classList.add("active");
            dotsContainer.appendChild(dot);
        });
    }

    // 점(dot) 네비게이션 업데이트
    function updateDots() {
        const dots = dotsContainer.querySelectorAll(".dot");
        dots.forEach((dot, index) => {
            dot.classList.toggle("active", index === currentIndex);
        });
    }

    // 마우스 드래그 시작
    previewContainer.addEventListener("mousedown", (e) => {
        e.preventDefault();
        startX = e.clientX;
        isDragging = true;
        prevTranslate = -currentIndex * 100;
        previewContainer.style.cursor = "grabbing";
    });

    // 마우스 드래그 동작
    previewContainer.addEventListener("mousemove", (e) => {
        if (!isDragging) return;

        const diffX = e.clientX - startX;
        currentTranslate = prevTranslate + (diffX / previewContainer.offsetWidth) * 100;
        setContainerTransform(currentTranslate);
    });

    // 드래그 종료
    ["mouseup", "mouseleave"].forEach((event) => {
        previewContainer.addEventListener(event, (e) => {
            if (!isDragging) return;

            const diffX = e.clientX - startX;

            if (diffX < -50 && currentIndex < images.length - 1) {
                currentIndex++; // 왼쪽으로 스와이프
            } else if (diffX > 50 && currentIndex > 0) {
                currentIndex--; // 오른쪽으로 스와이프
            }

            setContainerTransform(-currentIndex * 100);
            updateDots();

            isDragging = false;
            previewContainer.style.cursor = "grab";
        });
    });

    // 이미지 이동 설정
    function setContainerTransform(translatePercent) {
        const images = previewContainer.querySelectorAll("img");
        images.forEach((img, index) => {
            img.style.transform = `translateX(${translatePercent || -currentIndex * 100}%)`;
        });
    }

    // 채팅하기 버튼 
    $('#chatBtn').on('click', function (event) {

        event.preventDefault();

        // http 요청
        $.ajax({
            url: '/ehr/chat/check_login_exist.do',
            method: 'POST',
            data: { board_Id: board_Id },
            success: function (response) {
                if (response.messageId === 0) {
                    alert(response.message);

                } else if (response.messageId === 1) {
                   const chat_Id = response.message;
                   window.location.href = "/ehr/chat/" + chat_Id + ".do";

                } else if (response.messageId === 2) {
                    window.location.href = "/ehr/chat/create_chat/"+ board_Id + ".do";

                } else if (response.messageId === 3) {
                    const checkLogin = confirm("로그인이 필요합니다. 로그인하시겠습니까?");

                    if (checkLogin) {
                        window.location.href = "/ehr/user/login/login.do";
                    }

                }
            },
                error: function () {
                    alert("서버 요청 중 오류가 발생하였습니다.");
                }
            }); // http 요청 -- end

    }); // 로그아웃 -- end





});
