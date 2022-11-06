import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Drawing extends JPanel {
    boolean game_running = true;
    BufferedImage[] player = new BufferedImage[8];
    BufferedImage[][] tileMap;
    int[][] tilen;
    int changeX = 0, changeY = 0;
    int playerX, playerY, playerIndex;
    int screenStart, screenEnd;
    int frameCount;
    int jumpCount = 0;
    JFrame main_frame;


    public Drawing(BufferedImage[][] tiles, JFrame frame, int[][] tilesn) throws IOException {
        loadPlayer();
        playerIndex = 0;
        tileMap = tiles;
        tilen = tilesn;
        main_frame = frame;
        restart();
        this.setBackground(new Color(90, 70, 40));
        frame.add(this);
        addKeyListener(new Custom());
        requestFocus();
        startGameLoop();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintTileMap(g);
        paintPlayer(g);
        frameCount++;
    }

    public void paintTileMap(Graphics g) {
        if (screenEnd >= 42) {
            screenEnd = 41;
            screenStart = 26;
        }
        else if (playerX * 50 <= 400){
            screenStart = 0;
            screenEnd = 15;
        }

        for (int y = 0; y <= 7; y++) {
            for (int x = screenStart; x <= screenEnd; x++) {
                g.drawImage(tileMap[y][x], (x - screenStart) * 50, (y + 3) * 50, null);
            }
        }
    }
    public void paintPlayer(Graphics g) {
        int xpos;
        if (screenEnd == 41) {
            xpos = (playerX - 26) * 50;
        } else {
            xpos = Math.min(playerX * 50, 400);
        }
        g.drawImage(player[playerIndex], xpos, playerY * 50, 50, 50, null);
        checkGravity();
    }

    private void startGameLoop() {
        new Thread(() -> {
            while (game_running) {
                checkWin();
                try {
                    Thread.sleep(17);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.repaint();
            }
        }).start();
    }

    public class Custom implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if (!checkCollision(playerY, playerX + 1)) {
                    screenStart += 1;
                    screenEnd += 1;
                    playerX += 1;
                    playerIndex = 1;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (!checkCollision(playerY, playerX - 1)) {
                    screenStart -= 1;
                    screenEnd -= 1;
                    playerX -= 1;
                    playerIndex = 3;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                if(jumpCount < 2){
                    frameCount = 1;
                    playerY -= 1;
                    jumpCount++;

                }
            }
            if (e.getKeyChar() == 101){
                endGame();
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
            playerIndex = 0;
        }
    }

        public void checkWin() {
            if (playerX == 41) {
                game_running = false;
                this.setVisible(false);
                main_frame.setVisible(true);

                JPanel end = new JPanel(null);
                end.setBackground(new Color(90,70,40));
                JLabel youWon = new JLabel("You WON!");
                youWon.setFont(new Font("Courier", Font.BOLD, 50));
                youWon.setForeground(Color.WHITE);
                youWon.setBounds(300,10,260,100);
                JButton next = new JButton("next");
                next.setFont(new Font("Courier", Font.BOLD, 50));
                next.setBackground(new Color(200,150,90));
                next.setBounds(300,400,200,100);

                end.add(youWon);
                end.add(next);
                end.setVisible(true);

                main_frame.add(end);

                next.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        end.setVisible(false);
                        endGame();
                    }
                });
            }
        }
        public void endGame(){
            this.setVisible(false);
            main_frame.revalidate();
            GameStart g = new GameStart();
            g.chooseLevel(main_frame);
        }

        public boolean checkCollision(int x, int y) {
        if(x < 3){
            x = 0;
        }
        else x -= 3;
        if (tileMap[x][y] != null) {
            return true;
        }
        else return false;
        }
        public void checkGravity() {
            if (frameCount % 15 == 0) {
                if ((playerY + 1) >= 11) {
                    restart();
                }
                else if (checkCollision(playerY+1,playerX)){
                    int x;
                    if(playerY+1 < 3){ x = 0;}
                    else { x = playerY -2; }
                    if(tilen[x][playerX] == 5){
                        jumpCount = 0;
                        restart();
                    }
                    jumpCount = 0;
                }
                else if ((!checkCollision(playerY + 1, playerX))) { //jei po kojom nieko nera
                    playerY += 1;                           //tai krenta po viena
                }

            }
        }

        public void restart(){
            playerX = 0;
            playerY = 5;
            screenStart = 0;
            screenEnd = 15;
            frameCount = 1;
        }

        public void loadPlayer() throws IOException{
            player[0] = ImageIO.read(new File("images\\player.png"));
            player[1] = ImageIO.read(new File("images\\player1.png")); //right
            player[2] = ImageIO.read(new File("images\\player2.png")); //right
            player[3] = ImageIO.read(new File("images\\player3.png")); //left
            player[4] = ImageIO.read(new File("images\\player4.png")); //left
        }

}


