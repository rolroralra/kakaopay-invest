--INVEST_USER 데이터 생성
INSERT INTO INVEST_USER(ID, NAME, EMAIL)
VALUES (null, 'admin', 'admin@kakao.com'),              -- 1
       (null, 'rolroralra', 'rolroralra@naver.com'),    -- 2
       (null, 'guest01', 'guest01@kakao.com'),          -- 3
       (null, 'guest02', 'guest02@kakao.com'),          -- 4
       (null, 'guest03', 'guest03@kakao.com'),          -- 5
       (null, 'guest04', 'guest04@kakao.com')           -- 6
;

--INVEST_PRODUCT 데이터 생성
INSERT INTO INVEST_PRODUCT(ID, TITLE, STARTED_AT, FINISHED_AT, TOTAL_AMOUNT, CURRENT_AMOUNT, STATE)
VALUES (null, 'KAKAO', '2021-03-01 09:00:00', '2021-06-01 09:00:00', 15000000, 5000000, 'PROCEED'),    -- 1
       (null, 'NAVER', '2021-03-01 09:00:00', '2021-06-01 09:00:00', 25000000, 15000000, 'PROCEED'),   -- 2
       (null, 'GOOGLE', '2021-03-01 09:00:00', '2021-06-01 09:00:00', 50000000, 24000000, 'PROCEED'),  -- 3
       (null, 'APPLE', '2021-03-01 09:00:00', '2021-06-01 09:00:00', 75000000, 27000000, 'PROCEED'),   -- 4
       (null, 'SKIET', '2021-05-24 09:00:00', '2021-05-18 09:00:00', 9000000, 9000000, 'COMPLETED'),   -- 5
       (null, 'SAMSUNG', '2021-03-01 09:00:00', '2021-06-01 09:00:00', 35000000, 30000000, 'PROCEED'), -- 6
       (null, 'LG', '2021-03-01 09:00:00', '2021-05-19 09:00:00', 20000000, 20000000, 'COMPLETED'),    -- 7
       (null, 'IBM', '2021-03-01 09:00:00', '2021-06-01 09:00:00', 20000000, 20000000, 'PROCEED')     -- 8

;

--INVEST_ORDER 데이터 생성
INSERT INTO INVEST_ORDER(ID, USER_ID, STARTED_AT, FINISHED_AT, STATE)
VALUES (null, 2, '2021-03-01 09:00:00', '2021-05-01 09:00:00', 'COMPLETED'),    -- 1
       (null, 2, '2021-03-01 09:00:00', '2021-05-01 09:00:00', 'COMPLETED'),    -- 2
       (null, 3, '2021-03-01 09:00:00', '2021-05-01 09:00:00', 'COMPLETED'),    -- 3
       (null, 3, '2021-03-01 09:00:00', '2021-05-19 09:00:00', 'COMPLETED'),    -- 4
       (null, 4, '2021-03-01 09:00:00', '2021-05-01 09:00:00', 'COMPLETED'),    -- 5
       (null, 5, '2021-03-01 09:00:00', '2021-05-01 09:00:00', 'COMPLETED'),    -- 6
       (null, 6, '2021-05-24 09:00:00', '2021-05-01 09:00:00', 'COMPLETED')     -- 7
;

--INVEST_ORDER_ITEM 데이터 생성
INSERT INTO INVEST_ORDER_ITEM(ID, ORDER_ID, PRODUCT_ID, AMOUNT, STATE)
VALUES (null, 1, 1, 10, 'SUCCESS'),     -- 1
       (null, 1, 2, 30, 'SUCCESS'),     -- 2
       (null, 2, 2, 5, 'SUCCESS'),      -- 3
       (null, 3, 3, 100, 'SUCCESS'),    -- 4
       (null, 4, 4, 20, 'SUCCESS'),     -- 5
       (null, 5, 3, 20, 'SUCCESS'),     -- 6
       (null, 5, 4, 5, 'SUCCESS'),      -- 7
       (null, 5, 7, 1, 'SUCCESS'),      -- 8
       (null, 6, 6, 200, 'SUCCESS'),    -- 9
       (null, 7, 1, 30, 'SUCCESS'),     -- 10
       (null, 7, 7, 150, 'SUCCESS')     -- 11
;