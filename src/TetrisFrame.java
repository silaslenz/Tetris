import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TetrisFrame extends JFrame implements ActionListener, BoardListener
{
    private Board board;

    public TetrisFrame(Board board) throws HeadlessException {
	this.board = board;
	this.setLayout(new BorderLayout());
	TetrisComponent tetrisComponent = new TetrisComponent(board);
	this.add(tetrisComponent);
	pack();
	setVisible(true);
//        Menu
	JMenuBar menuBar = new JMenuBar();
	JMenu menu = new JMenu("A Menu");
	menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
	JMenuItem menuStop = new JMenuItem("Stop");
	menu.add(menuStop);
	menuStop.addActionListener(this);
	menuBar.add(menu);
	setJMenuBar(menuBar);
	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    @Override public void actionPerformed(final ActionEvent e) {
	if (e.getActionCommand().equals("Stop")) System.exit(0);
    }

    @Override public void boardChanged() {
//	System.out.println(board);
	board.removeFullRows();

	repaint();
    }


}
