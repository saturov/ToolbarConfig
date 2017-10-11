package ru.strv.toolbarconfigsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import ru.strv.toolbar_config.ToolbarConfig;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToolbarConfig
                .builder(this)
                .setTitleId(R.string.app_name)
                .setSubtitleId(R.string.app_name)
                .setDisplayHomeAsUpEnabled(true)
                .setOnMenuItemClickListener(new ToolbarConfig.OnClickMenuItemListener() {
                    @Override
                    public void onClickMenuItem(MenuItem item) {
                        Log.d("ToolbarConfigSample", "onClickMenuItem = " + item);
                    }
                })
                .apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}