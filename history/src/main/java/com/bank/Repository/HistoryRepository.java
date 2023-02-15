package com.bank.Repository;



import com.bank.Entity.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {

}
