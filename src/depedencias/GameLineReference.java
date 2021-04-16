package depedencias;

public class GameLineReference {
    private int init;
    private int shutdown;

    public GameLineReference(int init, int shutdown) {
        this.init = init;
        this.shutdown = shutdown;
    }

    public int getInit() {
        return this.init;
    }

    public int getShutdown() {
        return this.shutdown;
    }

}
