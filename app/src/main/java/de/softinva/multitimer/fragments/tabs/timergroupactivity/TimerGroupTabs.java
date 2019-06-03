package de.softinva.multitimer.fragments.tabs.timergroupactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.SavedStateVMFactory;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import de.softinva.multitimer.R;
import de.softinva.multitimer.activities.main.timergroup.TimerGroupViewModel;
import de.softinva.multitimer.classes.abstract_classes.AppFragment;
import de.softinva.multitimer.model.TIMER_GROUP_ACTIVITY_TABS;

public class TimerGroupTabs extends AppFragment {

    private TimerGroupTabsViewModel mViewModel;
    private TimerGroupTabsAdapter appTabsAdapter;
    ViewPager viewPager;
    String[] tabTitel;

    public static TimerGroupTabs newInstance() {
        return new TimerGroupTabs();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.app_tabs, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, new SavedStateVMFactory(this)).get(TimerGroupTabsViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tabTitel = new String[3];
        tabTitel[0] = getString(R.string.tab_title_1);
        tabTitel[1] = getString(R.string.tab_title_3);
        appTabsAdapter = new TimerGroupTabsAdapter(getChildFragmentManager(), this.tabTitel);
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
            TimerGroupViewModel model = ViewModelProviders.of(getActivity()).get(TimerGroupViewModel.class);
            model.getActiveTab().setValue(TIMER_GROUP_ACTIVITY_TABS.values()[position]);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
