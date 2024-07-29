import java.util.List;

public class Robo {
    private int x;
    private int y;
    private String cor;
    private int numero;
    private boolean explodiu;
    private int prevX;
    private int prevY;

    public Robo(String cor, int numero) {
        this.x = 0;
        this.y = 0;
        this.cor = cor;
        this.numero = numero;
        this.explodiu = false;
        this.prevX = x;
        this.prevY = y;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) throws MovimentoInvalidoException {
        if (x < 0) {
            throw new MovimentoInvalidoException("Movimento inválido: x < 0");
        }
        this.prevX = this.x;
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) throws MovimentoInvalidoException {
        if (y < 0) {
            throw new MovimentoInvalidoException("Movimento inválido: y < 0");
        }
        this.prevY = this.y;
        this.y = y;
    }

    public boolean isExplodiu() {
        return explodiu;
    }

    public void setExplodiu(boolean explodiu) {
        this.explodiu = explodiu;
    }

    public void voltarPosicaoAnterior() {
        this.x = this.prevX;
        this.y = this.prevY;
    }

    public void removerDoPlanoCartesiano() {
        this.x = -1;
        this.y = -1;
    }

    public void mover(String direcao) throws MovimentoInvalidoException {
        if (explodiu) {
            throw new MovimentoInvalidoException("O robô explodiu e não pode se mover.");
        }

        switch (direcao.toLowerCase()) {
            case "up":
                setY(y + 1);
                break;
            case "down":
                setY(y - 1);
                break;
            case "right":
                setX(x + 1);
                break;
            case "left":
                setX(x - 1);
                break;
            default:
                throw new MovimentoInvalidoException("Direção inválida: " + direcao);
        }

        System.out.println("Posição atual do robô de número: " + getNumero() + " - e de cor: " + getCor() + " (" + x + ", " + y + ")");
    }

    public void mover(int direcao) throws MovimentoInvalidoException {
        if (explodiu) {
            throw new MovimentoInvalidoException("O robô explodiu e não pode se mover.");
        }

        switch (direcao) {
            case 1:
                mover("up");
                break;
            case 2:
                mover("down");
                break;
            case 3:
                mover("right");
                break;
            case 4:
                mover("left");
                break;
            default:
                throw new MovimentoInvalidoException("Direção inválida: " + direcao);
        }
    }

    public void mostrarPlanoCartesiano1(int alimentoX, int alimentoY, Robo outroRobo, List<Obstaculo> obstaculos) {
        int minX = Math.min(0, Math.min(x, Math.min(alimentoX, outroRobo.getX())));
        int minY = Math.min(0, Math.min(y, Math.min(alimentoY, outroRobo.getY())));
        int maxX = Math.max(x, Math.max(alimentoX, outroRobo.getX()));
        int maxY = Math.max(y, Math.max(alimentoY, outroRobo.getY()));
    
        for (int j = maxY; j >= minY; j--) {
            for (int i = minX; i <= maxX; i++) {
                boolean isObstaculo = false;
                for (Obstaculo obstaculo : obstaculos) {
                    if (i == obstaculo.getX() && j == obstaculo.getY()) {
                        if (obstaculo instanceof Bomba) {
                            System.out.print("B   ");
                        } else if (obstaculo instanceof Rocha) {
                            System.out.print("R   ");
                        }
                        isObstaculo = true;
                        break;
                    }
                }
                if (!isObstaculo) {
                    if (i == x && j == y && !explodiu) {
                        System.out.print("R1  "); // Robo 1
                    } else if (i == outroRobo.getX() && j == outroRobo.getY() && !outroRobo.isExplodiu()) {
                        System.out.print("R2  "); // Robo 2
                    } else if (i == alimentoX && j == alimentoY) {
                        System.out.print("A   "); // Alimento
                    } else {
                        if (i >= 0 && j >= 0) { // Verifica se a coordenada é válida
                            System.out.print(".   "); // Espaço vazio
                        }
                    }
                }
            }
            System.out.println(); // Quebra de linha após cada linha do tabuleiro
        }
    }
    
    
    public void mostrarPlanoCartesiano(int alimentoX, int alimentoY, Robo outroRobo) {
        int minX = Math.min(0, Math.min(x, Math.min(alimentoX, outroRobo.getX())));
        int minY = Math.min(0, Math.min(y, Math.min(alimentoY, outroRobo.getY())));
        int maxX = Math.max(x, Math.max(alimentoX, outroRobo.getX()));
        int maxY = Math.max(y, Math.max(alimentoY, outroRobo.getY()));

        for (int j = maxY; j >= minY; j--) {
            for (int i = minX; i <= maxX; i++) {
                if (i == x && j == y) {
                    System.out.print("R1  "); // Robo 1
                } else if (i == outroRobo.getX() && j == outroRobo.getY()) {
                    System.out.print("R2  "); // Robo 2
                } else if (i == alimentoX && j == alimentoY) {
                    System.out.print("A   "); // Alimento
                } else {
                    System.out.print(".   "); // Espaço vazio
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean encontrouAlimento(int alimentoX, int alimentoY) {
        return this.x == alimentoX && this.y == alimentoY;
    }
    
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
