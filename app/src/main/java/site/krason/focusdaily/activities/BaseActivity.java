package site.krason.focusdaily.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import site.krason.focusdaily.R;

/**
 * @author Created by KCrason on 2016/12/13.
 * @email 535089696@qq.com
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            if (isExistToolbar()) {
                setToolbar();
            }
            initViews();
        }
    }

    protected abstract boolean isExistToolbar();

    /**
     * 注意在设置toolbar时，toolbar的id必须设置成toolbar，否则报空
     */
    public void setToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public abstract int getLayoutId();

    public abstract void initViews();

}
