package Classes.Abstract;

import Classes.ServerClasses.Player;
import Classes.ServerClasses.Server;
import Interfaces.iCommand;

public abstract class Command implements iCommand {

    protected Server server;
    protected Player player;

    public Command(Player player) throws Exception {
        this.server = Server.getInstance();
        this.player = player;
    }

    @Override
    public abstract int execute(String[] args, Player player);


}
