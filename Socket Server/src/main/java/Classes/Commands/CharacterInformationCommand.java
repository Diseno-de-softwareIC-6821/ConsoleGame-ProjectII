package Classes.Commands;

import Classes.Abstract.Command;
import Classes.GameObjects.GameCharacter;
import Classes.ServerClasses.Player;

public class CharacterInformationCommand extends Command {

    public CharacterInformationCommand() throws Exception {
        super();
    }

    @Override
    public String execute(String[] args, Player player) {
        // args[0] = nombre del personaje buscado
        // args[length-1] = nombre del jugador que envio el mensaje

        GameCharacter searchedCharacter = player.getCharacterByName(args[0]);

        if (searchedCharacter == null) {
            try {
                server.notifyObserver(args[args.length-1], "Character not found");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println("Character not found");
            return args[args.length-1]+": "+ "info player not found";
        }

        String notification = "info " + args[0] + " " + searchedCharacter.getJson();

        try {
            server.notifyObserver(args[args.length-1], notification);
        } catch (Exception e) {
            System.out.println("Player information server error");
            return "Player information couldn't be sent";
        }


        //<comando> <nombre personaje buscado> <json de estadisticas>
        // info elRisas {"name":"elRisas", "texure":"path", "health":100, [[0.2,0.3,0.7],[0.2,0.3,0.7],[0.2,0.3,0.7],...[0.2,0.3,0.7]]}
        return notification;
    }

}
