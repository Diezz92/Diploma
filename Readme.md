# Дипломный проект по профессии "Тестировщик"
Дипломный проект — автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

## Начало работы
1. C помощью команды `git clone` осуществить клонирование репозитория по ссылке `https://github.com/Diezz92/Diploma.git`.

### Предустановки
* Intellij IDEA
* Google Chrome
* GitHub, Git

### Установка и запуск
1. Открыть склонированный проект в Intellij IDEA.
1. Для запуска контейнеров ввести в терминале Intellij IDEA команду `docker-compose up`.
1. Запустить SUT командой: `java -jar artifacts/aqa-shop.jar --server.port=8080` - на БД mysql и БД postgresql.

Запуск считается успешным при наличии в логах сообщений `Tomcat started on port(s): 8080 (http) with context path ''` и `Started ShopApplication in ... seconds (JVM running for ...)`.

1. Для того, чтобы ознакомиться с формой, нужно войти в браузер `Google Chrome` и ввести ссылку `http://localhost:8080`.
1. Для запуска автотестов в IDEA открыть класс `DiplomaTest`.
1. При необходимости отключения SUT, находясь в терминале Intellij IDEA, нажать клавиши `CTRL+C`.
1. При необходимости отключения контейнеров ввести в терминале Intellij IDEA команду `docker-compose down`.


### Инструкция для получения отчетов с помощью Allure
1. Выполнить шаги 1-4 Инструкции подключения БД и включения SUT.
2. В терминале ввести команду `./gradlew clean test`.
3. Ввести команду `./gradlew allureServe`.
