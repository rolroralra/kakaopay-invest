--INVEST_USER 데이터 생성
INSERT INTO INVEST_USER(ID, NAME, EMAIL)
VALUES (null, 'admin', 'admin@kakao.com'),
       (null, 'rolroralra', 'rolroralra@naver.com'),
       (null, 'guest01', 'guest01@kakao.com'),
       (null, 'guest02', 'guest02@kakao.com'),
       (null, 'guest03', 'guest03@kakao.com'),
       (null, 'guest04', 'guest04@kakao.com')
;

--INVEST_PRODUCT 데이터 생성
INSERT INTO INVEST_PRODUCT(ID, TITLE, STARTED_AT, FINISHED_AT, TOTAL_AMOUNT, STATE)
VALUES (null, 'KAKAO', '2021-03-01 09:00:00', '2021-06-01 09:00:00', 15000000, 'PROCEED'),
       (null, 'NAVER', '2021-03-01 09:00:00', '2021-06-01 09:00:00', 25000000, 'PROCEED'),
       (null, 'GOOGLE', '2021-03-01 09:00:00', '2021-06-01 09:00:00', 50000000, 'PROCEED'),
       (null, 'APPLE', '2021-03-01 09:00:00', '2021-06-01 09:00:00', 75000000, 'PROCEED'),
       (null, 'SKIET', '2021-05-24 09:00:00', '2021-05-18 09:00:00', 9000000, 'COMPLETED'),
       (null, 'SAMSUNG', '2021-03-01 09:00:00', '2021-06-01 09:00:00', 35000000, 'PROCEED'),
       (null, 'LG', '2021-03-01 09:00:00', '2021-05-19 09:00:00', 20000000, 'COMPLETED')
;

--INVEST_ORDER 데이터 생성
INSERT INTO INVEST_ORDER(ID, USER_ID, STARTED_AT, FINISHED_AT, STATE)
VALUES (null, 2, '2021-03-01 09:00:00', '2021-05-01 09:00:00', 'COMPLETED'),
       (null, 2, '2021-03-01 09:00:00', '2021-05-01 09:00:00', 'COMPLETED'),
       (null, 3, '2021-03-01 09:00:00', '2021-05-01 09:00:00', 'COMPLETED'),
       (null, 3, '2021-03-01 09:00:00', '2021-05-19 09:00:00', 'COMPLETED'),
       (null, 4, '2021-03-01 09:00:00', '2021-05-01 09:00:00', 'COMPLETED'),
       (null, 5, '2021-03-01 09:00:00', '2021-05-01 09:00:00', 'COMPLETED'),
       (null, 6, '2021-05-24 09:00:00', '2021-05-01 09:00:00', 'COMPLETED')
;

--INVEST_ORDER_ITEM 데이터 생성
INSERT INTO INVEST_ORDER_ITEM(ID, ORDER_ID, PRODUCT_ID, AMOUNT, STATE)
VALUES (null, 1, 1, 10, 'SUCCESS'),
       (null, 1, 2, 30, 'SUCCESS'),
       (null, 2, 2, 5, 'SUCCESS'),
       (null, 3, 3, 100, 'SUCCESS'),
       (null, 4, 4, 20, 'SUCCESS'),
       (null, 5, 3, 20, 'SUCCESS'),
       (null, 5, 4, 5, 'SUCCESS'),
       (null, 5, 7, 1, 'SUCCESS'),
       (null, 6, 6, 200, 'SUCCESS'),
       (null, 7, 1, 30, 'SUCCESS'),
       (null, 7, 7, 150, 'SUCCESS')
;