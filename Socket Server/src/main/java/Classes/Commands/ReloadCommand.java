package Classes.Commands;

import Classes.Abstract.Command;
import Classes.ServerClasses.Player;
import Interfaces.iCommand;

public class ReloadCommand extends Command {


    public ReloadCommand() throws Exception {
        super();
    }

    @Override
    public String execute(String[] args, Player player) {
        return "-1";
    }

}
