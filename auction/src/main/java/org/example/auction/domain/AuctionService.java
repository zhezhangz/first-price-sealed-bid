package org.example.auction.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;

    private final BidRepository bidRepository;

    @Transactional
    public Auction create(Auction auction) {
        auction.open();
        return auctionRepository.save(auction);
    }

    public List<Auction> findAll(PageRequest pageRequest) {
        return auctionRepository.findAll(pageRequest);
    }

    @Transactional
    public Bid placeBid(Bid bid) {
        bid.setBidAt(Instant.now());
        Auction auction = auctionRepository.findById(bid.getAuctionId())
                .orElseThrow(AuctionNotFound::new);
        auction.placeBid(bid);
        return bidRepository.save(bid);
    }

    @Transactional
    public AuctionResult terminate(String auctionId) {
        Auction auction = auctionRepository.findByIdWithBids(auctionId)
                .orElseThrow(AuctionNotFound::new);
        if (auction.getStatus() == AuctionStatus.OPEN) {
            auction.close();
            auctionRepository.save(auction);
        }
        final Bid winner = auction.findWinner().orElse(null);
        return new AuctionResult(auction, winner);
    }
}
