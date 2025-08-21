package model;

import network.EPlayerState;

public class EndStateModel extends AObservable<String> {

    public void setEndState(EPlayerState playerState) {
        String endState;
        if (playerState == EPlayerState.Lost) {
            endState = "You lost 😣";
        }
        else {
            endState = "You won 🥳!!!";
        }

        super.notifyObservers(endState);
    }
}
