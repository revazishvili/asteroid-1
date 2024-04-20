// AsteroidGame.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AsteroidGame extends JPanel implements ActionListener {

    private Timer timer;
    private ArrayList<Asteroid> asteroids;
    private Ship ship;
    private ArrayList<Bullet> bullets;
    private Random random;
    private int score;
    private Clip explosionClip;
    private Clip shootingClip;

    public AsteroidGame() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                ship.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                ship.keyReleased(e);
            }
        });
        setFocusable(true);

        asteroids = new ArrayList<>();
        ship = new Ship(this);
        bullets = new ArrayList<>();
        random = new Random();
        score = 0;

        timer = new Timer(16, this);
        timer.start();

        loadSounds();
    }

    private void loadSounds() {
        try {
            AudioInputStream explosionStream = AudioSystem.getAudioInputStream(getClass().getResource("explosion.wav"));
            explosionClip = AudioSystem.getClip();
            explosionClip.open(explosionStream);

            AudioInputStream shootingStream = AudioSystem.getAudioInputStream(getClass().getResource("shooting.wav"));
            shootingClip = AudioSystem.getClip();
            shootingClip.open(shootingStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw background
        drawBackground(g);
        // Draw asteroids
        for (Asteroid asteroid : asteroids) {
            asteroid.draw(g);
        }
        // Draw bullets
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
        // Draw player ship
        ship.draw(g);
        // Draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 20);
    }

    private void drawBackground(Graphics g) {
        // Draw stars
        g.setColor(Color.WHITE);
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(getWidth());
            int y = random.nextInt(getHeight());
            g.fillRect(x, y, 2, 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ship.move();
        for (Asteroid asteroid : asteroids) {
            asteroid.move();
        }
        for (Bullet bullet : bullets) {
            bullet.move();
        }
        asteroids.removeIf(asteroid -> asteroid.isOutOfBounds());
        bullets.removeIf(bullet -> bullet.isOutOfBounds());
        handleCollisions();
        spawnAsteroid();
        repaint();
    }

    private void handleCollisions() {
        ArrayList<Asteroid> asteroidsToRemove = new ArrayList<>();
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();

        for (Asteroid asteroid : new ArrayList<>(asteroids)) {
            for (Bullet bullet : new ArrayList<>(bullets)) {
                if (asteroid.intersects(bullet)) {
                    asteroidsToRemove.add(asteroid);
                    bulletsToRemove.add(bullet);
                    score += 10;
                    playExplosionSound();
                    break;
                }
            }
        }

        asteroids.removeAll(asteroidsToRemove);
        bullets.removeAll(bulletsToRemove);
    }

    private void playExplosionSound() {
        try {
            if (explosionClip != null) {
                explosionClip.stop();
                explosionClip.setFramePosition(0);
                explosionClip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void spawnAsteroid() {
        if (random.nextInt(100) < 2) {
            int x = random.nextInt(800);
            int y = 0;
            int size = 30 + random.nextInt(40);
            int speed = 1 + random.nextInt(3);
            asteroids.add(new Asteroid(x, y, size, speed));
        }
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void playShootingSound() {
        try {
            if (shootingClip != null) {
                shootingClip.stop();
                shootingClip.setFramePosition(0);
                shootingClip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Asteroid Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        AsteroidGame game = new AsteroidGame();
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
