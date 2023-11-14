package com.quick.recording.emailservice.bundle;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.util.Locale;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class ResourceBundleControl  extends ResourceBundle.Control {

    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IOException {
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, "properties");
        ResourceBundle bundle = null;
        InputStream inputStream = null;
        if (reload) {
            URL url = loader.getResource(resourceName);
            if (Objects.nonNull(url)) {
                URLConnection connection = url.openConnection();
                if (Objects.nonNull(connection)) {
                    connection.setUseCaches(false);
                    inputStream = connection.getInputStream();
                }
            }
        } else {
            inputStream = loader.getResourceAsStream(resourceName);
        }
        if (Objects.nonNull(inputStream)) {
            try {
                bundle = new PropertyResourceBundle(new InputStreamReader(inputStream, UTF_8));
            } finally {
                inputStream.close();
            }
        }
        return bundle;
    }

}
