# Тестирование приложения app-deadline.jar работающего с СУБД MySQL через API
## Краткое описание

Проведено позитивное api тестирование фунцкции входа пользователя с использованием кода, полученного из БД.
При запуске приложения в БД записывается демо данные пользователей. Создан класс генерации и записи в БД новых пользователей. Пароль при записи в БД используется зашивровонный как у демо пользователей. При авторизации в таблицу кодов записываются сгенерированные приложением коды. Этот ког считывается из БД SQL запросом SELECT и используется для авторизации.

API запросы:
* Логин
```
POST http://localhost:9999/api/auth
Content-Type: application/json

{
  "login": "vasya",
  "password": "qwerty123"
}
```

* Верификация
```
POST http://localhost:9999/api/auth/verification
Content-Type: application/json

{
  "login": "vasya",
  "code": "599640"
}
```

В ответе, в поле "token" приходит токен аутентификации, который нужно использовать в последующих запросах

* Просмотр карт
```
GET http://localhost:9999/api/cards
Content-Type: application/json
Authorization: Bearer {{token}}
```
* Перевод с карты на карту (любую)
```
POST http://localhost:9999/api/transfer
Content-Type: application/json
Authorization: Bearer {{token}}

{
  "from": "5559 0000 0000 0002",
  "to": "5559 0000 0000 0008",
  "amount": 5000
}
```
Были изучены ответы на запросы с помощью postman. 
С помощью REST-assured реализовано api тестирование.




## Руководство использования

* Для запуска контейнера MySQL выполните:

```
docker-compose up
```

* Для запуска БД выполните:
 
```
docker-compose exec mysql mysql -u app -p app

```


* Для запуска приложения выполните:

```
java -jar app-deadline.jar -P:jdbc.url=jdbc:mysql://localhost:3306/app -P:jdbc.user=app -P:jdbc.password=pass
```

* Для запуска тестов:

```
./gradlew test -Dselenide.headless=true --info
```

В результате тестирования была найдена ошибка приложения при переводе средст с карты на карту:

[ссылка на issues c багом  Не верный баланс на карте после операции перевода](https://github.com/leonnika/aqa-hw8-api/issues/1)

