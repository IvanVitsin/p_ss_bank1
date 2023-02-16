package com.bank.profile.service;

import com.bank.profile.dto.PassportDTO;

import java.util.List;

/**
 * Интерфейс PassportService предоставляет методы для операций, связанных с паспортами,
 * таких как сохранение, обновление, удаление и получение всех паспортов.
 *
 * @author Sazonov Vladimir
 * @version 1.0
 * @since 12.02.2023
 */
public interface PassportService {
    /**
     * Сохраняет паспорт с заданными данными в предоставленном PassportDTO.
     *
     * @param passportDTO    объект содержащий данные паспорта, которые нужно сохранить.
     * @param registrationId ID регистрации, с которой должен быть связан паспорт.
     * @return объект PassportDTO с сохраненными паспортными данными.
     */
    PassportDTO savePassport(PassportDTO passportDTO, Long registrationId);

    /**
     * Обновляет паспорт с заданным идентификатором, добавляя сведения в предоставленный PassportDTO.
     *
     * @param passportDTO объект содержащий обновленные данные паспорта.
     * @param id          ID паспорта, который нужно обновить.
     * @return объект PassportDTO с обновленными паспортными данными.
     */
    PassportDTO update(PassportDTO passportDTO, Long id);

    /**
     * Удаляет паспорт с указанным ID.
     *
     * @param id ID удаляемого паспорта.
     */
    void delete(Long id);

    /**
     * Извлекает список всех паспортов, сохраненных в базе данных.
     *
     * @return список PassportDTO, содержащий данные всех паспортов.
     */
    List<PassportDTO> getALLPassport();
}
