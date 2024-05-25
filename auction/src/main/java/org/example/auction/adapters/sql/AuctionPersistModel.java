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
}
