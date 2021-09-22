package com.drawstuff;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Application extends JPanel{

    private static JPanel canvas;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Super Sweet NFT");
        canvas = new Application();
        canvas.setPreferredSize(new Dimension(400, 400));
        canvas.setBackground(generateRandomColor());

        frame.pack();
        frame.setSize(new Dimension(400, 400));
        frame.setContentPane(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        generateRandomShapes(g, getRandomNumber(5, 20));
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

}
