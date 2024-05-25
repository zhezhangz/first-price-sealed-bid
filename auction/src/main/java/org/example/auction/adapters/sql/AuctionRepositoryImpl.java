package org.example.auction.adapters.sql;

import lombok.RequiredArgsConstructor;
import org.example.auction.domain.Auction;
import org.example.auction.domain.AuctionRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuctionRepositoryImpl implements AuctionRepository {

    private final JpaAuctionRepository jpaAuctionRepository;

    @Override
    public Auction save(Auction auction) {
        var persistModel = AuctionMapper.MAPPER.fromDomain(auction);
        var saved = jpaAuctionRepository.save(persistModel);
        return AuctionMapper.MAPPER.toDomain(saved);
    }
}
