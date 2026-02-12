-- 1. 멤버 (ID를 1로 강제 지정)
INSERT INTO member (member_id, name, default_income, created_at) VALUES (1, '노아', 2600000, CURRENT_TIMESTAMP);
-- 멤버 시퀀스 초기화 (다음 멤버 가입 시 2번부터 시작하도록)
ALTER TABLE member ALTER COLUMN member_id RESTART WITH 2;

-- 2. 카테고리 (대분류)
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (1, '식비', NULL, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (2, '주거/통신', NULL, true);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (3, '교통', NULL, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (4, '쇼핑/문화', NULL, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (5, '기타', NULL, false);

-- 3. 카테고리 (소분류)
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (6, '배달음식', 1, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (7, '장보기', 1, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (8, '카페/간식', 1, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (9, '월세', 2, true);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (10, '통신비', 2, true);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (11, 'OTT구독', 2, true);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (12, '전자기기', 4, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (13, '의류', 4, false);
-- 카테고리 시퀀스 초기화
ALTER TABLE category ALTER COLUMN category_id RESTART WITH 100;

-- 4. 고정 지출 (ID 자동 생성)
INSERT INTO fixed_cost (member_id, category_id, amount, payment_day, title) VALUES (1, 9, 600000, 25, '월세');
INSERT INTO fixed_cost (member_id, category_id, amount, payment_day, title) VALUES (1, 10, 85000, 15, 'SKT 통신비');
INSERT INTO fixed_cost (member_id, category_id, amount, payment_day, title) VALUES (1, 11, 17000, 1, '넷플릭스');
-- 고정 지출 시퀀스 초기화
ALTER TABLE fixed_cost ALTER COLUMN fixed_cost_id RESTART WITH 100;

-- 5. 지출 내역 (ID 자동 생성)
INSERT INTO expense (member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (1, 7, 85000, '2026-02-01 14:00:00', '이마트 장보기(고기, 야채 등)', false, false);
INSERT INTO expense (member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (1, 6, 28000, '2026-02-02 19:30:00', '마라탕 & 꿔바로우', false, true);
INSERT INTO expense (member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (1, 8, 5500, '2026-02-03 08:30:00', '스타벅스 아메리카노', false, false);
INSERT INTO expense (member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (1, 12, 120000, '2026-02-04 22:00:00', '기계식 키보드(특가 못참음)', false, true);
INSERT INTO expense (member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (1, 3, 12500, '2026-02-05 08:45:00', '카카오택시(지각)', false, false);
INSERT INTO expense (member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (1, 6, 22000, '2026-02-07 20:00:00', '교촌치킨', false, true);
INSERT INTO expense (member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (1, 8, 15000, '2026-02-08 23:30:00', '편의점 맥주 & 안주', false, true);
INSERT INTO expense (member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (1, 1, 55000, '2026-02-10 19:00:00', '강남역 삼겹살 모임', false, false);
-- 지출 시퀀스 초기화
ALTER TABLE expense ALTER COLUMN expense_id RESTART WITH 100;

-- 6. 수입 내역
INSERT INTO income (amount, "DATE", income_type, is_regular, member_id) VALUES (2600000, '2026-02-01', 0, true, 1);
ALTER TABLE income ALTER COLUMN income_id RESTART WITH 100;

INSERT INTO inventory (member_id, name, purchase_date, expiry_date, status) VALUES (1, '숙주나물', '2026-02-09', '2026-02-12', 'IN_STORE');
INSERT INTO inventory (member_id, name, purchase_date, expiry_date, status) VALUES (1, '우유', '2026-02-05', '2026-02-13', 'IN_STORE');

-- 2. 유통기한 주의 (노란불, D-3 ~ D-7) -> 2026-02-15 ~ 18
INSERT INTO inventory (member_id, name, purchase_date, expiry_date, status) VALUES (1, '계란 30구', '2026-02-01', '2026-02-15', 'IN_STORE');
INSERT INTO inventory (member_id, name, purchase_date, expiry_date, status) VALUES (1, '두부', '2026-02-10', '2026-02-16', 'IN_STORE');

-- 3. 유통기한 넉넉 (초록불)
INSERT INTO inventory (member_id, name, purchase_date, expiry_date, status) VALUES (1, '냉동 만두', '2026-01-20', '2026-05-20', 'IN_STORE');
INSERT INTO inventory (member_id, name, purchase_date, expiry_date, status) VALUES (1, '고추장', '2025-12-01', '2026-12-01', 'IN_STORE');
ALTER TABLE inventory ALTER COLUMN inventory_id RESTART WITH 100;

