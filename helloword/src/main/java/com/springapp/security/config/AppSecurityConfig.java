package com.springapp.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * User: tor
 * Date: 23.06.14
 * Time: 18:28
 * Настройки Безопасности
 */
@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication().withUser("user").password("user").roles("USER");
        builder.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
        builder.inMemoryAuthentication().withUser("superadmin").password("superadmin").roles("SUPERADMIN");
    }

    /**
     * Обратите внимание что роли в месте где мы присваиваем их пользователю пишутся без префикса
     * ROLE_, в то время как в указании в методе access, в котором мы, с помощью языка выражений
     * SPEL (Spring Expression Language), задаем выражения проверки ресурса (в нашем случае
     * выражение проверки роли пользователя hasRole(‘ROLE_имя роли’)), мы пишем роль с
     * префиксом ROLE_. Еще одна маленькая хитрость для аутентификации:
     * defaultSuccessUrl("/", false),
     * установка второго параметра (alwaysUse) в false говорит Spring Security что в случае успешной
     * авторизации можно перенаправить пользователя на ту страничку, с которой он пришел на страницу
     * аутентификации.
     */
    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.authorizeRequests()
                .antMatchers("/protected/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/confidential/**").access("hasRole('ROLE_SUPERADMIN')");
//                .and().formLogin().defaultSuccessUrl("/", false)
        security.formLogin().defaultSuccessUrl("/", false);
    }
}
