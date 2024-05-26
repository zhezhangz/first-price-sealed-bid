# First-Price Sealed-Bid Auction

## Overview

This project is a simple implementation of
a [First-Price Sealed-Bid Auction](https://en.wikipedia.org/wiki/First-price_sealed-bid_auction) system.

## Dependencies

- Java 17
- Gradle 8.7
- Spring Boot 3.3.0

## Quickly Experience The Feature

To quickly experience the feature, follow the steps below.

#### Start applications

Go to `user` and `auction` directories, and, in each, run

```shell
./gradlew bootRun
```

#### Run the playbook script

Go to the root directory, and run

```shell
./playbook.sh
```

Follow the promotions in the playbook script.

## User Service

In the `user` directory, the User Service is implemented,
which provides user login feature and issues user token.

The user login feature requires a username and password.
However, for demo purposes, the password is ignored.

### Usage

#### Build And Test

Go to the `user` directory, and run

```shell
./gradlew build
```

#### Start Server

To start the server, and run

```shell
./gradlew bootRun
```

the user application will start on port `8080`.

The h2 database console is available at `http://localhost:8080/h2-console` with database URL `jdbc:h2:mem:fpsb-user`.

### API

#### PUT /users/login

Login with username and password to get a token.

> NOTE: The `password` is ignored, which means any fake password is accepted.

Request body:

```json
{
  "username": "alice",
  "password": "password"
}
```

#### GET /.well-known/jwks.json

Get the public key for JWT verification.
The auction service will use this endpoint to get the public key for token verification.

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

[//]: # (end of user table)

## Auction Service

In the `auction` directory,
the Auction Service is implemented,
which provides auction features.

### Usage

#### Build And Test

Go to the `auction` directory, and run

```shell
./gradlew build
```

#### Start Server

To start the server, and run

```shell
./gradlew bootRun
```

the user application will start on port `8081`.

The h2 database console is available at `http://localhost:8081/h2-console` with database URL `jdbc:h2:mem:fpsb-auction`.

### API

Each API requires a token in the `Authorization` header.

#### POST /auctions

Create a new auction.

Request body:

```json
{
  "product": "Cook's iPhone 21",
  "minPrice": 1000
}
```

#### GET /auctions

Get all auctions.

#### POST /auctions/{id}/bids

Place a bid on an auction.

Request body:

```json
{
  "price": 1200
}
```

#### PUT /auctions/{id}/termination

Close an auction and see who win the auction with which price.
No response body needed.
