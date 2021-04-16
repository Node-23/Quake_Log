package classes.controladores;

import java.io.IOException;
import java.util.ArrayList;

import classes.depedencias.Game;
import classes.depedencias.GameLineReference;

public class GameSetter {

    public static Game[] setGames() {
        int init = -1;
        ArrayList<GameLineReference> gameLineReferences = new ArrayList<GameLineReference>();

        try {
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

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
