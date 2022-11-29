package Data;

public class Stadistics {
    private String player;
    private int wins;
    private int loses;
    private int atacks;
    private int succesfulAtacks;
    private int failedAtacks;
    private int giveUps;

    public Stadistics(String player){
        this.player = player;
        this.wins = 0;
        this.loses = 0;
        this.atacks = 0;
        this.succesfulAtacks = 0;
        this.failedAtacks = 0;
        this.giveUps = 0;
    }

    public String getPlayer() {
        return player;
    }

    public int getLoses() {
        return loses;
    }

    public int getAtacks() {
        return atacks;
    }

    public int getSuccesfulAtacks() {
        return succesfulAtacks;
    }

    public int getFailedAtacks() {
        return failedAtacks;
    }

    public int getGiveUps() {
        return giveUps;
    }

    public int getWins() {
        return wins;
    }
}
