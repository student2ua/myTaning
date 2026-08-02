package com.tor.util.resource;

import java.io.IOException;
import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * User: tor
 * Date: 02.08.2026
 * Time: 21:44
 * <pre>
 * {@code
 * Connection dbConnection = getConnection(); // Ваше подключение к БД
 * ResourceBundle.Control dbControl = new DbBundleControl(dbConnection);
 *
 * // Вызов стандартный, но задействуется наш DbBundleControl
 * Locale locale = new Locale("uk");
 * ResourceBundle bundle = ResourceBundle.getBundle("PopulationAction", locale, dbControl);
 *
 * String reportTitle = bundle.getString("zvit_1");
 * }</pre>
 */
public class DbBundleControl extends DbResourceBundle.Control {
    private final Connection connection;

    public DbBundleControl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<String> getFormats(String baseName) {
        // Указываем собственный формат загрузки
        return Collections.singletonList("db");
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format,
                                    ClassLoader loader, boolean reload)
            throws IllegalAccessException, InstantiationException, IOException {
        if ("db".equals(format)) {
            return new DbResourceBundle(connection, baseName, locale,loader);
        }
        return super.newBundle(baseName, locale, format, loader, reload);
    }

    // Задаем время жизни кэша (в миллисекундах).
    // TTL_DONT_CACHE - не кэшировать (всегда свежие из БД)
    // 3600000L - кэшировать на 1 час
    @Override
    public long getTimeToLive(String baseName, Locale locale) {
        return 3600000L;
    }
}
