package Classes.Abstract;

import Classes.ServerClasses.Server;
import Interfaces.iCommand;

public abstract class Command implements iCommand {

    protected Server server;

    public Command() throws Exception {
        this.server = Server.getInstance();
    }

    @Override
    public abstract void execute(String[] args);


}
