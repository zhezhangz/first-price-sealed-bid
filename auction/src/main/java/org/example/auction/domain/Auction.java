package org.example.auction.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Auction {

    private final String id;

    private final String product;

    /**
     * The minimum price of the auction, in cents.
     */
    private final Long minPrice;

}
