package com.base.router;

import android.support.annotation.NonNull;

import com.base.library.rxlifecycle.components.support.RxFragment;
import com.base.library.utils.utilcode.util.LogUtils;
import com.base.router.empty.EmptyFragment;


/**
 * Describe:路由器
 * <p>
 * <p>
 * Created by zhigang wei
 * on 2018/4/18
 * <p>
 * <p>
 * Company :cpx
 */
public class FragmentRouter {

    @NonNull
    public static RxFragment getFragment(String name) {
        RxFragment fragment = null;
        try {
            Class fragmentClass = Class.forName(name);
            fragment = (RxFragment) fragmentClass.newInstance();
        } catch (Exception e) {
            fragment = new EmptyFragment();
            LogUtils.e("The fragment cannot be found");
        }
        return fragment;
    }
}
