package org.example.auction.adapters.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bid")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BidPersistModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String auctionId;

    private String buyer;

    private Long price;

    private String bidAt;
}
