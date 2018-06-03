База данных
Для тестирования использовать:
Сервер: database/src/test/java/com/luxoft/sdemenkov/database/server/ServerTest.java
Тестовый Клиент: /database/src/test/java/com/luxoft/sdemenkov/test/util/MockClient.java

Формат запроса json
Пример:
{"commandType":"CREATE","targetType":"TABLE","requestParametersMap":{"TargetName":"NEW_TABLE"}}
end

{"commandType":"SELECT","targetType":"TABLE","requestParametersMap":{"TargetName":"TEST/table2"}}
end

Используемые слова:
CREATE/DROP/SHOW/INSERT/SELECT
TABLE/SCHEMA
TargetName:ИмяСхемы[/ИмяТаблицы] <- for Create Table/select/show/drop
[Columns:name1,name2] <- for CreateTable/Insert
[ColumnsValues:value1,value2] <- for Insert
end

send <- send request from Mocked Client