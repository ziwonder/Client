package controller;

import model.EndStateModel;
import network.EPlayerState;
import view.EndStateView;

public class EndStateController {
    private final EndStateModel model;
    private EndStateView view;

    public EndStateController() {
        this.model = new EndStateModel();
        this.view = new EndStateView();
        model.addObserver(view);
    }

    public void endGame(EPlayerState playerState) {
        this.model.setEndState(playerState);
    }
}
