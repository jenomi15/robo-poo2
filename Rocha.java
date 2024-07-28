class Rocha extends Obstaculo {
    public Rocha(int id, int x, int y) {
        super(id, x, y);
    }

    @Override
    public void bater(Robo robo) {
        System.out.println("Rocha atingiu o robo de numero: " + robo.getNumero());
        robo.voltarPosicaoAnterior();
    }
}
