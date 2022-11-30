package Classes.ServerClasses;

import org.json.JSONObject;

public class PlayerStatistics {
    private int wins;
    private int losses;
    private int totalAttacks;
    private int successfulAttacks;
    private int failedAttacks;
    private int totalKilledEnemies;
    private int surrenderedGames;

    public PlayerStatistics() {
        this.wins = 0;
        this.losses = 0;
        this.totalAttacks = 0;
        this.successfulAttacks = 0;
        this.failedAttacks = 0;
        this.totalKilledEnemies = 0;
        this.surrenderedGames = 0;
    }

    public void addWin() {
        this.wins++;
    }

    public void addLoss() {
        this.losses++;
    }

    public void addSuccessfulAttack() {
        this.totalAttacks++;
        this.successfulAttacks++;
    }

    public void addFailedAttack() {
        this.totalAttacks++;
        this.failedAttacks++;
    }

    public void addKilledEnemy() {
        this.totalKilledEnemies++;
    }

    public void addSurrenderedGame() {
        this.surrenderedGames++;
    }

    public String getJsonStats(){
        JSONObject jsonStats = new JSONObject();
        jsonStats.put("wins", Integer.toString(this.wins));
        jsonStats.put("losses", Integer.toString(this.losses));
        jsonStats.put("totalAttacks", Integer.toString(this.totalAttacks));
        jsonStats.put("successfulAttacks", Integer.toString(this.successfulAttacks));
        jsonStats.put("failedAttacks", Integer.toString(this.failedAttacks));
        jsonStats.put("totalKilledEnemies", Integer.toString(this.totalKilledEnemies));
        jsonStats.put("surrenderedGames", Integer.toString(this.surrenderedGames));

        return jsonStats.toString();
    }

}
