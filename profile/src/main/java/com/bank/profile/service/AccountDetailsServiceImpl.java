package com.bank.profile.service;

import com.bank.profile.dto.AccountDetailsDTO;
import com.bank.profile.entity.AccountDetailsEntity;
import com.bank.profile.mapper.MultiEntityMapper;
import com.bank.profile.repository.AccountDetailsRepository;
import com.bank.profile.repository.ProfileRepository;
import com.bank.profile.util.exeptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Это реализация интерфейса AccountDetailsService, обеспечивающая базовые операции CRUD для AccountDetails.
 * <p>
 * Класс использует AccountDetailsRepository для взаимодействия с базой данных и ProfileRepository для доступа к данным профиля.
 * связанные с данными учетной записи.
 * <p>
 * Поля:
 * - accountDetailsRepository: экземпляр AccountDetailsRepository, используемый для выполнения операций с базой данных над сущностями AccountDetails.
 * - profileRepository: экземпляр ProfileRepository, используемый для доступа к данным профиля, связанным с данными учетной записи.
 *
 * @author Sazonov
 * @version 1.0
 * @since 12.02.2023
 */
@Slf4j
@Service
@AllArgsConstructor
public class AccountDetailsServiceImpl implements AccountDetailsService {
    private final AccountDetailsRepository accountDetailsRepository;
    private final ProfileRepository profileRepository;

    /**
     * Сохраняет новый объект AccountDetailsDTO в базе данных.
     *
     * @param accountDetailsDTO — объект передачи данных, представляющий данные учетной записи, которые необходимо сохранить.
     * @param idProfile         - ID профиля, связанного с данными учетной записи.
     * @return - сохраненный объект AccountDetailsDTO.
     * @throws NotFoundException, если профиль с данным идентификатором не найден в базе данных.
     */
    @Override
    @Transactional
    public AccountDetailsDTO saveAccountDetails(AccountDetailsDTO accountDetailsDTO, Long idProfile) {
        AccountDetailsEntity accountDetailsEntity = MultiEntityMapper
                .MAPPER
                .toAccountDetails(accountDetailsDTO);
        accountDetailsEntity.setProfileEntity(profileRepository.getReferenceById(idProfile));
        AccountDetailsEntity saveDetails = accountDetailsRepository.save(accountDetailsEntity);
        log.info("Данные об учетной записи добавлены в базу данных");
        return MultiEntityMapper.MAPPER.toAccountDetailsDTO(saveDetails);
    }

    /**
     * Обновляет существующий объект AccountDetailsDTO в базе данных.
     *
     * @param accountDetailsDTO — объект передачи данных, представляющий обновленные сведения об учетной записи.
     * @param id                - ID учетной записи, которые необходимо обновить.
     * @return обновленный объект AccountDetailsDTO.
     * @throws NotFoundException, если в базе данных не найдено ни одной учетной записи с данным идентификатором.
     */
    @Override
    @Transactional
    public AccountDetailsDTO updateAccountDetails(AccountDetailsDTO accountDetailsDTO, Long id) {
        Optional<AccountDetailsEntity> entityOptional = accountDetailsRepository.findById(id);
        if (entityOptional.isEmpty()) {
            log.error("does not exist");
            throw new NotFoundException("No account Details found with the given id");
        }
        AccountDetailsEntity accountDetailsEntity = entityOptional.get();
        accountDetailsEntity.setAccountId(accountDetailsDTO.getAccountId());
        log.info("Данные об учетной записи обновлены в базе данных");
        return MultiEntityMapper.MAPPER.toAccountDetailsDTO(accountDetailsEntity);
    }

    /**
     * Удаляет существующий объект AccountDetailsDTO из базы данных.
     *
     * @param id идентификатор удаляемой учетной записи.
     * @throws NotFoundException, если в базе данных не найдено ни одной учетной записи с данным идентификатором.
     */
    @Override
    @Transactional
    public void deleteAccountDetails(Long id) {
        Optional<AccountDetailsEntity> accountDetails = accountDetailsRepository.findById(id);
        if (accountDetails.isEmpty()) {
            log.error("does not exist");
            throw new NotFoundException("No account Details found with the given id");
        }
        accountDetailsRepository.deleteById(id);
        log.info("Данные об учетной записи удалена из базы данных");
    }

    /**
     * Получает список всех объектов AccountDetailsDTO из базы данных.
     *
     * @return список объектов AccountDetailsDTO.
     * @throws NotFoundException, если в базе данных не найдены сведения об учетной записи или  accountDetailsRepository имеет значение null.
     */
    @Override
    public List<AccountDetailsDTO> getALLAccountDetails() {
        if (accountDetailsRepository == null) {
            log.error("does not exist");
            throw new NotFoundException("accountDetailsRepository is null");
        }
        List<AccountDetailsEntity> allDetails = accountDetailsRepository.findAll();
        if (allDetails.isEmpty()) {
            log.error("does not exist");
            throw new NotFoundException("No account details found");
        }
        log.info("Данные об учетных записях изьяты из базы данных");
        return allDetails
                .stream()
                .map(MultiEntityMapper.MAPPER::toAccountDetailsDTO)
                .collect(Collectors.toList());
    }
}
