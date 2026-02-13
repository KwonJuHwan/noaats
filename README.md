💸 SaveME (세이브미)
"소 잃고 외양간 고치는 가계부는 그만, 쓰기 전에 막아주는 생존 가계부"

📖 Introduction
노아에이티에스 사전과제인 웹 프로토타입입니다.

선정 주제: 지출 관리 및 예산 (Expense Management & Budget)

SaveME는 사회초년생을 위한 능동형 자산 관리 서비스입니다.
단순히 지출을 기록하는 것을 넘어, 결제 직전(Pre-purchase)에 예산 시뮬레이션과 기회비용을 제시하여 사용자의 과소비 행동을 사전에 차단하는 것을 목표로 합니다.

배포 URL: https://noaats.onrender.com/

Tip: 별도의 회원가입 없이 바로 테스트 가능하고 "노아"의 시점으로 세팅되어있습니다.

📑 Detailed Documentation (핵심)
본 프로젝트의 상세 기획, 기술적 의사결정, 트러블 슈팅 등 자세한 내용은 아래 문서에서 확인하실 수 있습니다.

✨ Key Features
1. [Core] 통합 가계부 & 캘린더

수입, 고정 지출, 변동 지출을 통합 관리하여 '실질적인 가용 자금'을 산출합니다.

계층형 카테고리(대분류 > 소분류)와 AJAX 기반의 동적 UI를 제공합니다.

2. [Core] N일의 생존 예산 (Dynamic Budget)

월 예산이 아닌 '오늘 하루 쓸 수 있는 돈'을 실시간으로 계산합니다.

지출 입력 시, 내일의 예산이 줄어드는 것을 시각화하여 과소비를 방지합니다.

3. [Secondary] 식비 방어 & 냉장고 파먹기

배달/외식 지출 시도 시, 냉장고의 유통기한 임박 식재료를 알림으로 띄워 지출을 방어합니다.

장보기 지출 내역을 냉장고 재고로 자동 연동합니다.

4. [Secondary] 기회비용 & 심야 감지

심야 지름신 감지: 밤 10시 ~ 새벽 4시 사이 지출 시도 시 경고 모드(Dark Theme)가 발동됩니다.

기회비용 환산: 지출 금액을 '국밥 N그릇', '에어팟 N개' 등으로 환산하여 보여줍니다.

5. [Secondary] 월간 지출 통계
   
사용자의 소비 패턴을 대분류 기준으로 그룹핑하여 시각화함으로써 지출 비중을 제공합니다.

🛠️ Tech Stack

Backend
Language: Java 17

Framework: Spring Boot 3.x

Data Access: Spring Data JPA, QueryDSL

Database: H2 (In-memory / Local)

Frontend
Template Engine: Thymeleaf

Library: Vanilla JS (ES6+), Chart.js (통계 시각화)

Styling: CSS3 (Custom Design)

DevOps & Tools
Build: Gradle

Container: Docker

Deployment: Render (Web Service)

VCS: Git / GitHub

Project Structure (DDD)
본 프로젝트는 도메인 주도 설계(DDD)를 기반으로 패키지를 구조화했습니다.

src/main/java/com/saveme
├── global          # 전역 설정 (Config, Exception, Common Utils)
├── member          # 회원 도메인
├── ledger          # [Core] 가계부 도메인 (Income, Expense, Budget)
└── consumption     # [Secondary] 소비 관리 도메인 (Inventory, Warning)
