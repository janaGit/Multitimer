package de.softinva.multitimer.fragments.tabs.mainactivity;

import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import de.softinva.multitimer.activities.MainActivityViewModel;
import de.softinva.multitimer.R;
import de.softinva.multitimer.classes.AppFragment;
import de.softinva.multitimer.model.MAIN_ACTIVITY_TABS;

public class MainActivityTabs extends AppFragment {

    private MainActivityTabsViewModel mViewModel;
    private MainActivityTabsAdapter appTabsAdapter;
    ViewPager viewPager;
    String[] tabTitel;

    public static MainActivityTabs newInstance() {
        return new MainActivityTabs();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.app_tabs, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, new SavedStateVMFactory(this)).get(MainActivityTabsViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tabTitel = new String[3];
        tabTitel[0] = getString(R.string.tab_title_1);
        tabTitel[1] = getString(R.string.tab_title_2);
        tabTitel[2] = getString(R.string.tab_title_3);
        appTabsAdapter = new MainActivityTabsAdapter(getChildFragmentManager(), this.tabTitel);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(appTabsAdapter);
        viewPager.addOnPageChangeListener(this.onPageChangeListener);
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            MainActivityViewModel model = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);
            model.getActiveTab().setValue(MAIN_ACTIVITY_TABS.values()[position]);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
