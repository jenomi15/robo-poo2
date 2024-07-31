
import java.util.List;

public class Robo {
    private int x;
    private int y;
    private String caracteristicas;
    private int numero;
    private boolean explodiu;
    private int prevX;
    private int prevY;
    private boolean bateuNaRocha;
    private String ultimaDirecao;

    public Robo(String caracteristicas, int numero) {
        this.x = 0;
        this.y = 0;
        this.caracteristicas = caracteristicas;
        this.numero = numero;
        this.explodiu = false;
        this.prevX = x;
        this.prevY = y;
        this.bateuNaRocha = false ;
        this.ultimaDirecao = null;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setcaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) throws MovimentoInvalidoException {
        if (x < 0) {
            throw new MovimentoInvalidoException("Robo " + numero +" , de caracteristicas " + getCaracteristicas() + ": Movimento inválido: x < 0");
        } 
        this.prevX = this.x;
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public String getUltimaDirecao() {
        return ultimaDirecao;
    }

    public void setUltimaDirecao(String ultimaDirecao) {
        this.ultimaDirecao = ultimaDirecao;
    }

    public void setY(int y) throws MovimentoInvalidoException {
        if (y < 0) {
            throw new MovimentoInvalidoException("Robo " + numero + ", de caracteristicas"+ getCaracteristicas() + ": Movimento inválido: y < 0");
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
            throw new MovimentoInvalidoException("Robo " + numero + " , de caracteristicas" + getCaracteristicas() + ": O robô explodiu e não pode se mover.");
        }

        // Guarda a posição anterior antes de mudar
        this.prevX = this.x;
        this.prevY = this.y;

        switch (direcao.toLowerCase()) {
            case "up":
                setY(y + 1);
                setUltimaDirecao("up");
                break;
            case "down":
                setY(y - 1);
                setUltimaDirecao("down");
                break;
            case "right":
                setX(x + 1);
                setUltimaDirecao("right");
                break;
            case "left":
                setX(x - 1);
                setUltimaDirecao("left");
                break;
            default:
                throw new MovimentoInvalidoException("Robo " + numero + ", de caracteristicas" + ": Direção inválida: " + direcao);
                
        }

        // Apenas imprime a posição se o robô não explodiu
        if (!explodiu) {
            System.out.println("Posição atual do robô de número: " + getNumero() + " - e de caracteristicas: " + getCaracteristicas() + " (" + x + ", " + y + ")");
        }
    }
    
    

    public void mover(int direcao) throws MovimentoInvalidoException {
        if (explodiu) {
            throw new MovimentoInvalidoException("Robo " + numero + " , de caracteristicas" + getCaracteristicas() + ": O robô explodiu e não pode se mover.");
        }

        String direcaoString;
        switch (direcao) {
            case 1:
                direcaoString = "up";
                setUltimaDirecao("up");
                break;
            case 2:
                direcaoString = "down";
                setUltimaDirecao("down");
                break;
            case 3:
                direcaoString = "right";
                setUltimaDirecao("right");
                break;
            case 4:
                direcaoString = "left";
                setUltimaDirecao("left");
                break;
            default:
                throw new MovimentoInvalidoException("Robo " + numero + ": Direção inválida: " + direcao);
        }

        try {
            mover(direcaoString); // down
        } catch (MovimentoInvalidoException e) {
            throw new MovimentoInvalidoException("Robo " + numero +", de caracteristicas "+ getCaracteristicas()+ ": Movimento inválido: " + direcaoString);
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
        System.out.println("\n\n\n");
    }

    public void mostrarPlanoCartesiano(int alimentoX, int alimentoY, Robo outroRobo) {
        int minX = Math.min(0, Math.min(x, Math.min(alimentoX, outroRobo.getX())));
        int minY = Math.min(0, Math.min(y, Math.min(alimentoY, outroRobo.getY())));
        int maxX = Math.max(x, Math.max(alimentoX, outroRobo.getX()));
        int maxY = Math.max(y, Math.max(alimentoY, outroRobo.getY()));

        for (int j = maxY; j >= minY; j--) {
            for (int i = minX; i <= maxX; i++) {
                if (i == x && j == y && !(x == -1 && y == -1)) {
                    System.out.print("R1  "); // Robo 1
                } else if (i == outroRobo.getX() && j == outroRobo.getY() && !(outroRobo.getX() == -1 && outroRobo.getY() == -1)) {
                    System.out.print("R2  "); // Robo 2
                } else if (i == alimentoX && j == alimentoY) {
                    System.out.print("A   "); // Alimento
                } else {
                    if (i >= 0 && j >= 0) { // Verifica se a coordenada é válida
                        System.out.print(".   "); // Espaço vazio
                    }
                }
            }
            System.out.println(); // Quebra de linha após cada linha do tabuleiro
        }
        System.out.println("\n\n\n");
    }


   

    public void setNumero(int numero) {
        this.numero = numero;
    }
    public void mostrarPlanoComSobreposicao(int alimentoX, int alimentoY, Robo outroRobo) {
        int minX = Math.min(0, Math.min(x, Math.min(alimentoX, outroRobo.getX())));
        int minY = Math.min(0, Math.min(y, Math.min(alimentoY, outroRobo.getY())));
        int maxX = Math.max(x, Math.max(alimentoX, outroRobo.getX()));
        int maxY = Math.max(y, Math.max(alimentoY, outroRobo.getY()));

        for (int j = maxY; j >= minY; j--) {
            for (int i = minX; i <= maxX; i++) {
                if (i == alimentoX && j == alimentoY) {
                    System.out.print("A   "); // Alimento
                } else if (i == x && j == y && !(x == -1 && y == -1)) {
                    System.out.print("R1  "); // Robo atual (R1)
                } else if (i == outroRobo.getX() && j == outroRobo.getY() && !(outroRobo.getX() == -1 && outroRobo.getY() == -1)) {
                    System.out.print("R2  "); // Outro robo (R2)
                } else {
                    if (i >= 0 && j >= 0) { // Verifica se a coordenada é válida
                        System.out.print(".   "); // Espaço vazio
                    }
                }
            }
            System.out.println(); // Quebra de linha após cada linha do tabuleiro
        }
        System.out.println("\n\n\n");
    }

    public boolean isBateuNaRocha() {
        return bateuNaRocha;
    }

    public void setBateuNaRocha(boolean bateuNaRocha) {
        this.bateuNaRocha = bateuNaRocha;
    }
  
    public boolean encontrouAlimento(int alimentoX, int alimentoY) {
        return this.x == alimentoX && this.y == alimentoY;
    }

    public int getNumero() {
        return numero;
    }







    
}
