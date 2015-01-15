package com.walmart.labs.pcs.normalize.utils;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by pzhong1 on 1/15/15.
 */
public class ObserverExample {
    public ObserverExample(){
        ObservableExample.getObservableExample().addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println("observer is called: " + arg);
            }
        });
    }
}
