package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * This class store the real chess information.
 * The Chessboard has 8 * 8 cells, and each cell has a position for chess
 */
public class Chessboard {
    private final Cell[][] grid;
    StringBuilder sb;
    private int levelSelect;
    int step = 0;
    private int score = 0;
    private int stepLeft;
    int requiredScore;
    public Chessboard() {
        sb = new StringBuilder();
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];

        initGrid();
        initPieces();
        levelSelect = 1;
        score = Constant.getscoreNeedFromLevel(levelSelect);
        stepLeft = Constant.getStepMaxFromLevel(levelSelect);
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }
    public void printChessboard(){
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if(grid[i][j]!=null){
                    System.out.print(grid[i][j].getPiece().getName()+" ");
                }else{
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }
    public void initPieces() {
        Random rand = new Random();
        //String[] symbols = new String[]{"💎", "⚪", "▲", "🔶","★"};
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                String symbol;
                boolean hasConsecutivePieces;
                do {
                    symbol = Constant.symbols[rand.nextInt(Constant.symbols.length)];
                    hasConsecutivePieces = (j >= 2 && symbol.equals(grid[i][j - 1].getPiece().getName()) && symbol.equals(grid[i][j - 2].getPiece().getName()))
                            || (i >= 2 && symbol.equals(grid[i - 1][j].getPiece().getName()) && symbol.equals(grid[i - 2][j].getPiece().getName()));
                } while (hasConsecutivePieces);
                grid[i][j].setPiece(new ChessPiece(symbol));
            }
        }
    }
    private ChessPiece generateRandomPiece() {

        String randomPieceType = Util.RandomPick(Constant.symbols);
        // 创建对应类型的棋子对象
        return new ChessPiece(randomPieceType);
    }

    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    public Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }


    public ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        if (chessPiece != null)
            getGridAt(point).removePiece();//避免空引用
        return chessPiece;
    }
    public void removeAllChessPiece() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (grid[i][j].getPiece() != null)
                    grid[i][j].removePiece();
            }
        }
    }
    public void removePiecesOnBoard() {
        ChessboardPoint point;
        boolean[][] chessRemoveMark = new boolean[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()] ;
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                point = new ChessboardPoint(i, j);
                if (isMatch3(point))
                {
                    chessRemoveMark[i][j] = true;
                }
            }
        }
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (chessRemoveMark[i][j]) {
                    grid[i][j].removePiece();
                }
            }
        }
    }







    public boolean canRemove() {
        ChessboardPoint point;
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                point = new ChessboardPoint(i, j);
                if (isMatch3(point))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public int down() {
        int score = 0;
        for (int col = 0; col < Constant.CHESSBOARD_COL_SIZE.getNum(); col++) {
            int emptyCells = 0; // 记录当前列的空白单元格数量
            for (int row = Constant.CHESSBOARD_ROW_SIZE.getNum() - 1; row >= 0; row--) {
                ChessboardPoint currentPoint = new ChessboardPoint(row, col);
                ChessPiece currentPiece = getChessPieceAt(currentPoint);
                if (currentPiece == null) {
                    emptyCells++; // 如果当前位置为空，增加空白单元格数量
                } else if (emptyCells > 0) {
                    // 如果当前位置不为空且存在空白单元格
                    grid[row + emptyCells][col].setPiece(currentPiece);
                    grid[row][col].removePiece();
                }
            }
            score += (10*emptyCells);
        }
        return score;
    }


    public void generateNewPieces() {
        for (int col = 0; col < Constant.CHESSBOARD_COL_SIZE.getNum(); col++) {
            for (int row = 0; row < Constant.CHESSBOARD_ROW_SIZE.getNum(); row++) {
                if (grid[row][col].getPiece() == null) {
                    grid[row][col].setPiece(generateRandomPiece());
                }
            }
        }
    }
    public void generateNewPieces(int piece[][]) {
        for (int col = 0; col < Constant.CHESSBOARD_COL_SIZE.getNum(); col++) {
            for (int row = 0; row < Constant.CHESSBOARD_ROW_SIZE.getNum(); row++) {
                if (piece[row][col] == 0) {
                    grid[row][col].setPiece(null);
                }
                else
                {
                    grid[row][col].setPiece(new ChessPiece(Constant.symbols[piece[row][col]-1]));
                }
            }
        }
    }
    public void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }


    public void swapChessPiece(ChessboardPoint point1, ChessboardPoint point2) {
        ChessPiece chessPiece1 = removeChessPiece(point1);
        ChessPiece chessPiece2 = removeChessPiece(point2);
        setChessPiece(point1, chessPiece2);
        setChessPiece(point2, chessPiece1);
    }
    public boolean canSwap(ChessboardPoint point1, ChessboardPoint point2) {
        if (stepLeft == 0) return false;
        boolean canSwap = false;
        swapChessPiece(point1, point2);
        if (isMatch3(point1) || isMatch3(point2)) {
            canSwap = true;
            step++;
        }
        swapChessPiece(point1, point2);
        return canSwap;
    }

    private int computedLeftX(ChessboardPoint point) {
        if (point.getCol() == 0) return 0;
        int res = 0;
        ChessPiece currentPiece = grid[point.getRow()][point.getCol()].getPiece();
        for (int i = point.getCol() - 1; i >= 0; i--) {
            ChessPiece piece = grid[point.getRow()][i].getPiece();
            if (piece == null) continue;
            if (piece.getName().equals(currentPiece.getName())) {
                res++;
            } else {
                break;
            }
        }
        return res;
    }
    private int computedRightX(ChessboardPoint point) {
        int res = 0;
        if (point.getCol() == Constant.CHESSBOARD_COL_SIZE.getNum() - 1) return 0;
        ChessPiece currentPiece = grid[point.getRow()][point.getCol()].getPiece();
        for (int i = point.getCol() + 1; i < Constant.CHESSBOARD_COL_SIZE.getNum(); i++) {
            ChessPiece piece = grid[point.getRow()][i].getPiece();
            if (piece == null) continue;
            if (piece.getName().equals(currentPiece.getName())) {
                res++;
            } else {
                break;
            }
        }
        return res;
    }
    private int computedLeftY(ChessboardPoint point) {
        int res = 0;
        if (point.getRow() == 0) return 0;
        ChessPiece currentPiece = grid[point.getRow()][point.getCol()].getPiece();
        for (int i = point.getRow() - 1; i >= 0; i--) {
            ChessPiece piece = grid[i][point.getCol()].getPiece();
            if (piece == null) continue;
            if (piece.getName().equals(currentPiece.getName())) {
                res++;
            } else {
                break;
            }
        }
        return res;
    }
    private int computedRightY(ChessboardPoint point) {
        int res = 0;
        if (point.getRow() == Constant.CHESSBOARD_ROW_SIZE.getNum() - 1) return 0;
        ChessPiece currentPiece = grid[point.getRow()][point.getCol()].getPiece();
        for (int i = point.getRow() + 1; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            ChessPiece piece = grid[i][point.getCol()].getPiece();
            if (piece == null) continue;
            if (piece.getName().equals(currentPiece.getName())) {
                res++;
            } else {
                break;
            }
        }
        return res;
    }
    private boolean isMatch3(ChessboardPoint point)//判断点point x y 上是否符合3消
    {
        if (getChessPieceAt(point) == null) return false;
        if (computedRightX(point) + computedLeftX(point) >=2 ) return true;
        return computedRightY(point) + computedLeftY(point) >= 2;
    }
    public boolean isMatch4(ChessboardPoint point)//判断点point x y 上是否符合4消
    {
        if (getChessPieceAt(point) == null) return false;
        if (computedRightX(point) + computedLeftX(point) >=3 ) return true;
        if (computedRightY(point) + computedLeftY(point) >= 3) return true;
        if (computedRightX(point) + computedLeftX(point) >=2 && computedRightY(point) + computedLeftY(point) >= 2) return true;
        return false;
    }

    public boolean isMatch5(ChessboardPoint point)//判断点point x y 上是否符合5消
    {
        if (getChessPieceAt(point) == null) return false;
        if (computedRightX(point) + computedLeftX(point) >=4 ) return true;
        if (computedRightY(point) + computedLeftY(point) >= 4) return true;
        return false;
    }
    public Cell[][] getGrid() {
        return grid;
    }


    public List<String> convertBoardToList() {
        List<String> saveLines = new ArrayList<>();
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            sb.setLength(0);
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessPiece piece = grid[i][j].getPiece();
                if (piece != null) {
                    sb.append(Constant.PieceTypeMap.get(piece.getName()).intValue());
                    sb.append(" ");
                }
                else {
                    sb.append("0 ");
                }
            }
            saveLines.add(sb.toString());
        }
        saveLines.add(String.valueOf(levelSelect));
        saveLines.add(String.valueOf(score));
        saveLines.add(String.valueOf(stepLeft));
        sb.setLength(0);
        return saveLines;
    }

    public int getLevelSelect() {
        return levelSelect;
    }

    public void setLevelSelect(int levelSelect) {
        this.levelSelect = levelSelect;
    }

    public String getLabelText()
    {
        return "Level:"+levelSelect+"  Score:" + score+"  Steps Left:"+stepLeft;
    }

    public void setScore(int i) {
        score = i;
    }

    public void setStepLeft(int i) {
        stepLeft = i;
    }

    public int getStepLeft() {
        return stepLeft;
    }

    public void setRequiredScore(int i) {
        requiredScore = i;
    }

    public int getScore() {
        return score;
    }

    public int getRequiredScore() {
        return requiredScore;
    }

    public boolean needRefresh() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum() - 1; j++) {
                if(canSwap(new ChessboardPoint(i,j),new ChessboardPoint(i,j+1))) return false;
            }
        }
        for (int i = 0; i < Constant.CHESSBOARD_COL_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_ROW_SIZE.getNum() - 1; j++) {
                if(canSwap(new ChessboardPoint(j,i),new ChessboardPoint(j+1,i))) return false;
            }
        }
        return true;
    }

    public String getHintMessage() {
        if (needRefresh()) return "Hint:You need refresh the chessboard";
        ChessboardPoint hintPoint1 = new ChessboardPoint(0,0);
        ChessboardPoint hintPoint2 = new ChessboardPoint(0,0);
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum() - 1; j++) {
                if(canSwap(new ChessboardPoint(i,j),new ChessboardPoint(i,j+1)))
                {
                    hintPoint1 = new ChessboardPoint(i,j);
                    hintPoint2 = new ChessboardPoint(i,j+1);
                    break;
                }
            }
        }
        for (int i = 0; i < Constant.CHESSBOARD_COL_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_ROW_SIZE.getNum() - 1; j++) {
                if(canSwap(new ChessboardPoint(j,i),new ChessboardPoint(j+1,i)))
                {
                    hintPoint1 = new ChessboardPoint(j,i);
                    hintPoint2 = new ChessboardPoint(j+1,i);
                    break;
                }
            }
        }
        return String.format("Hint:You can swap (%d,%d) and (%d,%d)",hintPoint1.getCol() + 1,hintPoint1.getRow() +1,hintPoint2.getCol() +1,hintPoint2.getRow() + 1);
    }
    public void knockOut(ChessboardPoint point) {
        removeChessPiece(point);
        down();
        generateNewPieces();
    }
}
