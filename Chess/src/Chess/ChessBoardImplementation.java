package Chess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ChessBoardImplementation implements ChessBoard {

	ChessPiece	pieces[] = new ChessPiece[8 * 8];
	
	public ChessBoardImplementation() {
		for (int i = 0; i < 8; i++) {
			pieces[getPieceIndex(i, 1)] = new ChessPieceImplementation(ChessPiece.Color.WHITE, ChessPiece.Type.PAWN);
			pieces[getPieceIndex(i, 6)] = new ChessPieceImplementation(ChessPiece.Color.BLACK, ChessPiece.Type.PAWN);
		}
		pieces[getPieceIndex(0, 0)] = new ChessPieceImplementation(ChessPiece.Color.WHITE, ChessPiece.Type.ROOK);
		pieces[getPieceIndex(7, 0)] = new ChessPieceImplementation(ChessPiece.Color.WHITE, ChessPiece.Type.ROOK);
		pieces[getPieceIndex(0, 7)] = new ChessPieceImplementation(ChessPiece.Color.BLACK, ChessPiece.Type.ROOK);
		pieces[getPieceIndex(7, 7)] = new ChessPieceImplementation(ChessPiece.Color.BLACK, ChessPiece.Type.ROOK);

		pieces[getPieceIndex(1, 0)] = new ChessPieceImplementation(ChessPiece.Color.WHITE, ChessPiece.Type.KNIGHT);
		pieces[getPieceIndex(6, 0)] = new ChessPieceImplementation(ChessPiece.Color.WHITE, ChessPiece.Type.KNIGHT);
		pieces[getPieceIndex(1, 7)] = new ChessPieceImplementation(ChessPiece.Color.BLACK, ChessPiece.Type.KNIGHT);
		pieces[getPieceIndex(6, 7)] = new ChessPieceImplementation(ChessPiece.Color.BLACK, ChessPiece.Type.KNIGHT);

		pieces[getPieceIndex(2, 0)] = new ChessPieceImplementation(ChessPiece.Color.WHITE, ChessPiece.Type.BISHOP);
		pieces[getPieceIndex(5, 0)] = new ChessPieceImplementation(ChessPiece.Color.WHITE, ChessPiece.Type.BISHOP);
		pieces[getPieceIndex(2, 7)] = new ChessPieceImplementation(ChessPiece.Color.BLACK, ChessPiece.Type.BISHOP);
		pieces[getPieceIndex(5, 7)] = new ChessPieceImplementation(ChessPiece.Color.BLACK, ChessPiece.Type.BISHOP);

		pieces[getPieceIndex(4, 0)] = new ChessPieceImplementation(ChessPiece.Color.WHITE, ChessPiece.Type.KING);
		pieces[getPieceIndex(3, 0)] = new ChessPieceImplementation(ChessPiece.Color.WHITE, ChessPiece.Type.QUEEN);
		pieces[getPieceIndex(3, 7)] = new ChessPieceImplementation(ChessPiece.Color.BLACK, ChessPiece.Type.QUEEN);
		pieces[getPieceIndex(4, 7)] = new ChessPieceImplementation(ChessPiece.Color.BLACK, ChessPiece.Type.KING);
	}
	
	@Override
	public ChessPiece[] getPieces(ChessPiece.Color PieceColor) {
		int count = 0;
		for (ChessPiece piece : pieces)
			if (piece != null && piece.getColor() == PieceColor)
				count++;

		if (count == 0)
			return null;
		
		ChessPiece[] result = new ChessPiece[count];
		count = 0;
		for (ChessPiece piece : pieces)
			if (piece != null && piece.getColor() == PieceColor)
				result[count++] = piece;

		return result;
	}
	
	private	int getPieceIndex(int column, int row) {
		return row * 8 + column;
	}

	private	int getPieceIndex(PiecePosition position) {
		return position.getRow() * 8 + position.getColumn();
	}

	private	ChessPiece getPiece(int column, int row) {
		int index = getPieceIndex(column, row);
		return pieces[index];
	}

	@Override
	public ChessPiece getPieceAt(PiecePosition position) {
		if (!PiecePosition.isAvailable(position))
			return null;
		return getPiece(position.getColumn(), position.getRow());
	}

	@Override
	public PiecePosition getPiecePosition(ChessPiece APiece) {
		for (int row = 0; row < 8; row++)
			for (int column = 0; column < 8; column++)
				if (getPiece(column, row) == APiece)
					return new PiecePosition(column, row);
		return null;
	}

	@Override
	public void removePieceAt(PiecePosition Position) {
		pieces[getPieceIndex(Position.getColumn(), Position.getRow())] = null;
	}
        
	@Override
	public boolean movePieceTo(ChessPiece Piece, PiecePosition Position) {
		PiecePosition oldPosition = getPiecePosition(Piece);
		if (oldPosition != null) {
			int oldIndex = getPieceIndex(oldPosition);
                        int newIndex = getPieceIndex(Position);
                        if (pieces[oldIndex].getType() == ChessPiece.Type.KING && !pieces[oldIndex].wasMoved()) {
                            int difference = Position.getColumn() - oldPosition.getColumn();
                            
                            if (difference < -1 && pieces[getPieceIndex(0, oldPosition.getRow())] != null && 
                                    pieces[getPieceIndex(0, oldPosition.getRow())].getType() == ChessPiece.Type.ROOK &&
                                    !pieces[getPieceIndex(0, oldPosition.getRow())].wasMoved()) {
                                pieces[getPieceIndex(Position.getColumn() + 1, oldPosition.getRow())] = new ChessPieceImplementation(Piece.getColor(), ChessPiece.Type.ROOK);
                                pieces[getPieceIndex(Position.getColumn() + 1, oldPosition.getRow())].notifyMoved();
                                pieces[getPieceIndex(0, oldPosition.getRow())] = null;
                            }
                            if (difference > 1 && pieces[getPieceIndex(7, oldPosition.getRow())] != null && 
                                    pieces[getPieceIndex(7, oldPosition.getRow())].getType() == ChessPiece.Type.ROOK &&
                                    !pieces[getPieceIndex(7, oldPosition.getRow())].wasMoved()) {
                                pieces[getPieceIndex(Position.getColumn() - 1, oldPosition.getRow())] = new ChessPieceImplementation(Piece.getColor(), ChessPiece.Type.ROOK);
                                pieces[getPieceIndex(Position.getColumn() - 1, oldPosition.getRow())].notifyMoved();
                                pieces[getPieceIndex(7, oldPosition.getRow())] = null;
                            }
                        }
                        pieces[oldIndex] = null;
                        pieces[newIndex] = Piece;
			pieces[newIndex].notifyMoved();
                        if (pieces[newIndex].getType() == ChessPiece.Type.PAWN &&
                                (Position.getRow() == 0 || Position.getRow() == 7)) {
                            pieces[newIndex] = new ChessPieceImplementation(Piece.getColor(), ChessPiece.Type.QUEEN);
                        }
			return true;
		}
		return false;
	}

	@Override
	public boolean containsKing(ChessPiece.Color PieceColor) {
		for (ChessPiece piece : pieces) 
			if (piece != null && piece.getType() == ChessPiece.Type.KING && piece.getColor() == PieceColor)
				return true;
		return false;
	}
        
        public int getPieceCount() {
            int count = 0;
            for (int row = 0; row < 8; row++)
                for (int column = 0; column < 8; column++) {
                    if (pieces[getPieceIndex(column, row)] != null)
                        count++;
                }
            return count;
        }
        
	@Override
	public boolean saveToFile(File location) {
            try (FileWriter file = new FileWriter(location, true)) {
                file.write(getPieceCount() + "\n");
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (pieces[getPieceIndex(j, i)] != null) {
                            file.write(j + " " + i+ " " +
                                    pieces[getPieceIndex(j, i)].getColor() + " " +
                                    pieces[getPieceIndex(j, i)].getType() + " " +
                                    pieces[getPieceIndex(j, i)].wasMoved() + "\n");
                        }
                    }
                }
                return true;
            }
            catch (IOException ex) {
                System.err.format("IOException: %s%n", ex);
            }
            catch (Exception ex) {
                System.err.format("Unexpected error: %s%n", ex);
            }
            return false;
	}

	@Override
	public boolean loadFromFile(File location) {
            try {
                for (int row = 0; row < 8; row++)
                    for (int column = 0; column < 8; column++) {
                        pieces[getPieceIndex(column, row)] = null;
                    }
                Scanner r = new Scanner(location);
                String header = r.next();
                if (header.equals("ChessGame")) {
                    r.next();
                    int pieceCount = r.nextInt();
                    String text;
                    for (int i = 0; i < pieceCount; i++) {
                        text = r.next();
                        int col = Integer.parseInt(text);
                        text = r.next();
                        int row = Integer.parseInt(text);
                        ChessPiece.Color c = ChessPiece.Color.valueOf(r.next());
                        ChessPiece.Type t = ChessPiece.Type.valueOf(r.next());
                        pieces[getPieceIndex(col, row)] = new ChessPieceImplementation(c, t);
                        String moved = r.next();
                        if (moved.equals("true"))
                            pieces[getPieceIndex(col, row)].notifyMoved();
                    }
                    return true;
                }
                return false;
            }
            catch (FileNotFoundException ex) {
                System.err.format("IOException: %s%n", ex);
            }
            catch (Exception ex) {
                System.err.format("Unexpected error: %s%n", ex);
            }
            return false;
	}
}
