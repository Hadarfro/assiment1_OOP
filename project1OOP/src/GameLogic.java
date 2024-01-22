public class GameLogic implements PlayableLogic{

    private Player firstPlayer;
    private Player secendPlayer;
    private Player currentPlayer;
    private ConcretePiece[][] board;

    public GameLogic() {
        this.board = new ConcretePiece[11][11];
        this.firstPlayer = new ConcretePlayer(1);
        this.secendPlayer = new ConcretePlayer(2);
        this.currentPlayer = secendPlayer;
       initBoard();
    }
   public void initBoard(){// placing the board how it should be at the start
        initKing();
        initPawnPlayer1();
        initPawnPlayer2();
    }

    public void initKing(){ //placing the king at the start of the game
     Position kingPositionPlyaer1 = new Position(5,5);
     ConcretePiece player1King = new King(getFirstPlayer(),kingPositionPlyaer1);
     placePiece(player1King,kingPositionPlyaer1);
    }

    public void initPawnPlayer1(){
        //right and left sides
        setPawn(4,7,4, false, getFirstPlayer());
        setPawn(4,7,6, false, getFirstPlayer());

        //up and downsides
        setPawn(4,7,4, true, getFirstPlayer());
        setPawn(4,7,6, true, getFirstPlayer());

        //leftOvers :)
        setOnePawn(5,3, getFirstPlayer());
        setOnePawn(3,5,getFirstPlayer());
        setOnePawn(7,5,getFirstPlayer());
        setOnePawn(5,7, getFirstPlayer());
    }

    public void initPawnPlayer2(){

        //right and left sides
        setPawn(3,8,0, false, getSecondPlayer());
        setPawn(3,8,10, false, getSecondPlayer());

        //up and downsides
        setPawn(3,8,0, true, getSecondPlayer());
        setPawn(3,8,10, true, getSecondPlayer());

        //leftOvers :)
        setOnePawn(5,1, getSecondPlayer());
        setOnePawn(1,5, getSecondPlayer());
        setOnePawn(5,9, getSecondPlayer());
        setOnePawn(9,5, getSecondPlayer());

    }


    /**
     *  set few pawns at the same time
     *
     * @param start - the start of the for-loop (in the matrix - row/col)
     * @param end - the end of the for-loop (in the matrix - row/col)
     * @param staticNum - the num of index who don't change in loop
     * @param isRowChange - if the row change or the col
     * @param player - first player or second player
     */

    public void setPawn(int start, int end, int staticNum, boolean isRowChange, Player player) {

        //set pawn when the number of row is static
        if (!isRowChange) {

            for (int col = start; col < end; col++) {

                //if its first player:
                if (player.isPlayerOne()) {
                    Position pawnPositionPlayer = new Position(staticNum, col);
                    ConcretePiece player1Pawn = new Pawn(getFirstPlayer(), pawnPositionPlayer);
                    placePiece(player1Pawn, pawnPositionPlayer);
                }
                //if its second player:
                else {
                    Position pawnPositionPlayer = new Position(staticNum, col);
                    ConcretePiece player2Pawn = new Pawn(getSecondPlayer(), pawnPositionPlayer);
                    placePiece(player2Pawn, pawnPositionPlayer);
                }
            }
        }
        //set pawn when the number of col is static
        if (isRowChange) {

            for (int row = start; row < end; row++) {

                //if its first player:
                if (player.isPlayerOne()) {
                    Position pawnPositionPlayer = new Position(row, staticNum);
                    ConcretePiece player1Pawn = new Pawn(getFirstPlayer(), pawnPositionPlayer);
                    placePiece(player1Pawn, pawnPositionPlayer);
                }
                //if its second player:
                else {
                    Position pawnPositionPlayer = new Position(row, staticNum);
                    ConcretePiece player2PAWN = new Pawn(getSecondPlayer(), pawnPositionPlayer);
                    placePiece(player2PAWN, pawnPositionPlayer);
                }
            }
        }
    }

    //set only one pawn in every time
    public void setOnePawn(int row, int col, Player player) {
        Position pawnPos = new Position(row, col);

        if (player.isPlayerOne()) {
            ConcretePiece playerPawn = new Pawn(getFirstPlayer(), pawnPos);
            placePiece(playerPawn, pawnPos);
        }
        else {
            ConcretePiece playerPawn = new Pawn(getSecondPlayer(), pawnPos);
            placePiece(playerPawn, pawnPos);
        }

    }


    public void placePiece(ConcretePiece piece, Position position) {
        if (position.isValidPosition()) {
            board[position.getX()][position.getY()] = piece;
        } else {
            System.out.println("Invalid position on the board");
        }
    }

    //*****************************end init board ******************************


    @Override // code the killing of other piece
    public boolean move(Position a , Position b) {
        int positionX = a.getX();
        int positionY = a.getY();
        ConcretePiece piece = null;
        boolean isNull = false;
        if(board[b.getX()][b.getY()]!=null){// if position b is taken by another piece
            return false;
        }
        else if (a.getY()!=b.getY()&&a.getX()!=b.getX()){//if the two positions are not on the same line
            return false;
        }
        else if(a.getY()==b.getY()&&a.getX()==b.getX()){//if position a and position b are the same
            return true;
        }
       else if (!a.isValidPosition()||!b.isValidPosition()){// if the positions are on the board
            return false;
        }
        else if(!board[a.getX()][a.getY()].getOwner().equals(getCurrentPlayer())||getPieceAtPosition(a)==null){//if it's not a's piece turn return false or there is no piece at a's position
            return false;
        }
        while (piece == null && positionY != b.getY() || positionX != b.getX()) {
           if(positionX == b.getX() && positionY < b.getY()){
                positionY++;
            }
             else if(positionX == b.getX() && positionY > b.getY()){
                positionY--;
            }
            else  if(positionY == b.getY() && positionX < b.getX()){
                positionX++;
            }
            else if(positionY == b.getY() && positionX > b.getX()){
                positionX--;
            }
            piece = board[positionX][positionY];
        }
            if (positionY == b.getY() && positionX == b.getX()) {
                board[positionX][positionY] = board[a.getX()][a.getY()];
                board[a.getX()][a.getY()] = null;
                piece = board[positionX][positionY];
                if(piece.getType().equals("♔")){//continue!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                   if(positionX==0&&positionY==11||positionY==0&&positionX==11||positionX==0&&positionY==0||positionX==11&&positionY==11){

                    }
                }
                if (!piece.getType().equals("♔")&&positionX<11&&positionX>0&&positionY<11&&positionY>0) { // continue!!!!!!!!!!!!!!!!
                    if(board[positionX][positionY + 1] != null) {
                        if (piece.getOwner() != board[positionX][positionY + 1].getOwner()) {
                            if (board[positionX + 1][positionY + 1] == null && board[positionX - 1][positionY + 1] == null || board[positionX + 1][positionY + 1] == null && board[positionX][positionY + 2] == null || board[positionX - 1][positionY + 1] == null && board[positionX][positionY + 2] == null || board[positionX + 1][positionY + 1] == null && board[positionX][positionY + 2] == null && board[positionX - 1][positionY + 1] == null){
                                isNull = true;
                            }
                             if (!isNull && board[positionX][positionY + 1].getOwner() != board[positionX + 1][positionY + 1].getOwner() && board[positionX][positionY + 1].getOwner() != board[positionX - 1][positionY + 1].getOwner()) {
                                board[positionX][positionY + 1] = null;
                            }
                            else if (!isNull && board[positionX][positionY + 1].getOwner() != board[positionX + 1][positionY + 1].getOwner() && board[positionX][positionY + 1].getOwner() != board[positionX][positionY + 2].getOwner()) {
                                board[positionX][positionY + 1] = null;
                            }
                            else if (!isNull && board[positionX][positionY + 1].getOwner() != board[positionX - 1][positionY + 1].getOwner() && board[positionX][positionY + 1].getOwner() != board[positionX][positionY + 2].getOwner()) {
                                board[positionX][positionY + 1] = null;
                            }
                        }
                    }
                     isNull = false;
                  //  ***************************************************************************************************

                    if(board[positionX][positionY - 1] != null) {
                        if (piece.getOwner() != board[positionX][positionY - 1].getOwner()) {
                            if (board[positionX + 1][positionY - 1] == null && board[positionX - 1][positionY - 1] == null || board[positionX + 1][positionY - 1] == null && board[positionX][positionY - 2] == null || board[positionX - 1][positionY - 1] == null && board[positionX][positionY - 2] == null || board[positionX + 1][positionY - 1] == null && board[positionX][positionY - 2] == null && board[positionX - 1][positionY - 1] == null){
                                isNull = true;
                            }
                             if (!isNull && board[positionX][positionY - 1].getOwner() != board[positionX + 1][positionY - 1].getOwner() && board[positionX][positionY - 1].getOwner() != board[positionX - 1][positionY - 1].getOwner()) {
                                board[positionX][positionY - 1] = null;
                            }
                            else if (!isNull && board[positionX][positionY - 1].getOwner() != board[positionX + 1][positionY - 1].getOwner() && board[positionX][positionY - 1].getOwner() != board[positionX][positionY - 2].getOwner()) {
                                board[positionX][positionY - 1] = null;
                            }
                            else if (!isNull && board[positionX][positionY - 1].getOwner() != board[positionX - 1][positionY - 1].getOwner() && board[positionX][positionY - 1].getOwner() != board[positionX][positionY - 2].getOwner()) {
                                board[positionX][positionY - 1] = null;
                            }
                        }
                    }

                    isNull = false;
                    //***********************************************************************************************************

                    if(board[positionX + 1][positionY] != null) {
                        if (piece.getOwner() != board[positionX + 1][positionY].getOwner()) {
                            if (board[positionX + 2][positionY] == null && board[positionX + 1][positionY + 1] == null || board[positionX + 1][positionY + 1] == null && board[positionX + 1][positionY - 1] == null || board[positionX + 2][positionY] == null && board[positionX + 1][positionY - 1] == null || board[positionX + 1][positionY + 1] == null && board[positionX + 1][positionY - 1] == null && board[positionX + 2][positionY] == null){
                                isNull = true;
                            }
                             if (!isNull && board[positionX + 1][positionY].getOwner() != board[positionX + 1][positionY - 1].getOwner() && board[positionX + 1][positionY].getOwner() != board[positionX + 1][positionY + 1].getOwner()) {
                                board[positionX + 1][positionY] = null;
                            }
                            else if (!isNull && board[positionX + 1][positionY].getOwner() != board[positionX + 1][positionY + 1].getOwner() && board[positionX][positionY + 1].getOwner() != board[positionX + 2][positionY].getOwner()) {
                                board[positionX + 1][positionY] = null;
                            }
                            else if (!isNull && board[positionX + 1][positionY].getOwner() != board[positionX  + 1][positionY - 1].getOwner() && board[positionX][positionY + 1].getOwner() != board[positionX + 2][positionY].getOwner()) {
                                board[positionX + 1][positionY] = null;
                            }
                        }
                    }
                    isNull =false;
                    //******************************************************************************************************

                    if(board[positionX - 1][positionY] != null) {
                        if (piece.getOwner() != board[positionX - 1][positionY].getOwner()) {
                            if (board[positionX - 2][positionY] == null && board[positionX - 1][positionY + 1] == null || board[positionX - 1][positionY + 1] == null && board[positionX - 1][positionY - 1] == null || board[positionX - 2][positionY] == null && board[positionX - 1][positionY - 1] == null || board[positionX - 1][positionY + 1] == null && board[positionX - 1][positionY - 1] == null && board[positionX - 2][positionY] == null){
                                isNull = true;
                            }
                             if (!isNull && board[positionX - 1][positionY].getOwner() != board[positionX - 1][positionY - 1].getOwner() && board[positionX - 1][positionY].getOwner() != board[positionX - 1][positionY + 1].getOwner()) {
                                board[positionX - 1][positionY] = null;
                            }
                            else if (!isNull && board[positionX - 1][positionY].getOwner() != board[positionX - 1][positionY + 1].getOwner() && board[positionX][positionY - 1].getOwner() != board[positionX - 2][positionY].getOwner()) {
                                board[positionX - 1][positionY] = null;
                            }
                            else if (!isNull && board[positionX - 1][positionY].getOwner() != board[positionX  - 1][positionY - 1].getOwner() && board[positionX][positionY - 1].getOwner() != board[positionX - 2][positionY].getOwner()) {
                                board[positionX - 1][positionY] = null;
                            }
                        }
                    }
                }
                if(positionX == 0 && positionY != 0 && positionY != 11){
                    if(!piece.getOwner().equals(board[positionX][positionY + 1])){
                        if(!board[positionX][positionY + 1].getOwner().equals(board[positionX][positionY + 2])){
                            board[positionX][positionY + 1] = null;
                        }
                       else if(!board[positionX][positionY + 1].getOwner().equals(board[positionX + 1][positionY + 1])){
                            board[positionX][positionY + 1] = null;
                        }
                    }
                    if(!piece.getOwner().equals(board[positionX][positionY - 1])){
                        if(!board[positionX][positionY - 1].getOwner().equals(board[positionX][positionY - 2])){
                            board[positionX][positionY + 1] = null;
                        }
                        else if(!board[positionX][positionY - 1].getOwner().equals(board[positionX + 1][positionY - 1])){
                            board[positionX][positionY - 1] = null;
                        }
                    }
                    if(!piece.getOwner().equals(board[positionX + 1][positionY])){
                        if (piece.getOwner() != board[positionX + 1][positionY].getOwner()) {
                            if (board[positionX + 2][positionY] == null && board[positionX + 1][positionY + 1] == null || board[positionX + 1][positionY + 1] == null && board[positionX + 1][positionY - 1] == null || board[positionX + 2][positionY] == null && board[positionX + 1][positionY - 1] == null || board[positionX + 1][positionY + 1] == null && board[positionX + 1][positionY - 1] == null && board[positionX + 2][positionY] == null){
                                isNull = true;
                            }
                            if (!isNull && board[positionX + 1][positionY].getOwner() != board[positionX + 1][positionY - 1].getOwner() && board[positionX + 1][positionY].getOwner() != board[positionX + 1][positionY + 1].getOwner()) {
                                board[positionX + 1][positionY] = null;
                            }
                            else if (!isNull && board[positionX + 1][positionY].getOwner() != board[positionX + 1][positionY + 1].getOwner() && board[positionX][positionY + 1].getOwner() != board[positionX + 2][positionY].getOwner()) {
                                board[positionX + 1][positionY] = null;
                            }
                            else if (!isNull && board[positionX + 1][positionY].getOwner() != board[positionX  + 1][positionY - 1].getOwner() && board[positionX][positionY + 1].getOwner() != board[positionX + 2][positionY].getOwner()) {
                                board[positionX + 1][positionY] = null;
                            }
                        }
                    }
                }
                changeTurn();
                return true;
            }
            else {
                return false;
            }
    }

    public Player getCurrentPlayer(){ //continue!!!!!!!!!!!!!!!!!!!!
        return this.currentPlayer;
    }

    public void changeTurn(){
        if (this.currentPlayer.equals(getFirstPlayer())){
            this.currentPlayer = getSecondPlayer();
        }
        else {
            this.currentPlayer = getFirstPlayer();
        }
    }
    @Override // finished
    public Piece getPieceAtPosition(Position position) {
       if (position.isValidPosition()){
           return board[position.getX()][position.getY()];
       }
           return null;

    }

    @Override  // finished
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    @Override  // finished
    public Player getSecondPlayer() {
        return secendPlayer;
    }

    @Override
    public boolean isGameFinished() {//continue!!!!!
      if(getPieceAtPosition(new Position(0,0)).getType()=="♔"||getPieceAtPosition(new Position(11,11)).getType()=="♔"||getPieceAtPosition(new Position(11,0)).getType()=="♔"||getPieceAtPosition(new Position(0,11)).getType()=="♔"){
          return true;
      }
      //else if ()
        return false;
    }

    @Override
    public boolean isSecondPlayerTurn() {//need to check if it work
        if (firstPlayer.isPlayerOne()){
            return false;
        }
        return true;
    }

    @Override  // finished
    public void reset() {// need to clear the rest of the matrix
        for (int i = 0; i < 11; i++){
            for(int j = 0; j < 11; j++){
                board[i][j] = null;
            }
        }
        initBoard();
    }

    @Override
    public void undoLastMove() {

    }

    @Override  // finished
    public int getBoardSize() {
        return 11;
    }
    public ConcretePiece[][] getBoard() {  // finished
        return this.board;
    }
}
