abstract class Block {
    protected int x = 5;
    protected int y = 1;
    protected int rotation = 0;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    abstract char[][] moveDown(char[][] grid);
    abstract char[][] moveLeft(char[][] grid);
    abstract char[][] moveRight(char[][] grid);
    abstract char[][] hardDrop(char[][] grid);
    abstract char[][] rotateClockwise(char[][] grid);
    abstract char[][] rotateCounterClockwise(char[][] grid);
    abstract char[][] rotate180(char[][] grid);
    abstract char[][] getGrid(char[][] grid);
}
