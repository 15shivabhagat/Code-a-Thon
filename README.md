
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

