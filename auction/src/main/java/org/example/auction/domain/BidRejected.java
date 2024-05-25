package org.example.auction.domain;

public class BidRejected extends RuntimeException {

    public BidRejected(String message) {
        super("Bid rejected for " + message);
    }
}
