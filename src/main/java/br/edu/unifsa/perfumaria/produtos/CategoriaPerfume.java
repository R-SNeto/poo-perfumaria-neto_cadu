package br.edu.unifsa.perfumaria.produtos;

public enum CategoriaPerfume {

    PARFUM,
    EAUDEPARFUM,
    EAUDETOILETTE,
    COLONIA;

    public double getConcentracaoMin(CategoriaPerfume categoriaPerfume) {
        switch (categoriaPerfume) {
            case PARFUM -> { return 0.2; }
            case EAUDEPARFUM -> { return 0.15; }
            case EAUDETOILETTE -> { return 0.05; }
            case COLONIA -> { return 0.02; }
            default -> throw new IllegalArgumentException("Categoria inválida");
        }
    }

    public double getConcentracaoMax(CategoriaPerfume categoriaPerfume) {
        switch (categoriaPerfume) {
            case PARFUM -> { return 0.3; }
            case EAUDEPARFUM -> { return 0.2; }
            case EAUDETOILETTE -> { return 0.15; }
            case COLONIA -> { return 0.05; }
            default -> throw new IllegalArgumentException("Categoria inválida");
        }
    }

}
