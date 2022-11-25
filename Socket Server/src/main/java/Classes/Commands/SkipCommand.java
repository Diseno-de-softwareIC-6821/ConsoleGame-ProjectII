package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;
import Interfaces.iCommand;

public class SkipCommand extends Command {

    public SkipCommand() throws Exception {
        super();
    }

    @Override
    public int execute(String[] args, Player player) {
        return -1;
    }

}
