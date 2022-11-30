package Classes.Commands;

import Classes.Abstract.Command;
import Classes.GameObjects.GameCharacter;
import Classes.GameObjects.GameWeapon;
import Classes.ServerClasses.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;


public class AttackCommand extends Command {

    public AttackCommand() throws Exception {
        super();
    }

    @Override
    public String execute(String[] args, Player player) {
        //args[0] = nombre del jugador atacado;
        //args[1] = personaje que ataca;
        //args[2] = arma que usa para atacar;

        //args[length-1] = nombre del jugador que ataca;

        if(!server.getCurrentTurn().equals(args[args.length-1])){
            try {
                this.server.notifyObserver(args[args.length-1], "It's not your turn");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            return "attack " + args[args.length-1] + " not its turn";
        }

        args[0] = args[0].replace("_", " ");
        args[1] = args[1].replace("_", " ");
        args[2] = args[2].replace("_", " ");



        //Player validation
        Player attackedPlayer = this.server.getPlayerByName(args[0]);
        if(args[0].equals(args[args.length - 1])){
            try {
                this.server.notifyObserver(args[args.length-1], "You can't attack yourself");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println("You can't attack yourself");
            return "attack " + args[args.length-1] + " can't attack yourself";
        }
        if(attackedPlayer == null){
            try {
                server.notifyObserver(args[args.length-1], "Player " + args[0] + " doesn't exist");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println("Player " + args[0] + " doesn't exist");
            return "attack " + args[0] + " player doesn't exist";
        }

        //Character validations
        GameCharacter attackingCharacter = player.getCharacterByName(args[1]);

        for(GameCharacter character: player.getCharacters().values()){
            System.out.println(character.getName());
        }

        if(attackingCharacter == null){
            // character validation
            try {
                server.notifyObserver(args[args.length-1], "You don't have a character named " + args[1]);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println("You don't have a character named " + args[1]);
            return "attack " + args[1]+ " character doesn't exist for player " + args[args.length-1];
        }
        if(attackingCharacter.getHealth() <= 0){
            // health validation

            try {
                server.notifyObserver(args[args.length-1], "Character " + args[1] + " is dead");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println("Character " + args[1] + " is dead");
            return "attack " + args[1]+ " character is dead for player " + args[args.length-1];
        }

        //Weapon validations
        GameWeapon attackingWeapon = (GameWeapon) player.getCharacterByName(args[1]).getItemByName(args[2]);
        if(attackingWeapon == null){
            try {
                server.notifyObserver(args[args.length-1], "You don't have a weapon named " + args[2]);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println("Error: " + args[2] + " doesn't exist in any character for player " + args[args.length-1]);
            return "attack "+ args[2]+ " weapon doesn't exist";
        }
        if(!attackingWeapon.isAvailable()){
            try {
                server.notifyObserver(args[args.length-1], "Weapon " + args[2] + " is not available "+ "for character " + args[1]);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println("Weapon " + args[2] + " is not available "+ "for character " + args[1]);
            return "attack "+args[args.length-1]+" "+args[1]+" "+args[2]+" weapon not available for player "+args[args.length-1];
        }


        //Attack loop

        JSONObject attackSummary = new JSONObject();
        JSONArray damageLog = new JSONArray();

        HashMap<String, GameCharacter> attackedCharacters = attackedPlayer.getCharacters();

        double totalDamage = 0;

        for(GameCharacter character : attackedCharacters.values()){
            JSONObject characterDamageDone = new JSONObject();

            

            double damage = attackingWeapon.getDamage(character.getType());

            int characterHealth = character.getHealth();
            int damageDone = (int)(characterHealth * damage);

            if(characterHealth > 0){
                character.setHealth(characterHealth - damageDone);
                totalDamage += damageDone;
            }

            if (character.getHealth() <= 0) {
                player.getPlayerStats().addKilledEnemy();
            }
            characterDamageDone.put("name", character.getName());
            characterDamageDone.put("damage", Integer.toString(damageDone));
            
            damageLog.put(characterDamageDone);

        }

        attackSummary.put("attacked", args[0]);
        attackSummary.put("warrior", args[1]);
        attackSummary.put("element", attackingCharacter.getType());
        attackSummary.put("weapon", args[2]);
        attackSummary.put("damageTotal", Double.toString(totalDamage));
        attackSummary.put("warriorImg", attackingCharacter.getCurrentTexture());
        attackSummary.put("damageDone", damageLog);

        attackingWeapon.setAvailable(false);


        //Attack stats update
        if (totalDamage >= 1) {
            player.getPlayerStats().addSuccessfulAttack();
        } else {
            player.getPlayerStats().addFailedAttack();
        }

        String notification = "attack "+ attackSummary;

        try{
            this.server.nextTurn();

            //notify attacking player
            String attackingPlayerNotification = "attacking " + attackSummary.toString().replace(' ', '_');;
            this.server.notifyObserver(args[args.length-1], attackingPlayerNotification);

            //notify attacking player
            String attackedPlayerNotification = "attackedBy " + attackSummary.toString().replace(' ', '_');;;
            this.server.notifyObserver(args[0], attackedPlayerNotification);


        }
        catch (Exception e){
            System.out.println("Attack server error");
        }


        // envia al atacado
        // <commando ejecutado> <jugador que ataco> {"personaje": "daño recibido"}
        // attack daniel {"Michael Myers":79,"Chayanne":57,"Penny Wise":68,"Toledo":23}
        System.out.println(notification);
        return notification;
    }
}

