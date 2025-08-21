package view;

import model.AObservable;

public class EndStateView implements IObserver<String> {

    @Override
    public void update(AObservable<String> o, String arg) {
            System.out.println("Game is over. " + arg);
    }
}
