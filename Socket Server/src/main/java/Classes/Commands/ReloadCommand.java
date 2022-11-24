package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;
import Interfaces.iCommand;

public class ReloadCommand extends Command {


    public ReloadCommand(Player player) throws Exception {
        super(player);
    }

    @Override
    public int execute(String[] args, Player player) {
        return -1;
    }

}
