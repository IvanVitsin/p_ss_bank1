package com.bank.profile.util.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс исключения для обработки ошибки «NOT_FOUND».
 * <p>
 * Код состояния HTTP для этого исключения установлен на 404 (NOT_FOUND).
 *
 * @author Sazonov Vladimir
 * @version 1.0
 * @since 12.02.2023
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    /**
     * Конструктор для инициализации этого исключения и сообщение.
     */
    public NotFoundException(String message) {
        super(message);
    }
}
