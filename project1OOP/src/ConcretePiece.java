public abstract class  ConcretePiece implements Piece{
private Player owner;
private String typePiece; // king or pawn
    private Position piecePosition;
   public ConcretePiece(){
        owner = this.getOwner();
        typePiece = this.getType();
    }
   public ConcretePiece(Player player,String typePiece,Position piecePosition){
        this.owner = player;
        this.typePiece = typePiece;
        this.piecePosition = piecePosition;
    }

    @Override
    public Player getOwner() {
      Player p = this.owner;
        return p;
    }

    @Override
    public String getType() {
        if("♔".equals(this.typePiece)){
            return "♔";
        }
        if ("♙".equals(this.typePiece)){
            return "♙";
        }
        return "";
    }
}
