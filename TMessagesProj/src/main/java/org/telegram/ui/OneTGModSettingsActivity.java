package org.telegram.ui;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.OneTGModConfig;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.Theme;

/**
 * OneTGMod: власний екран налаштувань модів.
 * Відкривається з «Налаштування → OneTGMod». Усі перемикачі діють одразу.
 */
public class OneTGModSettingsActivity extends Activity {

    private LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            setTheme(Theme.isCurrentThemeDark() ? R.style.Theme_TMessages_Dark : R.style.Theme_TMessages);
        } catch (Throwable ignore) {
        }
        super.onCreate(savedInstanceState);
        buildUi();
    }

    private int dp(float v) {
        return AndroidUtilities.dp(v);
    }

    private int color(String key) {
        return Theme.getColor(key);
    }

    private void buildUi() {
        final int bg = color(Theme.key_windowBackgroundGray);
        final int cardBg = color(Theme.key_windowBackgroundWhite);
        final int textColor = color(Theme.key_windowBackgroundWhiteBlackText);
        final int grayColor = color(Theme.key_windowBackgroundWhiteGrayText);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(bg);

        // ---- верхня панель ----
        LinearLayout bar = new LinearLayout(this);
        bar.setOrientation(LinearLayout.HORIZONTAL);
        bar.setGravity(Gravity.CENTER_VERTICAL);
        bar.setBackgroundColor(cardBg);
        bar.setPadding(dp(8), dp(10), dp(16), dp(10));

        TextView back = new TextView(this);
        back.setText("\u2190");
        back.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        back.setTextColor(textColor);
        back.setGravity(Gravity.CENTER);
        back.setPadding(dp(12), dp(4), dp(12), dp(4));
        back.setOnClickListener(v -> finish());
        bar.addView(back, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView title = new TextView(this);
        title.setText("OneTGMod");
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextColor(textColor);
        title.setPadding(dp(4), 0, 0, 0);
        bar.addView(title, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        root.addView(bar, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        ScrollView scroll = new ScrollView(this);
        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(0, dp(8), 0, dp(24));
        scroll.addView(content, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        root.addView(scroll, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f));

        addSectionHeader("Модифікації", textColor);
        addSwitch("Приховане збереження видалених",
                "Видалені співрозмовником повідомлення залишаються у чаті (і ваші власні теж)",
                OneTGModConfig.isAntiDelete(), OneTGModConfig.KEY_ANTI_DELETE, cardBg, textColor, grayColor);
        addSwitch("Не надсилати «друкує…»",
                "Співрозмовник не бачить, що ви набираєте текст",
                OneTGModConfig.isHideTyping(), OneTGModConfig.KEY_HIDE_TYPING, cardBg, textColor, grayColor);
        addSwitch("Не показувати «онлайн»",
                "Статус присутності не оновлюється, поки ви в застосунку",
                OneTGModConfig.isHideOnline(), OneTGModConfig.KEY_HIDE_ONLINE, cardBg, textColor, grayColor);

        addSectionHeader("Кастомізація", textColor);
        addSwitch("Дозволити скріншоти",
                "Знімає системну заборону на знімки екрана у чатах",
                OneTGModConfig.isAllowScreenshots(), OneTGModConfig.KEY_ALLOW_SCREENSHOTS, cardBg, textColor, grayColor);
        addSwitch("Дозволити пересилання",
                "Ігнорує позначку «пересилання заборонено» у каналах і чатах",
                OneTGModConfig.isAllowForwards(), OneTGModConfig.KEY_ALLOW_FORWARDS, cardBg, textColor, grayColor);
        addSwitch("Без реклами",
                "Ховає спонсорські пости та рекламні блоки",
                OneTGModConfig.isNoAds(), OneTGModConfig.KEY_NO_ADS, cardBg, textColor, grayColor);

        TextView note = new TextView(this);
        note.setText("Зміни діють одразу, перезапуск не потрібен.\nЗбірка: OneTGMod 13.2.2 (arm64-v8a)");
        note.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        note.setTextColor(grayColor);
        note.setPadding(dp(20), dp(18), dp(20), dp(8));
        content.addView(note);

        setContentView(root);
    }

    private void addSectionHeader(String text, int textColor) {
        TextView tv = new TextView(this);
        tv.setText(text.toUpperCase());
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        tv.setTextColor(textColor);
        tv.setAlpha(0.55f);
        tv.setPadding(dp(20), dp(16), dp(20), dp(6));
        content.addView(tv);
    }

    private void addSwitch(String title, String subtitle, boolean checked, final String key,
                           int cardBg, int textColor, int grayColor) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setBackgroundColor(cardBg);
        row.setPadding(dp(20), dp(12), dp(16), dp(12));

        LinearLayout texts = new LinearLayout(this);
        texts.setOrientation(LinearLayout.VERTICAL);

        TextView t = new TextView(this);
        t.setText(title);
        t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        t.setTextColor(textColor);
        texts.addView(t);

        TextView s = new TextView(this);
        s.setText(subtitle);
        s.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        s.setTextColor(grayColor);
        s.setPadding(0, dp(2), 0, 0);
        texts.addView(s);

        row.addView(texts, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        SwitchCompat sw = new SwitchCompat(this);
        sw.setChecked(checked);
        sw.setOnCheckedChangeListener((buttonView, isChecked) -> OneTGModConfig.set(key, isChecked));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = dp(12);
        row.addView(sw, lp);

        content.addView(row, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        View divider = new View(this);
        divider.setBackgroundColor(grayColor);
        divider.setAlpha(0.15f);
        LinearLayout.LayoutParams dlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(0.5f));
        dlp.leftMargin = dp(20);
        content.addView(divider, dlp);
    }
}
