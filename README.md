## Registration

- Method: `POST`
- Endpoint: `/registration/register`
- Body:
```json
{
	"username": "admin",
	"password": "password"
}
```

## Authentication

- Method: `POST`
- Endpoint: `/login`
- Body:
```json
{
	"username": "admin",
	"password": "password"
}
```

## Get goods
- Method: `GET`
- Endpoint: `/employee/get`
- **Response** Body:
```json
[
  {
    "sell_price": 6399,
    "name": "Apple",
    "goods_id": 567001231,
    "buy_price": 5999,
    "id": 1,
    "category": "电子产品",
    "update_date": "2022-10-23T16:15",
    "status": "却贷"
  },
  {
    "sell_price": 6,
    "name": "圆规",
    "goods_id": 456782345,
    "buy_price": 3,
    "id": 2,
    "category": "文化用品",
    "update_date": "2022-10-24T17:15",
    "status": "有货"
  },
  {
    "sell_price": 100,
    "name": "布熊",
    "goods_id": 667512945,
    "buy_price": 50,
    "id": 3,
    "category": "玩具",
    "update_date": "2022-10-24T17:18",
    "status": "有货"
  }
]
```

## Add goods
- Method: `POST`
- Endpoint: `/employee/add`
- Body:
```json
{
    "goods_id": 567001231,
    "name": "Apple",
    "category": "电子产品",
    "buy_price": 5999,
    "sell_price": 6399
}
```

## Update goods
- Method: `POST`
- Endpoint: `/employee/update`
- Body:
```json
{
    "goods_id": 567001231,
    "name": "Apple",
    "category": "电子产品",
    "buy_price": 5999,
    "sell_price": 6399,
    "stock": 0
}
```

## Delete goods
- Method: `GET`
- Endpoint: `/employee/delete`
- Body:
```json
{
    "goods_id": 567001231
}
```

## Get clients
- Method: `POST`
- Endpoint: `/governmentClient/get`
- **Response** Body:
```json
[
  {
    "address": "大道街",
    "phone": 13345676457,
    "sex": "男",
    "name": "Jack",
    "client_id": 233454
  },
  {
    "address": "主体建筑天堂",
    "phone": 13445576483,
    "sex": "男",
    "name": "John",
    "client_id": 365478
  },
  {
    "address": "通往大学的主要门户",
    "phone": 13445576583,
    "sex": "女",
    "name": "Olivia",
    "client_id": 263559
  }
]
```

## Add client
- Method: `POST`
- Endpoint: `/governmentClient/add`
- Body:
```json
{
    "client_id": 233454,
    "name": "Jack",
    "sex": "男",
    "phone": 13345676457,
    "address": "大道街"
}
```

## Update client
- Method: `POST`
- Endpoint: `/governmentClient/update`
- Body:
```json
{
    "client_id": 233454,
    "name": "Jack",
    "sex": "男",
    "phone": 13345676457,
    "address": "大道街"
}
```

## Get sales
- Method: `GET`
- Endpoint: `/governmentGoods/get`
- **Response** body:
```json
[
  {
    "id": 1,
    "order_id": 567001231,
    "client_id": 233454,
    "amount": 3570000,
    "status": "未保存",
    "update_date": "2022-10-23 16:15:00"
  },
  {
    "id": 2,
    "order_id": 456782345,
    "client_id": 365478,
    "amount": 3570000,
    "status": "已经交",
    "update_date": "2022-10-24 17:15:00"
  }
]
```

## Add sale
- Method: `POST`
- Endpoint: `/governemntGoods/add`
- Body:
```json
{
  "order_id": 567001231,
  "client_id": 233454,
  "order_goods": [
    {
      "goods_id": 667512945,
      "goods_amount": 10
    },
    {
      "goods_id": 567001231,
      "goods_amount": 25
    }
  ]
}
```