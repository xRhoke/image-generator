package com.drawstuff;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Application extends JPanel{

    private static JPanel canvas;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Super Sweet NFT");
        canvas = new Application();
        canvas.setPreferredSize(new Dimension(600, 620));
        canvas.setBackground(Color.black);

        frame.pack();
        frame.setSize(new Dimension(600, 630));
        frame.setContentPane(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        loopOverGrid(g);
    }

    private static void generateRandomShapes(Graphics g, int shapes) {
        for (int i = 0; i < shapes; i++) {
            if (getRandomNumber(0, 100) <= 80) generateCircle(g);
            else generateRectangle(g);
        }
    }

    private static Color generateRandomColor() {
        int max = 255;
        int min = 0;
        int redValue = getRandomNumber(min, max);
        int greenValue = getRandomNumber(min, max);
        int blueValue = getRandomNumber(min, max);

        return new Color(redValue, greenValue, blueValue);
    }

    private static void generateRectangle(Graphics g){
        int minWidth = 10;
        int maxWidth = canvas.getWidth()/2;

        int minHeight = 10;
        int maxHeight = canvas.getHeight()/2;

        int width = getRandomNumber(minWidth, maxWidth);
        int height = getRandomNumber(minHeight, maxHeight);

        int x = getRandomNumber(width, canvas.getWidth()) - width;
        int y = getRandomNumber(height, canvas.getHeight()) - height;

        g.setColor(generateRandomColor());
        g.fillRect(x, y, width, height);
    }

    private static void generateRectangle10x10(Graphics g, Color color, int x, int y){
        int width = 10;
        int height = 10;

        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    private static void generateCircle(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        int minWidth = 10;
        int maxWidth = canvas.getWidth()/4;

        int minHeight = 10;
        int maxHeight = canvas.getHeight()/4;

        int width = getRandomNumber(minWidth, maxWidth);
        int height = getRandomNumber(minHeight, maxHeight);

        int x = getRandomNumber(width, canvas.getWidth()) - width;
        int y = getRandomNumber(height, canvas.getHeight()) - height;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(generateRandomColor());
        g2.fillOval(x, y, width, height);
    }

    private static int getRandomNumber(int min, int max){
        return new Random().nextInt(max - min + 1) + min;
    }

    private static void loopOverGridSection(Graphics g, int startX, int startY){
        int colorSize = 9;
        Color[] colors = new Color[colorSize];
        for (int m = 0; m < 3; m++) {
            colors[m] = generateRandomColor();
        }

        for (int m = 3; m < colorSize; m++) {
            colors[m] = Color.BLACK;
        }
        Color color;

        for (int i = 0; i < 10; i++) {
            //shuffle colors
            Color[] shuffledColors = new Color[3];
            for (int j = 0; j < 3; j++) {
                shuffledColors[j] = colors[getRandomNumber(0, colorSize - 1)];
            }

            for (int j = 0; j < 10; j++) {
                if (j == 0 || j == 9) color = shuffledColors[0];
                else if (j == 1 || j == 8) color = shuffledColors[1];
                else if (j == 2 || j == 7) color = shuffledColors[2];
                else if (j == 3 || j == 6) color = shuffledColors[1];
                else if (j == 4 || j == 5) color = shuffledColors[0];
                else color = Color.BLACK;
                generateRectangle10x10(g, color, startX + j * 10, startY + i * 10);
            }
        }
    }

    private static void loopOverGrid(Graphics g){

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                loopOverGridSection(g,i * 120, j * 120);
            }
        }
    }

}
