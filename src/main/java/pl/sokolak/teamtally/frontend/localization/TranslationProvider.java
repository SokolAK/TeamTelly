package pl.sokolak.teamtally.frontend.localization;

import com.vaadin.flow.i18n.I18NProvider;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class TranslationProvider implements I18NProvider {

    private static final String BUNDLE_PREFIX = "translate";
    private final Locale DEFAULT_LOCALE = Locale.of("en", "US");
    private final List<Locale> locales = List.of(DEFAULT_LOCALE);
    private final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PREFIX, DEFAULT_LOCALE);

    public String getTranslation(String key, Object... params) {
        return getTranslation(key, DEFAULT_LOCALE, params);
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        if (key == null) {
            LoggerFactory.getLogger(TranslationProvider.class.getName()).warn("Got lang request for key with null value!");
            return "";
        }

        // Unocomment if more than one supported languages
        // final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PREFIX, locale);

        String value;
        try {
            value = bundle.getString(key);
        } catch (final MissingResourceException e) {
            LoggerFactory.getLogger(TranslationProvider.class.getName()).warn("Missing resource", e);
            return "!" + locale.getLanguage() + ": " + key;
        }
        if (params.length > 0) {
            value = MessageFormat.format(value, params);
        }
        return value;
    }

    @Override
    public List<Locale> getProvidedLocales() {
        return locales;
    }
}

