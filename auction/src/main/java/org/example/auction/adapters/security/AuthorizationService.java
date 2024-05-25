package org.example.auction.adapters.security;

import lombok.RequiredArgsConstructor;
import org.example.auction.domain.AuctionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final AuctionRepository auctionRepository;

    public boolean canAccess(String auctionId, String userId) {
        return auctionRepository.findById(auctionId)
                .map(auction -> auction.getSeller().equals(userId))
                .orElse(false);
    }
}
