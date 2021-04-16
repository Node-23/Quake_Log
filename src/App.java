import java.util.ArrayList;

import classes.controladores.GameSetter;
import classes.depedencias.Game;

public class App {
    public static void main(String[] args) throws Exception {
        ArrayList<Game> partidas = GameSetter.setGames();
        
        for (Game partida : partidas) {
            System.out.println(partida);
        }
    }
}
