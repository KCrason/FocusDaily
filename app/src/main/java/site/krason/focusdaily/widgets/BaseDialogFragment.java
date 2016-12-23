package site.krason.focusdaily.widgets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Created by KCrason on 2016/12/22.
 * @email 535089696@qq.com
 */

public abstract class BaseDialogFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResoure(), container, false);
    }

    public abstract int getLayoutResoure();
}
