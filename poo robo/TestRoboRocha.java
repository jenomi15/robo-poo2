public class TestRoboRocha {
    public static void main(String[] args) {
        // Instanciando um robô e uma rocha
        Robo robo = new Robo("azul", 1);
        Rocha rocha = new Rocha(1, 1, 1);
        try {
            // Movendo o robô para a posição (1, 1)
            robo.mover("right");
            robo.mover("up");
            System.out.println("Posição do robô antes de bater na rocha: (" + robo.getX() + ", " + robo.getY() + ")");
            rocha.bater(robo);
            System.out.println("Posição do robô após bater na rocha: (" + robo.getX() + ", " + robo.getY() + ")");
        } catch (MovimentoInvalidoException e) {
            e.printStackTrace();
        }
    }
}
