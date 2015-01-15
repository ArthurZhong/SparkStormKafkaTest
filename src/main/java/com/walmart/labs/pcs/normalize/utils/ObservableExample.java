package com.walmart.labs.pcs.normalize.utils;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by pzhong1 on 1/15/15.
 */
public class ObservableExample implements Observer {

    //it has one atomatically watched string
    static final SynchronizedObservable observable = new SynchronizedObservable();
    //it is a manually watched string
    static final AtomicReference<String> stringRef = new AtomicReference<String>();

    public static void doSomething(){
        String oldString = "old";
        stringRef.set(oldString);
        System.out.println("successefuly set old string");

        String newString = "new";
        boolean status = stringRef.compareAndSet(oldString, newString);
        System.out.println("update status: " + status);
        if(status){
            notifyObservers("manually notify");
        }

        if(observable.hasChanged()){
            notifyObservers(stringRef.get());
        }
    }

    public static String getAtomRefString(){
        return stringRef.get();
    }
    public static Observable getObservableExample() {
        return observable;
    }

    public static void notifyObservers(String message) {
        observable.setChanged();
        try {
            observable.notifyObservers(message);
        } finally {
            observable.clearChanged();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Update called: " + arg);
    }

    static class SynchronizedObservable extends Observable {
        private String watchedValue = "test";
        public void setValue(String value) {
            // if value has changed notify observers
            if(!watchedValue.equals(value)) {
                watchedValue = value;
                // mark as value changed
                setChanged();
                notifyObservers(watchedValue);
            }

        }
        @Override
        public synchronized void setChanged() {
            super.setChanged();
        }

        @Override
        public synchronized void clearChanged() {
            super.clearChanged();
        }
    }

    public static void main(String[] args) {
        ObservableExample example = new ObservableExample();
        observable.addObserver(example);
        observable.setValue("test123");
        ObservableExample.doSomething();
    }
}
