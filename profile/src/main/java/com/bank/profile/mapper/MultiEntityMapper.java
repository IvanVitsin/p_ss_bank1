package com.bank.profile.mapper;

import com.bank.profile.dto.*;
import com.bank.profile.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MultiEntityMapper — это класс сопоставления, отвечающий за сопоставление между сущностями и DTO.
 * Класс использует библиотеку MapStruct для сопоставления сущностей и DTO.
 * Поля:
 * MAPPER — постоянный экземпляр MultiEntityMapper.
 *
 * @author Sazonov Vladimir
 * @version 1.0
 * @since 12.02.2023
 */
@Mapper(componentModel = "spring")
public interface MultiEntityMapper {
    MultiEntityMapper MAPPER = Mappers.getMapper(MultiEntityMapper.class);

    /**
     * toProfileDTO — это метод, который сопоставляет ProfileEntity с ProfileDTO.
     *
     * @param profileEntity сопоставляемый объект профиля.
     * @return объект ProfileDTO, представляющий сущность DTO профиля.
     */
    ProfileDTO toProfileDTO(ProfileEntity profileEntity);

    /**
     * toProfile — это метод, который сопоставляет ProfileDTO с ProfileEntity.
     *
     * @param profileDTO cопоставляемый DTO профиля
     * @return объект ProfileEntity, представляющий сущность профиля
     */
    ProfileEntity toProfile(ProfileDTO profileDTO);

    /**
     * toPassportDTO — это метод, который сопоставляет PassportEntity с PassportDTO.
     *
     * @param passportEntity сопоставляемый объект паспорта.
     * @return объект PassportDTO, представляющий сущность паспорта.
     */
    PassportDTO toPassportDTO(PassportEntity passportEntity);

    /**
     * toPassport — это метод, который сопоставляет PassportDTO с PassportEntity.
     *
     * @param passportDTO сопоставляемый объект DTO паспорта.
     * @return объект PassportEntity, представляющий DTO паспорта.
     */
    PassportEntity toPassport(PassportDTO passportDTO);

    /**
     * toRegistrationDTO — это метод, который сопоставляет RegistrationEntity с RegistrationDTO.
     *
     * @param registrationEntity сопоставляемый регистрационный объект.
     * @return объект RegistrationDTO, представляющий объект регистрации.
     */
    RegistrationDTO toRegistrationDTO(RegistrationEntity registrationEntity);

    /**
     * toRegistration — это метод, который сопоставляет RegistrationDTO с RegistrationEntity.
     *
     * @param registrationDTO сопоставляемый DTO для сопоставления.
     * @return объект RegistrationEntity, представляющий объект регистрации.
     */
    RegistrationEntity toRegistration(RegistrationDTO registrationDTO);

    /**
     * toActualRegistrationDTO — это метод, который сопоставляет ActualRegistrationEntity с ActualRegistrationDTO.
     *
     * @param actualRegistrationEntity сопоставляемый DTO для сопоставления..
     * @return объект ActualRegistrationDTO, представляющий DTO фактический  регистрации.
     */
    ActualRegistrationDTO toActualRegistrationDTO(ActualRegistrationEntity actualRegistrationEntity);

    /**
     * toActualRegistration — это метод, который сопоставляет ActualRegistrationDTO с ActualRegistrationEntity.
     *
     * @param actualRegistrationDTO фактическая регистрационная DTO для сопоставления.
     * @return обьект ActualRegistrationEntity представляющий сущность фактической регистрации
     */
    ActualRegistrationEntity toActualRegistration(ActualRegistrationDTO actualRegistrationDTO);

    /**
     * toAccountDetailsDTO — это метод, который сопоставляет AccountDetailsEntity с AccountDetailsDTO.
     *
     * @param accountDetailsEntity сопоставляемый объект учетной записи.
     * @return объект PassportDTO, представляющий DTO учетной записи.
     */
    AccountDetailsDTO toAccountDetailsDTO(AccountDetailsEntity accountDetailsEntity);

    /**
     * toPassport — это метод, который сопоставляет PassportDTO с PassportEntity.
     *
     * @param accountDetailsDTO сопоставляемый объект DTO паспорта.
     * @return объект PassportEntity, представляющий сущность паспорта.
     */
    AccountDetailsEntity toAccountDetails(AccountDetailsDTO accountDetailsDTO);
}
