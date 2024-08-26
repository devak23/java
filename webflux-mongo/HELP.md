## Testing

curl --location --request GET 'localhost:9292/products';
curl --location --request POST 'localhost:9292/products' --data '{"name": "Macbook Pro", "quantity":3, "price": 154330}' -H "Content-Type: application/json"
curl --location --request GET 'localhost:9292/products/66cc0a65b8cf5b4a19b7b5ba'
curl --location --request DELETE 'localhost:9292/products/delete/66cc0a65b8cf5b4a19b7b5ba'
curl --location --request PUT 'localhost:9292/products/66cc0a2cb8cf5b4a19b7b5b9' --data '{"id": "66cc0a2cb8cf5b4a19b7b5b9", "name": "Macbook Pro M2", "quantity":4, "price": 154330}' -H "Content-Type: application/json"


curl --location --request GET 'localhost:9292/products';
curl --location --request POST 'localhost:9292/products' --data '{"name": "Lenovo ThinkPad", "quantity":4, "price": 74984}' -H "Content-Type: application/json"
curl --location --request GET 'localhost:9292/products/66cc0a65b8cf5b4a19b7b5ba'
curl --location --request DELETE 'localhost:9292/products/delete/66cc0a65b8cf5b4a19b7b5ba'
curl --location --request PUT 'localhost:9292/products/66cc0a2cb8cf5b4a19b7b5b9' --data '{"id": "66cc0a2cb8cf5b4a19b7b5b9", "name": "Macbook Pro M2", "quantity":4, "price": 154330}' -H "Content-Type: application/json"

