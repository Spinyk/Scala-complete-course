Домашнее задание по лекции "Среда разработки и тестирования"

[Markdown syntax](https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet)

### 1. Составить перечень горячих клавиш (минимум 10):

* Command + N - Перейти к классу
* Command + J - Показать экран Live templates
* Сtrl + K - Комит
* Alt + = - Вывести тип
* Ctrl + Alt + L - Форматирование
* F9 - следующий брейкпоинт
* Shift x2 - Search Everywhere
* Ctrl + Shift + A - Find Action
* Ctrl + Shift + F - Поиск по тексту
* Alt + F7 - Find usage
 


### 2. Завести себе минимум 3 шаблона быстрой подстановки (Live templates)

1. `fcc` (пример, его надо заменить своим):

```
final case class()
```
2. `fmc`:

```
flatMap { case => }
``` 
3. `fec`:

```
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
```


### 3. Настроить 2 таски на прогон всех тестов: через IDEA и через SBT

В build.sbt добавляем

```
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % Test
```

Открываем SBT Console и пишем 
```
test
```

Идеевская таска в энитаске
