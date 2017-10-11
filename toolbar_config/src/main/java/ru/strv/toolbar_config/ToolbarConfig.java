package ru.strv.toolbar_config;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

/**
 * Builder class for flexible toolbar configuring.
 */
public class ToolbarConfig {


    public interface OnClickMenuItemListener {
        void onClickMenuItem(MenuItem item);
    }

    private AppCompatActivity activity;

    private
    @IdRes
    int toolbarId = R.id.toolbar;

    private boolean showHomeAsUp;

    private
    @StringRes
    int titleId;

    private
    @StringRes
    int subtitleId;

    private
    @DrawableRes
    int homeAsUpIndicatorId;

    private OnClickMenuItemListener onClickMenuItemListener;

    private View.OnClickListener onClickNavigationListener;

    private ToolbarConfig(AppCompatActivity activity) {
        this.activity = activity;
    }

    public static ToolbarConfig builder(AppCompatActivity activity) {
        return new ToolbarConfig(activity);
    }

    public ToolbarConfig setId(int toolbarId) {
        this.toolbarId = toolbarId;
        return this;
    }

    public ToolbarConfig setTitleId(@StringRes int titleId) {
        this.titleId = titleId;
        return this;
    }

    /**
     * Set the subtitle of this toolbar.
     *
     * @param subtitleId subtitle string resource ID
     */
    public ToolbarConfig setSubtitleId(@StringRes int subtitleId) {
        this.subtitleId = subtitleId;
        return this;
    }

    /**
     * Set whether home should be displayed as an "up" affordance.
     * Set this to true if selecting "home" returns up by a single level in your UI
     * rather than back to the top level or front page.
     *
     * @param showHomeAsUp True to show the user that selecting home will return one
     *                     level up rather than to the top level of the app
     */
    public ToolbarConfig setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
        this.showHomeAsUp = showHomeAsUp;
        return this;
    }

    /**
     * Set an alternate drawable to display next to the icon/logo/title
     * when DISPLAY_HOME_AS_UP is enabled. This can be useful if you are using
     * this mode to display an alternate selection for up navigation, such as a sliding drawer.
     *
     * @param resId Resource ID of a drawable to use for the up indicator, or null
     *              to use the theme's default
     */
    public ToolbarConfig setHomeAsUpIndicatorId(@DrawableRes int resId) {
        this.homeAsUpIndicatorId = resId;
        return this;
    }

    public ToolbarConfig setOnMenuItemClickListener(OnClickMenuItemListener onClickMenuItemListener) {
        this.onClickMenuItemListener = onClickMenuItemListener;
        return this;
    }

    public ToolbarConfig setOnNavigationClickListener(View.OnClickListener onClickNavigationItemListener) {
        this.onClickNavigationListener = onClickNavigationItemListener;
        return this;
    }

    public void apply() {
        if (activity != null) {
            Toolbar toolbar = activity.findViewById(toolbarId);
            if (toolbar != null) {
                configToolbar(toolbar);
            }
        }
    }

    private void configToolbar(Toolbar toolbar) {
        activity.setSupportActionBar(toolbar);
        configTitle(toolbar);
        configSubtitle(toolbar);
        configDisplayHomeAsUpEnabled(activity.getSupportActionBar());
        configHomeAsUpIndicator(activity.getSupportActionBar());
        configOnMenuItemClickListener(toolbar);
    }

    private void configTitle(Toolbar toolbar) {
        String subtitle = "";
        if (!TextUtils.isEmpty(toolbar.getResources().getString(titleId))) {
            subtitle = toolbar.getResources().getString(titleId);
        }
        if (!TextUtils.isEmpty(subtitle)) {
            toolbar.setTitle(subtitle);
        }
    }

    private void configSubtitle(Toolbar toolbar) {
        String subtitle = "";
        if (!TextUtils.isEmpty(toolbar.getResources().getString(subtitleId))) {
            subtitle = toolbar.getResources().getString(subtitleId);
        }
        if (!TextUtils.isEmpty(subtitle)) {
            toolbar.setSubtitle(subtitle);
        }
    }

    private void configDisplayHomeAsUpEnabled(ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(this.showHomeAsUp);
        }
    }

    private void configHomeAsUpIndicator(ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(this.homeAsUpIndicatorId);
        }
    }

    private void configOnMenuItemClickListener(Toolbar toolbar) {
        if (toolbar != null) {
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (ToolbarConfig.this.onClickMenuItemListener != null) {
                        ToolbarConfig.this.onClickMenuItemListener.onClickMenuItem(item);
                    }
                    return true;
                }
            });
        }
    }

}