/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package ae.emaratech.loginmodule.views.base;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import ae.emaratech.loginmodule.R;
import ae.emaratech.loginmodule.utils.LocaleManager;
import ae.emaratech.loginmodule.utils.NetworkUtils;
import dagger.android.AndroidInjection;
import ae.emaratech.loginmodule.utils.CommonUtils;

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity  implements BaseFragment.Callback{
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    // TODO
    // this can probably depend on isLoading variable of BaseViewModel,
    // since its going to be common for all the activities
    private ProgressDialog mProgressDialog;

    private T mViewDataBinding;
    private V mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performDependencyInjection();
        performDataBinding();
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();

    }
    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }



    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void openActivityOnTokenExpire() {
        //   startActivity(LoginActivity.getStartIntent(this));
        finish();
    }

    public boolean isNetworkConnected() {
        return NetworkUtils.isConnected(getApplicationContext());
    }

    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public void showSnackMessage(View parentView, String message, int length) {
        Snackbar.make(parentView, message, length)
                .setAction(getResources().getString(R.string.Close), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .show();
    }

    public void showSnackMessage(String message) {
        if (message != null)
            Log.d("error_log", message);
        Snackbar.make(findViewById(R.id.container), message, Snackbar.LENGTH_SHORT)
                .setAction(getResources().getString(R.string.Close), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                })
                .show();
    }



    public String getCurrentLanguage() {
        return LocaleManager.getLanguage(this);
    }

    public AlertDialog.Builder createAlertMsg(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage(message);
        builder.setCancelable(false);
        return builder;
    }

    public AlertDialog.Builder createAlertMsg(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        return builder;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    public void replaceCurrentFragment(int targetContainer, Fragment targetFragment, boolean addToBackStack, boolean animate) {
        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = false;
        fragmentPopped = manager.popBackStackImmediate(targetFragment
                .getClass().getName(), 0);
        if (!fragmentPopped
                && manager.findFragmentByTag(targetFragment.getClass()
                .getName()) == null) { // fragment
            FragmentTransaction ft = manager.beginTransaction();
            if (animate)
                ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out
                        , android.R.anim.fade_in,
                        android.R.anim.fade_out);
            ft.replace(targetContainer, targetFragment, targetFragment.getClass()
                    .getName());
            if (addToBackStack) {
                ft.addToBackStack(targetFragment.getClass().getName());
            }
            ft.commit();
        }
    }

    public void replaceCurrentFragment(Fragment targetFragment,
                                       boolean addToBackStack, boolean animate) {
        replaceCurrentFragment(R.id.container, targetFragment, addToBackStack, animate);
    }


    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    public void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

}

