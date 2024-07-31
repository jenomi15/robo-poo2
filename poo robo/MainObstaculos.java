import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainObstaculos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        
        Robo roboNormal = new Robo("azul - normal", 1);
        RoboInteligente roboInteligente = new RoboInteligente("verde - inteligente", 2);

        System.out.println("Digite a posição do alimento (x y): ");
        int alimentoX = scanner.nextInt();
        int alimentoY = scanner.nextInt();

        
        List<Obstaculo> obstaculos = new ArrayList<>();

        
        System.out.println("Digite a quantidade de bombas: ");
        int qtdBombas = scanner.nextInt();
        System.out.println("Digite a quantidade de rochas: ");
        int qtdRochas = scanner.nextInt();

        
        for (int i = 0; i < qtdBombas; i++) {
            System.out.println("Digite a posição da bomba " + (i + 1) + " (x y): ");
            int bombaX = scanner.nextInt();
            int bombaY = scanner.nextInt();
            obstaculos.add(new Bomba(i + 1, bombaX, bombaY)); 
        }
        for (int i = 0; i < qtdRochas; i++) {
            System.out.println("Digite a posição da rocha " + (i + 1) + " (x y): "); // aqui nao tem pra n ser sla , rocha 7 
            int rochaX = scanner.nextInt();
            int rochaY = scanner.nextInt();
            obstaculos.add(new Rocha(i + 1 + qtdBombas, rochaX, rochaY)); // esse qtd bombas aqui eh pra cada obstaculo ser unico 
        }

        boolean roboNormalExplodiu = false;
        boolean roboInteligenteExplodiu = false;

        int movimentosRobNormal = 0;
        int movimentosRobInteligente = 0;

        
        while (!roboNormal.encontrouAlimento(alimentoX, alimentoY) &&
               !roboInteligente.encontrouAlimento(alimentoX, alimentoY) &&
               !(roboNormalExplodiu && roboInteligenteExplodiu)) {

            
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
                    }
                if (!roboNormal.isExplodiu()) {
                    System.out.println("Robo Normal - Direção: " + roboNormal.getUltimaDirecao());
                    System.out.println("Robo Normal - Posição: (" + roboNormal.getX() + ", " + roboNormal.getY() + ")");
                }
            }

          
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
                                iterator.remove(); 
                                break;
                            }
                        }
                    }
                } catch (MovimentoInvalidoException e) {
                    System.out.println("Robo Inteligente: " + e.getMessage());
                }
                
                if (!roboInteligente.isExplodiu()) {
                    if (roboInteligente.isBateuNaRocha()) {
                        System.out.println("O robo inteligente bateu na rocha, portanto, ele retorna uma posição");
                        //System.out.println("Robo Inteligente - Direção: " + roboInteligente.getUltimaDirecao());
                        System.out.println("Robo Inteligente - Posição: (" + roboInteligente.getX() + ", " + roboInteligente.getY() + ")");
                        roboInteligente.setBateuNaRocha(false);
                    }
                    System.out.println("Robo Inteligente - Última Direção: " + roboInteligente.getUltimaDirecao());
                    System.out.println("Robo Inteligente - Posição: (" + roboInteligente.getX() + ", " + roboInteligente.getY() + ")");
                }
            }

           
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            
            if (!roboNormalExplodiu || !roboInteligenteExplodiu) {
                if(roboInteligenteExplodiu){
                   System.out.println("o robo inteligente explodiu. ultimo movimento :" + roboNormal.getUltimaDirecao() );
                }
                if(roboNormalExplodiu){
                    System.out.println("o robo normal explodiu. ultimo movimento : " + roboInteligente.getUltimaDirecao());
                }
                System.out.println("Plano Cartesiano:");
                roboNormal.mostrarPlanoCartesiano1(alimentoX, alimentoY, roboInteligente, obstaculos);
            }
        }

        
        if (roboNormal.encontrouAlimento(alimentoX, alimentoY)) {
            System.out.println("\nRobo Normal encontrou o alimento primeiro.");
        } else if (roboInteligente.encontrouAlimento(alimentoX, alimentoY)) {
            System.out.println("\nRobo Inteligente encontrou o alimento primeiro.");
        }

        
        if (roboNormalExplodiu) {
            System.out.println("Robo Normal explodiu. Última direção do robo normal = " + roboNormal.getUltimaDirecao());
        }
        if (roboInteligenteExplodiu) {
            System.out.println("Robo Inteligente explodiu. Última direção do robo inteligente = " + roboInteligente.getUltimaDirecao());
        }

        System.out.println("Movimentos Robo Normal: " + movimentosRobNormal);
        System.out.println("Movimentos Robo Inteligente: " + movimentosRobInteligente);

        scanner.close();
    }
}
