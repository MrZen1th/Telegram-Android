package org.telegram.messenger;

import android.content.SharedPreferences;

/**
 * OneTGMod: налаштування модів, що зберігаються у глобальних SharedPreferences.
 * Читаються з будь-якого місця застосунку (Java) без звернень до UI.
 */
public class OneTGModConfig {

    public static final String KEY_ANTI_DELETE       = "onetgmod_anti_delete";
    public static final String KEY_HIDE_TYPING       = "onetgmod_hide_typing";
    public static final String KEY_ALLOW_SCREENSHOTS = "onetgmod_allow_screenshots";
    public static final String KEY_ALLOW_FORWARDS    = "onetgmod_allow_forwards";
    public static final String KEY_NO_ADS            = "onetgmod_no_ads";
    public static final String KEY_HIDE_ONLINE       = "onetgmod_hide_online";

    private static SharedPreferences prefs() {
        return MessagesController.getGlobalMainSettings();
    }

    public static boolean get(String key, boolean defValue) {
        try {
            return prefs().getBoolean(key, defValue);
        } catch (Throwable e) {
            return defValue;
        }
    }

    public static void set(String key, boolean value) {
        try {
            prefs().edit().putBoolean(key, value).apply();
        } catch (Throwable ignore) {
        }
    }

    /** Не даємо видаляти повідомлення з локальної бази (анти-видалення). */
    public static boolean isAntiDelete() {
        return get(KEY_ANTI_DELETE, true);
    }

    /** Не надсилаємо на сервер стан «друкує…». */
    public static boolean isHideTyping() {
        return get(KEY_HIDE_TYPING, false);
    }

    /** Дозволяємо скріншоти (знімаємо FLAG_SECURE). */
    public static boolean isAllowScreenshots() {
        return get(KEY_ALLOW_SCREENSHOTS, true);
    }

    /** Ігноруємо заборону пересилання повідомлень у чатах. */
    public static boolean isAllowForwards() {
        return get(KEY_ALLOW_FORWARDS, true);
    }

    /** Ховаємо спонсорські/рекламні блоки. */
    public static boolean isNoAds() {
        return get(KEY_NO_ADS, true);
    }

    /** Не показуємо «онлайн» іншим (не оновлюємо статус присутності). */
    public static boolean isHideOnline() {
        return get(KEY_HIDE_ONLINE, false);
    }
}
