import javax.swing.*;
import java.awt.event.ActionEvent;

public final class BoardTest
{
    private BoardTest() {
    }

    public static void main(String[] args) {
	final int height = 10;
	final int width = 8;
	final int timeBetween = 300;
	final Board board = new Board(width, height);
//        board.setFalling(3, 3, 3);
//        board.addFalling();

//        board.fillWithRandom();
	final TetrisFrame tetrisFrame = new TetrisFrame(board);
	board.addBoardListener(tetrisFrame);
	final Action doOneStep = new AbstractAction()
	{
	    public void actionPerformed(final ActionEvent e) {
		board.tick();
//                System.out.println(BoardToTextConverter.convertToText(board));
//                board.fillWithRandom();
	    }
	};

	final Timer clockTimer = new Timer(timeBetween, doOneStep);
	clockTimer.setCoalesce(true);
	clockTimer.start();
//        clockTimer.stop();
    }
}

