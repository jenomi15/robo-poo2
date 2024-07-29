class Bomba extends Obstaculo {
    public Bomba(int id, int x, int y) {
        super(id, x, y);
    }

    @Override
    public void bater(Robo robo) {
        System.out.println("Bomba atingiu o robo de numero: " + robo.getNumero());
        robo.setExplodiu(true);
        robo.removerDoPlanoCartesiano();
    }  
}
