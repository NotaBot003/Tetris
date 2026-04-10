public class IPiece extends Block {
        
    @Override
    public char[][] getGrid(char[][] grid) {
        grid = drawErase(grid, true);
        return grid;
    }
    private char[][] move(char[][] grid, int dirX, int dirY) {
        drawErase(grid, false);
        if (inBounds(grid, dirX, dirY)) {
            x += dirX;
            y += dirY;
        }

        drawErase(grid, true);
        return grid;
    }

    @Override
    public char[][] moveLeft(char[][] grid) {
        move(grid, -1, 0);
        return grid;
    }

    @Override
    public char[][] moveRight(char[][] grid) {
        move(grid, 1, 0);
        return grid;
    }
    
    @Override
    public char[][] moveDown(char[][] grid) {
        move(grid, 0, 1);
        return grid;
    }

    @Override
    public char[][] hardDrop(char[][] grid) {
        int oldY;
        do {
            oldY = y;
            grid = moveDown(grid);
        } while (y != oldY);
        return grid;
    }

    private boolean inRotationBounds(int r, char[][] grid) {
        if (r == 90 && grid[y + 1][x] == 'b' && grid[y - 1][x] == 'b' && grid[y + 2][x] == 'b') {
            return true;
        } else if (r == 180 && grid[y][x - 1] == 'b' && grid[y][x - 2] == 'b' && grid[y][x + 1] == 'b') {
            return true;
        } else if (r == 270 && grid[y - 1][x] == 'b' && grid[y - 2][x] == 'b' && grid[y + 1][x] == 'b') {
            return true;
        } else if (r == 0 && grid[y][x + 1] == 'b' && grid[y][x + 2] == 'b' && grid[y][x - 1] == 'b') {
            return true;
        }
        return false;
    }

    @Override
    public char[][] rotateClockwise(char[][] grid) {
        grid = drawErase(grid, false);
        
        switch (rotation) {
            case 0:
                x += 1;
                if (inRotationBounds(90, grid)) {
                    rotation = 90;
                } else {
                    x -= 1;
                }   break;
            case 90:
                y += 1;
                if (inRotationBounds(180, grid)) {
                    rotation = 180;
                } else {
                    y -= 1;
                }   break;
            case 180:
                x -= 1;
                if (inRotationBounds(270, grid)) {
                    rotation = 270;
                } else {
                    x += 1;
                }   break;
            case 270:
                y -= 1;
                if (inRotationBounds(0, grid)) {
                    rotation = 0;
                } else {
                    y += 1;
                }   break;
            default:
                break;
        }
        
        grid = drawErase(grid, true);
        return(grid);
    }

    @Override
    public char[][] rotateCounterClockwise(char[][] grid) {
        grid = drawErase(grid, false);
        
        switch (rotation) {
            case 0:
                y += 1;
                if (inRotationBounds(270, grid)) {
                    rotation = 270;
                } else {
                    y -= 1;
                }   break;
            case 90:
                x -= 1;
                if (inRotationBounds(0, grid)) {
                    rotation = 0;
                } else {
                    x += 1;
                }   break;
            case 180:
                y -= 1;
                if (inRotationBounds(90, grid)) {
                    rotation = 90;
                } else {
                    y += 1;
                }   break;
            case 270:
                x += 1;
                if (inRotationBounds(180, grid)) {
                    rotation = 180;
                } else {
                    x -= 1;
                }   break;
            default:
                break;
        }
        
        grid = drawErase(grid, true);
        return(grid);
    }

    @Override
    public char[][] rotate180(char[][] grid) {
        grid = drawErase(grid, false);
        
        switch (rotation) {
            case 0:
                y += 1;
                x += 1;
                if (inRotationBounds(180, grid)) {
                    rotation = 180;
                } else {
                    y -= 1;
                    x -= 1;
                }   break;
            case 90:
                x -= 1;
                y += 1;
                if (inRotationBounds(270, grid)) {
                    rotation = 270;
                } else {
                    x += 1;
                    y -= 1;
                }   break;
            case 180:
                y -= 1;
                x -= 1;
                if (inRotationBounds(0, grid)) {
                    rotation = 0;
                } else {    
                    y += 1;
                    x += 1;
                }   break;
            case 270:
                x += 1;
                y -= 1;
                if (inRotationBounds(90, grid)) {
                    rotation = 90;
                } else {
                    x -= 1;
                    y += 1;
                }   break;
            default:
                break;
        }
        
        grid = drawErase(grid, true);
        return(grid);
    }

    private boolean inBounds(char[][] grid, int dirX, int dirY) {
        y += dirY;
        x += dirX;

        if (rotation == 0 
            && grid[y][x] == 'b'
            && grid[y][x - 1] == 'b'
            && grid[y][x + 1] == 'b'
            && grid[y][x + 2] == 'b') {
            x -= dirX;
            y -= dirY;
            return true;
        } else if (rotation == 90
            && grid[y][x] == 'b'
            && grid[y - 1][x] == 'b'
            && grid[y + 1][x] == 'b'
            && grid[y + 2][x] == 'b') {
            x -= dirX;
            y -= dirY;
            return true;
        } else if (rotation == 180
            && grid[y][x] == 'b'
            && grid[y][x - 1] == 'b'
            && grid[y][x - 2] == 'b'
            && grid[y][x + 1] == 'b') {
            x -= dirX;
            y -= dirY;
            return true;
        } else if (rotation == 270
            && grid[y][x] == 'b'
            && grid[y - 1][x] == 'b'
            && grid[y - 2][x] == 'b'
            && grid[y + 1][x] == 'b') {
            x -= dirX;
            y -= dirY;
            return true;
        } 
        y -= dirY;
        x -= dirX;
        return false;
    }

    private char[][] drawErase(char[][] grid, boolean isColored) {
        char draw = (isColored) ? 'i' : 'b';

        switch (rotation) {
            case 0 -> {
                grid[y][x] = draw;
                grid[y][x - 1] = draw;
                grid[y][x + 1] = draw;
                grid[y][x + 2] = draw;
            }
            case 90 -> {
                grid[y][x] = draw;
                grid[y - 1][x] = draw;
                grid[y + 1][x] = draw;
                grid[y + 2][x] = draw;
            }
            case 180 -> {
                grid[y][x] = draw;
                grid[y][x - 1] = draw;
                grid[y][x - 2] = draw;
                grid[y][x + 1] = draw;
            }
            case 270 -> {
                grid[y][x] = draw;
                grid[y - 1][x] = draw;
                grid[y - 2][x] = draw;
                grid[y + 1][x] = draw;
            }
            default -> {
            }
        }
        return grid;
    }
}
