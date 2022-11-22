package Classes.Commands;

import Interfaces.iCommand;

public class SkipCommand implements iCommand {

    @Override
    public void execute(String[] args) {
        args[0] = "";
    }

}
