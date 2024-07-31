public class RoboInteligente extends Robo {
    private String ultimaDirecao;
    private boolean bateuNaRocha ;

    public RoboInteligente(String caracteristicas, int numero) {
        super(caracteristicas, numero);
        this.ultimaDirecao = null; // Inicializa sem direção
        this.bateuNaRocha = false;
    }

    @Override
    public void mover(String direcao) throws MovimentoInvalidoException {
        try {
            super.mover(direcao);
            this.ultimaDirecao = direcao; // Atualiza a última direção válida
        } catch (MovimentoInvalidoException e) {
            System.out.println("Movimento inválido realizado pelo robo inteligente: " + direcao);
            // Tenta outros movimentos até encontrar um válido
            String[] direcoes = {"up", "down", "right", "left"};
            for (String novaDirecao : direcoes) {
                if (!novaDirecao.equals(direcao)) {
                    try {
                        super.mover(novaDirecao);
                        this.ultimaDirecao = novaDirecao; // Atualiza a última direção válida
                        System.out.println("movimento realizado pelo robo inteligente :" + novaDirecao);
                        return;
                    } catch (MovimentoInvalidoException ignored) {
                    }
                }
            }
        }
    }

    public String getUltimaDirecao() {
        return ultimaDirecao;
    }

    public void setBateuNaRocha(boolean bateuNaRocha) {
        this.bateuNaRocha = bateuNaRocha;
    }

    public boolean isBateuNaRocha() {
        return bateuNaRocha;
    }
}
