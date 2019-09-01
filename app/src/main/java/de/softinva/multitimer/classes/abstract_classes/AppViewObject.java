package de.softinva.multitimer.classes.abstract_classes;



import android.app.Activity;



public abstract class AppViewObject<T> {
    protected T obj;
    protected Activity activity;

    public AppViewObject(T obj, Activity activity) {
        this.obj = obj;
        this.activity = activity;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public Activity getActivity() {
        return activity;
    }

}
