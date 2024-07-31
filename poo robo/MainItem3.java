import java.util.Random;
import java.util.Scanner;

public class MainItem3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Criação dos robôs
        Robo roboNormal = new Robo("vermelho - NORMAL", 1);
        RoboInteligente roboInteligente = new RoboInteligente("azul - INTELIGENTE", 2);

        // Configurações do tabuleiro
        System.out.println("Digite a posição do alimento (x y):");
        int alimentoX = scanner.nextInt();
        int alimentoY = scanner.nextInt();

        // Contadores de movimentos
        int movimentosRoboNormal = 0;
        int movimentosRoboInteligente = 0;

        boolean roboNormalEncontrou = false;
        boolean roboInteligenteEncontrou = false;

        Random random = new Random();

        while (!roboNormalEncontrou || !roboInteligenteEncontrou) {
            // Movimento do Robô Normal
            if (!roboNormalEncontrou) {
                int movimentoNormal = random.nextInt(4) + 1;
                try {
                    roboNormal.mover(movimentoNormal);
                    movimentosRoboNormal++;
                    System.out.println("O robo normal realizou o movimento na direção " + roboNormal.getUltimaDirecao());
                } catch (MovimentoInvalidoException e) {
                    System.out.println("Robo Normal: " + e.getMessage());
                }
                if (roboNormal.encontrouAlimento(alimentoX, alimentoY)) {
                    try {
                        roboNormal.setX(alimentoX);
                        roboNormal.setY(alimentoY);
                    } catch (MovimentoInvalidoException e) {
                        System.out.println("Erro ao atualizar a posição do Robô Normal: " + e.getMessage());
                    }
                    roboNormal.setExplodiu(true);
                    roboNormalEncontrou = true;
                }
            }

            // Movimento do Robô Inteligente
            if (!roboInteligenteEncontrou) {
                int movimentoInteligente = random.nextInt(4) + 1;
                try {
                    roboInteligente.mover(movimentoInteligente);
                    movimentosRoboInteligente++;
                } catch (MovimentoInvalidoException e) {
                    System.out.println("Robo Inteligente: " + e.getMessage());
                }
                if (roboInteligente.encontrouAlimento(alimentoX, alimentoY)) {
                    try {
                        roboInteligente.setX(alimentoX);
                        roboInteligente.setY(alimentoY);
                    } catch (MovimentoInvalidoException e) {
                        System.out.println("Erro ao atualizar a posição do Robô Inteligente: " + e.getMessage());
                    }
                    roboInteligente.setExplodiu(true);
                    roboInteligenteEncontrou = true;
                }
                System.out.println("O robo inteligente realizou o movimento na direção: " + roboInteligente.getUltimaDirecao());
            }

            // Impressão do estado atual
            roboNormal.mostrarPlanoComSobreposicao(alimentoX, alimentoY, roboInteligente);
            System.out.println("Movimento Robô Normal: (" + roboNormal.getX() + "," + roboNormal.getY() + ")");
            System.out.println("Movimento Robô Inteligente: (" + roboInteligente.getX() + "," + roboInteligente.getY() + ")");

            // Verifica se os robôs encontraram o alimento
            if (roboNormalEncontrou) {
                System.out.println("O Robô Normal encontrou o alimento!");
            }
            if (roboInteligenteEncontrou) {
                System.out.println("O Robô Inteligente encontrou o alimento!");
            }

            // Atraso entre os movimentos
            try {
                Thread.sleep(1000); // 1 segundo de pausa entre movimentos
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Resultado final
        System.out.println("Número total de movimentos do Robô Normal: " + movimentosRoboNormal);
        System.out.println("Número total de movimentos do Robô Inteligente: " + movimentosRoboInteligente);
    }
}
