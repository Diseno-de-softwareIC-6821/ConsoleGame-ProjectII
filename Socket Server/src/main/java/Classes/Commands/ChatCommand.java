package Classes.Commands;

import Interfaces.iCommand;

public class ChatCommand implements iCommand {

    @Override
    public void execute(String[] args) {
        args[0] = "";
    }
}
