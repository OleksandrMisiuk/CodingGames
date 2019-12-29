package com.games.outrunGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TimerTask;

public class OutrunGame extends JPanel {

    private static final int WIDTH = 1024, HEIGHT = 768;
    private int roadW = 2000, segL = 200;
    private float camD = 0.84f;
    private List<Line> lines = new ArrayList<>();
    private int position = 1;
    private int playerX = 1;

    public OutrunGame() {
        init();

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_A) playerX -= 200;
                if (e.getKeyCode() == KeyEvent.VK_D) playerX += 200;
                if (e.getKeyCode() == KeyEvent.VK_W) position += 200;
                if (e.getKeyCode() == KeyEvent.VK_S) position -= 200;
            }
        });

        new java.util.Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 100, 1000 / 40);
    }

    private void init() {
        for (int i = 0; i < 1600; i++) {
            Line line = new Line();
            line.setZ(i * segL);
            if (i > 300 && i < 700) line.setCurve(0.5f);
            lines.add(line);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        drawRoad(g2d);

        g2d.setColor(Color.BLACK);
//        g2d.fillRect(0, 0, WIDTH, HEIGHT / 2 + 8);

//        g2d.clearRect(0,0,WIDTH, HEIGHT/2);

    }

    private void drawRoad(Graphics2D g2d) {
        int startPos = position / segL;
        float x = 0, dx = 0;
        for (int n = startPos; n < startPos + 300; n++) {
            Line l = lines.get(n);
            l.project((int) (playerX - x), 1500, position, camD, WIDTH, HEIGHT, roadW);
            x += dx;
            dx += lines.get(n).getCurve();


            Color grass = (n / 3) % 2 == 0 ? new Color(16, 200, 16) : new Color(0, 154, 0);
            Color rumble = (n / 3) % 2 == 0 ? new Color(255, 255, 255) : new Color(0, 0, 0);
            Color road = (n / 3) % 2 == 0 ? new Color(107, 107, 107) : new Color(105, 105, 105);

            Line p = lines.get(Math.abs(n - 1)); //previous line

//            drawQuad(g2d, grass, 0, (int)p.getYb(), WIDTH, 0, (int)l.getYb(), WIDTH);
//            drawQuad(g2d, rumble, (int)p.getXb(), (int)p.getYb(), (int)(p.W*1.2f), (int)l.getXb(), (int)l.getYb(), (int)(l.W*1.2f));
//            drawQuad(g2d, road,  (int)p.getXb(), (int)p.getYb(), (int)p.getW(), (int)l.getXb(), (int)l.getYb(), (int)l.getW());

            drawQuad(g2d, grass, 0, Math.round(p.getYb()), WIDTH, 0, Math.round(l.getYb()), WIDTH);
            drawQuad(g2d, rumble, Math.round(p.getXb()), Math.round(p.getYb()), Math.round((p.W * 1.2f)), Math.round(l.getXb()), Math.round(l.getYb()), Math.round((l.W * 1.2f)));
            drawQuad(g2d, road, Math.round(p.getXb()), Math.round(p.getYb()), Math.round(p.getW()), Math.round(l.getXb()), Math.round(l.getYb()), Math.round(l.getW()));
        }
    }


    private void drawQuad(Graphics2D g2d, Color color, int x1, int y1, int w1, int x2, int y2, int w2) {
        g2d.setColor(color);
        if(y1>HEIGHT/2&&y2>HEIGHT/2)
        g2d.fillPolygon(new int[]{x1 - w1, x2 - w2, x2 + w2, x1 + w1}, new int[]{y1, y2, y2, y1}, 4);
    }

    public static void main(String... strings) {
        JComponent bg = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Picture.draw(g, "C:/Developing/CodingMath/resources/bg.png", 0, 0 , OutrunGame.WIDTH, OutrunGame.HEIGHT/2);
//                g.fillRect(0, 0, WIDTH, HEIGHT + 8);
            }
        };
        bg.setPreferredSize(new Dimension(WIDTH, HEIGHT/2+8));
        JFrame frame = new JFrame("Outrun Game");
        JPanel panel = new OutrunGame();
        panel.setFocusable(true);

        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);


        frame.add(panel);
        panel.add(bg);
        frame.setVisible(true);
        panel.requestFocusInWindow();
    }
}
