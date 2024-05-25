package org.example.auction.adapters.sql;

import lombok.RequiredArgsConstructor;
import org.example.auction.domain.Bid;
import org.example.auction.domain.BidRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BidRepositoryImpl implements BidRepository {

    private final JpaBidRepository jpaBidRepository;

    @Override
    public Bid save(Bid bid) {
        var persistModel = BidMapper.MAPPER.fromDomain(bid);
        var saved = jpaBidRepository.save(persistModel);
        return BidMapper.MAPPER.toDomain(saved);
    }
}
