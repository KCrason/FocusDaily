package site.krason.focusdaily.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author Created by KCrason on 2016/12/13.
 * @email 535089696@qq.com
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        initViews();
    }

    public abstract int getLayoutId();

    public abstract void initViews();

}
