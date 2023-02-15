package com.bank.Controller;

import com.bank.DTO.HistoryDTO;
import com.bank.Entity.HistoryEntity;
import com.bank.Service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
@RequestMapping( "/api")
public class RestController {

    private final HistoryService historyService;

    @GetMapping("/history")
    public ResponseEntity<List<HistoryEntity>> getHistory() {
        return new ResponseEntity<>(historyService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/history")
    public ResponseEntity<HistoryDTO> saveHistory(@RequestBody HistoryDTO historyDTO) {
        return new ResponseEntity<>(historyService.saveHistory(historyDTO), HttpStatus.OK);
    }
    @PutMapping("/history")
    public ResponseEntity<HistoryDTO> updateHistory(@RequestBody HistoryDTO historyDTO) {
        return new ResponseEntity<>(historyService.updateHistory(historyDTO), HttpStatus.OK);
    }
    @DeleteMapping("/history")
    public ResponseEntity<HistoryDTO> deleteHistory(@RequestBody HistoryDTO historyDTO) {
        return new ResponseEntity<>(historyService.deleteHistory(historyDTO), HttpStatus.OK);
    }

}
