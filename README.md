# kNote

kNote это простое приложение для создания заметок, написанное на Kotlin.

## Поддерживаемые конструкции Markdown

kNote поддерживает разметку записей при помощи Markdown:
- Акцент (`*`, `_`)
- Сильный акцент (`**`, `__`)
- Перечеркивание (`~~`)
- Заголовки (`#{1,6}`)
- Ссылки (Только подсветка, без перехода по ссылкам) (`[]()` && `[][]`)
- Images (Только отображение изображений по ссылке)(`![]()`)
- Списки дел:
- [ ] Not _done_
    - [X] **Done** with `X`
    - [x] ~~and~~ **or** small `x`
___

## Функции

- [X] Добавление записей
- [X] Удаление записей
- [X] Просмотр записей
- [X] Редактирование записей 
- [X] Хранение записей в памяти телефона
- [X] Разметка текста
- [x] Отображние изображений
- [X] Отображение ссылок
- [X] Отправка текста записей в другие приложения
- [ ] Поиск по записям
- [ ] Сортировка записей

# Использованые библиотеки и технологии

- [Jetpack Compose](https://developer.android.com/jetpack/compose) - декларативный фреймворк для создания пользовательского интерфейса
- [Room](https://developer.android.com/jetpack/androidx/releases/room) - библиотека для работы с базами данных. Является оберткой над SQLite
- [Dagger-Hilt](https://dagger.dev/hilt/) - библиотеки для Dependency Injection
- [Material3](https://m3.material.io/) - набор стандартных элементов интерфейса, а так же гайдлайны по офрмлению приложений
- [Markwon](https://noties.io/Markwon/) - библиотека для рендеринга Markdown.

Для реализации **MdText** и **MdEditor** частично использовался код [отсюда](https://github.com/jeziellago/compose-markdown)

## Архитектура

Приложение раз-работано по принципам [MVVM](https://ru.wikipedia.org/wiki/Model-View-ViewModel) и [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html).

# Скриншоты

![photo_2023-03-12_05-40-49 (2).jpg](screenshots%2Fphoto_2023-03-12_05-40-49%20%282%29.jpg)
![photo_2023-03-12_05-40-49.jpg](screenshots%2Fphoto_2023-03-12_05-40-49.jpg)
![photo_2023-03-12_05-15-46 (4).jpg](screenshots%2Fphoto_2023-03-12_05-15-46%20%284%29.jpg)
![photo_2023-03-12_05-15-46 (3).jpg](screenshots%2Fphoto_2023-03-12_05-15-46%20%283%29.jpg)
![photo_2023-03-12_05-15-46.jpg](screenshots%2Fphoto_2023-03-12_05-15-46.jpg)

# Баги