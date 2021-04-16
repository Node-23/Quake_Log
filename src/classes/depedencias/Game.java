package classes.depedencias;

public class Game {
    private int id;
    private int total_kills;
    private Player[] players;

    public Game(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public int getTotal_kills() {
        return this.total_kills;
    }

    public void setTotal_kills(int total_kills) {
        this.total_kills = total_kills;
    }

    public Player[] getPlayers() {
        return this.players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "game_" + getId() + ": { \n" +
        "total_kills: " + getTotal_kills() + "; \n" +
        "players: ["+listaDePlayers()+"\n"+
        "kills: { \n"+ listaDeKills() + "\n}"
        ;
    }

    private String listaDePlayers() {
        String playersList = "";
        for (int i = 0; i < players.length; i++) {
            if (i == players.length - 1) {
                playersList += "'" + players[i].getNick() + "']";
            } else {
                playersList += "'" + players[i].getNick() + "', ";
            }
        }
        return playersList;
    }

    private String listaDeKills() {
        String killList = "";
        for (int i = 0; i < players.length; i++) {
            if (i == players.length - 1) {
                killList += "'" + players[i].getNick() + "': " + players[i].getKills() + "\n}";
            } else {
                killList += "'" + players[i].getNick() + "': " + players[i].getKills() + ",\n";
            }
        }
        return killList;
    }

}
