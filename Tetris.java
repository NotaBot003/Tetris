// General Graphics
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Tetris {

    // Global variables
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    // Window and drawing variables
    public static JFrame window;
    public static Canvas canvas;
    public static BufferStrategy bufferStrategy;

    public static boolean running = true;

    // Stores all game images
    public static final HashMap<String, Image> images = new HashMap<>();

    // Key handling
    public static boolean leftAllowed = true;
    public static boolean rightAllowed = true;
    public static boolean upAllowed = true;
    public static boolean downAllowed = true;
    public static boolean RAllowed = true;

    // Create global variables HERE
    
    public static int squareSize;
    public static char[][] grid = new char[22][12];

    // piece queue

    public static Block piece;
    public static Queue q = new Queue();

    public static void main(String[] args) {
        initWindow();
        initInput();
        initBufferStrategy();
        loadFolderImages();

        initLevelGrid("level.txt");
        gameLoop();
        cleanup();
    }

    public static void initWindow() {
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        window = new JFrame("Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(canvas);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static void initInput() {
        canvas.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();

                if (code == KeyEvent.VK_LEFT)  onLeftPressed();
                if (code == KeyEvent.VK_RIGHT) onRightPressed();
                if (code == KeyEvent.VK_UP)    onUpPressed();
                if (code == KeyEvent.VK_DOWN)  onDownPressed();
                if (code == KeyEvent.VK_ENTER) onEnterPressed();
                if (code == KeyEvent.VK_Q)     onQPressed();
                if (code == KeyEvent.VK_R)     onRPressed();
                if (code == KeyEvent.VK_SPACE) onSpacePressed();
                if (code == KeyEvent.VK_SHIFT) onShiftPressed();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();

                if (code == KeyEvent.VK_LEFT)  onLeftReleased();
                if (code == KeyEvent.VK_RIGHT) onRightReleased();
                if (code == KeyEvent.VK_UP)    onUpReleased();
                if (code == KeyEvent.VK_DOWN)  onDownReleased();
                if (code == KeyEvent.VK_R)     onRReleased();
            }
        });

        canvas.setFocusable(true);
        canvas.requestFocus();
    }

    public static void onLeftReleased() {
        leftAllowed  = true;
    }

    public static void onRightReleased() {
        rightAllowed = true;
    }

    public static void onUpReleased() {
        upAllowed = true;
    }

    public static void onDownReleased() {
        downAllowed  = true;
    }

    public static void onRReleased() {
        RAllowed = true;
    }

    public static void initBufferStrategy() {
        canvas.createBufferStrategy(3); // triple buffering
        bufferStrategy = canvas.getBufferStrategy();
    }

    public static void loadFolderImages() {
        File folder = new File("images");

        for (File f : folder.listFiles()) {
            if (f.getName().endsWith(".png")) {
                try {
                    Image img = ImageIO.read(f);
                    String filename = f.getName();
                    int lastDotIndex = filename.lastIndexOf(".");
                    String key = filename.substring(0, lastDotIndex);
                    images.put(key, img);
                } catch (Exception e) {
                    System.out.println("Could not load: " + f.getName());
                }
            }
        }
    }


///////////////////////// LEVEL INITIALIZATION ////////////////////

    public static void initLevelGrid(String filename) {
        initDefaultGrid();
        initQueue();
    }

    // you may add helper methods below
    public static void initDefaultGrid() {
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 12; j++) {
                if (j == 0 || j == 11 || i == 21) {
                    grid[i][j] = 'n';
                } else {
                    grid[i][j] = 'b';
                }
            }
        }
    }

    public static void initQueue() {
        for (int i = 0; i < 6; i++) {
            q.step();
        }
        piece = new IPiece();
        grid = piece.getGrid(grid);
    }

///////////////////////// MAIN GAME LOOP ////////////////////

    public static void gameLoop() {
        while (running) {
            update();
            draw();

            // Simple ~60 FPS cap
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }


///////////////////////// GAME LOGIC UPDATES ////////////////////

    public static void update() {

    }

    public static void newPiece() {
        piece = new IPiece();
        grid = piece.getGrid(grid);
    }
    
    

///////////////////////////// DRAWING //////////////////////////

    public static void draw() {
        Graphics g = bufferStrategy.getDrawGraphics();

        // Clear the screen (black background)
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Draw anything else here later...
        drawGrid(g);



        
        // Must be the last two lines of code in this method
        g.dispose();
        bufferStrategy.show();
    }

    public static void drawGrid(Graphics g) {
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 11; j++) {
                String currentSquare;

                currentSquare = switch (grid[i][j]) {
                    case 'z' -> "Red48";
                    case 't' -> "Purple48";
                    case 'b' -> "Black48";
                    case 'j' -> "Blue48";
                    case 'i' -> "Cyan48";
                    default -> "none";
                };

                squareSize = 48;
                if (!currentSquare.equals("none") && !(currentSquare.equals("Black48") && i == 0)) {
                    g.drawImage(images.get(currentSquare), j * squareSize + (WIDTH / 2 - squareSize * 6), i * squareSize, null);
                }
            }
        }
    }

    public static void drawOverlay(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200)); // 128 ≈ 50% opacity
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }

    

///////////////////////////// KEY EVENT HANDLING //////////////////////////

    public static void onLeftPressed() {
        if (leftAllowed) {
            movement("left");
        }
        leftAllowed = false;
    }

    public static void onRightPressed() {
        if (rightAllowed) {
            movement("right");
        }
        rightAllowed = false;
    }

    public static void onUpPressed() {
        if (upAllowed) {
            piece.rotateCounterClockwise(grid);
        }
        upAllowed = false;
    }

    public static void onDownPressed() {
        if (downAllowed) {
            piece.rotateClockwise(grid);
        }
        downAllowed = false;
    }

    public static void onRPressed() {
        if (RAllowed) {
            piece.rotate180(grid);
        }
        RAllowed = false;
    }

    public static void onSpacePressed() {
        movement("HD");
        newPiece();
    }

    public static void onShiftPressed() {
        movement("HD");
    }

    public static void onEnterPressed() {
    }
    public static void onQPressed() {

    }

    public static void movement(String direction) {
        if (direction.equals("left")) {
            grid = piece.moveLeft(grid);
        } else if (direction.equals("right")) {
            grid = piece.moveRight(grid);
        } else if (direction.equals("HD")) {
            grid = piece.hardDrop(grid);
        }
        // for (int i = 0; i < 22; i++) {
        //     for (int j = 0; j < 12; j++) {
        //         System.out.print(grid[i][j]);
        //     }   
        //     System.out.println("here");
        // }
    }

///////////////////////////// GAME CLEAN UP //////////////////////////

    public static void cleanup() {
        window.dispose();
    }
}