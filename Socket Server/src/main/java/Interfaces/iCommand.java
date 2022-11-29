package Interfaces;

import Classes.ServerClasses.Player;

public interface iCommand {
    public String execute(String[] args, Player player);
    // -1 = not implemented
    // 0 = error
    // 1 = success
}
