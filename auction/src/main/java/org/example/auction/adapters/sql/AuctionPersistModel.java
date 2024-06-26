package org.example.auction.adapters.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "auction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuctionPersistModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String product;

    private Long minPrice;

    private String seller;

    private String status;

    @OneToMany(mappedBy = "auctionId", fetch = FetchType.LAZY)
    private List<BidPersistModel> bids;
}
