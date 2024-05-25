package org.example.auction.adapters.sql;

import org.example.auction.domain.Auction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class AuctionMapper {

    public static final AuctionMapper MAPPER = Mappers.getMapper(AuctionMapper.class);

    @Mapping(target = "bids", ignore = true)
    public abstract Auction toDomain(AuctionPersistModel persistModel);

    public abstract Auction toDomainWithBids(AuctionPersistModel persistModel);

    public abstract AuctionPersistModel fromDomain(Auction domainModel);
}
