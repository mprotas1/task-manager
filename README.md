
# Task manager

Task manager is a simple, RESTful application to perform user's task management. It allows user to have its own account and to manage his tasks. Actions that may be taken on simple task are: updating one, getting task with specific ID, marking as 'done' etc. It runs on embedded Tomcat's server.

## Technology
Used techologies/frameworks such as:
* Java
* Spring Boot
* Spring Data
* Hibernate
* MySQL
* Jackson

## API Reference

#### Get all users

```http
  GET /user/
```
Returns the list of all users existing in the system.

#### Get user

```http
  GET /user/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of user to fetch |

#### Add user

```http
  POST /user/
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `username`      | `string` | **Required**. User's username |

#### Delete user

```http
  DELETE /user/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. User's id |

#### Update existing user

```http
  PUT /user/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. User's id |
| `username`      | `string` | User's new username |

#### Get all user's tasks

```http
  GET /user/${userId}/task
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userId`      | `string` | **Required**. User's id |

#### Get all user's task with specific ID

```http
  GET /user/${userId}/task/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userId`      | `string` | **Required**. User's id |
| `id`      | `string` | **Required**. Task's id |

#### Get user's task with specific ID

```http
  GET /user/${userId}/task/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userId`      | `string` | **Required**. User's id |
| `id`      | `string` | **Required**. Task's id |

#### Create task for user

```http
  POST /user/${userId}/task
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userId`      | `string` | **Required**. User's id |
| `title`      | `string` | **Required**. Task's title |
| `content`      | `string` | **Required**. Content |

#### Delete task

```http
  DELETE /user/${userId}/task/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userId`      | `string` | **Required**. User's id |
| `id`      | `string` | **Required**. Task's id |

#### Update task

```http
  PUT /user/${userId}/task/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userId`      | `string` | **Required**. User's id |
| `id`      | `string` | **Required**. Task's id |
| `title`      | `string` | **Required**. Task's title |
| `content`      | `string` | **Required**. Content |


