import javax.swing.*;

public class Tile extends JPanel implements Comparable<Tile> {
    int x, y;
    boolean isWall = true;
    Maze maze;
    Tile previous;
    boolean visited = false;
    int moves;

    public Tile(Maze m, int mv, int x, int y) {
        this.maze = m;
        this.x = x;
        this.y = y;
        this.previous = null;
        this.moves = mv;
    }

    public void setWall(boolean isWall) {
        this.isWall = isWall;
    }

    public double euclidian() {
        return Math.sqrt(Math.pow(x - maze.endX, 2) + Math.pow(y - maze.endY, 2));
    }

    @Override
    public int compareTo(Tile other) {
        if (moves + euclidian() > other.moves + other.euclidian())
            return 1;
        else if (moves + euclidian() < other.moves + other.euclidian())
            return -1;
        return 0;
    }
}

