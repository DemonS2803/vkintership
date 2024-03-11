Тетовое задание на стажировку в VK

Для запуска - настроить подкдючение к БД в src/main/recources/application.yaml
Тесты лежат в src/test/...../AppTest.java

Сначала сделал собственный кэш в бд, но перечитав ТЗ, понял, что это не требовалось и поменял на @Cacheable
В коммитах можно посмотреть старую версию

Добавление тестовых пользователей: src/main/recources/db/init_db.sql
стандартный (заданный в бд) пароль - "password"

Для логина надо отправить запрос POST на /api/v1/auth/login 
c телом { "login": "admin", "password": "password" }
полученный токен вставить в Header Authorization
Пример запроса для пользователя admin
http://localhost:8080/api/v1/users/get/all

Управление пользователями - /api/v1/auth/admin/users
пример тела запроса для создание пользователя
{
    "login": "newUSer",
    "password": "password",
    "authorities": [
        "ROLE_POSTS_VIEWER",
        "ROLE_POSTS_EDITOR",
        "ROLE_ALBUMS_EDITOR",
        "ROLE_ALBUMS_VIEWER"
    ]
}

audit пишется в бд (таблица security_audit)