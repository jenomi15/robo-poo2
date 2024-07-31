import java.util.Random;
import java.util.Scanner;

public class MainDoisRobos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        

        Robo robo1 = new Robo("azul" , 1);
        Robo robo2 = new Robo("verde" , 2);


        System.out.println("Digite a posição do alimento (x y): ");
        int alimentoX = scanner.nextInt();
        int alimentoY = scanner.nextInt();

        int movimentosRob1 = 0;
        int movimentosRob2 = 0;
        int movimentosInvalidosRob1 = 0;
        int movimentosInvalidosRob2 = 0;


        final int delayMillis = 700; 


        while (!robo1.encontrouAlimento(alimentoX, alimentoY) && !robo2.encontrouAlimento(alimentoX, alimentoY)) {
            int movimento1 = random.nextInt(4) + 1;
            int movimento2 = random.nextInt(4) + 1;

            try {
                robo1.mover(movimento1);
                movimentosRob1++;
            } catch (MovimentoInvalidoException e) {
                movimentosInvalidosRob1++;
                System.out.println("Robo 1: " + e.getMessage());
            }

            try {
                robo2.mover(movimento2);
                movimentosRob2++;
            } catch (MovimentoInvalidoException e) {
                movimentosInvalidosRob2++;
                System.out.println("Robo 2: " + e.getMessage());
            }

            // Exibir o plano cartesiano com os dois robôs e o alimento
            robo1.mostrarPlanoCartesiano(alimentoX, alimentoY, robo2);

            // Atraso para visualizar os movimentos
            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException e) {
                System.out.println("Erro ao pausar a execução: " + e.getMessage());
            }
        }

        if (robo1.encontrouAlimento(alimentoX, alimentoY)) {
            System.out.println("Robo 1 encontrou o alimento primeiro.");
        } else {
            System.out.println("Robo 2 encontrou o alimento primeiro.");
        }

        System.out.println("Movimentos válidos Robo 1: " + movimentosRob1);
        System.out.println("Movimentos inválidos Robo 1: " + movimentosInvalidosRob1);
        System.out.println("Movimentos válidos Robo 2: " + movimentosRob2);
        System.out.println("Movimentos inválidos Robo 2: " + movimentosInvalidosRob2);
    }
}
