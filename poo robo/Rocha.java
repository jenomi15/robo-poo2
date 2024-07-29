public class Rocha extends Obstaculo {
    public Rocha(int id, int x, int y) {
        super(id, x, y);
    }

    @Override
    public void bater(Robo robo) {
        System.out.println("Rocha atingiu o robô de número: " + robo.getNumero());
        robo.voltarPosicaoAnterior(); // O robô volta para a posição anterior
    }
}
