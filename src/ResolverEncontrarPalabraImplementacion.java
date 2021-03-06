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

    /**
     * Este metodo tiene el objetivo de buscar, con un for una posicion (X,Y) por donde arrancar
     * la recursividad, en este caso, va a buscar la primer letra que tiene la cadena.
     * Para el caso de "CASA" busca una "C" y a partir de ahi valida recursivamente
     * los vecinos
     * @param soup
     * @param longX
     * @param longY
     * @param word
     * @return
     */
    public ArrayList<Posicion> execute(char[][] soup, int longX, int longY, String word) {
        for (int i = 0; i < soup.length; i++) {
            for (int j = 0; j < soup[i].length; j++) {
                if (this.findInLetterSoup(soup, i, j, 0, word, longX, longY, 0)) {
                    return this.solution;
                }
                this.solution.clear();
            }
        }

        return new ArrayList<>();
    }

    /**
     * Este metodo lo primero que realiza son validaciones (caso base o caso fallido).
     * - Primer lugar validamos que la posicion que estamos analizando no se pase del tamano que posee la matriz.
     * - Segundo lugar, validamos que la letra que estamos evaluando en X,Y sea la que necesitamos
     * - Tercer lugar, validamos que la posicion actual no haya sido evaluada en una ejecucion previa.
     *
     * La funcion cuenta con tres llamadas recursivas en donde cada una de estas valida los vecinos
     * de la posicion que estemos evaluando en cuestion.
     * Evalua hacia abajo, en diagonal y hacia la derecha, no hacia la IZQ.
     *
     * Si alguno de nuestros candidatos cumplen con las condiciones, los removemos de la lista (ya que son candidatos)
     * y agregamos dicho candidato a un conjunto solucion "local" que se va a ir reiniciado a medida que hagamos
     * iteraciones en la funcion {@link #execute(char[][], int, int, String)}
     *
     * @param soup
     * @param posX
     * @param posY
     * @param offset
     * @param word
     * @param longX
     * @param longY
     * @return
     */
    private boolean findInLetterSoup(char[][] soup, int posX, int posY, int offset, String word, int longX, int longY, int direction) {
        if (offset == word.length())
            return true;

        if (posX > longX - 1 || posY > longY - 1
                || soup[posX][posY] != word.charAt(offset)
                || pathIncluded(posX, posY)) { // O(n)
            return false;
        }

        // Validamos que el resto de la cadena no sobrepase las dimensiones de la matriz (Poda)
        if (direction != 0) {
            boolean a = (longX - posX) < (word.length() - offset);
            boolean b = (longY - posY) < (word.length() - offset);
            if (direction == 1) {
                if (a) {
                    return false;
                }
            } else if (direction == -1) {
                if (b) {
                    return false;
                }
            } else {
                if (a || b) {
                    return false;
                }
            }
        }

        // Validar si el largo de la palabra sobrepasa la matriz
        // 1 ^ LONGITUD(Palabra) + (N ^ 2)

        this.path.add(new Posicion(posX, posY));
        boolean result;
        if (direction == 0) {
            // Primer ejecucion, buscamos en las tres direcciones
            result = findInLetterSoup(soup,posX + 1, posY, offset + 1, word, longX, longY, 1) ||
                    findInLetterSoup(soup, posX, posY + 1, offset + 1, word, longX, longY, -1) ||
                    findInLetterSoup(soup, posX + 1, posY + 1, offset + 1, word, longX, longY, -2);
        } else {
            if (direction == 1) {
                // Derecha
                result = findInLetterSoup(soup,posX + 1, posY, offset + 1, word, longX, longY, 1);
            } else if (direction == -1) {
                // Abajo
                result = findInLetterSoup(soup, posX, posY + 1, offset + 1, word, longX, longY, -1);
            } else {
                // Diagonal
                result = findInLetterSoup(soup, posX + 1, posY + 1, offset + 1, word, longX, longY, -2);
            }
        }

        this.removePath(posX, posY); // O(n)
        if (result) {
            this.solution.add(new Posicion(posX, posY));
        }
        return result;
    }

    /**
     * Funcion que recorre una lista y verifica si hay hay objeto que tenga esas caracteristicas
     * osea, que matchee con un valor de X, Y. En tal caso devuelve TRUE. Complejidad O(n)
     * @param posX
     * @param posY
     * @return
     */
    private boolean pathIncluded(int posX, int posY) {
        return this.path.stream().anyMatch(p -> (p.posX == posX && p.posY == posY));
    }

    /**
     * Funcion que recorre una lista y verifica si hay hay objeto que tenga esas caracteristicas
     * osea, que matchee con un valor de X, Y. De ser TRUE, elimina el objeto de la lista. Complejidad O(n)
     * @param posX
     * @param posY
     */
    private void removePath(int posX, int posY) {
        this.path.removeIf(p -> (p.posX == posX && p.posY == posY));
    }
}