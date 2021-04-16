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
            // TODO o usuário deve digitar o caminho do arquivo.
            String[] log = LogReader.reader("/home/matheus/Área de Trabalho/games.log");
            for (int i = 0; i < log.length; i++) {
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
                partidas.add(criadorDePartidas(i + 1, gameLineReferences.get(i), log));
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
            if (log[i].contains("ClientConnect:")) {
                String[] linhaDoLogSplitada = log[i].split(" ");
                int playerId = separarId(linhaDoLogSplitada); // TODO exception caso não ache o id do player
                if(!playerExiste(playerId, partida.getPlayers())){
                    partida.setPlayers(new Player(playerId));
                }
            }

            if (log[i].contains("ClientUserinfoChanged:")) {
                String[] linhaDoLogSplitada = log[i].split(" ");
                int playerId = separarId(linhaDoLogSplitada); // TODO exception caso não ache o id do player
                int index = pegarIndexPlayer(playerId, partida.getPlayers()); // TODO exception caso não ache o player
                String[] clientInfo = log[i].split("\\\\");
                String nick = clientInfo[1];
                partida.getPlayers().get(index).setNick(nick);
            }

            if (log[i].contains("Kill:")) {
                computarKillsTotais(partida);

                String[] linhaDoLogSplitada = log[i].split(":");
                String[] killInfo = linhaDoLogSplitada[2].split(" ");//[0] = quem matou; [1] = quem morreu; [2] = como morreu

                int idMatador = Integer.parseInt(killInfo[1]);
                int idVitima = Integer.parseInt(killInfo[2]);
                computarKills(idMatador, idVitima, partida.getPlayers());
            }
        }
        return partida;
    }

    private static int separarId(String[] linhaDoLogSplitada) {
        for (int i = 0; i < linhaDoLogSplitada.length; i++) {
            if (linhaDoLogSplitada[i].matches("[0-9]+")) {
                return Integer.parseInt(linhaDoLogSplitada[i]);
            }
        }
        return -1;
    }

    private static boolean playerExiste(int playerId, ArrayList<Player> players){
        for (int i = 0; i < players.size(); i++) {
            if(playerId == players.get(i).getId()){
                return true;
            }
        }
        return false;
    }

    private static int pegarIndexPlayer(int playerId, ArrayList<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getId() == playerId) {
                return i;
            }
        }
        return 0;
    }

    private static void incrementarPlayerKills(int playerIndex, ArrayList<Player> players) {
        int killsAtuais = players.get(playerIndex).getKills();
        players.get(playerIndex).setKills(killsAtuais + 1);
    }

    private static void decrementarPlayerKills(int playerIndex, ArrayList<Player> players) {
        int killsAtuais = players.get(playerIndex).getKills();
        if (killsAtuais == 0) {
            return;
        }
        players.get(playerIndex).setKills(killsAtuais - 1);
    }

    private static void computarKills(int idMatador, int idVitima, ArrayList<Player> players) {
        int index;
        if (idMatador == 1022) {
            index = pegarIndexPlayer(idVitima, players);
            decrementarPlayerKills(index, players);
        } else {
            index = pegarIndexPlayer(idMatador, players);
            incrementarPlayerKills(index, players);
        }
    }

    private static void computarKillsTotais(Game partida) {
        partida.setTotal_kills(partida.getTotal_kills() + 1);
    }
}
