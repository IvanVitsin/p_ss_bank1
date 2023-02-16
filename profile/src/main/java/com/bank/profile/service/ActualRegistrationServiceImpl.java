package com.bank.profile.service;

import com.bank.profile.dto.ActualRegistrationDTO;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.mapper.MultiEntityMapper;
import com.bank.profile.repository.ActualRegisRepository;
import com.bank.profile.util.exeptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс ActualRegistrationServiceImpl реализует интерфейс ActualRegistrationService.
 * и отвечает за управление фактическими регистрационными данными клиентов банка.
 * Логирование происходит с помощью ламбока аннотацией @Slf4j с выводом в консоль.
 * <p>
 * Поля:
 * -actualRegisRepository: экземпляр ActualRegisRepository, который используется для доступа к
 * фактическим регистрационным данным которые хранятся в базе данных.
 *
 * @author Sazonov Vladimir
 * @version 1.0
 * @since 12.02.2023
 */
@Slf4j
@Service
@AllArgsConstructor
public class ActualRegistrationServiceImpl implements ActualRegistrationService {
    private final ActualRegisRepository actualRegisRepository;

    /**
     * Метод saveActualRegistration принимает объект ActualRegistrationDTO в качестве параметра и
     * сохраняет  данные в базе данных.
     *
     * @param actualRegistrationDTO фактические регистрационные данные, которые необходимо сохранить в базе данных.
     * @return сохраненный объект ActualRegistrationDTO.
     */
    @Override
    @Transactional
    public ActualRegistrationDTO saveActualRegistration(ActualRegistrationDTO actualRegistrationDTO) {
        ActualRegistrationEntity actualRegistrationEntity = MultiEntityMapper
                .MAPPER
                .toActualRegistration(actualRegistrationDTO);
        ActualRegistrationEntity saveActualReg = actualRegisRepository.saveAndFlush(actualRegistrationEntity);
        log.info("Данные о фактической регистрации добавлены в базу данных");
        return MultiEntityMapper.MAPPER.toActualRegistrationDTO(saveActualReg);
    }

    /**
     * Метод updateActualRegistration принимает объект ActualRegistrationDTO и идентификатор в качестве параметров.
     * и обновляет фактические регистрационные данные в базе данных с заданным идентификатором.
     *
     * @param actualRegistrationDTO фактические регистрационные данные, которые необходимо обновить в базе данных..
     * @param id                    Идентификатор фактических регистрационных данных, подлежащих обновлению в базе данных.
     * @return обновленный объект ActualRegistrationDTO.
     * @throws NotFoundException, если в базе данных не найдено ни одной фактической регистрации с данным идентификатором.
     */
    @Override
    @Transactional
    public ActualRegistrationDTO updateActualRegistration(ActualRegistrationDTO actualRegistrationDTO,
                                                          Long id) {
        Optional<ActualRegistrationEntity> entityOptional = actualRegisRepository.findById(id);
        if (entityOptional.isEmpty()) {
            log.error("does not exist");
            throw new NotFoundException("No actual registration found with the given id");
        }
        ActualRegistrationEntity actualRegistration = entityOptional.get();
        actualRegistration.setCountry(actualRegistrationDTO.getCountry());
        actualRegistration.setRegion(actualRegistrationDTO.getRegion());
        actualRegistration.setCity(actualRegistrationDTO.getCity());
        actualRegistration.setDistrict(actualRegistrationDTO.getDistrict());
        actualRegistration.setLocality(actualRegistrationDTO.getLocality());
        actualRegistration.setStreet(actualRegistrationDTO.getStreet());
        actualRegistration.setHouseNumber(actualRegistrationDTO.getHouseNumber());
        actualRegistration.setHouseBlock(actualRegistrationDTO.getHouseBlock());
        actualRegistration.setFlatNumber(actualRegistrationDTO.getFlatNumber());
        actualRegistration.setIndex(actualRegistrationDTO.getIndex());
        actualRegisRepository.saveAndFlush(actualRegistration);
        log.info("Данные о фактической фактической регистрации обновлены в базе данных");
        return MultiEntityMapper.MAPPER.toActualRegistrationDTO(actualRegistration);
    }

    /**
     * Метод deleteActualRegistration удаляет существующий объект фактической регистрации из базы данных.
     *
     * @param id идентификатор удаляемой фактической регистрации.
     * @throws NotFoundException, если в базе данных не найдено ни одной фактической регистрации с данным идентификатором.
     */
    @Override
    @Transactional
    public void deleteActualRegistration(Long id) {
        Optional<ActualRegistrationEntity> registrationEntity = actualRegisRepository.findById(id);
        if (registrationEntity.isEmpty()) {
            log.error("does not exist");
            throw new NotFoundException("No actual registration found with the given id");
        }
        actualRegisRepository.deleteById(id);
        log.info("Данные о фактической фактической регистрации удалены из базы данных");
    }

    /**
     * Метод  getALLActualRegistration получает список всех объектов фактической регистрации из базы данных.
     *
     * @return список объектов фактической регистрации
     * @throws NotFoundException, если в базе данных не найдены сведения об фактической регистрации или экземпляр actualRegisRepository имеет значение null.
     */
    @Override
    public List<ActualRegistrationDTO> getALLActualRegistration() {
        if (actualRegisRepository == null) {
            log.error("does not exist");
            throw new NotFoundException("ActualRegistrationRepository is null");
        }
        List<ActualRegistrationEntity> allRegistration = actualRegisRepository.findAll();
        if (allRegistration.isEmpty()) {
            log.error("does not exist");
            throw new NotFoundException("No  Actual Registrations found");
        }
        log.info("Данные о фактической фактической регистрации изьяты из базы данных");
        return allRegistration
                .stream()
                .map(MultiEntityMapper.MAPPER::toActualRegistrationDTO)
                .collect(Collectors.toList());
    }
}
