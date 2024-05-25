package org.example.auction.domain;

import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface AuctionRepository {

    Auction save(Auction auction);

    List<Auction> findAll(PageRequest pageRequest);

    Optional<Auction> findById(String auctionId);

    Optional<Auction> findByIdWithBids(String auctionId);
}
