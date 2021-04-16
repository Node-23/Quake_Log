package classes.depedencias;

public class Player {
    private int id;
    private String nick;
    private int kills = 0;

    public Player(int id) {
        this.id = id;
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

}
