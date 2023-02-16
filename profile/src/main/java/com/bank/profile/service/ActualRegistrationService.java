package com.bank.profile.service;

import com.bank.profile.dto.ActualRegistrationDTO;

import java.util.List;

/**
 * Интерфейс ActualRegistrationService определяет методы управления данными фактической регистрации в банковской системе.
 * <p>
 * Он предоставляет методы для создания, обновления, удаления и получения сведений об фактической регистрации.
 *
 * @author Sazonov Vladimir
 * @version 1.0
 * @since 12.02.2023
 */
public interface ActualRegistrationService {
    /**
     * Этот метод используется для сохранения новой фактической регистрации в базе данных.
     *
     * @param actualRegistrationDTO объект фактических регистрационных данных для сохранения в базе данных.
     * @return Cохраненные данные фактической регистрации.
     */
    ActualRegistrationDTO saveActualRegistration(ActualRegistrationDTO actualRegistrationDTO);

    /**
     * Этот метод используется для обновления существующей фактической регистрации в базе данных.
     *
     * @param actualRegistrationDTO объект фактических регистрационных данных, подлежащий обновлению.
     * @param id                    идентификатор фактической регистрации, которую необходимо обновить.
     * @return Oбновленные данные фактической регистрации.
     */
    ActualRegistrationDTO updateActualRegistration(ActualRegistrationDTO actualRegistrationDTO, Long id);

    /**
     * Этот метод используется для удаления существующей фактической регистрации из базы данных.
     *
     * @param id идентификатор удаляемой фактической регистрации.
     */
    void deleteActualRegistration(Long id);

    /**
     * Получает все данные фактической регистрации в базе данных.
     *
     * @return список всех данных фактической регистрации
     */
    List<ActualRegistrationDTO> getALLActualRegistration();
}
