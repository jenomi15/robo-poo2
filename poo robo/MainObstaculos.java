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
        Robo roboNormal = new Robo("azul - normal", 1);
        RoboInteligente roboInteligente = new RoboInteligente("verde - inteligente", 2);

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
            String direcaoNormal = getDirecao(movimentoNormal);
            if (!roboNormalExplodiu) {
                try {
                    roboNormal.mover(direcaoNormal);
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
                }
                // Mostrar posição e direção do robô normal
                if (!roboNormal.isExplodiu()) {
                    System.out.println("Robo Normal - Direção: " + direcaoNormal);
                    System.out.println("Robo Normal - Posição: (" + roboNormal.getX() + ", " + roboNormal.getY() + ")");
                }
            }

            // Movimentação do robô inteligente
            int movimentoInteligente = random.nextInt(4) + 1;
            String direcaoInteligente = getDirecao(movimentoInteligente);
            if (!roboInteligenteExplodiu) {
                try {
                    roboInteligente.mover(direcaoInteligente);
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
                }
                // Mostrar posição e última direção válida do robô inteligente
                if (!roboInteligente.isExplodiu()) {
                    if(roboInteligente.isBateuNaRocha()){
                        System.out.println(" O robo inteligente bateu na rocha , portanto , ele retorna uma posição");
                        //System.out.println("Robo Inteligente - Direção: " + roboInteligente.getUltimaDirecao());
                        System.out.println("Robo Inteligente - Posição: (" + roboInteligente.getX() + ", " + roboInteligente.getY() + ")");
                       roboInteligente.setBateuNaRocha(false);
                    }
                    System.out.println("Robo Inteligente -  ultima Direção : " + roboInteligente.getUltimaDirecao());
                    System.out.println("Robo Inteligente - Posição: (" + roboInteligente.getX() + ", " + roboInteligente.getY() + ")");

                }
                
            }

            // Mostrar o plano cartesiano após cada movimento com delay
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Mostrar o plano cartesiano apenas se ambos os robôs não explodiram
            if (!roboNormalExplodiu || !roboInteligenteExplodiu) {
                System.out.println("Plano Cartesiano:");
                roboNormal.mostrarPlanoCartesiano1(alimentoX, alimentoY, roboInteligente, obstaculos);
            }
        }    

        // Exibir o resultado do jogo
        if (roboNormal.encontrouAlimento(alimentoX, alimentoY)) {
            System.out.println("\nRobo Normal encontrou o alimento primeiro.");
        } else if (roboInteligente.encontrouAlimento(alimentoX, alimentoY)) {
            System.out.println("\nRobo Inteligente encontrou o alimento primeiro.");
        }

        // Sempre exibe a informação sobre a explosão dos robôs
        if (roboNormalExplodiu) {
            System.out.println("Robo Normal explodiu.   ultima direção do robo normal = " + roboNormal.getUltimaDirecao() );
        }
        if (roboInteligenteExplodiu) {
            System.out.println("Robo Inteligente explodiu.  ultima direção do robo inteligente = " + roboInteligente.getUltimaDirecao());
        }

        System.out.println("Movimentos Robo Normal: " + movimentosRobNormal);
        System.out.println("Movimentos Robo Inteligente: " + movimentosRobInteligente);

        scanner.close();
    }

    private static String getDirecao(int movimento) {
        switch (movimento) {
            case 1: return "up";
            case 2: return "down";
            case 3: return "right";
            case 4: return "left";
            default: throw new IllegalArgumentException("Movimento inválido: " + movimento);
        }
    }
}
