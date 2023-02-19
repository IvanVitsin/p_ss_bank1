package src.main.java.com.bank.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * BeanUtil — это класс компонента, отвечающий за доступ к bean-компонентам из контекста приложения.
 * логирование происходит с помощью ламбока аннотацией @Slf4j с выводом в консоль.
 * Поля:
 * ApplicationContext: контекст приложения.
 *
 * @author Sazonov Vladimir
 * @version 1.0
 * @since 12.02.2023
 */
@Slf4j
@Component
public class BeanUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    /**
     * Этот метод устанавливает applicationContext приложения.
     *
     * @param applicationContext контекст приложения.
     * @throws BeansException, если возникает ошибка при установке контекста приложения.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        log.info("Контекст установлен BeanUtil.class");
    }

    /**
     * Этот метод возвращает bean указанного типа класса.
     *
     * @param beanClass тип класса возвращаемого компонента.
     * @return bean указанного типа класса.
     */
    public static <T> T getBean(Class<T> beanClass) {
        log.info("Получен бин указанного типа.BeanUtil.class");
        return context.getBean(beanClass);
    }
}