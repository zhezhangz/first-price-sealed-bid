# First-Price Sealed-Bid Auction

## User Service

The User Service provides user login feature and issues user token.

### API

#### PUT /users/login

Login with username and password to get a token.

> NOTE: The `password` is ignored for just demo the feature,
> which means any fake password is accepted.

Request body:

```json
{
  "username": "alice",
  "password": "password"
}
```

### Mocked initial users

There are 9 initial users in the database.
Mock users are created by [this Flyway script](user/src/main/resources/db/migration/V1_2__init_mock_users.sql).

User's password is ignored for just demo the feature,
which means, to logging in, use the username below with any fake password.

[//]: # (user table)

| id | username | uuid                                 |
|----|----------|--------------------------------------|
| 1  | alice    | 00000000-0000-0000-0000-000000000001 | 
| 2  | bobby    | 00000000-0000-0000-0000-000000000002 | 
| 3  | carla    | 00000000-0000-0000-0000-000000000003 | 
| 4  | demon    | 00000000-0000-0000-0000-000000000004 | 
| 5  | elisa    | 00000000-0000-0000-0000-000000000005 | 
| 6  | fenix    | 00000000-0000-0000-0000-000000000006 | 
| 7  | glare    | 00000000-0000-0000-0000-000000000007 | 
| 8  | hello    | 00000000-0000-0000-0000-000000000008 | 
| 9  | kathy    | 00000000-0000-0000-0000-000000000009 | 

