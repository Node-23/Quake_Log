package classes.depedencias;

public class Player {
    private String nick;
    private int kills;

    public Player(String nick, int kills) {
        this.nick = nick;
        this.kills = kills;
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
