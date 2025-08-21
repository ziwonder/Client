package client.main;

import messagesbase.messagesfromclient.PlayerRegistration;
import network.ClientNetwork;

public class RegistrationPhase {
    public void registerPlayer(ClientNetwork network){
        PlayerRegistration playerReg = new PlayerRegistration(
                "Anastasiia",
                "Zimanova",
                "zimanovaa04");
        network.registerPlayer(playerReg);
    }
}
