import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class MainMenu {

    JFrame Menu = new JFrame("Lavirint");
    JButton Start = new JButton("Start");
    JButton Exit = new JButton("Exit");
    JButton MapMaker = new JButton("Map Maker");
    ImageIcon picture = new ImageIcon("res/Images/lavirint.png");
    JLabel imageLabel = new JLabel(picture);
    ArrayList<String> mapList = new ArrayList<String>();
    JComboBox<String> lvlList;
    int buttonWidth = 100; //Sirina svakog dugmeta
    int buttonHeight = 30;//Visina svakog dugmeta
    int menuY = 460; //Lokacija dugmeta na ekranu
    int WIDTH = 490;
    int HEIGHT = 530;



    public MainMenu() {
        getMapList();
        lvlList = new JComboBox<String>(mapList.toArray(new String[mapList.size()]));

        //Podesavanja pozicije i izgleda glavnog menija
        Menu.setResizable(false);
        Menu.setSize(WIDTH, HEIGHT);
        Menu.setLayout(null);
        Menu.setLocationRelativeTo(null);
        Menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Podesavanje pozicije i izgleda dugmeta Start
        Start.setSize(buttonWidth, buttonHeight);
        Start.setLocation(10, menuY);
        Menu.add(Start);
        Start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                new Maze(lvlList.getSelectedItem().toString());
                Menu.setVisible(false);
            }

        });

        //Podesavanje pozicije i izgleda dugmeta Map Maker
        MapMaker.setSize(buttonWidth, buttonHeight);
        MapMaker.setLocation(260, menuY);
        Menu.add(MapMaker);
        MapMaker.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new MazeMapMaker();
                Menu.setVisible(false);
            }

        });

        //Podesavanje liste za izbor nivoa
        lvlList.setSize(buttonWidth + 35, buttonHeight);
        lvlList.setLocation(120, menuY);
        Menu.add(lvlList);

        //Podesavanja pozicije i izgleda dugmeta Exit
        Exit.setSize(buttonWidth, buttonHeight);
        Exit.setLocation(375, menuY);
        Menu.add(Exit);
        Exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //Prikaz slike
        imageLabel.setBounds((WIDTH - 412) / 2, 25, 412, 412);
        imageLabel.setVisible(true);
        Menu.add(imageLabel);
        Menu.setVisible(true);
    }

    static boolean levelsExistAlready = false;

    public void getMapList() {
        for (int i = 0; i < 99; i++) {
            File map = new File("./Level " + i + ".map");
            if (map.exists()) {
                mapList.add("Level " + i + ".map");
                levelsExistAlready = true;
            }
        }
    }
}
