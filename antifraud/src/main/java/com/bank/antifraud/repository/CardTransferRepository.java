package com.bank.antifraud.repository;

import com.bank.antifraud.entity.CardTransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardTransferRepository extends JpaRepository<CardTransferEntity, Long> {
}
