package com.bank.profile.util.exeptions;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * Класс CustomErrorController реализует ErrorController для обработки ошибок, возникающих в приложении.
 * <p>
 * Этот класс используется для обработки и возврата клиенту ответов об ошибках.
 * Поля:
 * errorAttributes — это экземпляр интерфейса ErrorAttributes.
 * PATH - строковое поле адресс ответа об ошибке
 *
 * @author Sazonov Vladimir
 * @version 1.0
 * @since 12.02.2023
 */
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Controller
public class CustomErrorController implements ErrorController {
    private static final String PATH = "/error";
    ErrorAttributes errorAttributes;

    /**
     * Метод error сопоставляется с путем «/error» и возвращает объект ответа с подробностями ошибки в теле.
     *
     * @param webRequest — объект веб-запроса.
     * @return ResponseEntity — Объект ответа с подробностями об ошибке.
     */
    @RequestMapping(CustomErrorController.PATH)
    public ResponseEntity<ErrorDto> error(WebRequest webRequest) {
        Map<String, Object> attributes = errorAttributes.getErrorAttributes(
                webRequest,
                ErrorAttributeOptions.of(ErrorAttributeOptions.Include.EXCEPTION, ErrorAttributeOptions.Include.MESSAGE)
        );
        return ResponseEntity
                .status((Integer) attributes.get("status"))
                .body(ErrorDto
                        .builder()
                        .error((String) attributes.get("error"))
                        .errorDescription((String) attributes.get("message"))
                        .build()
                );
    }
}
