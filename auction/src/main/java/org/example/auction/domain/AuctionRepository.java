package org.example.auction.domain;

import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface AuctionRepository {

    Auction save(Auction auction);

    List<Auction> findAll(PageRequest pageRequest);
}
