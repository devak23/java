## Changing States
Our state are changing like below

1. When the order is created it will be in SUBMITTED state.

2. From there, if PAY event happens, state will change to PAID

3. From PAID, if FULFILL event happens, state will change to FULFILLED

4. FULFILLED state is the end state.

5. But from FULFILLED, if CANCEL event happens, state will change to CANCELED

6. Also, from PAID, if CANCEL event happens, state will change to CANCELED

7. CANCELED state is also the end state.

## From the terminal:

Create an order:
```
curl --location --request POST 'localhost:8080/createOrder' --data-raw ''                              ✔ 
{"id":1,"orderDate":"2024-08-20","state":"SUBMITTED","event":null,"paymentType":null}
```

Send an event of PAY for id 1 and the state will be changed
```
curl --location --request PUT 'localhost:8080/order/v1/workflow/change' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 1,
    "event": "PAY",
    "paymentType" : "cash"
}'
```

For this input the state will not be changed.

```
curl --location --request PUT 'localhost:8080/order/v1/workflow/change' --header 'Content-Type: application/json' --data-raw '{"id": 1,"event": "PAY", "paymentType" : "cod"}'
```