package classes.depedencias;

public class Player implements Comparable<Player>{
    private int id;
    private String nick;
    private int kills;

    public Player(int id) {
        this.id = id;
        this.kills =0;
    }

    public int getId() {
        return this.id;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getKills() {
        return this.kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    @Override
    public int compareTo(Player pPlayer) {
        return (pPlayer.getKills() - this.kills);
    }

}
