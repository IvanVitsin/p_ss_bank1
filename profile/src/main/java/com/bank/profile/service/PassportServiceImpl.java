package com.bank.profile.service;

import com.bank.profile.dto.PassportDTO;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.MultiEntityMapper;
import com.bank.profile.repository.PassportRepository;
import com.bank.profile.repository.RegistrationRepository;
import com.bank.profile.util.exeptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Это класс , который реализует интерфейс PassportService.
 * Этот класс предоставляет методы для выполнения операций, связанных с паспортами.
 * Он использует паспортный репозиторий и регистрационный репозиторий для выполнения операций с базой данных.
 * Логирование происходит с помощью ламбока аннотацией @Slf4j с выводом в консоль.
 * Поля:
 * PassportRepository: PassportRepository
 * RegistrationRepository: Регистрационный репозиторий
 *
 * @author Sazonov Vladimir
 * @version 1.0
 * @since 12.02.2023
 */
@Slf4j
@Service
@AllArgsConstructor
public class PassportServiceImpl implements PassportService {
    private final PassportRepository passportRepository;
    private final RegistrationRepository registrationRepository;

    /**
     * Этот метод сохраняет новый паспорт в базу данных.
     *
     * @param passportDTO:    PassportDTO - объект  содержащий паспортные данные.
     * @param registrationId: идентификатор связанной регистрации.
     * @return PassportDTO - объект содержащий сохраненные паспортные данные.
     * @throws NotFoundException - когда не найдена регистрация с данным id.
     */
    @Override
    @Transactional
    public PassportDTO savePassport(PassportDTO passportDTO, Long registrationId) {
        Optional<RegistrationEntity> entityOptional = registrationRepository.findById(registrationId);
        if (entityOptional.isEmpty()) {
            log.error("does not exist");
            throw new NotFoundException("No passport found with the given id");
        }
        PassportEntity passportEntity = MultiEntityMapper.MAPPER.toPassport(passportDTO);
        passportEntity.setRegistrationEntity(registrationRepository.getReferenceById(registrationId));
        PassportEntity savePassport = passportRepository.saveAndFlush(passportEntity);
        log.info("Данные о паспорте добавлены в базу данных");
        return MultiEntityMapper.MAPPER.toPassportDTO(savePassport);
    }

    /**
     * Этот метод обновляет существующий паспорт в базе данных.
     *
     * @param passportDTO: объект  содержащий обновленные паспортные данные.
     * @param id:          идентификатор паспорта, который необходимо обновить.
     * @return PassportDTO - объект  содержащий обновленные паспортные данные.
     * @throws NotFoundException - когда паспорт с заданным id не найден.
     */
    @Override
    @Transactional
    public PassportDTO update(PassportDTO passportDTO, Long id) {
        Optional<PassportEntity> entityOptional = passportRepository.findById(id);
        if (entityOptional.isEmpty()) {
            log.error("does not exist");
            throw new NotFoundException("No passport found with the given id");
        }
        PassportEntity passportEntity = entityOptional.get();
        passportEntity.setId(id);
        passportEntity.setSeries(passportDTO.getSeries());
        passportEntity.setNumber(passportDTO.getNumber());
        passportEntity.setLastName(passportDTO.getLastName());
        passportEntity.setFirstName(passportDTO.getFirstName());
        passportEntity.setMiddleName(passportDTO.getMiddleName());
        passportEntity.setGender(passportDTO.getGender());
        passportEntity.setBirthDate(passportDTO.getBirthDate());
        passportEntity.setBirthPlace(passportDTO.getBirthPlace());
        passportEntity.setIssuedBy(passportDTO.getIssuedBy());
        passportEntity.setDateOfIssue(passportDTO.getDateOfIssue());
        passportEntity.setDivisionCode(passportDTO.getDivisionCode());
        passportEntity.setExpirationDate(passportDTO.getExpirationDate());
        passportRepository.saveAndFlush(passportEntity);
        log.info("Данные о паспорте обнавлены в базе данных");
        return MultiEntityMapper.MAPPER.toPassportDTO(passportEntity);
    }

    /**
     * Этот метод удаляет паспорт из базы данных.
     *
     * @param id: идентификатор удаляемого паспорта.
     * @throws NotFoundException - когда паспорт с заданным id не найден.
     */
    @Override
    @Transactional
    public void delete(Long id) {
        Optional<PassportEntity> passportEntity = passportRepository.findById(id);
        if (passportEntity.isEmpty()) {
            log.error("does not exist");
            throw new NotFoundException("No passport found with the given id");
        }
        passportRepository.deleteById(id);
        log.info("Данные о паспорте удалены из базы данных");
    }

    /**
     * Этот метод возвращает все паспорта из базы данных.
     *
     * @return - список объектов передачи паспортных данных.
     * @throws NotFoundException - когда отсутствует passportRepository или когда паспорта не найдены.
     */
    @Override
    public List<PassportDTO> getALLPassport() {
        if (passportRepository == null) {
            throw new NotFoundException("passportRepository is null");
        }
        List<PassportEntity> allPassport = passportRepository.findAll();
        if (allPassport.isEmpty()) {
            log.error("does not exist");
            throw new NotFoundException("No passports found");
        }
        log.info("Данные о паспорте изьяты из базы данных");
        return allPassport
                .stream()
                .map(MultiEntityMapper.MAPPER::toPassportDTO)
                .collect(Collectors.toList());
    }
}
