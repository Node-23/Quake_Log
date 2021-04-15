import model.LogReader;

public class App {
    public static void main(String[] args) throws Exception {
        String[] log = LogReader.reader("/home/matheus/Área de Trabalho/games.log");
        for (int i = 0; i < log.length; i++) {
            System.out.println(log[i]);
        }
    }

}

// /home/matheus/Área de Trabalho/games.log
