package com.bank.profile.service;

import com.bank.profile.dto.PassportDTO;
import com.bank.profile.dto.ProfileDTO;
import com.bank.profile.dto.RegistrationDTO;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.MultiEntityMapper;
import com.bank.profile.repository.ActualRegisRepository;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.repository.ProfileRepository;
import com.bank.profile.util.exeptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Этот класс является реализацией интерфейса  ProfileService
 * предназначен для реализации методов, определенных в интерфейсе для управления профилями
 * логирование происходит с помощью ламбока аннотацией @Slf4j с выводом в консоль.
 * <p>
 * Поля класса:
 * ProfileRepository: репозиторий профиля
 * PassportRepository: паспортный репозиторий
 * ActualRegisRepository: репозиторий регистрации
 *
 * @author Sazonov Vladimir
 * @version 1.0
 * @since 12.02.2023
 */
@Slf4j
@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final PassportRepository passportRepository;
    private final ActualRegisRepository actualRegisRepository;

    /**
     * Сохраняет профиль с указанными profileDTO, idPassport и idRegis.
     *
     * @param profileDTO объект DTO, содержащий информацию о профиле, которую необходимо сохранить.
     * @param idPassport Идентификатор паспорта, связанного с профилем.
     * @param idRegis    Идентификатор регистрации, связанной с профилем.
     * @return сохраненный объект ProfileDTO.
     */
    @Override
    @Transactional
    public ProfileDTO saveProfile(ProfileDTO profileDTO, Long idPassport, Long idRegis) {
        ProfileEntity profileEntity = MultiEntityMapper.MAPPER.toProfile(profileDTO);
        Optional<PassportEntity> entityOptional = passportRepository.findById(idPassport);
        if (entityOptional.isEmpty()) {
            log.error("does not exist");
            throw new NotFoundException("No passport found with the given id");
        }
        Optional<ActualRegistrationEntity> entityOptional1 = actualRegisRepository.findById(idRegis);

        if (entityOptional1.isEmpty()) {
            log.error("does not exist");
            throw new NotFoundException("No  actual registration found with the given id");
        }
        profileEntity.setPassportEntity(passportRepository.getReferenceById(idPassport));
        profileEntity.setActualRegistration(actualRegisRepository.getReferenceById(idRegis));
        ProfileEntity save = profileRepository.save(profileEntity);
        log.info("Данные о профиле добавлены в базу данных");
        return MultiEntityMapper.MAPPER.toProfileDTO(save);
    }

    /**
     * Сохраняет профиль с указанными profileDTO и idPassport без фактической регистрации.
     *
     * @param profileDTO объект DTO, содержащий информацию о профиле, которую необходимо сохранить.
     * @param idPassport идентификатор паспорта, связанного с профилем.
     * @return сохраненный объект ProfileDTO.
     */
    @Override
    @Transactional
    public ProfileDTO saveProfileNoActualRegistration(ProfileDTO profileDTO, Long idPassport) {
        Optional<PassportEntity> entityOptional = passportRepository.findById(idPassport);
        if (entityOptional.isEmpty()) {
            log.error("does not exist");
            throw new NotFoundException("No passport found with the given id");
        }
        ProfileEntity profileEntity = MultiEntityMapper.MAPPER.toProfile(profileDTO);
        profileEntity.setPassportEntity(passportRepository.getReferenceById(idPassport));
        ProfileEntity save = profileRepository.saveAndFlush(profileEntity);
        log.info("Данные о профиле добавлены в базу данных");
        return MultiEntityMapper.MAPPER.toProfileDTO(save);
    }

    /**
     * Обновляет профиль с указанными profileDTO и идентификатором.
     *
     * @param profileDTO объект DTO, содержащий обновленную информацию о профиле.
     * @param id         идентификатор профиля, который необходимо обновить.
     * @return обновленный объект ProfileDTO.
     */
    @Override
    @Transactional
    public ProfileDTO update(ProfileDTO profileDTO, Long id) {
        Optional<ProfileEntity> entityOptional = profileRepository.findById(id);
        if (entityOptional.isEmpty()) {
            log.error("does not exist");
            throw new NotFoundException("No profile found with the given id");
        }
        ProfileEntity profileEntity = entityOptional.get();
        profileEntity.setPhoneNumber(profileDTO.getPhoneNumber());
        profileEntity.setEmail(profileDTO.getEmail());
        profileEntity.setNameOnCard(profileDTO.getNameOnCard());
        profileEntity.setInn(profileDTO.getInn());
        profileEntity.setSnils(profileDTO.getSnils());
        profileRepository.saveAndFlush(profileEntity);
        log.info("Данные о профиле обнавлены в базе данных");
        return MultiEntityMapper.MAPPER.toProfileDTO(profileEntity);
    }

    /**
     * Удаляет профиль с указанным id.
     *
     * @param id Идентификатор удаляемого профиля.
     */
    @Override
    @Transactional
    public void delete(Long id) {
        Optional<ProfileEntity> profileEntity = profileRepository.findById(id);
        if (profileEntity.isEmpty()) {
            log.error("does not exist");
            throw new NotFoundException("No profile found with the given id");
        }
        profileRepository.deleteById(id);
        log.info("Данные о профиле удалены из базы данных");
    }

    /**
     * Извлекает паспортную информацию, связанную с профилем с указанным идентификатором.
     *
     * @param id идентификатор профиля.
     * @return объект PassportDTO, связанный с профилем.
     */
    @Override
    public PassportDTO getProfilePassport(Long id) {
        Optional<ProfileEntity> entityOptional = profileRepository.findById(id);
        if (entityOptional.isEmpty()) {
            log.error("does not exist");
            throw new NotFoundException("No profile found with the given id");
        }
        ProfileEntity profileEntity = entityOptional.get();
        PassportEntity passportEntity = profileEntity.getPassportEntity();
        log.info("Данные о профиле изьяты из базы данных");
        return MultiEntityMapper.MAPPER.toPassportDTO(passportEntity);
    }

    /**
     * Извлекает регистрационную информацию, связанную с паспортом профиля с указанным идентификатором.
     *
     * @param id идентификатор профиля.
     * @return объект RegistrationDTO, связанный с паспортом профиля.
     */
    @Override
    public RegistrationDTO getPassportRegistration(Long id) {
        Optional<ProfileEntity> entityOptional = profileRepository.findById(id);
        if (entityOptional.isEmpty()) {
            log.error("does not exist");
            throw new NotFoundException("No profile found with the given id");
        }
        ProfileEntity profileEntity = entityOptional.get();
        PassportEntity passportEntity = profileEntity.getPassportEntity();
        RegistrationEntity registrationEntity = passportEntity.getRegistrationEntity();
        log.info("Данные о профиле изьяты из базы данных");
        return MultiEntityMapper.MAPPER.toRegistrationDTO(registrationEntity);
    }
}
