document.addEventListener('DOMContentLoaded', () => {
  const container = document.getElementById('categoriesContainer');
  const itemWidth = 220; // 각 항목의 너비 (CSS에서 설정된 고정값)
  const itemsToShow = 5; // 화면에 보여질 항목 수
  const itemsToScroll = 5; // 클릭 시 스크롤할 항목 수
  let scrollPosition = 0;

  const prevBtn = document.getElementById('prevBtn');
  const nextBtn = document.getElementById('nextBtn');

  // 오른쪽 버튼 클릭
  nextBtn.addEventListener('click', () => {
      const maxScroll = (container.children.length - itemsToShow) * itemWidth; // 최대 스크롤 가능 위치
      scrollPosition += itemsToScroll * itemWidth;
      if (scrollPosition > maxScroll) scrollPosition = maxScroll; // 최대치 이상 스크롤 방지
      container.style.transform = `translateX(-${Math.round(scrollPosition / itemWidth) * itemWidth}px)`; // 정확한 위치 조정
  });

  // 왼쪽 버튼 클릭
  prevBtn.addEventListener('click', () => {
      scrollPosition -= itemsToScroll * itemWidth;
      if (scrollPosition < 0) scrollPosition = 0; // 최소치 이하 스크롤 방지
      container.style.transform = `translateX(-${Math.round(scrollPosition / itemWidth) * itemWidth}px)`; // 정확한 위치 조정
  });
});



