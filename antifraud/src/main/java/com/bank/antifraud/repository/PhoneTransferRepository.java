package com.bank.antifraud.repository;

import com.bank.antifraud.entity.PhoneTransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneTransferRepository extends JpaRepository<PhoneTransferEntity, Long> {
}
