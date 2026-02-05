window.common = {
  // 패턴 정의
  patterns: {
    email: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
    auth_code: /^\d{6}$/,
    name: /^[가-힣]{2,}$/,
    phone: /^\d{10,}$/,
    nickname: /^[a-z0-9가-힣]{2,}$/,
    price: /^\d+$/
  },

  // 검증 함수
  validateInput(type, value) {
    const pattern = this.patterns[type];

    if(!value){
      return false;
    }

    return pattern.test(value);
  },

  
};