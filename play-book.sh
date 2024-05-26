function press_enter_to_continue() {
  read -n 1 -s -r -p " -> Press any key to continue"
  printf "\n\n"
}

step=0
function step() {
  step=$((step + 1))
  printf "%s. %s\n" "$step" "$1"
}

function split() {
  echo '-----------------------------------------------'
  printf "\n"
}

function ok() {
  printf "  [  OK   ] %s\n" "$1"
}

function error() {
  printf "  [ ERROR ] %s\n\nPlease check and retry.\n\n" "$1"
  exit 1
}

function check_service() {
  if curl -ivs "$2" > /dev/null 2>&1;
    then ok "Server $1 is UP"
  else
    error "Server $1 is DOWN."
  fi
}

parse_json() {
  read -r json
  echo "$json" | tr "{" '\n' | tr , '\n' | tr "}" '\n' | grep "\"$1\"" | tr '"' ' ' | awk  -F' ' '{print $NF}'
}
function login_user() {
  curl -s -X PUT http://localhost:8080/users/login \
    -H "Content-Type: application/json" \
    -d '{
          "username": "'"$1"'",
          "password": "password"
        }
       '
}

printf "Let's play with First Price Sealed Bid Auctions\n"
printf "===============================================\n\n"

# 1
step "Check if the services are up and running:"

check_service user-service http://localhost:8080/h2-console
check_service auction-service http://localhost:8081/h2-console

split

# 2
step "Mocked users we are having today:"

printf "  - alice: 00000000-0000-0000-0000-000000000001\n"
printf "  - bobby: 00000000-0000-0000-0000-000000000002\n"
printf "  - carla: 00000000-0000-0000-0000-000000000003\n"

split
press_enter_to_continue

# 3
step "Login to get user tokens:"

user_token_alice="$(login_user alice)"
ok "User alice logged in successfully"
user_token_bobby="$(login_user bobby)"
ok "User bobby logged in successfully"
user_token_carla="$(login_user carla)"
ok "User carla logged in successfully"

split
press_enter_to_continue

# 4
step "Create an auction:"
printf "   User alice creates an auction for 'iPhone 21' with a minimum price of '1000'\n\n"

press_enter_to_continue

auction_id=$(curl -s -X POST http://localhost:8081/auctions \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $user_token_alice" \
  -d '{
        "product": "iPhone 21",
        "minPrice": 1000
      }
     ' | parse_json "id")

ok "created! Auction ID: $auction_id"

split

# 5
step "Place a bid:"
printf "   User bobby places a bid of '1200' for the auction 'iPhone 21'\n\n"

bobby_bid_id=$(curl -s -X POST http://localhost:8081/auctions/"$auction_id"/bids \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $user_token_bobby" \
  -d '{
        "price": 1200
      }
     ' | parse_json "id")

press_enter_to_continue
ok "placed! Bid ID: $bobby_bid_id"

split

# 6
step "Place another bid:"
printf "   User carla places a bid of '1400' for the auction 'iPhone 21'\n\n"

carla_bid_id=$(curl -s -X POST http://localhost:8081/auctions/"$auction_id"/bids \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $user_token_carla" \
  -d '{
        "price": 1400
      }
     ' | parse_json "id")

press_enter_to_continue
ok "placed! Bid ID: $carla_bid_id"

split

# 7
step "Close the auction:"
printf "   User alice closes the auction 'iPhone 21'\n\n"

press_enter_to_continue

termination_response=$(curl -s -X PUT http://localhost:8081/auctions/"$auction_id"/termination \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $user_token_alice")
winner=$(echo "${termination_response##*winner}" | parse_json "buyer")
price=$(echo "${termination_response##*winner}" | parse_json "price")

ok "Winner is $winner with price $price"

split
ok "That's all folks!"
