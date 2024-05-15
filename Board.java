import java.awt.*;

public class Board {
    // grid line width
    public static final int GRID_WIDTH = 8;
    // grid line half width
    public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2;

    // 2D array of ROWS-by-COLS Cell instances
    Cell[][] cells;
	
	/** Constructor to create the game board */
    public Board() {
        cells = new Cell[GameMain.ROWS][GameMain.COLS]; // Initialize the cells array
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                cells[row][col] = new Cell(row, col); // Create Cell objects
            }
        }
    }
	

	 /** Return true if it is a draw (i.e., no more EMPTY cells) */ 
    public boolean isDraw() {
        for (int row = 0; row < GameMain.ROWS; row++) {
            for (int col = 0; col < GameMain.COLS; col++) {
                if (cells[row][col].content == Player.Empty) {
                    return false; // If any empty cell is found, game is not a draw
                }
            }
        }
        return true; // If no empty cells are found, game is a draw
    }
	
	/** Return true if the current player "thePlayer" has won after making their move  */
	public boolean hasWon(Player thePlayer, int playerRow, int playerCol) {
	    // Check if thePlayer has three in the same row
        for (int row = 0; row < GameMain.ROWS; row++) {
            boolean rowWin = true;
            for (int col = 0; col < GameMain.COLS; col++) {
                if (cells[row][col].content != thePlayer) {
                    rowWin = false;
                    break;
                }
            }
            if (rowWin) {
                return true;
            }
        }

	    // Check if thePlayer has three in the same column
        for (int col = 0; col < GameMain.COLS; col++) {
            boolean colWin = true;
            for (int row = 0; row < GameMain.ROWS; row++) {
                if (cells[row][col].content != thePlayer) {
                    colWin = false;
                    break;
                }
            }
            if (colWin) {
                return true;
            }
        }

	    // Check if thePlayer has three in the main diagonal
        boolean diagonalWin = true;
        for (int i = 0; i < GameMain.ROWS; i++) {
            if (cells[i][i].content != thePlayer) {
                diagonalWin = false;
                break;
            }
        }
        if (diagonalWin) {
            return true;
        }

	    // Check if thePlayer has three in the other diagonal
        boolean reverseDiagonalWin = true;
        for (int i = 0; i < GameMain.ROWS; i++) {
            if (cells[i][GameMain.COLS - 1 - i].content != thePlayer) {
                reverseDiagonalWin = false;
                break;
            }
        }
        if (reverseDiagonalWin) {
            return true;
        }

	    // If none of the above conditions are met, thePlayer has not won
	    return false;
	}
	
	/**
	 * Draws the grid (rows then columns) using constant sizes, then call on the
	 * Cells to paint themselves into the grid
	 */
    public void paint(Graphics g) {
        // draw the grid
        g.setColor(Color.gray);
        for (int row = 1; row < GameMain.ROWS; ++row) {
            g.fillRoundRect(0, GameMain.CELL_SIZE * row - GRID_WIDHT_HALF,
                    GameMain.CANVAS_WIDTH - 1, GRID_WIDTH,
                    GRID_WIDTH, GRID_WIDTH);
        }
        for (int col = 1; col < GameMain.COLS; ++col) {
            g.fillRoundRect(GameMain.CELL_SIZE * col - GRID_WIDHT_HALF, 0,
                    GRID_WIDTH, GameMain.CANVAS_HEIGHT - 1,
                    GRID_WIDTH, GRID_WIDTH);
        }

        // Draw the cells
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                cells[row][col].paint(g);
            }
        }
    }

    /** Clear the content of all cells in the board */
    public void clearBoard() {
        for (int row = 0; row < GameMain.ROWS; row++) {
            for (int col = 0; col < GameMain.COLS; col++) {
                cells[row][col].clear(); // Reset cell content to Empty
            }
        }
    }

}
