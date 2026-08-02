package com.tor.util.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * User: tor
 * Date: 02.08.2026
 * Time: 21:39
 * Загрузка переводов по сети (HTTP / REST API)
 *
 * Динамическое обновление (Hot-Reload): Если нужно обновить тексты без перезапуска сервера/приложения, вы можете
 * переопределить метод getTimeToLive(...) в Control или вызывать метод очистки кэша Java
 * ResourceBundle.clearCache();
 */
public class NetworkBundleControl extends DbResourceBundle.Control {
    private final String serverUrl; // например, "http://i18n-server.com/api/translations/"

    public NetworkBundleControl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format,
                                    ClassLoader loader, boolean reload) throws IOException {

        // Формируем URL: http://i18n-server.com/api/translations/StudentAction_uk.properties
        String urlString = serverUrl + baseName + "_" + locale.getLanguage() + ".properties";
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(3000);

        InputStream stream = null;
        try {
            stream = connection.getInputStream();
            // Загружаем полученный поток как стандартный PropertyResourceBundle
            return new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
        } catch (FileNotFoundException e) {
            return null; // Если файл не найден на сервере
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }
}
