package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;

public class SurrenderCommand extends Command {

    public SurrenderCommand(Player player) throws Exception {
        super(player);
    }

    @Override
    public int execute(String[] args) {

        return -1;
    }
}

