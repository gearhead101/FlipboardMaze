package com.company.data;

/**
 * Represents each coordinate
 */
public class Coordinate {
    int x, y;
    String pathToHere;

    public Coordinate(final int x, final int y) {
        this.x = x;
        this.y = y;
        this.pathToHere = "";
    }

    public void setPath(final String new_path) {
        this.pathToHere = new_path;
    }

    public String getPath() {
        return pathToHere.toString();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
