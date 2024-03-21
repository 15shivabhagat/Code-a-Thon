
# Code A Thon

CRUD operation for managing leaderboard of a specific contest. The platform also gives virtual awards to the users called Badges based on their score.

## API Reference

The app will start running at http://localhost:8081


| Method  | URL        | Description                |
| :-------| :-------   | :------------------------- |
| GET     | /api/users | Get a list of registered users basis on score. |
| GET     |  /api/users/{id} |  Get a registered user on basis of id.  |
| POST    | /api/users | Register user by passing id and username. |
| PUT | /api/users/{id} | Update the score of registered user by passing score in body. |
| DELETE | /api/users/{id} | Remove the registered user by id. |




## Sample Valid JSON Request and Response Body
<POST /api/users> Register User
```http
{
    "userId":"1",
    "username":"15shivabhagat"
}
```
Response
```http
{
    "userId": "1",
    "username": "15shivabhagat",
    "score": 0,
    "badges": []
}
```

<GET /api/users> Get all registered users

Response
```http
[
    {
        "userId": "1",
        "username": "15shivabhagat",
        "score": 0,
        "badges": []
    }
]
```

<GET /api/users/1> Get registered user by id

Response
```http
{
    "userId": "1",
    "username": "15shivabhagat",
    "score": 0,
    "badges": []
}
```
<PUT /api/users/1> Update score of registered user by id
```http
{
    "score":76
}
```
Response
```http
{
    "userId": "1",
    "username": "15shivabhagat",
    "score": 76,
    "badges": [
        "CODE_MASTER"
    ]
}
```
<DELETE /api/users/1> Remove registered user by id

Response
```http
{
    "status": "OK",
    "message": "User removed Ssuccessfully!"
}
```
## Postman View
Postman Collection Link : https://elements.getpostman.com/redirect?entityId=27366721-ffb1b6fd-7e82-4a00-a261-5ccad5a2cabc&entityType=collection

![Screenshot (54)](https://github.com/15shivabhagat/Code-a-Thon/assets/71555394/9e9e7b86-706d-4613-9861-3c177f150290)
![Screenshot (55)](https://github.com/15shivabhagat/Code-a-Thon/assets/71555394/3de7bc69-f7c9-49b1-8ccf-701813ca600e)
![Screenshot (56)](https://github.com/15shivabhagat/Code-a-Thon/assets/71555394/b68f6d95-0251-419f-97cc-51a2004411d1)
![Screenshot (57)](https://github.com/15shivabhagat/Code-a-Thon/assets/71555394/9f893523-0c11-4b8c-a621-dd9da1f5ddd7)
![Screenshot (58)](https://github.com/15shivabhagat/Code-a-Thon/assets/71555394/f948fb61-ca9a-4796-b4c4-fce20a8dfb5f)




