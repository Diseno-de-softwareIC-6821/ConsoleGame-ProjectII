package Classes.Commands;

import Classes.Abstract.Command;
import Classes.GameObjects.GameCharacter;
import Classes.GameObjects.GameWeapon;
import Classes.ServerClasses.Player;
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

        args[0] = args[0].replace("_", " ");
        args[1] = args[1].replace("_", " ");
        args[2] = args[2].replace("_", " ");

        Player attackedPlayer = this.server.getPlayerByName(args[0]);

        //Player validation
        if(attackedPlayer == null){
            try {
                server.notifyObserver(args[args.length-1], "Player " + args[2] + " doesn't exist");
            } catch (Exception e) {
                System.out.println("Attack server error");
            }
            return "0";
        }

        //Character validations
        if(player.getCharacterByName(args[1]) == null){
            // character validation
            try {
                server.notifyObserver(args[args.length-1], "You don't have a character named " + args[0]);
            } catch (Exception e) {
                System.out.println("Attack server error");
            }
            return "attack " + args[1]+ " character doesn't exist for player " + args[args.length-1];
        }
        if(player.getCharacterByName(args[1]).getHealth() <= 0){
            // health validation

            try {
                server.notifyObserver(args[args.length-1], "Character " + args[1] + " is dead");
            } catch (Exception e) {
                System.out.println("Attack server error");
            }
            return "attack " + args[1]+ " character is dead for player " + args[args.length-1];
        }

        //Weapon validations
        GameWeapon usedWeapon = (GameWeapon) player.getCharacterByName(args[1]).getItemByName(args[2]);
        if(usedWeapon == null){
            try {
                server.notifyObserver(args[args.length-1], "You don't have a weapon named " + args[1]);
            } catch (Exception e) {
                System.out.println("Attack server error");
            }
            return "attack "+ args[2]+ " weapon doesn't exist";
        }
        if(!usedWeapon.isAvailable()){
            try {
                server.notifyObserver(args[args.length-1], "Weapon " + args[2] + " is not available "+ "for character " + args[1]);
            } catch (Exception e) {
                System.out.println("Attack server error");
            }
            return "attack "+args[args.length-1]+" "+args[1]+" "+args[2]+" weapon not available for player "+args[args.length-1];
        }


        //Attack loop
        HashMap<String, GameCharacter> attackedCharacters = attackedPlayer.getCharacters();

        JSONObject damageLog = new JSONObject();
        double totalDamage = 0;

        for(GameCharacter character : attackedCharacters.values()){
            double damage = usedWeapon.getDamage(character.getType());

            int characterHealth = character.getHealth();
            int damageDone = (int)(characterHealth * damage);

            if(characterHealth > 0){
                character.setHealth(characterHealth - damageDone);
                totalDamage += damageDone;
            }

            if (character.getHealth() <= 0) {
                player.getPlayerStats().addKilledEnemy();
            }

            damageLog.put(character.getName(), damageDone);

        }
        usedWeapon.setAvailable(false);



        //Attack stats update
        if (totalDamage >= 1) {
            player.getPlayerStats().addSuccessfulAttack();
        } else {
            player.getPlayerStats().addFailedAttack();
        }


        String notification = "attack "+ args[args.length-1]+" "+damageLog;

        try{
            System.out.println("Attack notification: " + notification);
            this.server.notifyObserver(args[0], notification);
        }
        catch (Exception e){
            System.out.println("Attack server error");
        }


        // envia al atacado
        // <commando ejecutado> <jugador que ataco> {"personaje": "daño recibido"}
        // attack daniel {"Michael Myers":79,"Chayanne":57,"Penny Wise":68,"Toledo":23}
        return notification;
    }
}

