package com.tor.util.resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * User: tor
 * Date: 02.08.2026
 * Time: 21:35
 * <p> Этот класс расширяет ResourceBundle и загружает ключи и значения из вашей таблицы БД
 *  </p>
 * + Реализация Fallback прямо внутри класса DbResourceBundle
 */
public class DbResourceBundle extends ResourceBundle {
    private final Map<String, String> dbLookup = new HashMap<String, String>();
    private ResourceBundle fallbackFileBundle;

    public DbResourceBundle(Connection conn, String bundleName, Locale locale, ClassLoader loader) {
        // 1. Загружаем данные из БД
        loadFromDb(conn, bundleName, locale);

        // 2. Пытаемся загрузить локальный .properties как резервный вариант
        try {
            // Ищем локальный файл с помощью стандартного загрузчика Java
            this.fallbackFileBundle = ResourceBundle.getBundle("i18n." + bundleName, locale, loader);
        } catch (MissingResourceException e) {
            // Локального файла нет — игнорируем
            this.fallbackFileBundle = null;
        }
    }

    private void loadFromDb(Connection conn, String bundleName, Locale locale) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT msg_key, msg_value FROM i18n_messages WHERE bundle_name = ? AND locale_code = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, bundleName);
            stmt.setString(2, locale.getLanguage());

            rs = stmt.executeQuery();
            while (rs.next()) {
                dbLookup.put(rs.getString("msg_key"), rs.getString("msg_value"));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка чтения из БД: " + e.getMessage());
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
        }
    }
    @Override
    protected Object handleGetObject(String key) {
        // Шаг A: Сначала проверяем, есть ли перевод в БД
        if (dbLookup.containsKey(key)) {
            return dbLookup.get(key);
        }

        // Шаг B: Если в БД нет ключа — берем из локального .properties файла
        if (fallbackFileBundle != null && fallbackFileBundle.containsKey(key)) {
            return fallbackFileBundle.getString(key);
        }

        // Нигде не найдено
        return null;
    }

    @Override
    public Enumeration<String> getKeys() {
        Set<String> allKeys = new HashSet<String>(dbLookup.keySet());
        if (fallbackFileBundle != null) {
            Enumeration<String> fileKeys = fallbackFileBundle.getKeys();
            while (fileKeys.hasMoreElements()) {
                allKeys.add(fileKeys.nextElement());
            }
        }
        return Collections.enumeration(allKeys);
    }
}
