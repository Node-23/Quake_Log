import java.util.ArrayList;
import java.util.Scanner;

import controladores.GameSetter;
import controladores.Writer;
import depedencias.Game;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Game> partidas = GameSetter.setGames(scanner);

        for (Game partida : partidas) {
            System.out.println(partida + "\n");
        }

        // TODO criar as exceptions de cada entrada 
        System.out.println("deseja imprimir o relatório geral?(y/n)");
        String resposta = scanner.nextLine();
        if (resposta.equals("y")) {
            Writer.criarRelatórioDePartida(GameSetter.gerarRelatorio(partidas), scanner,"relatorioGeral");
        }
        System.out.println("deseja imprimir o relatório de uma partida especifica?(y/n)");
        resposta = scanner.nextLine();
        if (resposta.equals("y")) {
            System.out.println("Qual id da partida que deseja imprimir?");
            int partidaId = Integer.parseInt(scanner.nextLine());
            Writer.criarRelatórioDePartida(GameSetter.gerarRelatorio(partidaId,partidas), scanner, ("relatorioPartida"+ partidaId));
        }
        scanner.close();
    }
}
