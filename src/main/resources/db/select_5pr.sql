-- 3 OR AND

SELECT * FROM services where price > 1000 or price < 100;
SELECT * FROM services where price <> 10000;
SELECT * FROM services where price > 100000 or price < 100;

-- 3 date, str func
SELECT * FROM clients WHERE cl_name = TRIM(' admin ');
SELECT * FROM orders where DateDiff("m", Now(), reception_time) > 1;
SELECT * FROM orders where DateDiff("m", Now(), reception_time) > 12;

-- 3 param
SELECT * FROM orders where reception_time > [Введите дату];
SELECT * FROM services where price > [Введите цену];
SELECT * FROM clients WHERE cl_name LIKE [Введите имя];

-- 3 like
SELECT * FROM clients WHERE cl_name LIKE "%ad%";
SELECT * FROM doctors WHERE d_name LIKE "%ad%";
SELECT * FROM services WHERE description LIKE "%возм%";

-- 3 asc sort
SELECT * FROM services ORDER BY price ASC;
select * FROM orders ORDER BY reception_time ASC; 
SELECT * FROM orders ORDER BY doctor_id ASC

-- 3 desc sort
SELECT * FROM services ORDER BY price DESC;
SELECT * FROM orders ORDER BY reception_time DESC; 
SELECT * FROM orders ORDER BY doctor_id DESC

-- 3 double sort
SELECT * FROM services ORDER BY price ASC, type DESC;
SELECT * FROM orders ORDER BY reception_time ASC, doctor_id ASC; 
SELECT * FROM animals ORDER BY an_type_id ASC, an_name ASC;

-- 15 group by 
SELECT count(*) FROM orders
    GROUP BY doctor_id;

SELECT count(*) FROM orders
    GROUP BY animal_id;

SELECT count(*) FROM orders
    GROUP BY client_id;

SELECT count(*) FROM orders
    WHERE client_id < 100
    GROUP BY client_id;

SELECT count(*) FROM orders
    WHERE client_id < 1000
    GROUP BY client_id;

SELECT order_id, count(*) from orders_services
    GROUP BY order_id;

SELECT avg(price) FROM sevices
    GROUP BY type;

SELECT avg(price) FROM sevices
    WHERE price > 1000
    GROUP BY type;

SELECT avg(price) FROM sevices
    WHERE price > 10000
    GROUP BY type;

SELECT max(price) from services
    GROUP BY type;

SELECT max(price) from services
    WHERE price > 1000
    GROUP BY type;

SELECT min(price) from services
    GROUP BY type;

SELECT min(price) from services
    WHERE price > 1000
    GROUP BY type;

SELECT sum(price) from services
    GROUP BY type;

SELECT sum(price) from services
    WHERE type = 'PRODUCT'
    GROUP BY type;

SELECT sum(price) from services
    WHERE type = 'SERVICE'
    GROUP BY type;