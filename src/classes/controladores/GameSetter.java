package classes.controladores;

import java.io.IOException;
import java.util.ArrayList;

import classes.depedencias.Game;
import classes.depedencias.GameLineReference;
import classes.depedencias.Player;

public class GameSetter {

    public static ArrayList<Game> setGames() {
        int init = -1;
        ArrayList<GameLineReference> gameLineReferences = new ArrayList<GameLineReference>();
        ArrayList<Game> partidas = new ArrayList<Game>();
        try {
            String[] log = LogReader.reader("/home/matheus/Área de Trabalho/games.log");
            for (int i = 0; i < log.length ; i++) { 
                if (log[i].contains("InitGame:")) {
                    init = i + 1; // Queremos a linha após o initgame, logo i+1
                }
                if (log[i].contains("ShutdownGame:") && init != -1) {
                    gameLineReferences.add(new GameLineReference(init, i - 1)); // Queremos a linha antes do
                                                                                // shutdowngame logo, i-1
                    init = -1;
                }
            }

            for (int i = 0; i < gameLineReferences.size(); i++) {
               partidas.add(criadorDePartidas(i+1,gameLineReferences.get(i),log));
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return partidas;
    }

    private static Game criadorDePartidas(int gameId, GameLineReference gameLineReference, String[] log) {
        Game partida = new Game(gameId);
        int inicio = gameLineReference.getInit();
        int fim = gameLineReference.getShutdown();

        for (int i = inicio; i <= fim; i++) {
            if(log[i].contains("ClientConnect:")){
                String[] linhaDoLogSplitada = log[i].split(" ");
                int playerId = pegarId(linhaDoLogSplitada); //TODO exception caso não ache o id do player
                partida.setPlayers(new Player(playerId));
            }

            if(log[i].contains("ClientUserinfoChanged:")){
                String[] linhaDoLogSplitada= log[i].split(" ");
                int playerId = pegarId(linhaDoLogSplitada); //TODO exception caso não ache o id do player
                int index = pegarIndexPlayer(playerId,partida.getPlayers()); //TODO exception caso não ache o player
                String[] clientInfo = log[i].split("\\\\");
                String nick = clientInfo[1];
                partida.getPlayers().get(index).setNick(nick);
            }
        }
        return partida;
    }

    private static int pegarId(String[] linhaDoLogSplitada){
        for (int i = 0; i < linhaDoLogSplitada.length; i++) {
            if (linhaDoLogSplitada[i].matches("[0-9]+")){
                return Integer.parseInt(linhaDoLogSplitada[i]);
            }
        }
        return -1;
    }

    private static int pegarIndexPlayer(int playerId,ArrayList<Player> players){
        
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i) .getId() == playerId) {
                return i;
            }
        }
        return 0;
    }
}
