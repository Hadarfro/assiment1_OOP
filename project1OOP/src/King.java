public class King extends ConcretePiece {
private Position kingPosition;

public King (){

}
    public King(Player owner,Position kingPosition){
       super(owner,"♔",kingPosition);
     this.kingPosition = kingPosition;
    }

    public Position getKingPosition(){
        return this.kingPosition;
    }

}
