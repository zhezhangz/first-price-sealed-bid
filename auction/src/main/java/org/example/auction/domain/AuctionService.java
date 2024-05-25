package org.example.auction.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;

    public Auction create(Auction auction) {
        auction.open();
        return auctionRepository.save(auction);
    }
}
