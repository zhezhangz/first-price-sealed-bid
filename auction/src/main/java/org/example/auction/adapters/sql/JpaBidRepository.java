package org.example.auction.adapters.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaBidRepository extends JpaRepository<BidPersistModel, String> {
}
