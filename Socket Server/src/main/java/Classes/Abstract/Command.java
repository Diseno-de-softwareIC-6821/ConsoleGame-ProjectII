package Classes.Abstract;

import Classes.ServerClasses.Player;
import Classes.ServerClasses.Server;
import Interfaces.iCommand;

public abstract class Command implements iCommand {

    protected Server server;
    protected Player player;

    public Command() throws Exception {
        this.server = Server.getInstance();
    }

    @Override
    public abstract String execute(String[] args, Player player);


}
