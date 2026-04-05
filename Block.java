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
    
    abstract void moveDown();
    abstract char[][] moveLeft(char[][] grid);
    abstract char[][] moveRight(char[][] grid);
    abstract void softDrop();
    abstract void hardDrop();
    abstract char[][] rotateClockwise(char[][] grid);
    abstract void rotateCounterClockwise();
    abstract void rotate180();
    abstract char[][] getGrid(char[][] grid);
}
