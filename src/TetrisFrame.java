import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TetrisFrame extends JFrame implements ActionListener, BoardListener {
    private Board board;

    public TetrisFrame(Board board) throws HeadlessException {
        this.setLayout(new BorderLayout());
        TetrisComponent tetrisComponent = new TetrisComponent(board);
        this.add(tetrisComponent);
        pack();
        setVisible(true);
//        Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("A Menu");
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        JMenuItem menuStop = new JMenuItem("Stop");
        menu.add(menuStop);
        menuStop.addActionListener(this);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    @Override
    public void actionPerformed(final java.awt.event.ActionEvent e) {
        if (e.getActionCommand().equals("Stop")) System.exit(0);
    }

    @Override
    public void boardChanged() {
        ArrayList fulls= findFullRow();
        for (Object full : fulls) {
            System.out.println(full);
        }
        repaint();
    }

    private ArrayList<Integer> findFullRow() {
        ArrayList<Integer> fullRows = new ArrayList<Integer>();
        if (board!=null){
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                if (board.getSquare(x, y) == SquareType.EMPTY) {
                    continue;
                } else {
                    fullRows.add(y);
                }
            }
        }}
        return fullRows;
    }
}
