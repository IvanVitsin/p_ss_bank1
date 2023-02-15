package com.bank.Mapper;


import com.bank.DTO.HistoryDTO;
import com.bank.Entity.HistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "history")
public interface HistoryMapper {

    HistoryMapper MAPPER = Mappers.getMapper(HistoryMapper.class);

    HistoryDTO toHistoryDTO(HistoryEntity historyEntity);

    HistoryEntity toHistory(HistoryDTO historyDTO);
}
