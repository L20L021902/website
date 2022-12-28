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
    "stock": 10
}
```