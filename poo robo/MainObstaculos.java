import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainObstaculos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        // Instanciando robôs
        Robo roboNormal = new Robo("azul - normal ", 1);
        RoboInteligente roboInteligente = new RoboInteligente("verde - inteligente ", 2);

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

        // Loop do jogo
        while (!roboNormal.encontrouAlimento(alimentoX, alimentoY) &&
               !roboInteligente.encontrouAlimento(alimentoX, alimentoY) &&
               !(roboNormalExplodiu && roboInteligenteExplodiu)) {

            // Movimentação do robô normal
            int movimentoNormal = random.nextInt(4) + 1;
            if (!roboNormalExplodiu) {
                try {
                    roboNormal.mover(movimentoNormal);
                    movimentosRobNormal++;
                    
                    Iterator<Obstaculo> iterator = obstaculos.iterator();
                    while (iterator.hasNext()) {
                        Obstaculo obstaculo = iterator.next();
                        if (roboNormal.getX() == obstaculo.getX() && roboNormal.getY() == obstaculo.getY()) {
                            obstaculo.bater(roboNormal);
                            if (roboNormal.isExplodiu()) {
                                roboNormalExplodiu = true;
                                iterator.remove(); // Remove o obstáculo da lista
                                break;
                            }
                        }
                    }
                } catch (MovimentoInvalidoException e) {
                    System.out.println("Robo Normal: " + e.getMessage());
                    // Não deve explodir, apenas continua o loop
                }
            }

            // Movimentação do robô inteligente
            int movimentoInteligente = random.nextInt(4) + 1;
            if (!roboInteligenteExplodiu) {
                try {
                    roboInteligente.mover(movimentoInteligente);
                    movimentosRobInteligente++;
                    
                    Iterator<Obstaculo> iterator = obstaculos.iterator();
                    while (iterator.hasNext()) {
                        Obstaculo obstaculo = iterator.next();
                        if (roboInteligente.getX() == obstaculo.getX() && roboInteligente.getY() == obstaculo.getY()) {
                            obstaculo.bater(roboInteligente);
                            if (roboInteligente.isExplodiu()) {
                                roboInteligenteExplodiu = true;
                                iterator.remove(); // Remove o obstáculo da lista
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
                Thread.sleep(600);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Mostrar o plano cartesiano apenas se ambos os robôs não explodiram
            if (!roboNormalExplodiu || !roboInteligenteExplodiu) {
                System.out.println("\n\nPlano Cartesiano:");
                roboNormal.mostrarPlanoCartesiano1(alimentoX, alimentoY, roboInteligente, obstaculos);
            }
        }    

        // Exibir o resultado do jogo
        if (roboNormal.encontrouAlimento(alimentoX, alimentoY)) {
            System.out.println("\nRobo Normal encontrou o alimento primeiro.");
        } else if (roboInteligente.encontrouAlimento(alimentoX, alimentoY)) {
            System.out.println("\nRobo Inteligente encontrou o alimento primeiro.");
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
        
        scanner.close();
    }
}
