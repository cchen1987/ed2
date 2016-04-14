package Chess;

public class ChessMovementManager {
	
	static PiecePosition[] getAvailablePositionsOfPawn(ChessPiece aPiece, ChessBoard aBoard) {
		PiecePosition auxPosition, position = aBoard.getPiecePosition(aPiece);
		ChessMovementHelper result = new ChessMovementHelper();
		int direction = aPiece.getColor() == ChessPiece.Color.WHITE ? 1 : -1;
                auxPosition = position.getDisplacedPiece(0, direction);
		result.addPositionWithDisplacementIfAvaiable(position, aBoard, 0, direction);
		if (!aPiece.wasMoved() && aBoard.getPieceAt(auxPosition) == null)
			result.addPositionWithDisplacementIfAvaiable(position, aBoard, 0, direction * 2);
		ChessPiece piece;
		auxPosition = position.getDisplacedPiece(1, direction);
		piece = aBoard.getPieceAt(auxPosition);
		if(piece!= null && piece.getColor() != aPiece.getColor())
			result.addPosition(auxPosition, 0, 0);

		auxPosition = position.getDisplacedPiece(-1, direction);
		piece = aBoard.getPieceAt(auxPosition);
                if(piece!= null && piece.getColor() != aPiece.getColor())
			result.addPosition(auxPosition, 0, 0);
		
		return result.getResult();
	}

	static void generateStepMovement(ChessPiece aPiece, ChessBoard aBoard, ChessMovementHelper aResult, 
			PiecePosition aPosition, int columnIncrement, int rowIncrement, int maxTimes) {

		PiecePosition auxPosition;
		int times = maxTimes;
		
		auxPosition = aPosition.getDisplacedPiece(columnIncrement, rowIncrement);
		while ((times-- > 0) && auxPosition != null && PiecePosition.isAvailable(auxPosition, 0, 0)) {
			boolean added = aResult.addPositionWithDisplacementIfAvaiable(auxPosition, aBoard, 0, 0);
			if (!added) {
				ChessPiece piece = aBoard.getPieceAt(auxPosition);
				if (piece != null && piece.getColor() != aPiece.getColor())
					aResult.addPosition(auxPosition, 0, 0);
				break;
			}
			auxPosition = auxPosition.getDisplacedPiece(columnIncrement, rowIncrement);
		}
	}

	static PiecePosition[] getAvailablePositionsOfBishop(ChessPiece aPiece, ChessBoard aBoard) {
		PiecePosition position = aBoard.getPiecePosition(aPiece);
		ChessMovementHelper result = new ChessMovementHelper();

		generateStepMovement(aPiece, aBoard, result, position, 1, 1, 8);
		generateStepMovement(aPiece, aBoard, result, position, -1, 1, 8);
		generateStepMovement(aPiece, aBoard, result, position, 1, -1, 8);
		generateStepMovement(aPiece, aBoard, result, position, -1, -1, 8);
		
		return result.getResult();
	}
	static PiecePosition[] getAvailablePositionsOfKing(ChessPiece aPiece, ChessBoard aBoard) {
		PiecePosition position = aBoard.getPiecePosition(aPiece);
		ChessMovementHelper result = new ChessMovementHelper();

		generateStepMovement(aPiece, aBoard, result, position, 1, 1, 1);
		generateStepMovement(aPiece, aBoard, result, position, -1, 1, 1);
		generateStepMovement(aPiece, aBoard, result, position, 1, -1, 1);
		generateStepMovement(aPiece, aBoard, result, position, -1, -1, 1);
		generateStepMovement(aPiece, aBoard, result, position, 1, 0, 1);
		generateStepMovement(aPiece, aBoard, result, position, -1, 0, 1);
		generateStepMovement(aPiece, aBoard, result, position, 0, 1, 1);
		generateStepMovement(aPiece, aBoard, result, position, 0, -1, 1);
		
                if (!aPiece.wasMoved()) {
                    boolean leftNotAvailable = false;
                    boolean rightNotAvailable = false;
                    int row = aBoard.getPiecePosition(aPiece).getRow();
                    
                    ChessPiece leftRook = aBoard.getPieceAt(new PiecePosition(0, row));
                    if (leftRook != null && leftRook.getType() == ChessPiece.Type.ROOK && 
                            !leftRook.wasMoved()) {
                        for (int i = 1; i < 4 && leftNotAvailable; i++) {
                            if (aBoard.getPieceAt(new PiecePosition(i, row)) != null)
                                leftNotAvailable = true;
                        }
                    }
                    ChessPiece rightRook = aBoard.getPieceAt(new PiecePosition(7, row));
                    if (rightRook != null && rightRook.getType() == ChessPiece.Type.ROOK && 
                            !rightRook.wasMoved()) {
                        for (int i = 5; i < 7 && rightNotAvailable; i++) {
                            if (aBoard.getPieceAt(new PiecePosition(i, row)) != null)
                                rightNotAvailable = true;
                        }
                    }
                    if (!leftNotAvailable) {
                        generateStepMovement(aPiece, aBoard, result, position, -2, 0, 1);
                    }
                    if (!rightNotAvailable) {
                        generateStepMovement(aPiece, aBoard, result, position, 2, 0, 1);
                    }
                }
                
		return result.getResult();
	}

