package de.softinva.multitimer.classes;



public abstract class AppViewObject<T> {
    protected T obj;
    protected Object context;

    public AppViewObject(T obj) {
        this.obj = obj;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public Object getContext() {
        return context;
    }

    public void setContext(Object context) {
        this.context = context;
    }
}
