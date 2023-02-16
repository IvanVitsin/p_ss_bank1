package com.bank.profile.service;

import com.bank.profile.dto.RegistrationDTO;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.mapper.MultiEntityMapper;
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
 * Этот класс является реализацией интерфейса  RegistrationService.
 * Он предоставляет методы для выполнения операций CRUD с регистрационными данными
 * логирование происходит с помощью ламбока аннотацией @Slf4j с выводом в консоль.
 * <p>
 * Поля:
 * RegistrationRepository: репозиторий регистрации,используется для доступа к регистрационным данным в базе данных.
 *
 * @author Sazonov Vladimir
 * @version 1.0
 * @since 12.02.2023
 */
@Slf4j
@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationRepository registrationRepository;

    /**
     * Создает новый объект регистрация на основе информации в предоставленном объекте RegistrationDTO.
     *
     * @param registrationDTO — объект содержащий информацию о создаваемом объекте регистрации.
     * @return созданный объект регистрация как объект RegistrationDTO
     */
    @Override
    @Transactional
    public RegistrationDTO saveRegistration(RegistrationDTO registrationDTO) {
        RegistrationEntity registrationEntity = MultiEntityMapper.MAPPER.toRegistration(registrationDTO);
        RegistrationEntity saveReg = registrationRepository.saveAndFlush(registrationEntity);
        log.info("Данные о регистрации добавлены в базу данных");
        return MultiEntityMapper.MAPPER.toRegistrationDTO(saveReg);
    }

    /**
     * Обновляет существующий объект регистрация информацией из предоставленного объекта RegistrationDTO.
     *
     * @param registrationDTO — объект содержащий информацию об обновленном объекте регистрации.
     * @param id              идентификатор объекта регистрации, который необходимо обновить.
     * @return обновленный объект регистрация как объект RegistrationDTO
     */
    @Override
    @Transactional
    public RegistrationDTO update(RegistrationDTO registrationDTO, Long id) {
        Optional<RegistrationEntity> entityOptional = registrationRepository.findById(id);
        if (entityOptional.isEmpty()) {
            log.error("does not exist");
            throw new NotFoundException("No registration found with the given id");
        }
        RegistrationEntity registrationEntity = entityOptional.get();
        registrationEntity.setCountry(registrationDTO.getCountry());
        registrationEntity.setRegion(registrationDTO.getRegion());
        registrationEntity.setCity(registrationDTO.getCity());
        registrationEntity.setDistrict(registrationDTO.getDistrict());
        registrationEntity.setLocality(registrationDTO.getLocality());
        registrationEntity.setStreet(registrationDTO.getStreet());
        registrationEntity.setHouseNumber(registrationDTO.getHouseNumber());
        registrationEntity.setHouseBlock(registrationDTO.getHouseBlock());
        registrationEntity.setFlatNumber(registrationDTO.getFlatNumber());
        registrationEntity.setIndex(registrationDTO.getIndex());
        registrationRepository.saveAndFlush(registrationEntity);
        log.info("Данные о регистрации обнавлены в базе данных");
        return MultiEntityMapper.MAPPER.toRegistrationDTO(registrationEntity);
    }

    /**
     * Удаляет объект регистрации с указанным идентификатором.
     *
     * @param id идентификатор объекта регистрации, который необходимо удалить.
     */
    @Override
    @Transactional
    public void delete(Long id) {
        Optional<RegistrationEntity> registrationEntity = registrationRepository.findById(id);
        if (registrationEntity.isEmpty()) {
            log.error("does not exist");
            throw new NotFoundException("No registration found with the given id");
        }
        log.info("Данные успешно удалены из базы данных");
        registrationRepository.deleteById(id);
    }

    /**
     * Извлекает список всех сущностей регистрации, хранящихся в базе данных.
     *
     * @return список всех объектов Registration в виде списка объектов RegistrationDTO
     */
    @Override
    public List<RegistrationDTO> getALLRegistration() {
        if (registrationRepository == null) {
            log.error("does not exist");
            throw new NotFoundException("registrationRepository is null");
        }
        List<RegistrationEntity> allRegistration = registrationRepository.findAll();
        if (allRegistration.isEmpty()) {
            log.error("does not exist");
            throw new NotFoundException("No account details found");
        }
        log.info("Все данные успешно изьяты из базы данных");
        return allRegistration
                .stream()
                .map(MultiEntityMapper.MAPPER::toRegistrationDTO)
                .collect(Collectors.toList());
    }
}
