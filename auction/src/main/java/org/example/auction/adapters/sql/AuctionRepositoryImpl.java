package org.example.auction.adapters.sql;

import lombok.RequiredArgsConstructor;
import org.example.auction.domain.Auction;
import org.example.auction.domain.AuctionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Auction> findAll(PageRequest pageRequest) {
        return jpaAuctionRepository.findAll(pageRequest).stream()
                .map(AuctionMapper.MAPPER::toDomain)
                .toList();
    }

    @Override
    public Optional<Auction> findById(String auctionId) {
        return jpaAuctionRepository.findById(auctionId)
                .map(AuctionMapper.MAPPER::toDomain);
    }

    @Override
    public Optional<Auction> findByIdWithBids(String auctionId) {
        return jpaAuctionRepository.findById(auctionId)
                .map(AuctionMapper.MAPPER::toDomainWithBids);
    }
}
