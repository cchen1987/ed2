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
        switch (this.getType()) {
            case PAWN:
                return ChessMovementManager.getAvailablePositionsOfPawn(this, aBoard);
            case KING:
                return ChessMovementManager.getAvailablePositionsOfKing(this, aBoard);
            case BISHOP:
                return ChessMovementManager.getAvailablePositionsOfBishop(this, aBoard);
            case KNIGHT:
                return ChessMovementManager.getAvailablePositionsOfKnight(this, aBoard);
            case QUEEN:
                return ChessMovementManager.getAvailablePositionsOfQueen(this, aBoard);
            case ROOK:
                return ChessMovementManager.getAvailablePositionsOfRook(this, aBoard);
            default:
                return null;
        }
    }
}
