import edu.princeton.cs.algs4.MinPQ;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class Maze extends JFrame {
    public static int rows = 20;
    public static int columns = 20;
    public static int panelSize = 25;
    public static int map[][] = new int[columns][rows];
    int endX;
    int endY;
    ArrayList<Tile> tiles;

    int startX;
    int startY;

    ArrayList<Tile> solution;
    boolean solveable;
    boolean finished = false;
    int ind = 0;

    public Maze(String str) {
        tiles = new ArrayList<>();
        loadMap(str);
        this.setResizable(false);
        this.setSize((columns * panelSize) + 50, (rows * panelSize) + 70);
        this.setTitle("Lavirint");
        this.setLayout(null);

        this.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                revalidate();
                repaint();

                if (key == KeyEvent.VK_ENTER) {
                    solve();
                    if (!solveable)
                        JOptionPane.showMessageDialog(null, "Lavirint nema resenje");
                }

                if (key == KeyEvent.VK_SPACE) {
                    if (finished && ind < solution.size()) {
                        Tile t = solution.get(ind);
                        if (ind > 0) {
                            Tile t2 = solution.get(ind - 1);
                            t2.setBackground(Color.RED);
                        }
                        ind++;
                        t.setBackground(Color.BLUE);
                    }

                }
            }

            @Override
            public void keyReleased(KeyEvent arg0) {

            }

            @Override
            public void keyTyped(KeyEvent arg0) {

            }

        });

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new MainMenu();
            }
        });

        this.setLocationRelativeTo(null);

        for (int y = 0; y < columns; y++) {
            for (int x = 0; x < rows; x++) {
                Tile tile = new Tile(this, 0, x, y);
                tile.setSize(panelSize, panelSize);
                tile.setLocation((x * panelSize) + 23, (y * panelSize) + 25);
                if (map[x][y] == 0) {
                    tile.setBackground(Color.GRAY);
                } else {
                    tile.setBackground(Color.WHITE);
                    tile.setWall(false);
                    //ulaz u lavirint se nalazi u prvoj koloni
                    if (x == 0) {
                        startX = x;
                        startY = y;
                    }
                    //izlaz iz lavirinta se nalazi u poslednjoj koloni
                    if (x == columns - 1) {
                        endX = x;
                        endY = y;
                    }
                }

                tile.setVisible(true);
                tiles.add(tile);
                this.add(tile);
            }
        }
        this.setVisible(true);
        Tile current = getTileAtPosition(startX, startY);
        current.setBackground(Color.BLUE);
    }

    public static void main(String args[]) {
        new MainMenu();
    }

    public void loadMap(String str) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(str));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String mapStr = sb.toString();

            int counter = 0;
            for (int y = 0; y < columns; y++) {
                for (int x = 0; x < rows; x++) {
                    String mapChar = mapStr.substring(counter, counter + 1);
                    if (!mapChar.equals("\r\n") && !mapChar.equals("\n") && !mapChar.equals("\r")) {
                        map[x][y] = Integer.parseInt(mapChar);
                    } else {
                        x--;
                        System.out.print(mapChar);
                    }
                    counter++;
                }
            }
        } catch (Exception e) {
            System.out.println("Nije moguce ucitati mapu, kreirati novu.");
        }
    }

    public Tile getTileAtPosition(int x, int y) {
        for (Tile t : tiles) {
            if (t.x == x && t.y == y)
                return t;
        }
        return null;
    }

    public void solve() {
        MinPQ<Tile> mpq = new MinPQ<>();
        solution = new ArrayList<>();
        Tile current = getTileAtPosition(startX, startY);
        mpq.insert(current);
        while (!mpq.isEmpty()) {
            current = mpq.delMin();   //vraca cvor sa najmanjom cenom iz reda mpq
            current.visited = true;
            if (current.x == endX && current.y == endY) {
                solveable = true;
                while (current != null) {
                    current.setBackground(Color.RED);
                    solution.add(current);
                    current = current.previous;
                }
                break;
            }
            Tile t;
            if (current.x - 1 >= 0) {    //provera da li smo izasli van opsega
                t = getTileAtPosition(current.x - 1, current.y);
                if (!t.isWall && !t.visited) {
                    t.previous = current;
                    t.moves = current.moves + 1;
                    mpq.insert(t);
                }
            }
            if (current.x + 1 < columns) {
                t = getTileAtPosition(current.x + 1, current.y);
                if (!t.isWall && !t.visited) {
                    t.previous = current;
                    t.moves = current.moves + 1;
                    mpq.insert(t);
                }
            }
            if (current.y + 1 < rows) {
                t = getTileAtPosition(current.x, current.y + 1);
                if (!t.isWall && !t.visited) {
                    t.previous = current;
                    t.moves = current.moves + 1;
                    mpq.insert(t);
                }
            }
            if (current.y - 1 >= 0) {
                t = getTileAtPosition(current.x, current.y - 1);
                if (!t.isWall && !t.visited) {
                    t.previous = current;
                    t.moves = current.moves + 1;
                    mpq.insert(t);
                }
            }
        }
        Collections.reverse(solution);
        finished = true;
    }
}
