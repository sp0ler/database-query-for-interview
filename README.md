<h1 align="center">Привет, меня зовут Денис</a> 
<img src="https://github.com/blackcater/blackcater/raw/main/images/Hi.gif" height="32"/></h1>

### О проекте: ###
Необходимо разработать приложение, предоставляющее сервис работы с данными в БД. Данный сервис, на основании входных параметров(аргументы командной строки), типа операции и входного файла – извлекает необходимые данные из БД и формирует результат обработки в выходной файл. 
Все возможные ошибки должны быть обработаны и зафиксированы в выходном файле.

### Использование: ###
> java -jar database-query-for-interview-1.0.jar ip=<IP-адрес> password=<пароль> username=<логин> bd_name=<название БД> input=<путь>\input.json output=<путь>\output.json

### Пример исходного файла №1#: ###
```
{
	"criterias": [ 
		{"lastName": "Иванов"}, 
		{"productName": "Зеленый чай", "minTimes": 5}, 
		{"minExpenses": 100, "maxExpenses": 1000}, 
		{"badCustomers": 5} 
	]
}
```

### Пример исходного файла №2#: ###
```
{
  "startDate": "5.11.2022",
  "endDate": "7.11.2022"
}
```