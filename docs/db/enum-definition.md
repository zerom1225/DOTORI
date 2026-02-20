# 상태값 ENUM 표

## 1. MEMBER

### 1.1 MEMBER.role
| code | label | 설명     |
| ---: | ----- | ------ |
|    0 | USER  | 일반 사용자 |
|    1 | ADMIN | 관리자    |

### DB CHECK
```sql
ROLE IN (0, 1)
```

### 1.2 MEMBER.status
| code | label | 설명     |
| ---: | ----- | ------ |
|    0 | ACTIVE  | 정상 회원 |
|    1 | WITHDRAWN | 탈퇴(비활성)   |
|    2 | SUSPENDED | 정지(운영자 제재)  |

### DB CHECK
```sql
STATUS IN (0, 1, 2)
```

## 2. PRODUCT_BOARD

### 2.1 PRODUCT_BOARD.how_trade
| code | label | 설명     |
| ---: | ----- | ------ |
|    0 | DIRECT  | 직거래 |
|    1 | DELIVERY | 택배거래  |
|    2 | BOTH | 둘 다 가능  |

### DB CHECK
```sql
HOW_TRADE IN (0, 1, 2)
```

### 2.2 PRODUCT_BOARD.board_status
| code | label | 설명     |
| ---: | ----- | ------ |
|    0 |  ON_SALE | 판매중 |
|    1 | SOLD | 거래완료  |
|    2 | DELETED | 삭제(비활성)  |
|    3 | HIDDEN | 숨김(운영자)(선택)  |

### DB CHECK
```sql
BOARD_STATUS IN (0, 1, 2, 3)
```

## 3. TRANSACTION

### 3.1 TRANSACTION.status
| code | label | 설명     |
| ---: | ----- | ------ |
|    0 |  COMPLETED | 거래완료 |
|    1 | CANCELED | 거래취소(비활성)  |

### DB CHECK
```sql
STATUS IN (0, 1)
```

## 4. CHAT

### 4.1 CHAT.is_active
| code | label | 설명     |
| ---: | ----- | ------ |
|    0 |  ACTIVE | 활성 |
|    1 | INACTIVE | 비활성  |

### DB CHECK
```sql
IS_ACTIVE IN (0, 1)
```

## 5. BOARD_REPORT

### 5.1 BOARD_REPORT.report_category
| code | label | 설명     |
| ---: | ----- | ------ |
|    0 | SPAM | 스팸 |
|    1 | ILLEGAL | 거래금지품목  |
|    2 | FRAUD | 사기 의심 |
|    3 | ABUSE | 불쾌한 내용  |

### DB CHECK
```sql
REPORT_CATEGORY IN (0, 1, 2, 3)
```

### 5.2 BOARD_REPORT.report_status
| code | label | 설명     |
| ---: | ----- | ------ |
|    0 | RECEIVED | 접수중 |
|    1 | RESOLVED | 처리완료  |
|    2 | IN_REVIEW | 검토중 |
|    3 | REJECTED | 반려  |

### DB CHECK
```sql
REPORT_STATUS IN (0, 1, 2, 3)
```

---

### **이 enum 표를 “취업 포인트”로 바꾸는 3가지 추가 작업**

### A) Enum 표를 문서에 박고, DB CHECK랑 1:1로 연결

- enum 표가 곧 CHECK 제약의 스펙이 되게 “문서 ↔ DB” 싱크가 맞는 걸 보여줌

### B) 코드에서는 Java Enum으로 만들고 “변환 함수”를 둬라

- 예: `BoardStatus.fromCode(int code)`

- DB 저장은 code, 로직은 enum

### C) 상태 전이 테스트(이게 진짜 강함)

- 예: SOLD 상태에서 다시 RESERVED로 바꾸려 하면 서비스에서 막기

- “DB는 값 범위만 막고, 전이 규칙은 서비스가 막는다” →  설계 이해도가 드러남



