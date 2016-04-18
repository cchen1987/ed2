package chessdesktop;

import Chess.ChessAI;
import Chess.ChessPiece;
import Chess.PiecePosition;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ChessBoardRenderer {
	final double LEFT_MARGIN = 50;
	final double TOP_MARGIN = 50;
	final double PIECE_OFFSET = 5;
	
	private final Chess.ChessBoard board = new Chess.ChessBoardImplementation();
	private Chess.ChessPiece movingPiece;
	private Chess.ChessPiece.Color currentColor = ChessPiece.Color.WHITE;
        
	static private void drawCrown(GraphicsContext aContext, double minX, double minY, double width, double height) {
		double maxX = minX + width;
		double maxY = minY + height;
		double[] x = {minX, minX, maxX, maxX, minX + width * 0.8, (minX + maxX) * 0.5, minX + width * 0.2};
		double[] y = {minY, maxY, maxY, minY, minY + height * 0.5, minY, minY + height * 0.5};
		aContext.fillPolygon(x, y, 7);
	}
	
	static private void drawTriangle(GraphicsContext aContext, double minX, double minY, double width, double height) {
		double maxX = minX + width;
		double maxY = minY + height;
		double[] x = {minX + width * 0.5, minX, maxX};
		double[] y = {minY, maxY, maxY};
		aContext.fillPolygon(x, y, 3);
	}

        ChessPiece.Color getCurrentColor() {
            return currentColor;
        }
        
	void drawAvailablePositions(Canvas canvas, Chess.ChessPiece piece, double width, double height) {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            Bounds boardBounds = getBoardBounds(canvas);
            PiecePosition[] p = piece.getAvailablePositions(board);
            if (piece.getColor() == currentColor) {
                for (int i = 0; i < p.length; i++) {
                    gc.setFill(Color.BURLYWOOD);
                    gc.fillRect(boardBounds.getMinX() + p[i].getColumn() * width, boardBounds.getMinY() + p[i].getRow() * height, 
                        boardBounds.getMinX() * 1.1 + (p[i].getColumn() + 1), boardBounds.getMinY() * 0.6 + (p[i].getRow() + 1));
                }
            }
        }
        
	void drawPiece(Canvas canvas, Chess.ChessPiece piece, double minX, double minY, double width, double height) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
                if (piece == movingPiece && containsKing(ChessPiece.Color.BLACK) && containsKing(ChessPiece.Color.WHITE) && !isTie()) {
                    gc.setFill(Color.GREY);
                }
                else if (piece.getColor() == Chess.ChessPiece.Color.BLACK)
                        gc.setFill(Color.BLACK);
                else
                        gc.setFill(Color.WHITE);

		minX += PIECE_OFFSET;
		minY += PIECE_OFFSET;
		width -= PIECE_OFFSET * 2;
		height -= PIECE_OFFSET * 2;
		
		switch(piece.getType()) {
			case KING:
				drawCrown(gc, minX, minY, width, height);
				break;
			case QUEEN:
				drawCrown(gc, minX + width * 0.3, minY, width * 0.4, height * 0.3);
				gc.fillOval(minX, minY + height * 0.3, width, height * 0.7);
				gc.fillRect(minX, minY + height * 0.7, width, height * 0.3);
				break;
			case ROOK: {
				double maxX = minX + width;
				double maxY = minY + height;
				double[] x = {minX, minX, maxX, maxX, 
					minX + width * 0.8, minX + width * 0.8, 
					minX + width * 0.6, minX + width * 0.6, 
					minX + width * 0.4, minX + width * 0.4, 
					minX + width * 0.2, minX + width * 0.2
				};
				double[] y = {minY, maxY, maxY, 
					minY, minY,
					minY + height * 0.3, minY + height * 0.3, 
					minY, minY,
					minY + height * 0.3, minY + height * 0.3,
					minY
				};
				gc.fillPolygon(x, y, 12);
				break;
			}
			case BISHOP:
				gc.fillOval(minX + width * 0.4, minY, width * 0.2, height);
				drawTriangle(gc, minX + width * 0.3, minY + height * 0.2, width * 0.4, height * 0.8);
				break;
			case KNIGHT:
                                gc.fillRect(minX, minY + height * 0.4, width * 0.6, height * 0.6);
                                drawTriangle(gc, minX, minY, width, height * 0.45);
				break;
			case PAWN: 
				drawTriangle(gc, minX, minY + height * 0.3, width, height * 0.7);
				break;
		}
                
	}
	
	Bounds getBoardBounds(Canvas canvas) {
		Bounds bounds = canvas.getBoundsInLocal();		
		return new BoundingBox(bounds.getMinX() + LEFT_MARGIN, bounds.getMinY() + TOP_MARGIN, 
			bounds.getWidth() - LEFT_MARGIN, bounds.getHeight() - TOP_MARGIN);		
	}
	
	void drawPiece(Canvas canvas, Bounds boardBounds, Chess.ChessPiece piece) {
		PiecePosition position = board.getPiecePosition(piece);
		int c = position.getColumn();
		int r = position.getRow();
		double width = boardBounds.getWidth() / 8;
		double height = boardBounds.getHeight() / 8;
		drawPiece(canvas, piece, boardBounds.getMinX() + c * width, 
                    boardBounds.getMinY() + r * height, width, height);
	}

	void draw(Canvas canvas) {
		Bounds bounds = canvas.getBoundsInLocal();
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		Bounds boardBounds = getBoardBounds(canvas);
		double width = boardBounds.getWidth() / 8;
		double height = boardBounds.getHeight() / 8;
                
		gc.setFill(Color.WHITE);
		gc.clearRect(0, 0, bounds.getWidth(), bounds.getHeight());
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 0)
					gc.setFill(Color.AQUA);
				else
					gc.setFill(Color.YELLOW);
				gc.fillRect(boardBounds.getMinX() + i * width, boardBounds.getMinY() + j * height, 
                                    boardBounds.getMinX() + (i + 1) * width, boardBounds.getMinY() + (j + 1) * height);
			}
		}
                
                if (movingPiece != null && containsKing(ChessPiece.Color.BLACK) && containsKing(ChessPiece.Color.WHITE) &&
                        !isTie())
                    drawAvailablePositions(canvas, movingPiece, width, height);
                
		for (ChessPiece piece : board.getPieces())
			drawPiece(canvas, boardBounds, piece);
                
		gc.setFill(Color.BLACK);
		for (int i = 0; i < 8; i++) {
			gc.fillText("(" + (i + 1) + ")", 10, TOP_MARGIN + (i + 0.5) * height);
		}
		for (int i = 0; i < 8; i++) {
			gc.fillText("(" + (i + 1) + ")", LEFT_MARGIN + (i + 0.5) * width, 10);
		}
	}
	
	ChessPiece getPieceAt(Canvas canvas, double x, double y) {
		Bounds boardBounds = getBoardBounds(canvas);
		double width = boardBounds.getWidth() / 8;
		double height = boardBounds.getHeight() / 8;

		for (ChessPiece piece : board.getPieces()) {
			PiecePosition position = board.getPiecePosition(piece);
			int c = position.getColumn();
			int r = position.getRow();

			double minx = boardBounds.getMinX() + (c + 0.0) * width;
			double maxx = boardBounds.getMinX() + (c + 1.0) * width;
			double miny = boardBounds.getMinY() + (r + 0.0) * height;
			double maxy = boardBounds.getMinY() + (r + 1.0) * height;
			
			if (minx <= x && x <= maxx && miny <= y && y <= maxy)
				return piece;
		}
		
		return null;
	}

	public void setMovingPiece(ChessPiece movingPiece) {
		this.movingPiece = movingPiece;
	}

	public ChessPiece getMovingPiece() {
		return movingPiece;
	}
	
	boolean movePieceTo(Canvas canvas, double x, double y) {
		if (movingPiece == null)
			return false;
                
		Bounds boardBounds = getBoardBounds(canvas);
		if (boardBounds.contains(x, y) && containsKing(ChessPiece.Color.WHITE) &&
                            containsKing(ChessPiece.Color.WHITE) && !isTie()) {
			double width = boardBounds.getWidth() / 8;
			double height = boardBounds.getHeight() / 8;
			int column = (int)((x - boardBounds.getMinX()) / width);
			int row = (int)((y - boardBounds.getMinY()) / height);
			PiecePosition position = new PiecePosition(column, row);
			if (movingPiece.canMoveToPosition(position, board) && movingPiece.getColor() == currentColor) {
                                currentColor = currentColor == ChessPiece.Color.WHITE ? ChessPiece.Color.BLACK : ChessPiece.Color.WHITE;
                            return board.movePieceTo(movingPiece, position);
                        }
		}
		return false;
	}

	boolean containsKing(ChessPiece.Color aColor) {
		return board.containsKing(aColor);
	}
        
        Chess.ChessBoard getBoard() {
            return board;
        }
        
        void setCurrentColor(Chess.ChessPiece.Color currentColor) {
            this.currentColor = currentColor;
        }
        
        boolean isTie() {
            ChessPiece[] pBlack = board.getPieces(ChessPiece.Color.BLACK);
            ChessPiece[] pWhite = board.getPieces(ChessPiece.Color.WHITE);
            int bRook = 0, bQueen = 0, bPawn = 0, bBishop = 0, bKnight = 0;
            int wRook = 0, wQueen = 0, wPawn = 0, wBishop = 0, wKnight = 0;
            
            for (int i = 0; i < pBlack.length; i++) {
                if (pBlack[i].getType() == ChessPiece.Type.ROOK)
                    bRook++;
                if (pBlack[i].getType() == ChessPiece.Type.QUEEN)
                    bQueen++;
                if (pBlack[i].getType() == ChessPiece.Type.PAWN)
                    bPawn++;
                if (pBlack[i].getType() == ChessPiece.Type.BISHOP)
                    bBishop++;
                if (pBlack[i].getType() == ChessPiece.Type.KNIGHT)
                    bKnight++;
            }
            for (int i = 0; i < pWhite.length; i++) {
                if (pWhite[i].getType() == ChessPiece.Type.ROOK)
                    wRook++;
                if (pWhite[i].getType() == ChessPiece.Type.QUEEN)
                    wQueen++;
                if (pWhite[i].getType() == ChessPiece.Type.PAWN)
                    wPawn++;
                if (pWhite[i].getType() == ChessPiece.Type.BISHOP)
                    wBishop++;
                if (pWhite[i].getType() == ChessPiece.Type.KNIGHT)
                    wKnight++;
            }
            
            if (bRook == 0 && wRook == 0 && bQueen == 0 && wQueen == 0 && bPawn == 0 &&
                    wPawn == 0 && bBishop == 0 && wBishop == 0 && bKnight == 0 &&
                    wKnight == 0)
                return true;
            else if (bRook == 0 && wRook == 0 && bQueen == 0 && wQueen == 0 && bPawn == 0 &&
                    wPawn == 0 && bKnight == 0 && wKnight == 0 && ((bBishop == 1 && wBishop == 0) ||
                    (bBishop == 0 && wBishop == 1)))
                    return true;
            else if (bRook == 0 && wRook == 0 && bQueen == 0 && wQueen == 0 && bPawn == 0 &&
                    wPawn == 0 && bBishop == 0 && wBishop == 0 && ((bKnight == 1 && wKnight == 0) ||
                    (bKnight == 0 && wKnight == 1)))
                return true;
            else if (bRook == 0 && wRook == 0 && bQueen == 0 && wQueen == 0 && bPawn == 0 &&
                    wPawn == 0 && bKnight == 0 && wKnight == 0 && bBishop == 1 && wBishop == 1)
                return true;
            
            return false;
        }
}
