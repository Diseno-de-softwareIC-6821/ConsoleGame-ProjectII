package Interfaces;

import Classes.ServerClasses.Player;

public interface iCommand {
    public int execute(String[] args, Player player);
    // -1 = not implemented
    // 0 = error
    // 1 = success
}
