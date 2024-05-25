package org.example.auction.adapters.http;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.auction.domain.Auction;
import org.example.auction.domain.AuctionService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auctions")
@Slf4j
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Auction createAuction(Authentication authentication, @RequestBody @Valid Auction auction) {
        final String userUuid = authentication.getName();
        log.info("Creating auction: {} for user {}", auction, userUuid);
        auction.setSeller(userUuid);
        return auctionService.create(auction);
    }
}
