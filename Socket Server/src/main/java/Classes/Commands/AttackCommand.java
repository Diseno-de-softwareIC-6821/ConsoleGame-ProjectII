package Classes.Commands;

import Classes.Abstract.Command;
import Classes.GameObjects.GameCharacter;
import Classes.GameObjects.GameWeapon;
import Classes.ServerClasses.Player;
import Classes.ServerClasses.Server;
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

        if(attackedPlayer == null){
            try {
                server.notifyObserver(args[args.length-1], "Player " + args[2] + " doesn't exist");
            } catch (Exception e) {
                System.out.println("Attack server error");
            }
            return "0";
        }

        if(player.getCharacterByName(args[1]) == null){
            try {
                server.notifyObserver(args[args.length-1], "You don't have a character named " + args[0]);
            } catch (Exception e) {
                System.out.println("Attack server error");
            }
            return "0";
        }

        if(player.getCharacterByName(args[1]).getItemByName(args[2]) == null){
            try {
                server.notifyObserver(args[args.length-1], "You don't have a weapon named " + args[1]);
            } catch (Exception e) {
                System.out.println("Attack server error");
            }
            return "0";
        }


        HashMap<String, GameCharacter> attackedCharacters = attackedPlayer.getCharacters();
        GameWeapon usedWeapon = (GameWeapon) player.getCharacterByName(args[1]).getItemByName(args[2]);

        JSONObject damageLog = new JSONObject();

        for(GameCharacter character : attackedCharacters.values()){
            double damage = usedWeapon.getDamage(character.getType());

            int characterHealth = character.getHealth();
            int damageDone = (int)(characterHealth * damage);

            character.setHealth(characterHealth - damageDone);

            damageLog.put(character.getName(), damageDone);
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

