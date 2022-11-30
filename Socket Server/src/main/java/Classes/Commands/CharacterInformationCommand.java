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

        String notification = "info " + searchedCharacter.getJson();

        try {
            server.notifyObserver(args[args.length-1], notification);
        } catch (Exception e) {
            System.out.println("Player information server error");
            return "Player information couldn't be sent";
        }


        //<comando> <json de estadisticas>
        // info {"name":"elRisas", "texure":"path", "health":100, weapons:[{name:"pistola", damage:[[0.4,0.3,...],[0.4,0.3,...]]},...]}
        return notification;
    }

}
