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

- [x] Create Rest API `POST /auctions`
- [x] Create `AuctionService` to register product
- [x] Create db table `auction` to store auction
- [x] Create `GET /auctions` to list all auctions, for testing purpose

### Buyer bid in a product

- [x] Create Rest API `POST /auctions/{auction-id}/bids`
    - bid price
    - auction id

- [x] Create `Bid` domain model
    - id
    - bid price
    - bid time
    - bid buyer
    - auction id

- [x] Add a list of `Bid` in `Auction` domain model

- [x] Create `BidService` to handle bid
    - [x] Set bid time
    - [x] Update db for auction with bid

- [x] Add `bid` method in `Auction` take a `Bid` object
    - [x] Validate bid price >= min price
    - [x] Validate auction status = OPEN
    - [x] Validate buyer != seller

- [x] Create db table `bid` to store bid
    - id
    - bid price
    - bid time
    - bid buyer
    - auction id

- [x] Handle transactional consistency between auction and bids

### Seller end an auction

- [ ] Associate bids with auction
    - [ ] Join bid to auction when querying auction
    - [ ] Lazy detach when bid is not needed for auction

