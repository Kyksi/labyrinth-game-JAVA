
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;

public class Lab_v2 extends Applet implements ActionListener, KeyListener, Runnable {

    AudioClip ac1, ac2, ac3, ac4, ac5, ac6, ac7, ac8, ac9;
    Thread CollisionChecker = null;
    BufferedImage backbuffer;
    int x = 22;
    int y = 542;
    int yVelocity = 0;
    int xVelocity = 0;
    int SpeedXangry1 = 3, SpeedYangry1 = 0, SpeedYangry2 = 3, SpeedXangry3 = -3, SpeedYangry3 = 0, SpeedXangry4 = 0, SpeedYangry4 = 3, SpeedXangry5 = 3, SpeedYangry5 = 0;
    int XAngry1 = 832, YAngry1 = 307, XAngry2 = 506, YAngry2 = 323, XAngry3 = 1107, YAngry3 = 493, XAngry4 = 325, YAngry4 = 17, XAngry5 = 232, YAngry5 = 159;
    int hp = 100;
    int min = 0;
    int sec = 0;
    int millis = 0;
    int stars = 0;
    boolean started, CzasStart, zvezda1 = true, zvezda2 = true, zvezda3 = true, zvezda4 = true, zvezda5 = true, open = false;
    Timer time;
    Image smile, finish, tlo, zvezda, zycie, angry;
    Font font = new Font("Arial", Font.BOLD, 17);
    Font font1 = new Font("Arial", Font.BOLD, 22);
    Color color1, color2, color3, color4;
    Color blue = new Color(63, 72, 204);

    public void init() {
        ac1 = getAudioClip(getCodeBase(), "audio1.WAV");
        ac2 = getAudioClip(getCodeBase(), "audio2.WAV");
        ac3 = getAudioClip(getCodeBase(), "audio3.WAV");
        ac4 = getAudioClip(getCodeBase(), "audio4.WAV");
        ac5 = getAudioClip(getCodeBase(), "audio5.WAV");
        ac6 = getAudioClip(getCodeBase(), "audio6.WAV");
        ac7 = getAudioClip(getCodeBase(), "audio7.WAV");
        ac8 = getAudioClip(getCodeBase(), "audio8.WAV");
        ac9 = getAudioClip(getCodeBase(), "audio9.WAV");

        tlo = getImage(getCodeBase(), "tlo1.png");
        smile = getImage(getCodeBase(), "smile.png");
        finish = getImage(getCodeBase(), "finish.png");
        zvezda = getImage(getCodeBase(), "zvezda.jpg");
        angry = getImage(getCodeBase(), "angry.jpg");
        zycie = getImage(getCodeBase(), "zycie.jpg");

        MediaTracker mediaTracker = new MediaTracker(this);
        mediaTracker.addImage(tlo, 0);
        mediaTracker.addImage(smile, 0);
        mediaTracker.addImage(finish, 0);
        mediaTracker.addImage(zvezda, 0);
        mediaTracker.addImage(angry, 0);
        mediaTracker.addImage(zycie, 0);

        try {
            mediaTracker.waitForID(0);
        } catch (InterruptedException ie) {
            return;
        }

        resize(tlo.getWidth(this), tlo.getHeight(this));
        backbuffer = new BufferedImage(tlo.getWidth(this), tlo.getHeight(this), BufferedImage.TYPE_INT_RGB);
        backbuffer.getGraphics().drawImage(tlo, 0, 0, this);

        time = new Timer(5, this);

        this.addKeyListener(this);
        this.setFocusable(true);

        ac2.play();
    }

    public void start() {
        if (CollisionChecker == null) {
            CollisionChecker = new Thread(this);
            CollisionChecker.start();
        }
    }

