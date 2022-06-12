import model.Posicion;
import model.ResolverEncontrarPalabraInterface;

import java.util.ArrayList;
import java.util.List;

public class ResolverEncontrarPalabraImplementacion implements ResolverEncontrarPalabraInterface {
    private List<Posicion> path;
    private ArrayList<Posicion> solution;

    public ResolverEncontrarPalabraImplementacion() {
        this.path = new ArrayList<>();
        this.solution = new ArrayList<>();
    }

    public ArrayList<Posicion> resolverEncontrarPalabra(char[][]matrizLetras, int longX, int longY, String palabraBuscar){
        return this.execute(matrizLetras, longX, longY, palabraBuscar);
    }

    public ArrayList<Posicion> execute(char[][] soup, int longX, int longY, String word) {
        for (int i = 0; i < soup.length; i++) {
            for (int j = 0; j < soup[i].length; j++) {
                if (this.findInLetterSoup(soup, i, j, 0, word, longX, longY)) {
                    return this.solution;
                }
                this.solution.clear();
            }
        }

        return new ArrayList<>();
    }

    private boolean findInLetterSoup(char[][] soup, int posX, int posY, int offset, String word, int longX, int longY) {
        if (offset == word.length())
            return true;

        if (posX > longX || posY > longY
                || soup[posX][posY] != word.charAt(offset)
                || pathIncluded(posX, posY)) {
            return false;
        }

        this.path.add(new Posicion(posX, posY));
        boolean result = findInLetterSoup(soup,posX + 1, posY, offset + 1, word, longX, longY) ||
                findInLetterSoup(soup, posX, posY + 1, offset + 1, word, longX, longY) ||
                findInLetterSoup(soup, posX + 1, posY + 1, offset + 1, word, longX, longY);

        this.removePath(posX, posY);
        this.solution.add(new Posicion(posX, posY));
        return result;
    }

    private boolean pathIncluded(int posX, int posY) {
        return this.path.stream().anyMatch(p -> (p.posX == posX && p.posY == posY));
    }

    private void removePath(int posX, int posY) {
        this.path.removeIf(p -> (p.posX == posX && p.posY == posY));
    }
}