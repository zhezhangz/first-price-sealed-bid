package org.example.auction.adapters.sql;

import org.example.auction.domain.Bid;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class BidMapper {

    public static final BidMapper MAPPER = Mappers.getMapper(BidMapper.class);

    public abstract Bid toDomain(BidPersistModel persistModel);

    public abstract BidPersistModel fromDomain(Bid domainModel);
}
