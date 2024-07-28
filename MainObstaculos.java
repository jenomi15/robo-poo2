import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainObstaculos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        Robo roboNormal = new Robo("azul", 1);
        RoboInteligente roboInteligente = new RoboInteligente("verde", 2);

        System.out.println("Digite a posição do alimento (x y): ");
        int alimentoX = scanner.nextInt();
        int alimentoY = scanner.nextInt();
        
        // Definindo obstáculos
        List<Obstaculo> obstaculos = new ArrayList<>();
        
        // Pedir ao usuário a quantidade de bombas ou rochas
        System.out.println("Digite a quantidade de bombas: ");
        int qtdBombas = scanner.nextInt();
        System.out.println("Digite a quantidade de rochas: ");
        int qtdRochas = scanner.nextInt();
        
        // Adicionar obstáculos conforme a quantidade digitada pelo usuário
        for (int i = 0; i < qtdBombas; i++) {
            System.out.println("Digite a posição da bomba " + (i + 1) + " (x y): ");
            int bombaX = scanner.nextInt();
            int bombaY = scanner.nextInt();
            obstaculos.add(new Bomba(i + 1, bombaX, bombaY)); // IDs das bombas começam de 1
        }
        for (int i = 0; i < qtdRochas; i++) {
            System.out.println("Digite a posição da rocha " + (i + 1) + " (x y): ");
            int rochaX = scanner.nextInt();
            int rochaY = scanner.nextInt();
            obstaculos.add(new Rocha(i + 1 + qtdBombas, rochaX, rochaY)); // IDs das rochas começam após os IDs das bombas
        }

        boolean roboNormalExplodiu = false;
        boolean roboInteligenteExplodiu = false;
        
        int movimentosRobNormal = 0;
        int movimentosRobInteligente = 0;

        while (!roboNormal.encontrouAlimento(alimentoX, alimentoY) &&
               !roboInteligente.encontrouAlimento(alimentoX, alimentoY) &&
               !roboNormalExplodiu && !roboInteligenteExplodiu) {

            // Movimentação do robo normal
            int movimentoNormal = random.nextInt(4) + 1;
            if (!roboNormalExplodiu) {
                try {
                    roboNormal.mover(movimentoNormal);
                    movimentosRobNormal++;
                    for (Obstaculo obstaculo : obstaculos) {
                        if (roboNormal.getX() == obstaculo.getX() && roboNormal.getY() == obstaculo.getY()) {
                            obstaculo.bater(roboNormal);
                            if (roboNormal.isExplodiu()) {
                                roboNormalExplodiu = true;
                                break;
                            }
                        }
                    }
                } catch (MovimentoInvalidoException e) {
                    System.out.println("Robo Normal: " + e.getMessage());
                    // Não deve explodir, apenas continua o loop
                }
            }

            // Movimentação do robo inteligente
            int movimentoInteligente = random.nextInt(4) + 1;
            if (!roboInteligenteExplodiu) {
                try {
                    roboInteligente.mover(movimentoInteligente);
                    movimentosRobInteligente++;
                    for (Obstaculo obstaculo : obstaculos) {
                        if (roboInteligente.getX() == obstaculo.getX() && roboInteligente.getY() == obstaculo.getY()) {
                            obstaculo.bater(roboInteligente);
                            if (roboInteligente.isExplodiu()) {
                                roboInteligenteExplodiu = true;
                                break;
                            }
                        }
                    }
                } catch (MovimentoInvalidoException e) {
                    System.out.println("Robo Inteligente: " + e.getMessage());
                    // Não deve explodir, apenas continua o loop
                }
            }

            // Mostrar o plano cartesiano após cada movimento com delay
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("Plano Cartesiano:");
            roboNormal.mostrarPlanoCartesiano1(alimentoX, alimentoY, roboInteligente, obstaculos);
        }

        if (roboNormal.encontrouAlimento(alimentoX, alimentoY)) {
            System.out.println("Robo Normal encontrou o alimento primeiro.");
        } else if (roboInteligente.encontrouAlimento(alimentoX, alimentoY)) {
            System.out.println("Robo Inteligente encontrou o alimento primeiro.");
        } else {
            if (roboNormalExplodiu) {
                System.out.println("Robo Normal explodiu.");
            }
            if (roboInteligenteExplodiu) {
                System.out.println("Robo Inteligente explodiu.");
            }
        }

        System.out.println("Movimentos Robo Normal: " + movimentosRobNormal);
        System.out.println("Movimentos Robo Inteligente: " + movimentosRobInteligente);
    }
}
