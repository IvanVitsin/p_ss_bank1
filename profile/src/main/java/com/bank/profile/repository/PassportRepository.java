package com.bank.profile.repository;

import com.bank.profile.entity.PassportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс репозитория для обработки операций с данными для сущности PassportEntity.
 *
 * @author Sazonov Vladimir
 * @version 1.0
 * @since 12.02.2023
 */
@Repository
public interface PassportRepository extends JpaRepository<PassportEntity, Long> {
}
