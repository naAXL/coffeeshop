
#### Получить все категории
```bash
GET http://localhost:8080/api/categories
```

#### Создать категорию
```bash
POST http://localhost:8080/api/categories
Content-Type: application/json

{
  "imageUrl": "https://example.com/image.jpg",
  "names": {
    "ru": "Кофе",
    "en": "Coffee"
  }
}
```

#### Обновить категорию
```bash
PUT http://localhost:8080/api/categories/{id}
Content-Type: application/json

{
  "imageUrl": "https://example.com/new-image.jpg",
  "names": {
    "ru": "Горячий кофе",
    "en": "Hot Coffee"
  }
}
```

#### Удалить категорию
```bash
DELETE http://localhost:8080/api/categories/{id}
```

#### Получить позиции категории
```bash
GET http://localhost:8080/api/categories/{categoryId}/items
```

#### Создать позицию меню
```bash
POST http://localhost:8080/api/items
Content-Type: application/json

{
  "categoryId": 1,
  "imageUrl": "https://example.com/espresso.jpg",
  "price": 3.50,
  "names": {
    "ru": "Эспрессо",
    "en": "Espresso"
  },
  "descriptions": {
    "ru": "Классический итальянский кофе",
    "en": "Classic Italian coffee"
  }
}
```

#### Обновить позицию меню
```bash
PUT http://localhost:8080/api/items/{id}
```

#### Удалить позицию меню
```bash
DELETE http://localhost:8080/api/items/{id}
```
