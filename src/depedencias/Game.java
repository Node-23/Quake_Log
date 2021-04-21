package depedencias;

import java.util.ArrayList;

public class Game {
    private int id;
    private int total_kills = 0;
    ArrayList<Player> players;
    ArrayList<DeathType> deathTypes;

    public Game(int id) {
        this.id = id;
        this.total_kills = 0;
        this.players = new ArrayList<Player>();
        this.deathTypes = new ArrayList<DeathType>();
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


    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(Player player) {
        this.players.add(player);
    }

    public ArrayList<DeathType> getDeathTypes() {
        return this.deathTypes;
    }

    public void setDeathTypes(DeathType deathType) {
        this.deathTypes.add(deathType);
    }

    @Override
    public String toString() {
        return "game_" + getId() + ": { \n" +
        "   total_kills: " + getTotal_kills() + "; \n" +
        "   players: ["+listaDePlayers()+"\n"+
        "   kills:{ \n"+ listaDeKills() + "   }"+
        "kills_by_means:{\n" + listaDeTiposDeMorte() + "\n}";
    }

    private String listaDePlayers() {
        String playersList = "";
        for (int i = 0; i < players.size(); i++) {
            if (i == players.size() - 1) {
                playersList += "'" + players.get(i).getNick() + "']";
            } else {
                playersList += "'" + players.get(i).getNick() + "', ";
            }
        }
        return playersList;
    }

    private String listaDeKills() {
        String killList = "";
        for (int i = 0; i < players.size(); i++) {
            if (i == players.size() - 1) {
                killList += "       '" + players.get(i).getNick() + "': " + players.get(i).getKills() + "\n";
            } else {
                killList += "       '" + players.get(i).getNick() + "': " + players.get(i).getKills() + ",\n";
            }
        }
        return killList;
    }

    private String listaDeTiposDeMorte(){
        String tipoDeMorteList ="";
        if(deathTypes.isEmpty() == true){
            return "";
        }
        
        for (int i = 0; i < deathTypes.size(); i++) {
            if (i == deathTypes.size()-1) {
                tipoDeMorteList += "        '" + deathTypes.get(i).getName() + "' : "+ deathTypes.get(i).getTotal() + "\n    }";
            } else {
                tipoDeMorteList += "        '" + deathTypes.get(i).getName() + "' : "+ deathTypes.get(i).getTotal() + "',\n";
            }
        }
        return tipoDeMorteList;
    }

}
