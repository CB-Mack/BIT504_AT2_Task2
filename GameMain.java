import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameMain extends JPanel implements MouseListener {
    // Constants for game
    public static final int ROWS = 3;
    public static final int COLS = 3;
    public static final String TITLE = "Tic Tac Toe";

    // Constants for dimensions used for drawing
    public static final int CELL_SIZE = 100;
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static final int SYMBOL_STROKE_WIDTH = 8;

    // Game object variables
    private Board board;
    private GameState currentState;
    private Player currentPlayer;
    private JLabel statusBar;

    /** Constructor to setup the UI and game components on the panel */
    public GameMain() {
        // Add event listener for mouse clicks
        addMouseListener(this);

        // Setup the status bar
        statusBar = new JLabel("         ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        statusBar.setOpaque(true);
        statusBar.setBackground(Color.LIGHT_GRAY);
        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));

        // Create a new instance of the game board
        board = new Board();

        // Initialize the game board
        initGame();
    }

    public static void main(String[] args) {
        // Run GUI code in Event Dispatch thread for thread safety.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create a main window to contain the panel
                JFrame frame = new JFrame(TITLE);

                // Create the new GameMain panel and add it to the frame
                GameMain gameMain = new GameMain();
                frame.add(gameMain);

                // Set the default close operation of the frame to exit_on_close
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    /** Custom painting codes on this JPanel */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);
        board.paint(g);

        if (currentState == GameState.Playing) {
            statusBar.setForeground(Color.BLACK);
            if (currentPlayer == Player.Cross) {
                statusBar.setText("X's Turn");
            } else {
                statusBar.setText("O's Turn");
            }
        } else if (currentState == GameState.Draw) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw ¯\\_(ツ)_/¯! Click to play again.");
        } else if (currentState == GameState.Cross_won) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Won ദ്ദി(•̀ ᴗ -)! Click to play again.");
        } else if (currentState == GameState.Nought_won) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'O' Won (ˆᗜˆ)! Click to play again.");
        }
    }

    /** Initialise the game-board contents and the current status of GameState and Player) */
    public void initGame() {
        board.clearBoard();
        currentState = GameState.Playing;
        currentPlayer = Player.Cross;
    }

    public void updateGame(Player thePlayer, int row, int col) {
        if (board.hasWon(thePlayer, row, col)) {
            if (thePlayer == Player.Cross) {
                currentState = GameState.Cross_won;
            } else {
                currentState = GameState.Nought_won;
            }
        } else if (board.isDraw()) {
            currentState = GameState.Draw;
        }
    }


    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        int rowSelected = mouseY / CELL_SIZE;
        int colSelected = mouseX / CELL_SIZE;

        if (currentState == GameState.Playing) {
            if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS
                    && board.cells[rowSelected][colSelected].content == Player.Empty) {
                board.cells[rowSelected][colSelected].content = currentPlayer;
                updateGame(currentPlayer, rowSelected, colSelected);
                currentPlayer = (currentPlayer == Player.Cross) ? Player.Nought : Player.Cross;
            }
        } else {
            initGame();
        }

        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
