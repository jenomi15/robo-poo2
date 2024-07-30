public class RoboInteligente extends Robo {
    private String ultimaDirecao;

    public RoboInteligente(String cor, int numero) {
        super(cor, numero);
        this.ultimaDirecao = null; // Inicializa sem direção
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
}
