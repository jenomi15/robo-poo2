import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Robo robo = new Robo("vermelho" , 1 );

        

        System.out.println("Digite a posição do alimento (x y): ");
        int alimentoX = scanner.nextInt();
        int alimentoY = scanner.nextInt();

        while (!robo.encontrouAlimento(alimentoX, alimentoY)) {
            robo.mostrarPlanoCartesiano(alimentoX, alimentoY, robo); // Exibe o plano cartesiano
            System.out.println("Digite o movimento (up, down, right, left) ou (1, 2, 3, 4): ");
            String entrada = scanner.next();
            
            try {
                if (entrada.matches("\\d+")) {
                    // Entrada é um número
                    int movimento = Integer.parseInt(entrada);
                    robo.mover(movimento);
                } else {
                    // Entrada é uma string
                    robo.mover(entrada);
                }
            } catch (MovimentoInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }

        robo.mostrarPlanoCartesiano(alimentoX, alimentoY, robo); // Exibe o plano cartesiano final
        System.out.println("O robô encontrou o alimento.");
    }
}
