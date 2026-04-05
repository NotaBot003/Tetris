public class TPiece extends Block {
    
    public char[][] getGrid(char[][] grid) {
        grid = drawErase(grid, true);
        return grid;
    }
    private char[][] move(char[][] grid, int direction) {
        drawErase(grid, false);
        if (inBounds(grid, direction)) {
            x += direction;
        }

        drawErase(grid, true);
        return grid;
    }
    public char[][] moveLeft(char[][] grid) {
        move(grid, -1);
        return grid;
    }
    public char[][] moveRight(char[][] grid) {
        move(grid, 1);
        return grid;
    }
    
    public void moveDown() {}
    public void softDrop() {}
    public void hardDrop() {}
    public char[][] rotateClockwise(char[][] grid) {
        grid = drawErase(grid, false);
        
        if (rotation == 0 && y < 20 && grid[y + 1][x] == 'b') {
            rotation = 90;
        } else if (rotation == 90 && x > 0 && grid[y][x - 1] == 'b') {
            rotation = 180;
        } else if (rotation == 180 && y > 0 && grid[y - 1][x] == 'b') {
            rotation = 270;
        } else if (rotation == 270 && x < 10 && grid[y][x + 1] == 'b') {
            rotation = 0;
        }
        
        grid = drawErase(grid, true);
        return(grid);
    }
    public void rotateCounterClockwise() {}
    public void rotate180() {}

    public boolean inBounds(char[][] grid, int dirX) {
        x += dirX;
        if (rotation == 0 
            && grid[y][x] == 'b'
            && grid[y - 1][x] == 'b'
            && grid[y][x - 1] == 'b'
            && grid[y][x + 1] == 'b') {
            x -= dirX;
            return true;
        } else if (rotation == 90
            && grid[y][x] == 'b'
            && grid[y - 1][x] == 'b'
            && grid[y + 1][x] == 'b'
            && grid[y][x + 1] == 'b') {
            x -= dirX;
            return true;
        } else if (rotation == 180
            && grid[y][x] == 'b'
            && grid[y + 1][x] == 'b'
            && grid[y][x - 1] == 'b'
            && grid[y][x + 1] == 'b') {
            x -= dirX;
            return true;
        } else if (rotation == 270
            && grid[y][x] == 'b'
            && grid[y - 1][x] == 'b'
            && grid[y + 1][x] == 'b'
            && grid[y][x - 1] == 'b') {
            x -= dirX;
            return true;
        } 
        x -= dirX;
        return false;
    }

    private char[][] drawErase(char[][] grid, boolean isColored) {
        char draw = (isColored) ? 'p' : 'b';

        if (rotation == 0) {
            grid[y][x] = draw;
            grid[y - 1][x] = draw;
            grid[y][x - 1] = draw;
            grid[y][x + 1] = draw;
        } else if (rotation == 90) {
            grid[y][x] = draw;
            grid[y - 1][x] = draw;
            grid[y + 1][x] = draw;
            grid[y][x + 1] = draw;
        } else if (rotation == 180) {
            grid[y][x] = draw;
            grid[y + 1][x] = draw;
            grid[y][x - 1] = draw;
            grid[y][x + 1] = draw;
        } else if (rotation == 270) {
            grid[y][x] = draw;
            grid[y - 1][x] = draw;
            grid[y + 1][x] = draw;
            grid[y][x - 1] = draw;
        }
        return grid;
    }
}