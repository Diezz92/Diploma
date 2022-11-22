### Инструкция по подключению БД и включения SUT
1. C помощью команды `git clone` осуществить клонирование репозитория по ссылке `https://github.com/Diezz92/Diploma.git`.
2. Открыть склонированный проект в Intellij IDEA.
3. Для запуска контейнеров ввести в терминале Intellij IDEA команду `docker-compose up`.
4. Запустить SUT командой:
- `java -jar artifacts/aqa-shop.jar --server.port=8080` - на БД mysql и БД postgresql.

Запуск считается успешным при наличии в логах сообщений `Tomcat started on port(s): 8080 (http) with context path ''` и `Started ShopApplication in ... seconds (JVM running for ...)`.

5. Войти в браузер `Google Chrome` и ввести ссылку `http://localhost:8080`.
6. При необходимости отключения SUT, находясь в терминале Intellij IDEA, нажать клавиши `CTRL+C`.
7. При необходимости отключения контейнеров ввести в терминале Intellij IDEA команду `docker-compose down`.


### Инструкция для получения отчетов с помощью Allure
1. Выполнить шаги 1-4 Инструкции подключения БД и включения SUT.
2. В терминале ввести команду `./gradlew clean test`.
3. Ввести команду `./gradlew allureServe`.
