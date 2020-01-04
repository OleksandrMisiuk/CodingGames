package com.games.bejeweledGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class BejeweledGame extends JPanel {

    private int ts = 54; //tile size
    private Point offset = new Point(48, 24);
    private static int WIDTH = 750, HEIGHT = 520;
    private Piece[][] grid = new Piece[10][10];
    private Random random = new Random(System.currentTimeMillis());
    private BufferedImage background, gems;
    private static String BACKGROUND_PATH = "C:/Developing/CodingMath/resources/bejeweled/background.png",
            GEMS_PATH = "C:/Developing/CodingMath/resources/bejeweled/gems.png";

    public BejeweledGame() {
        super();
        initGrid();
        initImages();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(background, 0 , 0, null);
    }

    private void initGrid(){
        for(int i = 1; i<8; i++){
            for(int j = 1; j<8; j++){
                grid[i][j] = new Piece();
                grid[i][j].setKind(random.nextInt()%7);
                grid[i][j].setCol(j);
                grid[i][j].setRow(i);
                grid[i][j].setX(j*ts);
                grid[i][j].setY(i*ts);
            }
        }
    }

    private void initImages() {
        try {
            background = ImageIO.read(new File(BACKGROUND_PATH));
            gems = ImageIO.read(new File(GEMS_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... str) {
        JFrame frame = new JFrame("Bejeweled Game");
        JPanel panel = new BejeweledGame();
        panel.setFocusable(true);

        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.add(panel);
        frame.setVisible(true);
    }
}
