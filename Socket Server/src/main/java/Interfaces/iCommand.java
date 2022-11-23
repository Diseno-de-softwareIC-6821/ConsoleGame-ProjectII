package Interfaces;

public interface iCommand {
    public int execute(String[] args);
    // -1 = not implemented
    // 0 = error
    // 1 = success
}
