/**
 * Created by silas on 1/26/15.
 */
public class TetrominoMaker {
    private static SquareType[][] createSquareArray(int sideLength) {
        SquareType[][] block = new SquareType[sideLength][sideLength];
        for (int x = 0; x < block.length; x++) {
            for (int y = 0; y < block[0].length; y++) {
                block[x][y] = SquareType.EMPTY;
            }
        }
        return block;
    }

    public static Poly getPoly(int n) {
        int width = 4;
        int height = 4;
        SquareType[][] block;
        System.out.println(SquareType.values()[n]);
        switch (SquareType.values()[n]) {
            case I:
                block = createSquareArray(4);
                block[1][0] = SquareType.L;
                block[1][1] = SquareType.L;
                block[1][2] = SquareType.L;
                block[1][3] = SquareType.L;
                break;
            case O:
                block = createSquareArray(2);
                block[0][0] = SquareType.O;
                block[0][1] = SquareType.O;
                block[1][0] = SquareType.O;
                block[1][1] = SquareType.O;
                break;
            case T:
                block = createSquareArray(3);
                block[1][0] = SquareType.T;
                block[0][1] = SquareType.T;
                block[1][1] = SquareType.T;
                block[2][1] = SquareType.T;
                break;
            case S:
                block = createSquareArray(3);
                block[1][0] = SquareType.S;
                block[2][0] = SquareType.S;
                block[0][1] = SquareType.S;
                block[1][1] = SquareType.S;
                break;
            case Z:
                block = createSquareArray(3);
                block[0][0] = SquareType.Z;
                block[1][0] = SquareType.Z;
                block[1][1] = SquareType.Z;
                block[2][1] = SquareType.Z;
                break;
            case J:
                block = createSquareArray(3);
                block[0][0] = SquareType.Z;
                block[1][0] = SquareType.Z;
                block[1][1] = SquareType.Z;
                block[2][1] = SquareType.Z;
                break;
            case L:
                block = createSquareArray(3);
                block[0][0] = SquareType.Z;
                block[1][0] = SquareType.Z;
                block[1][1] = SquareType.Z;
                block[2][1] = SquareType.Z;
                break;
            default:
                block = createSquareArray(0);
                break;
        }
        return new Poly(block);
    }

    public int getNumberOfTypes() {
        return SquareType.values().length - 2;
    }


}
