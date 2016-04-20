package Chess;

public class ChessPieceImplementation implements ChessPiece {
    Color color;
    Type type;
    boolean moved;
    
    public ChessPieceImplementation(Color color, Type type) {
        this.color = color;
        this.type = type;
        moved = false;
    }
    
    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Type getType() {
        return type;
    }
    
    @Override
    public void notifyMoved() {
        moved = true;
    }

    @Override
    public boolean wasMoved() {
        return moved ? true : false;
    }

    @Override
    public PiecePosition[] getAvailablePositions(ChessBoard aBoard) {
        if (this.type == Type.PAWN)
            return ChessMovementManager.getAvailablePositionsOfPawn(this, aBoard);
        if (this.type == Type.KING)
            return ChessMovementManager.getAvailablePositionsOfKing(this, aBoard);
        if (this.type == Type.BISHOP)
            return ChessMovementManager.getAvailablePositionsOfBishop(this, aBoard);
        if (this.type == Type.KNIGHT)
            return ChessMovementManager.getAvailablePositionsOfKnight(this, aBoard);
        if (this.type == Type.QUEEN)
            return ChessMovementManager.getAvailablePositionsOfQueen(this, aBoard);
        if (this.type == Type.ROOK)
            return ChessMovementManager.getAvailablePositionsOfRook(this, aBoard);
        return null;
    }
}