	static PiecePosition[] getAvailablePositionsOfQueen(ChessPiece aPiece, ChessBoard aBoard) {
		PiecePosition position = aBoard.getPiecePosition(aPiece);
		ChessMovementHelper result = new ChessMovementHelper();

		generateStepMovement(aPiece, aBoard, result, position, 1, 1, 8);
		generateStepMovement(aPiece, aBoard, result, position, -1, 1, 8);
		generateStepMovement(aPiece, aBoard, result, position, 1, -1, 8);
		generateStepMovement(aPiece, aBoard, result, position, 1, 0, 8);
		generateStepMovement(aPiece, aBoard, result, position, -1, 0, 8);
		generateStepMovement(aPiece, aBoard, result, position, 0, 1, 8);
		generateStepMovement(aPiece, aBoard, result, position, 0, -1, 8);
                generateStepMovement(aPiece, aBoard, result, position, -1, -1, 8);
		
		return result.getResult();
	}

	static PiecePosition[] getAvailablePositionsOfKnight(ChessPiece aPiece, ChessBoard aBoard) {
		PiecePosition position = aBoard.getPiecePosition(aPiece);
		ChessMovementHelper result = new ChessMovementHelper();

		generateStepMovement(aPiece, aBoard, result, position, 1, -2, 1);
		generateStepMovement(aPiece, aBoard, result, position, 1, 2, 1);
		generateStepMovement(aPiece, aBoard, result, position, -1, 2, 1);
		generateStepMovement(aPiece, aBoard, result, position, -1, -2, 1);
		generateStepMovement(aPiece, aBoard, result, position, 2, 1, 1);
		generateStepMovement(aPiece, aBoard, result, position, 2, -1, 1);
		generateStepMovement(aPiece, aBoard, result, position, -2, 1, 1);
		generateStepMovement(aPiece, aBoard, result, position, -2, -1, 1);

		return result.getResult();
	}

	static PiecePosition[] getAvailablePositionsOfRook(ChessPiece aPiece, ChessBoard aBoard) {
		PiecePosition position = aBoard.getPiecePosition(aPiece);
		ChessMovementHelper result = new ChessMovementHelper();

		generateStepMovement(aPiece, aBoard, result, position, 1, 0, 8);
		generateStepMovement(aPiece, aBoard, result, position, -1, 0, 8);
		generateStepMovement(aPiece, aBoard, result, position, 0, 1, 8);
		generateStepMovement(aPiece, aBoard, result, position, 0, -1, 8);
		
		return result.getResult();
	}
	
}
