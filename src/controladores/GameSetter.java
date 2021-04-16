package controladores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import depedencias.Game;
import depedencias.GameLineReference;
import depedencias.Player;

public class GameSetter {
    public static ArrayList<Game> setGames(Scanner scanner) {
        int init = -1;
        ArrayList<GameLineReference> gameLineReferences = new ArrayList<GameLineReference>();
        ArrayList<Game> partidas = new ArrayList<Game>();
        try {
            String[] log = LogReader.reader(scanner);
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
                partida.setPlayers(new Player(playerId));
                continue;
            }

            if (log[i].contains("ClientUserinfoChanged:")) {
                String[] linhaDoLogSplitada = log[i].split(" ");
                int playerId = separarId(linhaDoLogSplitada); // TODO exception caso não ache o id do player
                int index = pegarIndexPlayer(playerId, partida.getPlayers()); // TODO exception caso não ache o player
                String[] clientInfo = log[i].split("\\\\");
                String nick = clientInfo[1];
                partida.getPlayers().get(index).setNick(nick);
                continue;
            }

            if (log[i].contains("Kill:")) {
                computarKillsTotais(partida);

                String[] linhaDoLogSplitada = log[i].split(":");
                String[] killInfo = linhaDoLogSplitada[2].split(" ");// [0] = quem matou; [1] = quem morreu; [2] = como
                                                                     // morreu

                int idMatador = Integer.parseInt(killInfo[1]);
                int idVitima = Integer.parseInt(killInfo[2]);
                computarKills(idMatador, idVitima, partida.getPlayers());
                continue;
            }

            if (log[i].contains("ClientDisconnect:")) {
                String[] linhaDoLogSplitada = log[i].split(" ");
                int playerId = separarId(linhaDoLogSplitada); // TODO exception caso não ache o id do player
                playerDesconectado(playerId, partida.getPlayers());
                continue;
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

    // TODO mudar esse método para ao receber o id devolver um player(Deixaria o
    // código mais limpo)
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

    private static void playerDesconectado(int playerId, ArrayList<Player> players) {
        int index = pegarIndexPlayer(playerId, players);
        players.remove(index);
    }

    public static String gerarRelatorio(ArrayList<Game> partidas){
        String relatorio= "-RELATÓRIO GERAL-\n";
        for (int i = 0; i < partidas.size(); i++) {
            relatorio += gerarRelatorio(i+1, partidas)+"\n";
        }
        return relatorio;
    }

    public static String gerarRelatorio(int gameId, ArrayList<Game> partidas){
        //TODO exception caso não ache a partida
        String relatorio ="-RELATÓRIO PARTIDA " + gameId+"-\n"+
        "       Ranking:\n";
        Game partida = retornarPartidaPorId(gameId, partidas);
        Collections.sort(partida.getPlayers());
        for (int i = 0; i < partida.getPlayers().size(); i++) {
            relatorio += "  " + (i+1) + "-" +partida.getPlayers().get(i).getNick()+ "," + partida.getPlayers().get(i).getKills()+ " Kills"+
            "\n";
        }
        return relatorio;
    }

    private static Game retornarPartidaPorId(int gameId, ArrayList<Game> partidas){
        for (Game game : partidas) {
           if(game.getId() == gameId){
               return game;
           }
       }
       return null;
    }
}
