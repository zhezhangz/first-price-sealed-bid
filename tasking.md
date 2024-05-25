# First-price sealed-bid auction

## User Service

### User login

- [x] Create Rest API /users/{username}/login
- [x] Create `UserService` to verify user
- [x] Create db table `user` to store users

### Mock initial users

- [x] Create flyway script to mock user

### User token

- [x] Use `UserService` to issue user token

## Auction Service

### Validate user token

- [x] Add `/.well-known/jwks.json` endpoint in user service
- [x] Add TokenValidator in auction service
- [x] Protect auction service with Spring security

### Seller register new product

### Buyer bid in a product

### Seller end an auction
