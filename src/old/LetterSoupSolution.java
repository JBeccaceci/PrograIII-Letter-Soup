package old;

import model.Posicion;

import java.util.ArrayList;
import java.util.List;

public class LetterSoupSolution {
    private char[][]soup;
    private int longX;
    private int longY;
    private String word;
    private List<Posicion> path;
    private ArrayList<Posicion> solution;

    public LetterSoupSolution(char[][] soup, int longX, int longY, String word) {
        this.soup = soup;
        this.longX = longX;
        this.longY = longY;
        this.word = word;
        this.path = new ArrayList<>();
        this.solution = new ArrayList<>();
    }

    private boolean pathIncluded(int posX, int posY) {
        return this.path.stream().anyMatch(p -> (p.posX == posX && p.posY == posY));
    }

    private void removePath(int posX, int posY) {
        this.path.removeIf(p -> (p.posX == posX && p.posY == posY));
    }

    public ArrayList<Posicion> execute() {
        for (int i = 0; i < this.soup.length; i++) {
            for (int j = 0; j < this.soup[i].length; j++) {
                if (this.findInLetterSoup(i, j, 0)) {
                    return this.solution;
                }
                this.solution.clear();
            }
        }

        return new ArrayList<>();
    }

    private boolean findInLetterSoup(int posX, int posY, int offset) {
        if (offset == word.length())
            return true;

        if (posX > this.longX || posY > this.longY
                || this.soup[posX][posY] != this.word.charAt(offset)
                || this.pathIncluded(posX, posY)) {
            return false;
        }

        this.path.add(new Posicion(posX, posY));
        boolean result = findInLetterSoup(posX + 1, posY, offset + 1) ||
                findInLetterSoup(posX, posY + 1, offset + 1) ||
                findInLetterSoup(posX + 1, posY + 1, offset + 1);

        this.removePath(posX, posY);
        this.solution.add(new Posicion(posX, posY));
        return result;
    }
}
