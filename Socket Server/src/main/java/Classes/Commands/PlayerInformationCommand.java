package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;
import Interfaces.iCommand;

public class PlayerInformationCommand extends Command {

    public PlayerInformationCommand(Player player) throws Exception {
        super(player);
    }

    @Override
    public int execute(String[] args) {
        return -1;
    }

}
