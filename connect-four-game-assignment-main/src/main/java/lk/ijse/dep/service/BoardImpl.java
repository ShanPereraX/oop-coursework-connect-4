package lk.ijse.dep.service;

@SuppressWarnings("ALL")
public class BoardImpl implements Board {
    final Piece [] [] pieces;
    final BoardUI boardUI;

    public BoardImpl(BoardUI boardUI) {
        this.boardUI = boardUI;
        pieces = new Piece[NUM_OF_COLS][NUM_OF_ROWS];
        setArrayValues();

        System.out.println("Array initialized");
    }
    public void setArrayValues(){
        for (int i = 0; i<NUM_OF_COLS; i++){
            for (int j=0; j< NUM_OF_ROWS; j++){
                pieces[i][j] = Piece.EMPTY;
            }
        }
        System.out.println("Values set Piece.Empty");
    }

    @Override
    public boolean isLegalMove(int col) {

        System.out.println("in islegalMove method");
        for (int i = 0; i<NUM_OF_ROWS; i++ ){
            if(pieces[col][i].equals(Piece.EMPTY)) {
                System.out.println("legal move");
                return true;
            }
        }
        System.out.println("illegal move");
        return false;
    }

    @Override
    public int findNextAvailableSpot(int col) {
        System.out.println("in findNextAvailableSpot method");
        for (int i = 0; i<pieces[col].length; i++){
            if(pieces[col][i].equals(Piece.EMPTY)) return i;
        }
        return -1;
    }

    @Override
    public BoardUI getBoardUI() {
        return boardUI;
    }

    @Override
    public boolean existLegalMoves() {
        for (Piece [] elementGroup : pieces){
            for (Piece ele: elementGroup){
                if(ele.equals(Piece.EMPTY)) return false;
            }
        }
        return true;
    }

    @Override
    public void updateMove(int col, Piece move) {
        pieces[col][findNextAvailableSpot(col)] = move;
        boardUI.update(col, !move.equals(Piece.GREEN));
    }

    @Override
    public void updateMove(int col, int row, Piece move) {

    }

    @Override
    public void findWinner() {
        for (int i = 0; i < 5; i++) {
            int countHuman = 0, countAi = 0;
            for (int j = 0; j < 4; j++) {
                switch(pieces[j][i]){
                    case BLUE:countHuman++; break;
                    case GREEN: countAi++; break;
                    default:break;
                }
            }
            if (countHuman == 4) {
                boardUI.notifyWinner(new Winner(Piece.BLUE, 0, i, 3, i));
                break;
            }
            else if (countAi == 4) {
                boardUI.notifyWinner(new Winner(Piece.GREEN, 0, i, 3, i));
                break;
            }else if(countAi!=4&countAi!=4& existLegalMoves()){
                boardUI.notifyWinner(new Winner(Piece.EMPTY));
            }
            else {
                for (int x = 0; x < 5; x++) {
                    int countHuman1 = 0, countAi1 = 0;
                    for (int y = 1; y < 5; y++) {
                        switch(pieces[y][x]){
                            case BLUE:countHuman1++; break;
                            case GREEN: countAi1++; break;
                            default:break;
                        }
                    }
                    if (countHuman1 == 4 ) {
                        boardUI.notifyWinner(new Winner(Piece.BLUE, 1, x, 4, x));
                        break;
                    } else if (countAi1 == 4 ) {
                        boardUI.notifyWinner(new Winner(Piece.GREEN, 1, x, 4, x));
                        break;
                    }else if(countAi!=4&countAi!=4& existLegalMoves()){
                        boardUI.notifyWinner(new Winner(Piece.EMPTY));
                        break;
                    }
                    else {
                        for (int m = 0; m < 5; m++) {
                            int countHuman11 = 0, countAi11 = 0;
                            for (int l = 2; l < 6; l++) {
                                switch(pieces[l][m]){
                                    case BLUE:countHuman11++; break;
                                    case GREEN: countAi11++; break;
                                    default:break;
                                }
                            }
                            if (patternMethod(2, 5, m, m, countHuman11, countAi11)) break;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 6; i++) {
            int countHuman = 0, countAi = 0;
            for (int j = 0; j < 4; j++) {
                switch(pieces[i][j]){
                    case BLUE:countHuman++; break;
                    case GREEN: countAi++; break;
                    default:break;
                }
                if (countHuman == 4) {
                    boardUI.notifyWinner(new Winner(Piece.BLUE, i, 0, i, 3));
                    break;
                } else if (countAi == 4) {
                    boardUI.notifyWinner(new Winner(Piece.GREEN, i, 0, i, 3));
                    break;
                } else if(countAi!=4&countAi!=4& existLegalMoves()){
                    boardUI.notifyWinner(new Winner(Piece.EMPTY));
                }
                else {
                    for (int x = 0; x < 6; x++) {
                        int countHuman1 = 0, countAi1 = 0;
                        for (int y = 1; y < 5; y++) {
                            switch(pieces[x][y]){
                                case BLUE:countHuman1++; break;
                                case GREEN: countAi1++; break;
                                default:break;
                            }
                            if (patternMethod(x, x, 1, 4, countHuman1, countAi1)) break;
                        }
                    }
                }
            }
        }
    }
    public boolean patternMethod(int col1, int col2, int row1, int row2, int countHuman, int countAi) {
        if (countHuman == 4) {
            boardUI.notifyWinner(new Winner(Piece.BLUE, col1, row1, col2, row2));
            return true;
        } else if (countAi == 4) {
            boardUI.notifyWinner(new Winner(Piece.GREEN, col1, row1, col2, row2));
            return true;
        }
        return false;
    }
}