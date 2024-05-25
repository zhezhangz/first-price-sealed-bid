package org.example.auction.adapters.sql;

import org.example.auction.domain.Bid;
import org.example.auction.domain.BidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql(scripts = {"/database_init/init_auctions.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class BidRepositoryImplTest {

    @Autowired
    private JpaBidRepository jpaBidRepository;

    private BidRepository bidRepository;

    @BeforeEach
    void setUp() {
        bidRepository = new BidRepositoryImpl(jpaBidRepository);
    }

    @Test
    void should_save_bid_to_db() {
        bidRepository.save(new Bid(null, "1", "mock-buyer-1", 1_000L, Instant.now()));

        final var savedBid = jpaBidRepository.findAll().stream().filter(b -> b.getBuyer().equals("mock-buyer-1")).findAny();
        assertThat(savedBid).isPresent()
                .map(BidPersistModel::getId).isPresent();
    }
}
