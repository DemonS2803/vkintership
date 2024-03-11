Тетовое задание на стажировку в VK

Для запуска - настроить подкдючение к БД в src/main/recources/application.yaml
Тесты лежат в src/test/...../AppTest.java

Добавление тестовых пользователей: src/main/recources/db/init_db.sql
стандартный (заданный в бд) пароль - "password"

Для логина надо отправить запрос POST на /api/v1/auth/login 
c телом { "login": "admin", "password": "password" }