package pl.sokolak.teamtally.frontend.localization;

public class Translator {

    private static final TranslationProvider translationProvider = new TranslationProvider();

    public static String t(String key, Object... params) {
        return translationProvider.getTranslation(key, params);
    }
}
