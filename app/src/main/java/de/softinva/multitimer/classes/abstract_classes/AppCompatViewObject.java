package de.softinva.multitimer.classes.abstract_classes;



import androidx.appcompat.app.AppCompatActivity;

public abstract class AppCompatViewObject<T> extends AppViewObject<T> {
    protected AppCompatActivity activity;

    public AppCompatViewObject(T obj, AppCompatActivity activity) {
        super(obj, activity);
        this.activity = activity;
    }

    @Override
    public AppCompatActivity getActivity() {
        return activity;
    }

}
