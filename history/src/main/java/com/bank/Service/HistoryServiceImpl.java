package com.bank.Service;


import com.bank.DTO.HistoryDTO;
import com.bank.Entity.HistoryEntity;
import com.bank.Mapper.HistoryMapper;
import com.bank.Repository.HistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;

    @Override
    @Transactional
    public List<HistoryEntity> getAll() {
        return historyRepository.findAll();
    }

    @Override
    @Transactional
    public HistoryDTO saveHistory(HistoryDTO saveHistory) {
        HistoryEntity historyEntity = HistoryMapper.MAPPER.toHistory(saveHistory);
        HistoryEntity historyEntity1 = historyRepository.save(historyEntity);
        return HistoryMapper.MAPPER.toHistoryDTO(historyEntity1);
    }
    @Override
    @Transactional
    public HistoryDTO updateHistory(HistoryDTO updateHistory) {
        HistoryEntity historyEntity = HistoryMapper.MAPPER.toHistory(updateHistory);
        HistoryEntity historyEntity2 = historyRepository.save(historyEntity);
        return HistoryMapper.MAPPER.toHistoryDTO(historyEntity2);
    }

    @Override
    @Transactional
    public HistoryDTO deleteHistory(HistoryDTO updateHistory) {
        HistoryEntity historyEntity = HistoryMapper.MAPPER.toHistory(updateHistory);
        HistoryEntity historyEntity1 = historyRepository.save(historyEntity);
        return HistoryMapper.MAPPER.toHistoryDTO(historyEntity1);
    }
}

