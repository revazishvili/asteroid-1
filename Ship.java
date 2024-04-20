// Ship.java
import java.awt.*;
import java.awt.event.KeyEvent;

public class Ship {
    private int x, y, width, height;
    private int speed;
    private boolean left, right, shooting;
    private AsteroidGame game;

    public Ship(AsteroidGame game) {
        this.game = game;
        x = 400;
        y = 500;
        width = 60; // Increase ship size
        height = 60; // Increase ship size
        speed = 5;
    }

    public void draw(Graphics g) {
        // Draw ship
        g.setColor(Color.GRAY);
        g.fillRect(x, y, width, height);
        // Draw ship wings
        g.setColor(Color.LIGHT_GRAY);
        g.fillPolygon(new int[]{x, x + width / 2, x + width}, new int[]{y + height, y, y + height}, 3);
    }

    public void move() {
        if (left) {
            x -= speed;
        }
        if (right) {
            x += speed;
        }
        x = Math.max(0, Math.min(x, 800 - width));
        if (shooting) {
            shoot();
        }
    }

    private void shoot() {
        int bulletX = x + width / 2 - 2;
        int bulletY = y - 10;
        Bullet bullet = new Bullet(bulletX, bulletY);
        game.getBullets().add(bullet);
        game.playShootingSound();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            shooting = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            shooting = false;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
