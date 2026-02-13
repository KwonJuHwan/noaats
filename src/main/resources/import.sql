-- [1] 회원 데이터
INSERT INTO member (member_id, name, default_income, created_at) VALUES (1, '노아', 2600000, '2024-01-01 00:00:00');

-- [2] 수입 데이터
INSERT INTO income (income_id, member_id, amount, date, income_type, is_regular) VALUES (1, 1, 2600000, '2026-01-10', 'SALARY', true);
INSERT INTO income (income_id, member_id, amount, date, income_type, is_regular) VALUES (2, 1, 500000, '2026-01-20', 'BONUS', false);

-- [3] 카테고리 데이터
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (1, '식비', NULL, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (11, '배달음식', 1, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (12, '장보기', 1, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (13, '카페', 1, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (14, '간식', 1, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (15, '외식', 1, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (2, '교통', NULL, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (21, '대중교통', 2, true);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (22, '택시', 2, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (3, '쇼핑', NULL, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (31, '의류', 3, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (32, '생필품', 3, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (4, '문화', NULL, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (41, 'OTT/구독', 4, true);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (5, '자기개발', NULL, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (51, '도서', 5, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (52, '운동', 5, true);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (6, '저축', NULL, true);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (61, '주택청약', 6, true);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (7, '의료/건강', NULL, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (71, '병원', 7, false);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (8, '주택', NULL, true);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (9, '공과금', NULL, true);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (91, '통신비', 9, true);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (92, '관리비', 9, true);
INSERT INTO category (category_id, name, parent_id, is_fixed_candidate) VALUES (10, '경조사', NULL, false);

-- [4] 고정 지출
INSERT INTO fixed_cost (fixed_cost_id, member_id, category_id, amount, payment_day, title, end_date) VALUES (1, 1, 92, 150000, 25, '관리비', NULL);
INSERT INTO fixed_cost (fixed_cost_id, member_id, category_id, amount, payment_day, title, end_date) VALUES (2, 1, 91, 89000, 10, '휴대폰', NULL);
INSERT INTO fixed_cost (fixed_cost_id, member_id, category_id, amount, payment_day, title, end_date) VALUES (3, 1, 41, 17000, 1, '넷플릭스', NULL);
INSERT INTO fixed_cost (fixed_cost_id, member_id, category_id, amount, payment_day, title, end_date) VALUES (4, 1, 61, 50000, 15, '청약저축', NULL);
INSERT INTO fixed_cost (fixed_cost_id, member_id, category_id, amount, payment_day, title, end_date) VALUES (5, 1, 21, 80000, 29, '알뜰교통카드', NULL);
INSERT INTO fixed_cost (fixed_cost_id, member_id, category_id, amount, payment_day, title, end_date) VALUES (6, 1, 7, 100000, 5, '실비보험', NULL);
INSERT INTO fixed_cost (fixed_cost_id, member_id, category_id, amount, payment_day, title, end_date) VALUES (7, 1, 8, 600000, 25, '월세', NULL);


-- [5] 1월 지출 시나리오

-- 1/1 (휴일): 헬스장 + 점심외식 + 저녁배달
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (1, 1, 52, 600000, '2026-01-01 11:00:00', '헬스장 1년권', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (2, 1, 15, 12000, '2026-01-01 13:00:00', '점심 국밥', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (3, 1, 13, 4500, '2026-01-01 14:00:00', '식후 아아', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (4, 1, 11, 24000, '2026-01-01 19:00:00', '저녁 치킨', false, false);

-- 1/2 (금): 출근
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (5, 1, 13, 4500, '2026-01-02 08:30:00', '출근 커피', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (6, 1, 15, 11000, '2026-01-02 12:30:00', '점심 부대찌개', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (7, 1, 11, 28000, '2026-01-02 19:30:00', '불금 족발', false, false);

-- 1/3 (토): 점심 외식 + 장보기 -> 저녁 집밥(지출X)
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (8, 1, 15, 13000, '2026-01-03 13:00:00', '주말 점심 파스타', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (9, 1, 12, 35000, '2026-01-03 16:00:00', '이마트 장보기', false, false);
INSERT INTO inventory (inventory_id, member_id, expense_id, name, purchase_date, expiry_date, status) VALUES (1, 1, 9, '계란/우유', '2026-01-03', '2026-01-15', 'CONSUMED');
-- 저녁은 장본걸로 해결

-- 1/4 (일): 점심 외식 + 저녁 집밥
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (10, 1, 15, 9000, '2026-01-04 13:00:00', '간단 점심 김밥', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (11, 1, 13, 5000, '2026-01-04 14:00:00', '카페 독서', false, false);

-- 1/5 (월): 일상 복귀 (점심+커피+저녁)
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (12, 1, 13, 4500, '2026-01-05 08:30:00', '출근 커피', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (13, 1, 15, 10000, '2026-01-05 12:30:00', '점심 제육볶음', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (14, 1, 11, 18000, '2026-01-05 19:30:00', '저녁 서브웨이', false, false);

-- 1/6 (화)
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (15, 1, 15, 11000, '2026-01-06 12:30:00', '점심 순두부', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (16, 1, 13, 4500, '2026-01-06 13:00:00', '식후 커피', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (17, 1, 11, 22000, '2026-01-06 19:00:00', '저녁 피자', false, false);

-- 1/7 (수) - 충동구매 포함
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (18, 1, 15, 12000, '2026-01-07 12:30:00', '점심 돈까스', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (19, 1, 11, 28000, '2026-01-07 20:00:00', '저녁 마라탕(충동)', false, true);

-- 1/8 (목)
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (20, 1, 13, 4500, '2026-01-08 08:30:00', '출근 커피', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (21, 1, 15, 9000, '2026-01-08 12:30:00', '점심 라면+김밥', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (22, 1, 11, 19000, '2026-01-08 19:30:00', '저녁 햄버거 세트', false, false);

-- 1/9 (금)
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (23, 1, 15, 13000, '2026-01-09 12:30:00', '점심 쌀국수', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (24, 1, 15, 35000, '2026-01-09 19:00:00', '불금 회식(1/N)', false, false);

-- 1/10 (토): 장보기 -> 저녁 집밥
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (25, 1, 15, 12000, '2026-01-10 13:00:00', '주말 점심', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (26, 1, 12, 45000, '2026-01-10 17:00:00', '고기/채소 장보기', false, false);
INSERT INTO inventory (inventory_id, member_id, expense_id, name, purchase_date, expiry_date, status) VALUES (2, 1, 26, '삼겹살/채소', '2026-01-10', '2026-01-16', 'CONSUMED');

-- 1/11 (일): 집밥 Day
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (27, 1, 13, 6000, '2026-01-11 14:00:00', '카페 라떼', false, false);

-- 1/12 (월)
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (28, 1, 13, 4500, '2026-01-12 08:30:00', '출근 커피', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (29, 1, 15, 10000, '2026-01-12 12:30:00', '점심 백반', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (30, 1, 11, 21000, '2026-01-12 19:30:00', '저녁 찜닭', false, false);

-- 1/13 (화): 코트 구매
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (31, 1, 15, 11000, '2026-01-13 12:30:00', '점심 김치찌개', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (32, 1, 31, 280000, '2026-01-13 18:30:00', '겨울 코트', false, true);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (33, 1, 11, 8000, '2026-01-13 20:00:00', '저녁 햄버거', false, false);

-- 1/14 ~ 1/23: 매일 반복되는 식비
-- 14(수)
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (34, 1, 15, 12000, '2026-01-14 12:30:00', '점심 덮밥', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (35, 1, 11, 25000, '2026-01-14 19:00:00', '저녁 족발', false, false);
-- 15(목)
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (36, 1, 13, 4500, '2026-01-15 12:30:00', '식후 커피', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (37, 1, 15, 9000, '2026-01-15 12:30:00', '점심 분식', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (38, 1, 11, 16000, '2026-01-15 19:30:00', '저녁 초밥', false, false);
-- 16(금)
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (39, 1, 15, 11000, '2026-01-16 12:30:00', '점심 칼국수', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (40, 1, 15, 45000, '2026-01-16 19:00:00', '친구랑 이자카야', false, false);
-- 17(토)
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (41, 1, 15, 15000, '2026-01-17 13:00:00', '해장 짬뽕', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (42, 1, 13, 6000, '2026-01-17 14:00:00', '카페', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (43, 1, 11, 22000, '2026-01-17 19:00:00', '저녁 치킨', false, false);
-- 18(일)
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (44, 1, 11, 20000, '2026-01-18 13:00:00', '점심 배달', false, false);
-- 19(월)
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (45, 1, 13, 4500, '2026-01-19 08:30:00', '출근 커피', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (46, 1, 15, 10000, '2026-01-19 12:30:00', '점심 볶음밥', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (47, 1, 11, 18000, '2026-01-19 19:30:00', '저녁 김치찜', false, false);
-- 20(화)
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (48, 1, 15, 12000, '2026-01-20 12:30:00', '점심 텐동', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (49, 1, 11, 16000, '2026-01-20 19:00:00', '저녁 짜장면', false, false);

-- 1/24: 대량 장보기 -> 25~26일 저녁 집밥
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (50, 1, 15, 10000, '2026-01-24 13:00:00', '점심 우동', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (51, 1, 12, 80000, '2026-01-24 16:00:00', '트레이더스 장보기', false, false);
INSERT INTO inventory (inventory_id, member_id, expense_id, name, purchase_date, expiry_date, status) VALUES (3, 1, 51, '냉동만두/볶음밥', '2026-01-24', '2026-03-30', 'IN_STORE');

-- 1/28~31: 월말 지출
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (52, 1, 13, 4500, '2026-01-28 08:30:00', '출근 커피', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (53, 1, 10, 100000, '2026-01-28 11:00:00', '축의금', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (54, 1, 15, 11000, '2026-01-28 12:30:00', '점심 국밥', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (55, 1, 11, 19500, '2026-01-29 19:00:00', '저녁 버거킹', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (56, 1, 15, 55000, '2026-01-31 18:30:00', '월말 외식', false, false);


-- [6] 2월 지출 시나리오
-- 2/1 (일)
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (60, 1, 11, 22000, '2026-02-01 13:00:00', '해장 쌀국수', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (61, 1, 11, 18000, '2026-02-01 19:00:00', '저녁 떡볶이', false, false);

-- 2/2 (월)
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (62, 1, 13, 4500, '2026-02-02 08:30:00', '출근 커피', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (63, 1, 15, 10000, '2026-02-02 12:30:00', '점심 백반', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (64, 1, 11, 18000, '2026-02-02 19:00:00', '저녁 샌드위치', false, false);

-- 2/3 (화): 장보기 -> 저녁 샐러드
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (65, 1, 15, 12000, '2026-02-03 12:30:00', '점심 순대국', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (66, 1, 12, 35000, '2026-02-03 19:00:00', '다이어트 장보기', false, false);
INSERT INTO inventory (inventory_id, member_id, expense_id, name, purchase_date, expiry_date, status) VALUES (4, 1, 66, '샐러드/닭가슴살', '2026-02-03', '2026-02-08', 'IN_STORE'); -- 만료됨(테스트)

-- 2/4 (수): 점심은 먹고 저녁은 집밥
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (67, 1, 15, 9000, '2026-02-04 12:30:00', '점심 김밥', false, false);

-- 2/5 (목): 다시 배달 시작
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (68, 1, 13, 4500, '2026-02-05 08:30:00', '출근 커피', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (69, 1, 15, 11000, '2026-02-05 12:30:00', '점심 카레', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (70, 1, 14, 4000, '2026-02-05 16:00:00', '과자', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (71, 1, 11, 18000, '2026-02-05 20:00:00', '저녁 닭발', false, false);

-- 2/6 (금)
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (72, 1, 15, 13000, '2026-02-06 12:30:00', '점심 파스타', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (73, 1, 11, 20000, '2026-02-06 20:00:00', '불금 치킨', false, false);

-- 2/7 (토)
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (74, 1, 13, 6000, '2026-02-07 14:00:00', '주말 라떼', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (75, 1, 15, 32000, '2026-02-07 18:00:00', '저녁 초밥', false, false);

-- 2/8 (일)
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (76, 1, 11, 24000, '2026-02-08 13:00:00', '점심 찜닭', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (90, 1, 12, 15000, '2026-02-8 19:10:00', '마늘', false, false);
INSERT INTO inventory (inventory_id, member_id, expense_id, name, purchase_date, expiry_date, status) VALUES (6, 1, 90, '마늘', '2026-02-13', '2026-02-27', 'IN_STORE');
-- 2/9 (월)
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (77, 1, 13, 4500, '2026-02-09 12:30:00', '점심 커피', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (78, 1, 15, 10000, '2026-02-09 12:30:00', '점심 찌개', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (79, 1, 11, 15000, '2026-02-09 19:00:00', '김밥천국', false, false);

-- 2/10 (화): 장보기
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (80, 1, 15, 12000, '2026-02-10 12:30:00', '점심 곰탕', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (81, 1, 12, 15000, '2026-02-10 19:00:00', '콩나물', false, false);
INSERT INTO inventory (inventory_id, member_id, expense_id, name, purchase_date, expiry_date, status) VALUES (5, 1, 81, '콩나물', '2026-02-10', '2026-02-20', 'IN_STORE');
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (91, 1, 12, 15000, '2026-02-10 19:00:00', '두부', false, false);
INSERT INTO inventory (inventory_id, member_id, expense_id, name, purchase_date, expiry_date, status) VALUES (7, 1, 91, '두부', '2026-02-10', '2026-02-25', 'IN_STORE');

-- 2/11 (수)
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (82, 1, 13, 4500, '2026-02-11 08:30:00', '수요일 커피', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (83, 1, 15, 9000, '2026-02-11 12:00:00', '점심 햄버거', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (84, 1, 11, 17000, '2026-02-11 19:00:00', '저녁 돈까스', false, false);

-- 2/12 (목)
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (85, 1, 15, 11000, '2026-02-12 12:30:00', '점심 덮밥', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (86, 1, 11, 21000, '2026-02-12 19:30:00', '저녁 떡볶이', false, false);

-- 2/13 (금)
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (87, 1, 22, 12000, '2026-02-13 08:50:00', '지각 택시', false, true);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (88, 1, 15, 13000, '2026-02-13 12:30:00', '점심 짬뽕', false, false);
INSERT INTO expense (expense_id, member_id, category_id, amount, spent_at, memo, is_fixed, is_impulse) VALUES (89, 1, 13, 4500, '2026-02-13 13:00:00', '식후 커피', false, false);

-- [7] 시퀀스 초기화
ALTER TABLE member ALTER COLUMN member_id RESTART WITH 100;
ALTER TABLE income ALTER COLUMN income_id RESTART WITH 100;
ALTER TABLE category ALTER COLUMN category_id RESTART WITH 100;
ALTER TABLE fixed_cost ALTER COLUMN fixed_cost_id RESTART WITH 100;
ALTER TABLE expense ALTER COLUMN expense_id RESTART WITH 100;
ALTER TABLE inventory ALTER COLUMN inventory_id RESTART WITH 100;