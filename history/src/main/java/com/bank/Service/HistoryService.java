package com.bank.Service;


import com.bank.DTO.HistoryDTO;
import com.bank.Entity.HistoryEntity;

import java.util.List;

public interface HistoryService {

    List<HistoryEntity> getAll();

    HistoryDTO saveHistory(HistoryDTO saveHistory);
    HistoryDTO updateHistory(HistoryDTO updateHistory);
    HistoryDTO deleteHistory(HistoryDTO updateHistory);

}
