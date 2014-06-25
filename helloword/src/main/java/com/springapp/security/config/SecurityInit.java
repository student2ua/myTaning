package com.springapp.security.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * User: tor
 * Date: 23.06.14
 * Time: 19:00
 * Нам нужно настроить все так чтобы определенный URL паттерн (путь к определенному ресурсу)
 * проходил через уровень безопасности (проходил бы проверку фильтрами Spring Security).
 * Традиционный подход подразумевал настройку сервлет фильтра, в котором мы проверяли
 * бы учетные данные безопасности. С появлением Setvlet 3.x больше нету необходимости
 * объявлять фильтры в web.xml, вся настройка может быть осуществлена с помощью Java классов.
 * Как раз для этого нам нужен AbstractAnnotationConfigDispatcherServletInitializer
 */
public class SecurityInit extends AbstractSecurityWebApplicationInitializer {

}
