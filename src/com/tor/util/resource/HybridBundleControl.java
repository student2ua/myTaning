package com.tor.util.resource;

import java.io.IOException;
import java.sql.Connection;
import java.util.*;

/**
 * User: tor
 * Date: 02.08.2026
 * Time: 21:38
 * Комбинирование форматов в ResourceBundle.Control
 */
public class HybridBundleControl extends ResourceBundle.Control {
    private final Connection connection;

    public HybridBundleControl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<String> getFormats(String baseName) {
        // Порядок важен: сначала проверяется "db", затем "java.properties"
        List<String> formats = new ArrayList<String>();
        formats.add("db");
        formats.add("java.properties");
        return Collections.unmodifiableList(formats);
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format,
                                    ClassLoader loader, boolean reload)
            throws IllegalAccessException, InstantiationException, IOException {

        if ("db".equals(format)) {
            // Если создаем бандл из БД
            return new DbResourceBundle(connection, baseName, locale, loader);
        }

        // Для формата "java.properties" отдаем обработку стандартному Java Control
        return super.newBundle(baseName, locale, format, loader, reload);
    }
}
