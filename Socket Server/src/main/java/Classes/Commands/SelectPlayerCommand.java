package Classes.Commands;

import Interfaces.iCommand;

public class SelectPlayerCommand implements iCommand {

    @Override
    public void execute(String[] args) {
        args[0] = "";
    }

}
