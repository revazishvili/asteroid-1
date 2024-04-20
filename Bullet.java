// Bullet.java
import java.awt.*;

public class Bullet {
    private int x, y, width, height;
    private int speed;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        width = 5;
        height = 10;
        speed = 5;
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, width, height);
    }

    public void move() {
        y -= speed;
    }

    public boolean isOutOfBounds() {
        return y < 0;
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
