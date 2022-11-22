package Classes.Commands;

import Interfaces.iCommand;

public class ReloadCommand implements iCommand {

    @Override
    public void execute(String[] args) {
        args[0] = "";
    }

}
