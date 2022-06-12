package model;

import java.util.Objects;

public class Posicion {
    public int posX;
    public int posY;

    public Posicion(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Posicion posicion = (Posicion) o;
        return posX == posicion.posX && posY == posicion.posY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posX, posY);
    }
}
