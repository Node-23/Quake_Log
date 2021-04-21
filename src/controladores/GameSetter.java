package controladores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import depedencias.DeathType;
import depedencias.Game;
import depedencias.GameLineReference;
import depedencias.Player;

public class GameSetter {
    public static ArrayList<Game> setGames(Scanner scanner) {
        int init = -1;
        ArrayList<GameLineReference> gameLineReferences = new ArrayList<GameLineReference>();
        ArrayList<Game> partidas = new ArrayList<Game>();
        String[] log = LogReader.reader(scanner);
        for (int i = 0; i < log.length; i++) {
            if (log[i].contains("InitGame:")) {
                init = i + 1; // Queremos a linha após o initgame, logo i+1
            }
            if (log[i].contains("------------------------------------------------------------") && init != -1) {
                gameLineReferences.add(new GameLineReference(init, i - 1)); // Queremos a linha antes do
                                                                            // shutdowngame logo, i-1
                init = -1;
            }
        }

        for (int i = 0; i < gameLineReferences.size(); i++) {
            partidas.add(criadorDePartidas(i + 1, gameLineReferences.get(i), log));
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
                int playerId = separarId(linhaDoLogSplitada);
                partida.setPlayers(new Player(playerId));
                continue;
            }

            if (log[i].contains("ClientUserinfoChanged:")) {
                String[] linhaDoLogSplitada = log[i].split(" ");
                int playerId = separarId(linhaDoLogSplitada);
                int index = pegarIndexPlayer(playerId, partida.getPlayers());
                String[] clientInfo = log[i].split("\\\\");
                String nick = clientInfo[1];
                partida.getPlayers().get(index).setNick(nick);
                continue;
            }

            if (log[i].contains("Kill:")) {
                computarKillsTotais(partida);

                String[] linhaDoLogSplitada = log[i].split(":");
                String[] linhaTipoDeMorte = linhaDoLogSplitada[3].split("by");
                String deathName = linhaTipoDeMorte[1];
                String[] killInfo = linhaDoLogSplitada[2].split(" ");// [1] = quem matou; [2] = quem morreu; [3] = como
                // morreu
                computarTipoDeMorte(Integer.parseInt(killInfo[3]), deathName, partida.getDeathTypes());
                int idMatador = Integer.parseInt(killInfo[1]);
                int idVitima = Integer.parseInt(killInfo[2]);
                computarKills(idMatador, idVitima, partida.getPlayers());
                continue;
            }

            if (log[i].contains("ClientDisconnect:")) {
                String[] linhaDoLogSplitada = log[i].split(" ");
                int playerId = separarId(linhaDoLogSplitada);
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

    private static void computarTipoDeMorte(int deathId, String deathname, ArrayList<DeathType> deathTypes) {
        for (int i = 0; i < deathTypes.size(); i++) {
            if (deathId == deathTypes.get(i).getId()) {
                deathTypes.get(i).setTotal(deathTypes.get(i).getTotal() + 1);
                return;
            }
        }
        DeathType death = new DeathType(deathId, deathname);
        deathTypes.add(death);
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

    public static String gerarRelatorio(ArrayList<Game> partidas) {
        String relatorio = "-RELATÓRIO GERAL-\n";
        for (int i = 0; i < partidas.size(); i++) {
            relatorio += gerarRelatorio(i + 1, partidas) + "\n";
        }
        return relatorio;
    }

    public static String gerarRelatorio(int gameId, ArrayList<Game> partidas) {
        String relatorio = "-RELATÓRIO PARTIDA " + gameId + "-\n" + "       Ranking:\n";
        Game partida = retornarPartidaPorId(gameId, partidas);
        Collections.sort(partida.getPlayers());
        for (int i = 0; i < partida.getPlayers().size(); i++) {
            relatorio += "  " + (i + 1) + "-" + partida.getPlayers().get(i).getNick() + ","
                    + partida.getPlayers().get(i).getKills() + " Kills" + "\n";
        }
        return relatorio;
    }

    private static Game retornarPartidaPorId(int gameId, ArrayList<Game> partidas) {
        for (Game game : partidas) {
            if (game.getId() == gameId) {
                return game;
            }
        }
        return null;
    }
}
