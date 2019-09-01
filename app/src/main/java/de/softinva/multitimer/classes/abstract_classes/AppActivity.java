package de.softinva.multitimer.classes.abstract_classes;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import de.softinva.multitimer.BR;
import de.softinva.multitimer.R;
import de.softinva.multitimer.activities.importtimergroups.ImportTimerGroupActivity;
import de.softinva.multitimer.classes.interfaces.IAppModelBinding;


public abstract class AppActivity<T extends ViewModel> extends AppCompatActivity implements IAppModelBinding<T> {
    protected T model;
    protected AppViewObject viewObject;
    protected ViewDataBinding binding;

    private static final int MENU_GROUP_MAIN = 1;
    private static final int MENU_ITEM_MAIN_MENU = 2;
    private static final int MENU_ITEM_IMPORT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this, new SavedStateViewModelFactory(this))
                .get(returnModelClass());
        setClassSpecificObjects();
        viewObject = returnViewObject();
        binding = returnBinding();
        bindModel();

        setActionBar();
        setHomeUpButton();
        setTitle();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public T getModel() {
        return model;
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public ViewDataBinding getBinding() {
        return binding;
    }

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return this;
    }

    protected abstract AppViewObject returnViewObject();


    protected void setHomeUpButton() {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void bindModel() {
        if (viewObject != null) {
            binding.setVariable(BR.viewObject, viewObject);
            binding.setLifecycleOwner(this);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        SubMenu subMenu = menu.addSubMenu(MENU_GROUP_MAIN, MENU_ITEM_MAIN_MENU, Menu.NONE, getResources().getString(R.string.menu_item_app_menu));
        subMenu.add(Menu.NONE, MENU_ITEM_IMPORT, Menu.NONE, getResources().getString(R.string.menu_item_import));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            menu.setGroupDividerEnabled(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ITEM_IMPORT:
                ImportTimerGroupActivity.startNewActivity(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    protected abstract Class<T> returnModelClass();

    protected abstract void setActionBar();

    protected abstract ViewDataBinding returnBinding();

    protected abstract void setTitle();

    protected abstract void setClassSpecificObjects();
}
