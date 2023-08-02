package dev.olatejulian.javaapiexample.shared.common;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public final class CustomExceptionMessages {
    private static final String BUNDLE_NAME = "messages";

    private static final ResourceBundle.Control UTF8_CONTROL = new UTF8Control();

    public static String getMessage(String code, Locale locale) {
        var bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale, UTF8_CONTROL);

        return bundle.getString(code);
    }

    private CustomExceptionMessages() {
    }

    private static class UTF8Control extends ResourceBundle.Control {
        @Override
        public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader,
                boolean reload)
                throws IllegalAccessException, InstantiationException, IOException {
            var bundleName = toBundleName(baseName, locale);

            var resourceName = toResourceName(bundleName, "properties");

            try (var stream = loader.getResourceAsStream(resourceName)) {
                return new PropertyResourceBundle(new InputStreamReader(stream, StandardCharsets.UTF_8));
            }
        }
    }
}