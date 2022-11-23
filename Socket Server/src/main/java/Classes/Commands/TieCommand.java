package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;

public class TieCommand extends Command {

    public TieCommand(Player player) throws Exception {
        super(player);
    }

    @Override
    public int execute(String[] args) {

        return -1;
    }
}

