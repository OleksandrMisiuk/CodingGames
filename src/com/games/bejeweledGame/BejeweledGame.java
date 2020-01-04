package com.games.bejeweledGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.TimerTask;

public class BejeweledGame extends JPanel {

    private int ts = 54; //tile size
    private Point offset = new Point(48, 24);
    private static int WIDTH = 750, HEIGHT = 520;
    private Piece[][] grid = new Piece[10][10];
    private Random random = new Random(System.currentTimeMillis());
    private BufferedImage background, gems;
    private static String BACKGROUND_PATH = "C:/Developing/CodingMath/resources/bejeweled/background.png",
            GEMS_PATH = "C:/Developing/CodingMath/resources/bejeweled/gems.png";

    private Point pos = new Point();
    private int x0, y0, x, y, click;
    private boolean isSwap = false, isMoving = false;


    public BejeweledGame() {
        super();
        initGrid();
        initImages();

        new java.util.Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                repaint();
                piecesMove();
                noMatch();
                matchFinding();
                updateGrid();
            }
        }, 300, 1000 / 40);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (!isSwap && !isMoving) click++;
                    pos.setLocation(e.getX() - offset.getX(), e.getY() - offset.getY());
                    System.out.println(pos);
                }
            }
        });
    }

    private void matchFinding() {
        for (int i = 1; i <= 8; i++)
            for (int j = 1; j <= 8; j++) {
                if (grid[i][j].getKind() == grid[i + 1][j].getKind())
                    if (grid[i][j].getKind() == grid[i - 1][j].getKind())
                        for (int n = -1; n <= 1; n++) grid[i + n][j].setMatch(grid[i + n][j].getMatch() + 1);

                if (grid[i][j].getKind() == grid[i][j + 1].getKind())
                    if (grid[i][j].getKind() == grid[i][j - 1].getKind())
                        for (int n = -1; n <= 1; n++) grid[i][j + n].setMatch(grid[i][j + n].getMatch() + 1);
            }
    }

    private void noMatch() {
        if (isSwap && !isMoving) {
            swapPieces(grid[y0][x0], grid[y][x]);
            isSwap = false;
        }
    }

    private void updateGrid() {
        if (!isMoving) {
            for (int i = 8; i > 0; i--)
                for (int j = 1; j <= 8; j++)
                    if (grid[i][j].getMatch() != 0)
                        for (int n = i; n > 0; n--)
                            if (grid[n][j].getMatch() == 0) {
                                swapPieces(grid[n][j], grid[i][j]);
                                break;
                            }


            for (int j = 1; j <= 8; j++)
                for (int i = 8, n = 0; i > 0; i--)
                    if (grid[i][j].getMatch() != 0) {
                        grid[i][j].setKind(random.nextInt(1000) % 7);
                        grid[i][j].setY(-ts * n++);
                        grid[i][j].setMatch(0);
                    }
        }

    }

    private void piecesMove() {
        if (click == 1) {
            x0 = pos.x / ts + 1;
            y0 = pos.y / ts + 1;
            System.out.println(x0 + " " + y0);
        }
        if (click == 2) {
            x = pos.x / ts + 1;
            y = pos.y / ts + 1;
            if (Math.abs(x - x0) + Math.abs(y - y0) == 1) {
                swapPieces(grid[y0][x0], grid[y][x]);
                click = 0;
                isSwap = false;
            } else click = 1;
        }

        isMoving = false;
        for (int i = 1; i <= 8; i++)
            for (int j = 1; j <= 8; j++) {
                Piece p = grid[i][j];
                int dx, dy;
                for (int n = 0; n < 4; n++)   // 4 - speed
                {
                    dx = p.getX() - p.getCol() * ts;
                    dy = p.getY() - p.getRow() * ts;
                    if (dx != 0) p.setX(p.getX() - dx / Math.abs(dx));
                    if (dy != 0) p.setY(p.getY() - dy / Math.abs(dy));
                    if (dx != 0 || dy != 0) isMoving = true;
                }
            }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(background, 0, 0, null);
        drawPieces(g2d);
    }

    private void initGrid() {
        for (int i = 0; i <= 9; i++) {
            for (int j = 0; j <= 9; j++) {
                grid[i][j] = new Piece();
                grid[i][j].setKind(random.nextInt(1000) % 7);
                grid[i][j].setCol(j);
                grid[i][j].setRow(i);
                grid[i][j].setX(j * ts);
                grid[i][j].setY(i * ts);
            }
        }
    }

    private void drawPieces(Graphics2D g2d) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                Piece p = grid[i][j];
                BufferedImage gem = gems.getSubimage(p.getKind() * 49, 0, 49, 49);
                g2d.drawImage(gem, (int) (p.getX() + offset.getX() - ts), (int) (p.getY() + offset.getY() - ts), null);
            }
        }
    }

    private void swapPieces(Piece p1, Piece p2) {
        int temp = p1.getCol();
        p1.setCol(p2.getCol());
        p2.setCol(temp);
        temp = p1.getRow();
        p1.setRow(p2.getRow());
        p2.setRow(temp);
        grid[p1.getRow()][p1.getCol()] = p1;
        grid[p2.getRow()][p2.getCol()] = p2;

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