    public void run() {
        Graphics g;
        g = getGraphics();
        while (CollisionChecker != null) {
            try {
                paint(g);
                CheckTheCollision();
                CollisionChecker.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }

    public void paint(Graphics g) {
        Color back = new Color(153, 217, 234);
        setBackground(back);
        showStatus("Java labirynt v.2.0                                                                                                                                                                                                                                                                                                                                  Created by NSemeniuk");
        if (started) {
            g.drawImage(backbuffer, 0, 0, this);
            g.drawImage(finish, 1000, 20, this);
            stars(g);
            angry(g);
            finish(g);
            g.drawImage(smile, x, y, this);
            PaintHP(g);
            czas(g);
        } else {
            g.setFont(font1);
            g.drawString("Java labirynt v 2.0", tlo.getWidth(this) / 2 - 70, tlo.getHeight(this) / 2 - 100);
            g.drawString("Press 'S' to start game", tlo.getWidth(this) / 2 - 100, tlo.getHeight(this) / 2 - 60);
        }
    }

    public void finish(Graphics g) {
        if (1000 < getX() && getX() < 1120 && 20 < getY()+10 && getY()+10 < 100) {
            started = false;
            CzasStart = false;
            time.stop();
            g.setColor(Color.black);
            g.fillRect(tlo.getWidth(this) / 2 - 200, tlo.getHeight(this) / 2 - 150, 420, 200);
            g.setColor(Color.green);
            g.setFont(font1);
            g.drawString("!!!  YOU ARE WIN  !!!", tlo.getWidth(this) / 2 - 60, tlo.getHeight(this) / 2 - 80);
            g.drawString("Congratulations, my friend", tlo.getWidth(this) / 2 - 130, tlo.getHeight(this) / 2 - 40);
            g.setFont(font);
            g.drawString("(if you want try again, press 'ENTER')", tlo.getWidth(this) / 2 - 140, tlo.getHeight(this) / 2 - 10);
            ac6.stop();
            ac7.play();
        }
    }

    public void stars(Graphics g) {
        if (zvezda1) {
            g.drawImage(zvezda, 700, 472, this);
        }
        if (zvezda2) {
            g.drawImage(zvezda, 15, 115, this);
        }
        if (zvezda3) {
            g.drawImage(zvezda, 655, 20, this);
        }
        if (zvezda4) {
            g.drawImage(zvezda, 1042, 456, this);
        }
        if (zvezda5) {
            g.drawImage(zvezda, 927, 218, this);
        }
        g.drawImage(zycie, 327, 275, this);

        if (getX() + 8 > 700 && getX() < 725 && getY() + 8 > 472 && getY() + 8 < 497) {
            zvezda1 = false;
            stars = 1;
            ac9.play();
        }
        if (getX() + 8 > 15 && getX() < 40 && getY() + 8 > 115 && getY() + 8 < 140) {
            zvezda2 = false;
            stars = 3;
            ac9.play();
        }
        if (getX() + 8 > 655 && getX() < 680 && getY() + 8 > 20 && getY() + 8 < 45) {
            zvezda3 = false;
            stars = 4;
            ac9.play();
        }
        if (getX() + 8 > 1042 && getX() < 1067 && getY() + 8 > 456 && getY() + 8 < 481) {
            zvezda4 = false;
            stars = 2;
            ac9.play();
        }
        if (getX() + 8 > 927 && getX() < 953 && getY() + 8 > 218 && getY() + 8 < 243) {
            zvezda5 = false;
            stars = 5;
            ac9.play();
        }

        if (open == false) {
            g.setColor(blue);
            g.fillRect(1086, 114, 53, 5);
        }
        if (stars == 5) {
            open = true;
        }
    }

    public void PaintHP(Graphics g) {
        if (hp > 40) {
            g.setColor(Color.green);
        } else if (hp <= 40) {
            g.setColor(Color.red);
        }
        g.fillRect(20, 20, hp * 2, 15);
        if (hp <= 0) {
            Death(g);
        }
        AngryDeath();
        g.setColor(Color.black);
        g.setFont(font);
        g.drawString("hp: " + hp + " %", 20, 59);
        g.drawString("Stars: " + stars + "/5", 20, 90);
    }

    public void czas(Graphics g) {
        if (CzasStart) {
            millis = millis + 1;
        }
        if (millis == 60) {
            sec = sec + 1;
            millis = 0;
        }
        if (sec == 60) {
            min = min + 1;
            sec = 0;
        }
        if (min < 10) {
            g.drawString("0" + min, 150, 59);
        } else if (min >= 10) {
            g.drawString("" + min, 150, 59);
        }
        if (sec < 10) {
            g.drawString(":0" + sec, 168, 59);
        } else if (sec >= 10) {
            g.drawString(":" + sec, 168, 59);
        }
        if (millis < 10) {
            g.drawString(":0" + millis, 191, 59);
        } else if (millis >= 10) {
            g.drawString(":" + millis, 191, 59);
        }
    }

    public void paintPause(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(tlo.getWidth(this) / 2 - 170, tlo.getHeight(this) / 2 - 120, 370, 120);
        g.setColor(Color.green);
        g.setFont(font1);
        g.drawString("Game Pause", tlo.getWidth(this) / 2 - 50, tlo.getHeight(this) / 2 - 78);
        g.drawString("Press 'S' to return", tlo.getWidth(this) / 2 - 80, tlo.getHeight(this) / 2 - 38);
        ac6.stop();
    }

    public void Death(Graphics g) {
        ac3.play();
        started = false;
        CzasStart = false;
        time.stop();
        g.setColor(Color.black);
        g.fillRect(tlo.getWidth(this) / 2 - 200, tlo.getHeight(this) / 2 - 150, 420, 170);
        g.setColor(Color.green);
        g.setFont(font1);
        g.drawString("GAME OVER", tlo.getWidth(this) / 2 - 60, tlo.getHeight(this) / 2 - 80);
        g.drawString("If you want try again, press 'ENTER'", tlo.getWidth(this) / 2 - 170, tlo.getHeight(this) / 2 - 40);
        ac6.stop();
    }

    public void angry(Graphics g) {
        if (XAngry1 >= 1110) {
            SpeedXangry1 = -3;
        }
        if (XAngry1 <= 831 && YAngry1 == 307) {
            SpeedXangry1 = 0;
            SpeedYangry1 = -3;
            XAngry1 = 832;
        }
        if (YAngry1 <= 55) {
            SpeedYangry1 = 3;
        }
        if (YAngry1 > 307) {
            SpeedYangry1 = 0;
            SpeedXangry1 = 3;
            YAngry1 = 307;
        }
        XAngry1 += SpeedXangry1;
        YAngry1 += SpeedYangry1;
        g.drawImage(angry, XAngry1, YAngry1, this);

        if (YAngry2 <= 270) {
            SpeedYangry2 = 3;
        }
        if (YAngry2 >= 548) {
            SpeedYangry2 = -3;
        }
        YAngry2 += SpeedYangry2;
        g.drawImage(angry, XAngry2, YAngry2, this);

        if (XAngry3 <= 782) {
            SpeedXangry3 = 0;
            SpeedYangry3 = -3;
            XAngry3 = 783;
        }
        if (XAngry3 >= 1115) {
            SpeedXangry3 = -3;
        }
        if (YAngry3 <= 427) {
            SpeedYangry3 = 3;
        }
        if (YAngry3 >= 494) {
            SpeedYangry3 = 0;
            SpeedXangry3 = 3;
            YAngry3 = 493;
        }
        XAngry3 += SpeedXangry3;
        YAngry3 += SpeedYangry3;
        g.drawImage(angry, XAngry3, YAngry3, this);

        if (YAngry4 > 128) {
            SpeedYangry4 = 0;
            SpeedXangry4 = 3;
        }
        if (XAngry4 > 654) {
            SpeedXangry4 = -3;
            YAngry4 = 128;
        }
        if (XAngry4 < 325) {
            SpeedXangry4 = 0;
            SpeedYangry4 = -3;
        }
        if (YAngry4 <= 12) {
            SpeedYangry4 = 3;
            XAngry4 = 325;
        }
        XAngry4 += SpeedXangry4;
        YAngry4 += SpeedYangry4;
        g.drawImage(angry, XAngry4, YAngry4, this);
        
        if(XAngry5 >= 356){
            SpeedXangry5 = 0;
            SpeedYangry5 = 3;
        }
        if(YAngry5 >= 240){
            SpeedXangry5 = -3;
            SpeedYangry5 = 0;
        }
        if(XAngry5 < 232){
            SpeedXangry5 = 0;
            SpeedYangry5 = -3;
        }
        if(YAngry5 < 159){
            SpeedXangry5 = 3;
            SpeedYangry5 = 0;
            YAngry5 = 159;
        }
        XAngry5 += SpeedXangry5;
        YAngry5 += SpeedYangry5;
        g.drawImage(angry, XAngry5, YAngry5, this);
    }

    public void AngryDeath() {
        if (getX() + 8 > XAngry1 && getX() < XAngry1 + 20 && getY() + 8 > YAngry1 && getY() + 8 < YAngry1 + 20) {
            hp = 0;
        }
        if (getX() + 8 > XAngry2 && getX() < XAngry2 + 20 && getY() + 8 > YAngry2 && getY() + 8 < YAngry2 + 20) {
            hp = 0;
        }
        if (getX() + 8 > XAngry3 && getX() < XAngry3 + 20 && getY() + 8 > YAngry3 && getY() + 8 < YAngry3 + 20) {
            hp = 0;
        }
        if (getX() + 8 > XAngry4 && getX() < XAngry4 + 20 && getY() + 8 > YAngry4 && getY() + 8 < YAngry4 + 20) {
            hp = 0;
        }
        if (getX() + 8 > XAngry5 && getX() < XAngry5 + 20 && getY() + 8 > YAngry5 && getY() + 8 < YAngry5 + 20) {
            hp = 0;
        }
        if (getX() + 8 > 327 && getX() < 347 && getY() + 8 > 275 && getY() + 8 < 295) {
            hp = 100;
            ac8.play();
        }
    }

    public void update() {
        y = y + yVelocity;
        x = x + xVelocity;
    }

    public void actionPerformed(ActionEvent e) {
        update();
    }

    public void setYVelocity(int speedY) {
        yVelocity = 3 * speedY;
    }

    public void setXVelocity(int speedX) {
        xVelocity = 3 * speedX;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void keyPressed(KeyEvent e) {
        Graphics g;
        g = getGraphics();
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            setYVelocity(-1);
            setXVelocity(0);
            ac4.play();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            setYVelocity(1);
            setXVelocity(0);
            ac4.play();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            setYVelocity(0);
            setXVelocity(-1);
            ac4.play();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            setYVelocity(0);
            setXVelocity(1);
            ac4.play();
        }
        if (e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
            started = true;
            CzasStart = true;
            time.start();
            ac6.play();
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            CzasStart = false;
            started = false;
            time.stop();
            paintPause(g);
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            hp = 100;
            x = 22;
            y = 542;
            millis = 0;
            sec = 0;
            min = 0;
            stars = 0;
            zvezda1 = true;
            zvezda2 = true;
            zvezda3 = true;
            zvezda4 = true;
            zvezda5 = true;
            open = false;
            CzasStart = true;
            started = true;
            time.start();
            ac5.play();
            ac6.play();
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            setYVelocity(0);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            setXVelocity(0);
        }
        ac4.stop();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void CheckTheCollision() {

        color1 = new Color(backbuffer.getRGB(getX() + 5, getY() - 2), true);
        color2 = new Color(backbuffer.getRGB(getX() + 22, getY() + 5), true);
        color3 = new Color(backbuffer.getRGB(getX() - 2, getY() + 5), true);
        color4 = new Color(backbuffer.getRGB(getX() + 5, getY() + 22), true);

        int red1 = color1.getRed();
        int green1 = color1.getGreen();
        int blue1 = color1.getBlue();

        int red2 = color2.getRed();
        int green2 = color2.getGreen();
        int blue2 = color2.getBlue();

        int red3 = color3.getRed();
        int green3 = color3.getGreen();
        int blue3 = color3.getBlue();

        int red4 = color4.getRed();
        int green4 = color4.getGreen();
        int blue4 = color4.getBlue();

        if (red1 != 153 && green1 != 217 && blue1 != 234) {
            y = getY() + 5;
            hp = hp - 5;
            ac1.play();
        }
        if (red2 != 153 && green2 != 217 && blue2 != 234) {
            x = getX() - 5;
            hp = hp - 5;
            ac1.play();
        }
        if (red3 != 153 && green3 != 217 && blue3 != 234) {
            x = getX() + 5;
            hp = hp - 5;
            ac1.play();
        }
        if (red4 != 153 && green4 != 217 && blue4 != 234) {
            y = getY() - 5;
            hp = hp - 5;
            ac1.play();
        }
    }
}