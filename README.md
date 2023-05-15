
# Task manager

Task manager is a simple, RESTful application to perform user's task management. It allows user to have its own account and to manage his tasks. Actions that may be taken on simple task are: updating one, getting task with specific ID, marking as 'done' etc. It is also possible to get the particular Page of list of all users/user's tasks. Runs on embedded Tomcat's server. When downloaded - can be run using docker container.

## Technology
Used technologies/frameworks such as:
* Java
* Spring Boot
* Spring Data JPA
* Docker
* Hibernate
* MySQL
* Jackson
* Maven

## API Reference

#### Get all users

```http
  GET /user/
```
Returns the list of all users existing in the system.

#### Get all users as a Page

```http
  GET /user/{page}
```
Returns the Page consisting of page.Size users.

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `page`      | `integer` | **Required**. Number of page |

#### Get all users as a Page sorted by their id

```http
  GET /user/{page}/sorted
```
Returns the Page consisting of page.Size users sorted by id ascending.

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `page`      | `integer` | **Required**. Number of page |

#### Get user

```http
  GET /user/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `integer` | **Required**. Id of user to fetch |

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
| `id`      | `integer` | **Required**. User's id |

#### Update existing user

```http
  PUT /user/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `integer` | **Required**. User's id |
| `username`      | `string` | User's new username |

#### Get all user's tasks

```http
  GET /user/${userId}/task
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userId`      | `integer` | **Required**. User's id |

#### Get all user's tasks as a Page<Task>

```http
  GET /user/${userId}/task/page/${page}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userId`      | `integer` | **Required**. User's id |
| `page`      | `integer` | **Required**. Number of page |
  
#### Get all user's tasks as a Page<Task> sorted

```http
  GET /user/${userId}/task/page/${page}/sorted
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userId`      | `integer` | **Required**. User's id |
| `page`      | `integer` | **Required**. Number of page |

#### Get user's task with specific ID

```http
  GET /user/${userId}/task/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userId`      | `integer` | **Required**. User's id |
| `id`      | `string` | **Required**. Task's id |

#### Get user's task with specific ID

```http
  GET /user/${userId}/task/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userId`      | `integer` | **Required**. User's id |
| `id`      | `integer` | **Required**. Task's id |

#### Create task for user

```http
  POST /user/${userId}/task
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userId`      | `integer` | **Required**. User's id |
| `title`      | `string` | **Required**. Task's title |
| `content`      | `string` | **Required**. Content |

#### Delete task

```http
  DELETE /user/${userId}/task/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userId`      | `integer` | **Required**. User's id |
| `id`      | `integer` | **Required**. Task's id |

#### Update task

```http
  PUT /user/${userId}/task/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `userId`      | `integer` | **Required**. User's id |
| `id`      | `integer` | **Required**. Task's id |
| `title`      | `string` | **Required**. Task's title |
| `content`      | `string` | **Required**. Content |

## System requirments

To run this application you need:

* Java 17 (or higher)
* Docker
* Maven
