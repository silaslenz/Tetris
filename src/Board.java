import javax.swing.*;
import java.awt.event.ActionEvent;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private SquareType[][] squares;
    private int width;
    private int height;
    private Poly falling;
    private int fallingXPosition;
    private int fallingYPosition;
    private List<BoardListener> boardListeners = new ArrayList<BoardListener>();
    private Random random = new Random();
    private boolean gameOver = false;

    public Board(final int width, final int height) {
        this.squares = new SquareType[width][height];
        this.width = width;
        this.height = height;

        for (int x = 0; x < squares.length; x++) {
            for (int y = 0; y < squares[0].length; y++) {
                if (y == 0 || y == height - 1 || x == 0 || x == width - 1) {
                    squares[x][y] = SquareType.OUTSIDE;
                } else {
                    squares[x][y] = SquareType.EMPTY;
                }
            }
        }
    }

    public void tick() {
        if (gameOver) return;
        else if (getFalling() == null) {
//
            setFalling(2, width / 2, 0);
            if (canFitPolyOnNext("down")) addFalling();
            else gameOver = true;
        } else if (getFalling().getClass() == Poly.class) {
            if (canFitPolyOnNext("down")) {
                relocateFalling(fallingXPosition, fallingYPosition + 1);
                addFalling();
            } else {
                falling = null;
            }
        }
    }

    private boolean canFitPolyOnNext(String direction) {
        if (getFalling()==null){
            return false;
        }
        removeFalling();

        if (direction.equals("down")) {

	    fallingYPosition += 1;
            for (int x = 0; x < falling.getSquareType().length; x++) {
                for (int y = 0; y < falling.getSquareType()[0].length; y++) {
                    if ((((fallingXPosition + x) < 0 || fallingXPosition + x > getWidth() - 1 || (fallingYPosition + y) >= height) || ((squares[fallingXPosition + x][fallingYPosition + y] != SquareType.EMPTY && (fallingYPosition + y) > 3))) && falling.getSquareType()[x][y] != SquareType.EMPTY) {
                        //Readd falling on old position and deny movement
			fallingYPosition -= 1;
                        addFalling();
                        return false;
                    }
                }
            }

            //Let requester move its poly
	    fallingYPosition -= 1;
            return true;
        } else if (direction.equals("left")) {
	    fallingXPosition -= 1;
            for (int x = 0; x < falling.getSquareType().length; x++) {
                for (int y = 0; y < falling.getSquareType()[0].length; y++) {
                    if (((fallingXPosition + x) < 0 || fallingXPosition + x > getWidth() - 1 || ((squares[fallingXPosition + x][fallingYPosition + y] != SquareType.EMPTY ))) && falling.getSquareType()[x][y] != SquareType.EMPTY) {
                        //Readd falling on old position and deny movement
			fallingXPosition += 1;
                        addFalling();
                        return false;
                    }
                }
            }
            //Let requester move its poly
	    fallingXPosition += 1;
            return true;
        } else if (direction.equals("right")) {
	    fallingXPosition += 1;
            for (int x = 0; x < falling.getSquareType().length; x++) {
                for (int y = 0; y < falling.getSquareType()[0].length; y++) {
                    if (((fallingXPosition + x) < 0 || fallingXPosition + x > getWidth() - 1 || ((squares[fallingXPosition + x][fallingYPosition + y] != SquareType.EMPTY ))) && falling.getSquareType()[x][y] != SquareType.EMPTY) {
                        //Readd falling on old position and deny movement
			fallingXPosition -= 1;
                        addFalling();
                        return false;
                    }
                }
            }
            //Let requester move its poly
	    fallingXPosition -= 1;
            return true;
        } else {
            throw new InvalidParameterException();
        }
    }

    public void moveLeft() {
        if (canFitPolyOnNext("left")) {
            relocateFalling(fallingXPosition - 1, fallingYPosition);
            addFalling();

        }
    }

    public void moveRight() {
        if (canFitPolyOnNext("right")) {
            relocateFalling(fallingXPosition + 1, fallingYPosition);
            addFalling();
        }
    }
    public void rotate() {

    }

    public void addBoardListener(BoardListener bl) {
        boardListeners.add(bl);
    }

    private void notifyListeners() {
        for (BoardListener boardListener : boardListeners) {
            boardListener.boardChanged();
        }
    }

    public Poly getFalling() {
        return this.falling;
    }

    public int getFallingXPosition() {
        return fallingXPosition;
    }

    public int getFallingYPosition() {
        return fallingYPosition;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setFalling(int type, int fallingXPosition, int fallingYPosition) {
        this.fallingXPosition = fallingXPosition;
        this.fallingYPosition = fallingYPosition;
        falling = TetrominoMaker.getPoly(type);
        notifyListeners();
    }

    public void relocateFalling(int fallingXPosition, int fallingYPosition) {
        removeFalling();
        this.fallingXPosition = fallingXPosition;
        this.fallingYPosition = fallingYPosition;
    }

    public SquareType getSquare(int x, int y) {
        return squares[x][y];
    }

    private void removeFalling() {
        SquareType[][] squaresBackup = squares;
        boolean spaceWasEmpty = false;
        if (getFalling() != null) {
            for (int x = 0; x < falling.getSquareType().length; x++) {
                for (int y = 0; y < falling.getSquareType()[0].length; y++) {
                    if (falling.getSquareType()[x][y] != SquareType.EMPTY && squares[fallingXPosition + x][fallingYPosition + y] == falling.getSquareType()[x][y]) {
                        squares[fallingXPosition + x][fallingYPosition + y] = SquareType.EMPTY;

                    } else {
                        spaceWasEmpty = true;
                    }
                }
            }
            if (spaceWasEmpty) {
//            System.out.println("space was empty, could not remove tetromino");
                squares = squaresBackup;
            }
            notifyListeners();
        }
    }

    public void addFalling() {
        SquareType[][] squaresBackup = squares;
        boolean spaceWasOccupied = false;
        for (int x = 0; x < falling.getSquareType().length; x++) {
            for (int y = 0; y < falling.getSquareType()[0].length; y++) {
                if (falling.getSquareType()[x][y] != SquareType.EMPTY && squares[fallingXPosition + x][fallingYPosition + y] == SquareType.EMPTY) {
                    squares[fallingXPosition + x][fallingYPosition + y] = falling.getSquareType()[x][y];

                }
//                else if (fallingYPosition > 10) {
//                    spaceWasOccupied = true;
//                }
            }
        }
        if (spaceWasOccupied) {
            System.out.println("space was occupied, could not place tetromino");
            squares = squaresBackup;
        }
        notifyListeners();
    }

    public void fillWithRandom() {
        Random random = new Random();
        for (int x = 0; x < squares.length; x++) {
            for (int y = 0; y < squares[0].length; y++) {
                if (y == 0 || y == height - 1 || x == 0 || x == width - 1) {
                    squares[x][y] = SquareType.OUTSIDE;
                } else {
                    squares[x][y] = SquareType.values()[random.nextInt(7)];
                }
            }
        }
        notifyListeners();
    }

    class LeftAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            System.out.println("test");
        }
    }
}
