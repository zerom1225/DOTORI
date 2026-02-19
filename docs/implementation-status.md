# Implementation Status (요구사항 기반 구현 현황)

기준일: 2026-02-19  
프로젝트: 도토리 (중고거래 시스템)

> 현재 상태 메모: **핵심 플로우는 동작**하나, 예외/검증/권한/트랜잭션/테스트가 전반적으로 미흡하여 다수 항목을 `PARTIAL`로 표기했습니다.  
> `(미구현)` 표기된 요구사항은 `TODO`로 분류했습니다.

---

## 상태 정의
- **DONE**: 요구사항 플로우 정상 동작 + 치명적 결함 없음
- **PARTIAL**: 핵심 플로우 동작하나 일부 누락 또는 품질 요소(검증/예외/권한/트랜잭션/테스트) 부족
- **TODO**: 미구현 또는 실행 불가 / 구현 여부 미확인

## 품질 태그(Gaps)
- **V**: Validation(입력 검증)
- **E**: Exception(예외 처리/에러 메시지/에러 응답 표준화)
- **A**: Auth(인증/인가, 소유자 체크)
- **T**: Tx(트랜잭션/동시성/데이터 정합성)
- **S**: Test(단위/통합 테스트)

## Evidence 규칙
- 화면 캡처: `docs/assets/` (예: `run-success-login.png`)
- API 증빙: curl/포스트맨 결과 캡처 또는 응답 JSON
- Code ref: `패키지/클래스#메서드` 형식으로 갱신

---

## 1. 로그인/회원가입

| Req ID | 요구사항 | 상태 | Evidence | Code ref | Gaps | Next action | Priority |
|---|---|---|---|---|---|---|---|
| 1.1 | 회원가입(이메일 인증, 중복 체크) | PARTIAL |  | TBD | V,E,S | 입력값(이메일/전화/닉네임) 검증, 중복가입 예외 표준화, 테스트 추가 | P0 |
| 1.2 | 로그인(+ 비밀번호 찾기 포함) | PARTIAL | docs/assets/run-success-login.png | TBD | E,S | 로그인 실패/락/세션 만료 등 예외 케이스 정리 + 테스트 | P0 |
| 1.3 | 로그아웃 | PARTIAL |  | TBD | S | 세션 무효화/리다이렉트 플로우 점검 + 테스트 | P1 |
| 1.4 | 이메일 찾기(전화번호 기반) | TODO |  | TBD | V,E,S | 기능 구현 또는 현행 구현 여부 확인 후 증빙 추가 | P2 |

---

## 2. 사용자 마이페이지

| Req ID | 요구사항 | 상태 | Evidence | Code ref | Gaps | Next action | Priority |
|---|---|---|---|---|---|---|---|
| 2.1 | 사용자 정보 관리(프로필/이메일/닉네임 수정, 중복 체크) | PARTIAL | docs/assets/run-success-mypage.png | TBD | V,E,A,S | 로그인 사용자만 수정(A), 입력검증/중복검사(V), 실패 응답 표준화(E), 테스트(S) | P0 |
| 2.2 | 회원 탈퇴 | TODO |  | TBD | V,E,A,T,S | 탈퇴 플로우/데이터 처리 정책 정의(소프트/하드), 트랜잭션 적용 | P1 |
| 2.3 | 구매 내역 조회(+ 구매 게시글 링크) | PARTIAL |  | TBD | E,A,S | 권한(A)·빈 결과/페이징(E)·테스트(S) 보강 | P1 |
| 2.4 | 판매 내역 조회(+ 판매 게시글 링크) | PARTIAL |  | TBD | E,A,S | 권한(A)·빈 결과/페이징(E)·테스트(S) 보강 | P1 |

---

## 3. 사용자 상품 관리(등록/수정/삭제/끌어올리기)

| Req ID | 요구사항 | 상태 | Evidence | Code ref | Gaps | Next action | Priority |
|---|---|---|---|---|---|---|---|
| 3.1 | 상품 등록(제목/설명/이미지/카테고리/가격/거래방식/지역) | PARTIAL |  | TBD | V,E,A,T,S | 입력검증(V), 로그인 사용자만 등록(A), 이미지/파일 예외(E), 등록 tx 정리(T), 테스트(S) | P0 |
| 3.2 | 상품 수정(+ 판매상태 변경) | PARTIAL |  | TBD | V,E,A,T,S | 소유자 체크(A), 상태 전이 규칙/정합성(T), 검증/예외/테스트 | P0 |
| 3.3 | 상품 삭제(+ 연관 데이터 정리) | PARTIAL |  | TBD | E,A,T,S | 소유자 체크(A), 연관 데이터 삭제/제약조건 정리(T), 실패 케이스 예외(E), 테스트(S) | P0 |
| 3.4 | 끌어올리기(1시간 제한) | TODO |  | TBD | A,T,S | rate-limit(쿨다운) 정책 + DB 업데이트 tx + 테스트 | P2 |

