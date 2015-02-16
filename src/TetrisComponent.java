import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.EnumMap;

public class TetrisComponent extends JComponent
{
    private static int RECT_WIDTH;
    private Board board;
//    public TetrisComponent(Board board, EnumMap<SquareType, Color> colorMap) {
//        this.board = board;
//        this.colorMap = colorMap;
//    }

    private EnumMap<SquareType, Color> colorMap;

    public TetrisComponent(Board board) {
	this.board = board;
	Action leftAction = new LeftAction();

	leftAction = new LeftAction();
	this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "doMoveLeft");
	this.getActionMap().put("doMoveLeft", leftAction);
	Action rightAction = new RightAction();

	rightAction = new RightAction();
	this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "doMoveRight");
	this.getActionMap().put("doMoveRight", rightAction);
    }

    public void drawAgain() {

	repaint();
    }

    @Override public Dimension getPreferredSize() {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	double width = screenSize.getWidth();
	RECT_WIDTH = Math.min(500 / board.getWidth(), (int) ((screenSize.getHeight() - 100) / board.getHeight()));
	System.out.println(RECT_WIDTH);
	return new Dimension(RECT_WIDTH * board.getWidth(), (int) screenSize.getHeight() - 100);
    }

    @Override protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	final Graphics2D g2d = (Graphics2D) g;
	g2d.setColor(Color.blue);
	for (int y = 0; y < board.getHeight(); y++) {
	    for (int x = 0; x < board.getWidth(); x++) {
		switch (board.getSquare(x, y)) {

		    case EMPTY:
			g2d.setColor(Color.WHITE);
			g2d.fillRect(x * RECT_WIDTH, y * RECT_WIDTH, RECT_WIDTH, RECT_WIDTH);
			break;
		    case I:
			g2d.setColor(Color.CYAN);
			g2d.fillRect(x * RECT_WIDTH, y * RECT_WIDTH, RECT_WIDTH, RECT_WIDTH);
			break;
		    case O:
			g2d.setColor(Color.GRAY);
			g2d.fillRect(x * RECT_WIDTH, y * RECT_WIDTH, RECT_WIDTH, RECT_WIDTH);
			break;
		    case T:
			g2d.setColor(Color.YELLOW);
			g2d.fillRect(x * RECT_WIDTH, y * RECT_WIDTH, RECT_WIDTH, RECT_WIDTH);
			break;
		    case S:
			g2d.setColor(Color.RED);
			g2d.fillRect(x * RECT_WIDTH, y * RECT_WIDTH, RECT_WIDTH, RECT_WIDTH);
			break;
		    case Z:
			g2d.setColor(Color.GREEN);
			g2d.fillRect(x * RECT_WIDTH, y * RECT_WIDTH, RECT_WIDTH, RECT_WIDTH);
			break;
		    case J:
			g2d.setColor(Color.blue);
			g2d.fillRect(x * RECT_WIDTH, y * RECT_WIDTH, RECT_WIDTH, RECT_WIDTH);
			break;
		    case L:
			g2d.setColor(Color.magenta);
			g2d.fillRect(x * RECT_WIDTH, y * RECT_WIDTH, RECT_WIDTH, RECT_WIDTH);
			break;
		    case OUTSIDE:
			g2d.setColor(Color.black);
			g2d.fillRect(x * RECT_WIDTH, y * RECT_WIDTH, RECT_WIDTH, RECT_WIDTH);
			break;
		}
	    }
	}

    }


    private ArrayList<Integer> findFullRow() {
	ArrayList<Integer> fullRows = new ArrayList<Integer>();
	System.out.println(board.getHeight());
	for (int y = 0; y < board.getHeight(); y++) {
	    System.out.printf("row");
	    for (int x = 0; x < board.getWidth(); x++) {
		if (board.getSquare(x, y) == SquareType.EMPTY) {
		    System.out.println("askjjj");
		} else {
		    fullRows.add(y);
		}
	    }
	}
	return fullRows;

    }

    class LeftAction extends AbstractAction
    {
	public void actionPerformed(ActionEvent e) {
	    board.moveLeft();
	}
    }

    class RightAction extends AbstractAction
    {
	public void actionPerformed(ActionEvent e) {
	    board.moveRight();
	}
    }
}
