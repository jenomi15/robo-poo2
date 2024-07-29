public class RoboInteligente extends Robo {

    public RoboInteligente(String cor , int numero) {
        super(cor , numero);

    }

    @Override
    public void mover(String direcao) throws MovimentoInvalidoException {
        try {
            super.mover(direcao);
        } catch (MovimentoInvalidoException e) {
            System.out.println("Movimento inválido: " + direcao);
            // Tenta outros movimentos até encontrar um válido
            String[] direcoes = {"up", "down", "right", "left"};
            for (String novaDirecao : direcoes) {
                if (!novaDirecao.equals(direcao)) {
                    try {
                        super.mover(novaDirecao);
                        return;
                    } catch (MovimentoInvalidoException ignored) {
                    }
                }
            }
        }
    }
}
// nao é aqui o movimento