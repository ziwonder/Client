package view;

import model.AObservable;

public interface IObserver<T> {
    void update(AObservable<T> o, T arg);
}
