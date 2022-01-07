package org.cis120.FlappyBird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class FlappyBird extends JPanel {

    // instantiate all the variables necessary for the game
    private static final int FRAME = 800;
    private static final Color BACKGROUND = new Color(204, 204, 204);
    private BufferedImage myImage;
    private Graphics myBuffer;

    // the bird
    private Bird bd;
    private double fallSpeed = 1.2;

    // the pipes
    private ArrayList<MovingPipe> topPipes = new ArrayList<>();
    private ArrayList<MovingPipe> botPipes = new ArrayList<>();
    private Timer timer;

    // hardmode projectiles
    private Projectile prj;

    // pictures
    private ImageIcon[][] selections = new ImageIcon[2][3];

    // score
    private int score = 0;
    private int highScore = 0;

    // options for background and bird
    private int bgChoice;
    private int birdChoice;

    // game state
    private boolean paused = false;
    private boolean loadPrevious = false;
    private boolean loadPrevPipes = false;
    private boolean hardmode = false;

    // bg_#, bird_#, score, high score, bird init pos
    private ArrayList<Integer> savedParams = new ArrayList<>();
    private ArrayList<Integer> loadParams = new ArrayList<>();

    // top pipes, bot pipes
    private ArrayList<Integer> loadPipes = new ArrayList<>();

    public FlappyBird() {
        String file = "src\\main\\java\\org\\cis120\\FlappyBird\\saved_game.txt";
        String pipeFile = "src\\main\\java\\org\\cis120\\FlappyBird\\saved_pipes.txt";
        int params;
        String dataParams;
        String dataPipes;

        Object[] mode = { "Normal", "Expert" };
        int modeState = JOptionPane.showOptionDialog(
                null,
                "Normal or expert? Expert mode includes a " +
                        "projectile to dodge while simultaneously " +
                        "manuvering the pipes",
                "Mode",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, mode, mode[0]
        );
        if (modeState == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        } else if (modeState == 1) {
            hardmode = true;
        }

        Object[] games = { "New Game", "Load Previous Save" };
        int gameState = JOptionPane.showOptionDialog(
                null,
                "Start a New Game or Load the Previous Save", "Game State",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, games, games[0]
        );
        if (gameState == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        } else if (gameState == 1) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                params = Integer.valueOf(br.readLine());
                if (params == 1) { // indicate whether load is valid
                    loadPrevious = true; // indicate load
                    while ((dataParams = br.readLine()) != null) { // load in the previous save
                        loadParams.add(Integer.valueOf(dataParams));
                        System.out.println(dataParams);
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "No file found.");
                loadPrevious = false;
            }
            try (BufferedReader brPipes = new BufferedReader(new FileReader(pipeFile))) {
                loadPrevPipes = true; // indicate load
                while ((dataPipes = brPipes.readLine()) != null) { // load in the previous save
                    loadPipes.add(Integer.valueOf(dataPipes));
                    System.out.println(dataPipes);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "No file found.");
                loadPrevPipes = false;
            }
        }

        for (int m = 0; m < selections.length; m++) {
            for (int n = 0; n < selections[0].length; n++) {
                if (m == 0) {
                    selections[m][n] = new ImageIcon(
                            "src\\main\\java\\org\\cis120\\FlappyBird\\bg_" + n + ".png"
                    );
                } else {
                    selections[m][n] = new ImageIcon(
                            "src\\main\\java\\org\\cis120\\FlappyBird\\bird_" + n + ".png"
                    );
                }
            }
        }
        if (loadPrevious) {
            bgChoice = loadParams.get(0);
            birdChoice = loadParams.get(1);
            score = loadParams.get(2);
            highScore = loadParams.get(3);
        } else {
            // picking the background
            Object[] bg = { "Day Background", "Night Background", "Grassy Background" };
            bgChoice = JOptionPane.showOptionDialog(
                    null,
                    "Select a background", "Background Choice",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, bg, bg[0]
            );

            if (bgChoice == JOptionPane.CLOSED_OPTION) {
                System.exit(0);
            }

            // picking the color of the bird
            Object[] bird = { "Yellow Bird", "Blue Bird", "Red Bird" };
            birdChoice = JOptionPane.showOptionDialog(
                    null,
                    "Select a bird", "Bird Choice",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, bird, bird[0]
            );

            if (birdChoice == JOptionPane.CLOSED_OPTION) {
                System.exit(0);
            }
        }
        // save game params
        savedParams.add(bgChoice);
        savedParams.add(birdChoice);

        // drawing the objects
        myImage = new BufferedImage(FRAME, FRAME, BufferedImage.TYPE_INT_RGB);
        myBuffer = myImage.getGraphics();
        myBuffer.setColor(BACKGROUND);
        myBuffer.fillRect(0, 0, FRAME, FRAME);
        if (loadPrevious) {
            bd = new Bird(50, loadParams.get(4));
        } else {
            bd = new Bird(50, 400);
        }
        addKeyListener(new Key());
        addKeyListener(new Key());
        setFocusable(true);
        if (hardmode) {
            prj = new Projectile(800, 400, Color.RED);
        }
        // creating the pipes, with a random variation
        if (loadPrevPipes) {
            loadSavedPipes();
        } else {
            resetPipes();
        }
        timer = new Timer(10, new Listener());
        timer.start();
    }

    // the key press that allows the bird to "bounce" upwards
    private class Key extends KeyAdapter {
        ImageIcon pause = new ImageIcon("src\\main\\java\\org\\cis120\\FlappyBird\\pause.png");

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE && bd.getRadius() < bd.getY() && !paused) {
                bd.jump(25);
                fallSpeed = 1.2;
            }
            if (e.getKeyCode() == KeyEvent.VK_P) {
                paused = true;
                timer.stop();
                myBuffer.drawImage(pause.getImage(), 0, 100, 800, 500, null);
                repaint();
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE && paused) {
                // save and exit
                String filename = "src\\main\\java\\org\\cis120\\FlappyBird\\saved_game.txt";
                String pipesFilename = "src\\main\\java\\org\\cis120\\FlappyBird\\saved_pipes.txt";
                try {
                    savedParams.add(score);
                    savedParams.add(highScore);
                    savedParams.add((int) bd.getY());
                    FileWriter fw = new FileWriter(filename, false);
                    fw.write("1\n");
                    for (int i : savedParams) {
                        System.out.println("----" + i + "------");
                        fw.write(i + "\n");
                    }
                    fw.close();
                    FileWriter fwPipes = new FileWriter(pipesFilename, false);
                    for (int k = 0; k < topPipes.size(); k++) {
                        fwPipes.write(topPipes.get(k).getX() + "\n");
                        fwPipes.write(topPipes.get(k).getY() + "\n");
                        fwPipes.write(topPipes.get(k).getYWidth() + "\n");
                        fwPipes.write(botPipes.get(k).getX() + "\n");
                        fwPipes.write(botPipes.get(k).getY() + "\n");
                        fwPipes.write(botPipes.get(k).getYWidth() + "\n");
                    }
                    fwPipes.close();
                    System.exit(0);
                } catch (Exception E) {
                    JOptionPane.showMessageDialog(null, "No file found.");
                    System.exit(0);
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_R) {
                timer.start();
                paused = false;
            }
        }
    }

    // drawing the images
    public void paintComponent(Graphics g) {
        g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
    }

    private class Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            myBuffer.setColor(BACKGROUND);
            myBuffer.fillRect(0, 0, FRAME, FRAME);

            // background creation
            myBuffer.drawImage(selections[0][bgChoice].getImage(), 0, 0, 800, 800, null);
            // bd.draw(myBuffer);

            // bird creation
            myBuffer.drawImage(
                    selections[1][birdChoice].getImage(),
                    (int) (bd.getX() - 18), (int) (bd.getY() - 17), 35, 35, null
            );

            // code for the objects to move
            bd.move(fallSpeed);
            fallSpeed += .1;
            if (hardmode) {
                ImageIcon bullet = new ImageIcon(
                        "src\\main\\java\\org\\cis120\\FlappyBird\\bullet.png"
                );
                if (prj.getX() < 0 - prj.getDiameter()) {
                    prj.setX(800);
                }
                if (prj.getY() < 0) {
                    prj.setY(0);
                }
                if (prj.getY() > 800 - prj.getDiameter()) {
                    prj.setY(800 - prj.getDiameter());
                }
                prj.draw(myBuffer);
                myBuffer.drawImage(
                        bullet.getImage(),
                        (int) (prj.getX() - 15), (int) (prj.getY() - 15), 30, 30, null
                );
                prj.move(5);
            }
            for (int i = 0; i < topPipes.size(); i++) {
                topPipes.get(i).draw(myBuffer);
                botPipes.get(i).draw(myBuffer);
                topPipes.get(i).move();
                botPipes.get(i).move();
            }
            repaint();

            // what happens when you lose the game
            if ((bd.getY() > (FRAME - bd.getDiameter())) ||
                    topPipes.get(0).inPipe(bd) || botPipes.get(0).inPipe(bd)
                    || (hardmode && prj.inPrj(bd))) {
                timer.stop();
                // gives you an option dialog that you lost, shows you your score,
                // gives you option to quit or play again
                Object[] endGameOptions = { "Play Again", "Save High Score & Exit", "Quit" };
                int reply = JOptionPane.showOptionDialog(
                        null,
                        "You Lost. Your Score is " + score + " \n" +
                                "Your High Score is " + highScore,
                        "You lost.",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, endGameOptions, endGameOptions[0]
                );

                if (reply == JOptionPane.CLOSED_OPTION) {
                    System.exit(0);
                } else if (reply == 0) {
                    // reset pipes
                    resetPipes();
                    // resets the bird position and the score
                    bd.setX(50);
                    bd.setY(400);
                    score = 0;
                    fallSpeed = 1.2;
                    timer.start();
                } else if (reply == 1) {
                    // save and exit
                    String paramFilename =
                            "src\\main\\java\\org\\cis120\\FlappyBird\\saved_game.txt";
                    String pipesFilename =
                            "src\\main\\java\\org\\cis120\\FlappyBird\\saved_pipes.txt";
                    try {
                        savedParams.add(0); // score is 0 because loss
                        savedParams.add(highScore); // saves high score
                        savedParams.add(400); // default start position
                        FileWriter fw = new FileWriter(paramFilename, false);
                        fw.write("1\n");
                        for (int i : savedParams) {
                            System.out.println("----" + i + "------");
                            fw.write(i + "\n");
                        }
                        fw.close();
                        resetPipes();

                        FileWriter fwPipes = new FileWriter(pipesFilename, false);
                        for (int k = 0; k < topPipes.size(); k++) {
                            fwPipes.write(topPipes.get(k).getX() + "\n");
                            fwPipes.write(topPipes.get(k).getY() + "\n");
                            fwPipes.write(topPipes.get(k).getYWidth() + "\n");
                            fwPipes.write(botPipes.get(k).getX() + "\n");
                            fwPipes.write(botPipes.get(k).getY() + "\n");
                            fwPipes.write(botPipes.get(k).getYWidth() + "\n");
                        }
                        fwPipes.close();
                        System.exit(0);
                    } catch (Exception E) {
                        JOptionPane.showMessageDialog(null, "No file found.");
                        System.exit(0);
                    }
                } else {
                    // if you choose not to play
                    System.exit(0);
                }
            } else if (topPipes.get(0).getX() <= -75) {  // if you get past a pipe
                timer.stop();
                // increases score
                score++;
                if (score > highScore) {
                    highScore = score;
                }
                addTakePipe();
                timer.start();
            }
            // if the user picked a night background, the text is set to white so it can be
            // read easier
            if (bgChoice == 1) {
                myBuffer.setColor(Color.WHITE);
            } else { // if the user picked a day background, the text is set to black so it can be
                     // read easier
                myBuffer.setColor(Color.BLACK);
            }

            // draw the score
            myBuffer.setFont(new Font("Monospaced", Font.BOLD, 24));
            myBuffer.drawString("Score: " + score, FRAME - 150, 25);
            myBuffer.drawString("High Score: " + highScore, FRAME - 220, 50);
            repaint();
        }
    }

    private void addTakePipe() {
        int number = (int) (Math.random() * 7);
        MovingPipe newTop = null;
        MovingPipe newBot = null;

        switch (number) {
            case 0:
                newTop = new MovingPipe(1525, 0, 75, 25);
                newBot = new MovingPipe(725 + (2 * 400), 125, 75, 675);
                break;

            case 1:
                newTop = new MovingPipe(1525, 0, 75, 100);
                newBot = new MovingPipe(1525, 200, 75, 600);
                break;

            case 2:
                newTop = new MovingPipe(1525, 0, 75, 200);
                newBot = new MovingPipe(1525, 300, 75, 500);
                break;

            case 3:
                newTop = new MovingPipe(1525, 0, 75, 350);
                newBot = new MovingPipe(725 + (2 * 400), 450, 75, 350);
                break;

            case 4:
                newTop = new MovingPipe(1525, 0, 75, 500);
                newBot = new MovingPipe(1525, 600, 75, 200);
                break;

            case 5:
                newTop = new MovingPipe(1525, 0, 75, 600);
                newBot = new MovingPipe(1525, 700, 75, 100);
                break;

            case 6:
                newTop = new MovingPipe(1525, 0, 75, 675);
                newBot = new MovingPipe(1525, 775, 75, 25);
                break;

            default:
                System.out.println("error in pipe randomization");
                System.exit(0);
        }

        for (int i = 0; i < topPipes.size() - 1; i++) {
            topPipes.set(i, topPipes.get(i + 1));
            botPipes.set(i, botPipes.get(i + 1));
        }

        topPipes.set(topPipes.size() - 1, newTop);
        botPipes.set(botPipes.size() - 1, newBot);

    }

    private void loadSavedPipes() {
        // clear the pipes
        topPipes.clear();
        botPipes.clear();

        /////////////////////////////////// x, y, ywidth
        MovingPipe topPipe;
        MovingPipe botPipe;
        boolean top = true;

        for (int i = 0; i < 8; i++) {
            int[] topParams = new int[3];
            int[] botParams = new int[3];
            if (top) {
                for (int j = 0; j < 3; j++) {
                    topParams[j] = loadPipes.get(i * 3 + j);
                }
                topPipe = new MovingPipe(topParams[0], topParams[1], 75, topParams[2]);
                topPipes.add(topPipe);
                top = false;
            } else {
                for (int k = 0; k < 3; k++) {
                    botParams[k] = loadPipes.get(i * 3 + k);
                }
                botPipe = new MovingPipe(botParams[0], botParams[1], 75, botParams[2]);
                botPipes.add(botPipe);
                top = true;
            }
        }
    }

    private void resetPipes() {
        // clear the pipes
        topPipes.clear();
        botPipes.clear();

        MovingPipe topPipe;
        MovingPipe botPipe;

        for (int i = 0; i < 4; i++) {
            // instantiates new ones in random positions
            int number = (int) (Math.random() * 7);
            int xpos = 725 + (i * 400);

            switch (number) {
                case 0:
                    topPipe = new MovingPipe(xpos, 0, 75, 25);
                    botPipe = new MovingPipe(xpos, 125, 75, 675);
                    topPipes.add(topPipe);
                    botPipes.add(botPipe);
                    break;

                case 1:
                    topPipe = new MovingPipe(xpos, 0, 75, 100);
                    botPipe = new MovingPipe(xpos, 200, 75, 600);
                    topPipes.add(topPipe);
                    botPipes.add(botPipe);
                    break;

                case 2:
                    topPipe = new MovingPipe(xpos, 0, 75, 200);
                    botPipe = new MovingPipe(xpos, 300, 75, 500);
                    topPipes.add(topPipe);
                    botPipes.add(botPipe);
                    break;

                case 3:
                    topPipe = new MovingPipe(xpos, 0, 75, 350);
                    botPipe = new MovingPipe(xpos, 450, 75, 350);
                    topPipes.add(topPipe);
                    botPipes.add(botPipe);
                    break;

                case 4:
                    topPipe = new MovingPipe(xpos, 0, 75, 500);
                    botPipe = new MovingPipe(xpos, 600, 75, 200);
                    topPipes.add(topPipe);
                    botPipes.add(botPipe);
                    break;

                case 5:
                    topPipe = new MovingPipe(xpos, 0, 75, 600);
                    botPipe = new MovingPipe(xpos, 700, 75, 100);
                    topPipes.add(topPipe);
                    botPipes.add(botPipe);
                    break;

                case 6:
                    topPipe = new MovingPipe(xpos, 0, 75, 675);
                    botPipe = new MovingPipe(xpos, 775, 75, 25);
                    topPipes.add(topPipe);
                    botPipes.add(botPipe);
                    break;
                default:
                    System.out.println("error in pipe randomization");
                    System.exit(0);
            }
        }
    }
}
