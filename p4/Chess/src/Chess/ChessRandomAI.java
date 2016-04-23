package Chess;

import java.util.Random;

public class ChessRandomAI implements ChessAI {
	private final Random random = new Random();
	
	@Override
	public boolean performNextMovement(ChessBoard aTable, ChessPiece.Color playerColor) {
		ChessPiece[] pieces = aTable.getPieces(playerColor);
                if (pieces != null) {
                    ChessUtils.randomizeArray(pieces);

                    for (ChessPiece piece : pieces) {
                            PiecePosition[] positions = piece.getAvailablePositions(aTable);
                            if (positions.length > 0) {
                                    PiecePosition position = positions[random.nextInt(positions.length)];
                                    if (position != null && aTable.movePieceTo(piece, position))
                                            return true;
                            }
                    }
                    return true;
                }
		return false;
	}
}
