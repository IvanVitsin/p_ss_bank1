package com.bank.profile.service;

import com.bank.profile.dto.AccountDetailsDTO;

import java.util.List;

/**
 * Интерфейс AccountDetailsService определяет методы управления данными счета в банковской системе.
 * <p>
 * Он предоставляет методы для создания, обновления, удаления и получения сведений об учетной записи.
 *
 * @author Sazonov Vladimir
 * @version 1.0
 * @since 12.02.2023
 */
public interface AccountDetailsService {
    /**
     * Сохраняет данные учетной записи для данного профиля.
     *
     * @param accountDetailsDTO данные учетной записи, которые необходимо сохранить.
     * @param idProfile         идентификатор профиля, которому принадлежат данные учетной записи.
     * @return сохраненные данные учетной записи
     */
    AccountDetailsDTO saveAccountDetails(AccountDetailsDTO accountDetailsDTO, Long idProfile);

    /**
     * Обновляет данные учетной записи для данного идентификатора.
     *
     * @param accountDetailsDTO обновленные данные учетной записи
     * @param id                идентификатор данных учетной записи, которые необходимо обновить
     * @return обновленные данные учетной записи
     */
    AccountDetailsDTO updateAccountDetails(AccountDetailsDTO accountDetailsDTO, Long id);

    /**
     * Удаляет данные учетной записи для данного идентификатора.
     *
     * @param id идентификатор удаляемой учетной записи.
     */
    void deleteAccountDetails(Long id);

    /**
     * Получает все данные учетной записи в базе данных.
     *
     * @return список всех данных учетной записи
     */
    List<AccountDetailsDTO> getALLAccountDetails();
}
