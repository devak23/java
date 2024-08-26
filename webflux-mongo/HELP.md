## Testing

curl --location --request POST 'localhost:9292/products' --data '{"name": "Macbook Pro", "quantity":3, "price": 154330}' -H "Content-Type: application/json"
curl --location --request GET 'localhost:9292/products/66cc0a65b8cf5b4a19b7b5ba'
curl --location --request DELETE 'localhost:9292/products/delete/66cc0a65b8cf5b4a19b7b5ba'

