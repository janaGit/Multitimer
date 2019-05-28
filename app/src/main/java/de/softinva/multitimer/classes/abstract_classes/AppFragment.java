package de.softinva.multitimer.classes.abstract_classes;

import androidx.fragment.app.Fragment;
import de.softinva.multitimer.utility.AppLogger;

public abstract class AppFragment extends Fragment {
    protected AppLogger logger = new AppLogger(this);
    public AppFragment(){
        super();
    }
}
