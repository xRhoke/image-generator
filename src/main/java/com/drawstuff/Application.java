package com.drawstuff;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Application extends JPanel{

    private static JPanel canvas;
    private static JFrame frame;
    private static JButton drawButton;
    private static JButton exportButton;
    private static JPanel buttonPanel;

    //Image meta data
    private static int imageCount = 0;
    private static int fireScore;
    private static int earthScore;
    private static int waterScore;
    private static int darkScore;
    private static int lightScore;

    //Collection meta data
    private static int maxFire = 0;
    private static int maxEarth = 0;
    private static int maxWater = 0;
    private static int maxDark = 0;
    private static int maxLight = 0;

    public static void main(String[] args) {
        frame = new JFrame("Super Sweet NFT");
        canvas = new Application();
        buttonPanel = new JPanel();
        drawButton = new JButton("Draw");
        exportButton = new JButton("Export");

        canvas.setSize(new Dimension(500, 500));
        canvas.setBackground(Color.BLACK);

        buttonPanel.add(drawButton);
        buttonPanel.add(exportButton);

        drawButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                canvas.repaint();
            }
        });

        exportButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                exportImages(100);
            }
        });

        frame.pack();
        frame.setSize(new Dimension(500, 500));
        frame.setLayout(new BorderLayout());
        frame.add(canvas);
        frame.add(buttonPanel);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        generateImage(g, 10, 8);
    }

    private static Color generateRandomColor() {
        int max = 255;
        int min = 0;

        int redValue = getRandomNumber(min, max);
        int greenValue = getRandomNumber(min, max);
        int blueValue = getRandomNumber(min, max);

        Color randomColor = new Color(redValue, greenValue, blueValue);
        return randomColor;
    }

    private static void generateSquare(Graphics g, Color color, int xPos, int yPos, int squareSize){
        int width = squareSize;
        int height = squareSize;
        g.setColor(color);
        g.fillRect(xPos, yPos, width, height);
    }

    private static int getRandomNumber(int min, int max){
        return new Random().nextInt(max - min + 1) + min;
    }

    private static void generateImage(Graphics g, int squareSize, int chanceOfBlack){
        Color[] colors = buildColors(chanceOfBlack);
        fireScore = 0;
        earthScore = 0;
        waterScore = 0;
        lightScore = 0;

        for (int i = 0; i < canvas.getWidth(); i++) {
            Color[] shuffledColors = shuffleColors(colors);
            Color color;
            for(int j = 0; j < canvas.getHeight()/2; j++){
                color = shuffledColors[getRandomNumber(0, shuffledColors.length - 1)];
                fireScore += color.getRed();
                waterScore += color.getBlue();
                earthScore += color.getGreen();
                darkScore += (255*3) - color.getRed() - color.getGreen() - color.getBlue();
                generateSquare(g, color, j * squareSize, i * squareSize, squareSize);
                generateSquare(g, color, canvas.getHeight() - (j + 1) * squareSize, i * squareSize, squareSize);
            }
            for (int j = canvas.getWidth()/2 - i; j > 0; j--) {
                generateSquare(g, Color.BLACK, j * squareSize, i * squareSize, squareSize);
                generateSquare(g, Color.BLACK, canvas.getHeight() - (j + 1) * squareSize, i * squareSize, squareSize);
            }
        }
        fireScore = fireScore/10000;
        earthScore = earthScore/10000;
        waterScore = waterScore/10000;
        darkScore = darkScore/100000;
        lightScore = fireScore+earthScore+waterScore;
        if(fireScore > maxFire) maxFire = fireScore;
        if(earthScore > maxEarth) maxEarth = earthScore;
        if(waterScore > maxWater) maxWater = waterScore;
        if(darkScore > maxDark) maxDark = darkScore;
        if(lightScore > maxLight) maxLight = lightScore;
    }

    private static Color[] shuffleColors(Color[] colors) {
        Color[] shuffledColors = new Color[colors.length];
        for (int j = 0; j < shuffledColors.length; j++) {
            shuffledColors[j] = colors[getRandomNumber(0, colors.length - 1)];
        }

        return shuffledColors;
    }

    private static Color[] buildColors(int chanceOfBlack) {
        Color[] colors = new Color[chanceOfBlack + 3];
        for (int i = 0; i < 3; i++) {
            colors[i] = generateRandomColor();
        }

        for (int i = 3; i < colors.length; i++) {
            colors[i] = Color.BLACK;
        }

        return colors;
    }

    private static void exportImages(int images){
        int i = 0;
        while (i < images) {
            BufferedImage image = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            canvas.printAll(g);
            g.dispose();
            try {
                ImageIO.write(image, "png", new File(String.format("./images/Rune_%s.png", imageCount)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.printf("Rune_%s created with Fire:%s, Earth:%s, Water:%s, Dark:%s, Light:%s\n", imageCount, fireScore, earthScore, waterScore, darkScore, lightScore);
            imageCount++;
            i++;
        }
        System.out.printf("\nMax Fire:%s\n" +
                "Max Earth:%s\n" +
                "Max Water:%s\n" +
                "Max Dark:%s\n" +
                "Max Light:%s\n",
                maxFire, maxEarth, maxWater, maxDark, maxLight);
    }
}
