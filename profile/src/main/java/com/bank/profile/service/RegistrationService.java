package com.bank.profile.service;
import com.bank.profile.dto.RegistrationDTO;

import java.util.List;

/**
 * Интерфейс RegistrationService определяет методы
 * для создания, обновления, удаления и получения информации об объектах регистрация.
 *
 * @author Sazonov Vladimir
 * @version 1.0
 * @since 12.02.2023
 */
public interface RegistrationService {
    /**
     * Создает новый объект регистрация на основе информации в предоставленном объекте RegistrationDTO.
     *
     * @param registrationDTO — объект содержащий информацию о создаваемом объекте регистрации.
     * @return созданный объект регистрация как объект RegistrationDTO
     */
    RegistrationDTO saveRegistration(RegistrationDTO registrationDTO);

    /**
     * Обновляет существующий объект регистрация информацией из предоставленного объекта RegistrationDTO.
     *
     * @param registrationDTO — объект содержащий информацию об обновленном объекте регистрации.
     * @param id              идентификатор объекта регистрации, который необходимо обновить.
     * @return обновленный объект регистрация как объект RegistrationDTO
     */
    RegistrationDTO update(RegistrationDTO registrationDTO, Long id);

    /**
     * Удаляет объект регистрации с указанным идентификатором.
     *
     * @param id идентификатор объекта регистрации, который необходимо удалить.
     */
    void delete(Long id);

    /**
     * Извлекает список всех сущностей регистрации, хранящихся в базе данных.
     *
     * @return список всех объектов Registration в виде списка объектов RegistrationDTO
     */
    List<RegistrationDTO> getALLRegistration();
}
