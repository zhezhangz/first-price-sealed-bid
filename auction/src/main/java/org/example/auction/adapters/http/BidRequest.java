package org.example.auction.adapters.http;

import jakarta.validation.constraints.Positive;

public record BidRequest(
        @Positive(message = "bid price should be positive") Long price) {
}
