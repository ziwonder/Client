package model;

import view.IObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class AObservable<T> {
    private List<IObserver<T>> observers;
    public AObservable() {
        observers = new ArrayList<>();
    }

    public void addObserver(IObserver<T> observer) {
        observers.add(observer);
    }

    public void notifyObservers(T arg) {
        observers.forEach( observer -> observer.update(this, arg));
    }
}
