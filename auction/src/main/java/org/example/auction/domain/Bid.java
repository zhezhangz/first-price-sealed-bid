package org.example.auction.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Bid {

    private String id;

    private String auctionId;

    private String buyer;

    private Long price;

    private Instant bidAt;
}
