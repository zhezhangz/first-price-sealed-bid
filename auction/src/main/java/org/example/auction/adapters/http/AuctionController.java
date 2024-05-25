package org.example.auction.adapters.http;

import lombok.extern.slf4j.Slf4j;
import org.example.auction.domain.Auction;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auctions")
@Slf4j
public class AuctionController {

    @PostMapping
    public String createAuction(Authentication authentication, @RequestBody Auction auction) {
        log.info("Creating auction: {}", auction);
        return "success";
    }
}
