This is the api you should use:

POST: http://localhost:6000/api/shoppingservice/products
{
   "productName": "Moto g62",
   "prductDescription": "5g mobile",
   "productPrice": 16000.00,
   "quantity": 9
}

POST: http://localhost:6000/api/shoppingservice/products
{
   "productName": "Nokia g62",
   "prductDescription": "Strong mobile",
   "productPrice": 2000.00,
   "quantity": 80
}

POST: http://localhost:6000/api/shoppingservice/customer
{
  "customerName": "Aritri Maity",
  "customerEmail":"aritri.maity@gmail.com",
  "customerBillingAddress": {
    "doorNo": 19,
    "streetName": "palashpai road",
    "layout": "ma kali sound",
    "city":"Midnapore",
    "pincode": 721146
  },
    "customerShippingAddress": {
    "doorNo": 19,
    "streetName": "palashpai road",
    "layout": "ma kali sound",
    "city":"Midnapore",
    "pincode": 721146
  }
}

PUT: http://localhost:6000/api/shoppingservice/customer/1/cart
[
    {
        "itemId": 1,
        "productId":1,
        "productName": "Moto g62",
        "quantity": 3,
        "price": null
    },
     {
        "itemId": 2,
        "productId":2,
        "productName": "Strong mobile",
        "quantity": 3,
        "price": null
    }
]

POST: http://localhost:6000/api/shoppingservice/customer/1/order

GET: http://localhost:6000/api/shoppingservice/customer/1/orders