---

## 4. 사용자 상품 조회(목록/상세/검색/필터/정렬)

| Req ID | 요구사항 | 상태 | Evidence | Code ref | Gaps | Next action | Priority |
|---|---|---|---|---|---|---|---|
| 4.1 | 상품 목록 조회(20개 페이징) | PARTIAL | docs/assets/run-success-product-search.png | TBD | E,S | 페이징/페이지네이션 기준 명확화, 빈 결과 처리(E), 테스트(S) | P1 |
| 4.2 | 상품 상세 조회(+ 채팅방 개설 진입) | PARTIAL | docs/assets/run-success-product-detail.png | TBD | E,A,S | 접근 제어(A)·없는 게시글/삭제글 예외(E)·테스트(S) | P0 |
| 4.3 | 상품 검색(부분 일치, 제목/설명) | PARTIAL | docs/assets/run-success-product-search.png | TBD | V,E,S | 검색어 검증(V), 성능/인덱스 고려(추후), 테스트(S) | P1 |
| 4.4 | 필터링(카테고리/가격) | TODO |  | TBD | V,E,S | 요구사항 기준 필터 UI/쿼리 설계 후 구현 | P2 |
| 4.5 | 정렬(최신/가격 낮/높) | TODO |  | TBD | V,E,S | 정렬 파라미터 화이트리스트 검증 + 구현 | P2 |

---

## 5. 사용자 채팅(WebSocket)

| Req ID | 요구사항 | 상태 | Evidence | Code ref | Gaps | Next action | Priority |
|---|---|---|---|---|---|---|---|
| 5.1 | 1:1 채팅(상품별 채팅방, 텍스트/이미지 전송) | PARTIAL | docs/assets/run-success-chat.png | TBD | A,E,T,S | 인증된 사용자만 연결(A), 메시지/파일 예외(E), 저장/동기화 정합성(T), 테스트(S) | P0 |
| 5.2 | 채팅방 목록 조회/나가기(삭제) | PARTIAL |  | TBD | A,E,S | 본인 채팅방만 조회/삭제(A), 예외/테스트 보강 | P1 |
| 5.3 | 실시간 동기화(+ 알림 선택) | PARTIAL | docs/assets/run-success-chat.png | TBD | E,T,S | 재연결/중복전송/순서 보장 등 안정성(T,E) 강화 + 테스트 | P1 |

---

## 6. 관리자 회원 관리

| Req ID | 요구사항 | 상태 | Evidence | Code ref | Gaps | Next action | Priority |
|---|---|---|---|---|---|---|---|
| 6.1 | 회원정보 조회 | TODO |  | TBD | A,E,S | 관리자 인증/인가(A) 설계 후 조회 화면/기능 구현 | P2 |
| 6.2 | 회원정보 삭제 | TODO |  | TBD | A,E,T,S | 삭제 정책(소프트/하드) + 트랜잭션/감사로그 고려 | P2 |

---

## 7. 관리자 상품 관리

| Req ID | 요구사항 | 상태 | Evidence | Code ref | Gaps | Next action | Priority |
|---|---|---|---|---|---|---|---|
| 7.1 | 상품 목록/상세 조회 | TODO |  | TBD | A,E,S | 관리자 전용 조회 기능 구현 | P2 |
| 7.2 | 상품 삭제 | TODO |  | TBD | A,E,T,S | 관리자 삭제 권한/사유/로그 정책 정의 후 구현 | P2 |
| 7.3 | 카테고리 관리(추가/삭제) | TODO |  | TBD | A,E,T,S | 카테고리 테이블/제약 설계 후 관리 기능 구현 | P2 |

---

## 8. 신고 기능

| Req ID | 요구사항 | 상태 | Evidence | Code ref | Gaps | Next action | Priority |
|---|---|---|---|---|---|---|---|
| 8.1 | 사용자 신고(상품/사용자) | TODO |  | TBD | V,E,A,T,S | 신고 데이터 모델/중복신고 정책/권한(A) 포함 설계 후 구현 | P2 |
| 8.2 | 관리자 신고 처리(삭제/정지) | TODO |  | TBD | A,E,T,S | 관리자 처리 플로우/감사로그/정책 정의 후 구현 | P2 |

---

## 요약
- DONE: 0개 / PARTIAL: 14개 / TODO: 9개
- P0 우선순위(안전/정합성/권한): **회원가입/로그인**, **상품 등록/수정/삭제**, **상품 상세**, **채팅 인증/정합성**
- 다음 단계(리팩토링 준비): P0 항목부터 `A(권한)` + `T(트랜잭션)` 개선 → 그 다음 `V/E` 표준화 → 마지막에 테스트(S)