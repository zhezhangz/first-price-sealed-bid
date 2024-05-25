package org.example.auction.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;

    public Auction create(Auction auction) {
        auction.open();
        return auctionRepository.save(auction);
    }

    public List<Auction> findAll(PageRequest pageRequest) {
        return auctionRepository.findAll(pageRequest);
    }
}
