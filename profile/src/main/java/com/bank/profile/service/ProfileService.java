package com.bank.profile.service;

import com.bank.profile.dto.PassportDTO;
import com.bank.profile.dto.ProfileDTO;
import com.bank.profile.dto.RegistrationDTO;

/**
 * ProfileService — это интерфейс, определяющий методы управления профилями пользователей.
 * <p>
 * Он содержит методы сохранения профиля, обновления профиля и удаления профиля, а также
 * <p>
 * способы получения паспорта и регистрационных данных, связанных с профилем.
 *
 * @author Sazonov Vladimir
 * @version 1.0
 * @since 12.02.2023
 */
public interface ProfileService {
    /**
     * Сохраняет профиль с указанными profileDTO, idPassport и idRegis.
     *
     * @param profileDTO объект DTO, содержащий информацию о профиле, которую необходимо сохранить.
     * @param idPassport Идентификатор паспорта, связанного с профилем.
     * @param idRegis    Идентификатор регистрации, связанной с профилем.
     * @return сохраненный объект ProfileDTO.
     */
    ProfileDTO saveProfile(ProfileDTO profileDTO, Long idPassport, Long idRegis);

    /**
     * Сохраняет профиль с указанными profileDTO и idPassport без фактической регистрации.
     *
     * @param profileDTO объект DTO, содержащий информацию о профиле, которую необходимо сохранить.
     * @param idPassport идентификатор паспорта, связанного с профилем.
     * @return сохраненный объект ProfileDTO.
     */
    ProfileDTO saveProfileNoActualRegistration(ProfileDTO profileDTO, Long idPassport);

    /**
     * Обновляет профиль с указанными profileDTO и идентификатором.
     *
     * @param profileDTO объект DTO, содержащий обновленную информацию о профиле.
     * @param id         идентификатор профиля, который необходимо обновить.
     * @return обновленный объект ProfileDTO.
     */
    ProfileDTO update(ProfileDTO profileDTO, Long id);

    /**
     * Удаляет профиль с указанным id.
     *
     * @param id Идентификатор удаляемого профиля.
     */
    void delete(Long id);

    /**
     * Извлекает паспортную информацию, связанную с профилем с указанным идентификатором.
     *
     * @param id идентификатор профиля.
     * @return объект PassportDTO, связанный с профилем.
     */
    PassportDTO getProfilePassport(Long id);

    /**
     * Извлекает регистрационную информацию, связанную с паспортом профиля с указанным идентификатором.
     *
     * @param id идентификатор профиля.
     * @return объект RegistrationDTO, связанный с паспортом профиля.
     */
    RegistrationDTO getPassportRegistration(Long id);
}
