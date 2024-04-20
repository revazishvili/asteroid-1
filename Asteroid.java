// Asteroid.java
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Asteroid {
    private int x, y, size, speed;

    public Asteroid(int x, int y, int size, int speed) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed = speed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, size, size);
    }

    public void move() {
        y += speed;
    }

    public boolean isOutOfBounds() {
        return y > 600;
    }

    public boolean intersects(Bullet bullet) {
        Ellipse2D.Double asteroidShape = new Ellipse2D.Double(x, y, size, size);
        Rectangle bulletRect = new Rectangle(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
        return asteroidShape.intersects(bulletRect);
    }
}
