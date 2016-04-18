package Chess;

public class PiecePosition {

	/**
         * Este método comprueba que la columna y la fila introducida son válidos
         * @param column
         * @param row
         * @return True si es válido, false si no lo es
         */
	public static boolean isAvailable(int column, int row) {
		return column >= 0 && column < 8 && row >= 0 && row < 8;
	}

	/**
         * Este método comprueba si la posicion introducida es válida y si la 
         * posicion a la que se quiere mover es válido o no
         * @param position
         * @param columnIncrement
         * @param rowIncrement
         * @return True si se puede mover a la columna y fila indicada, false si no
         * se puede
         */
	static boolean isAvailable(PiecePosition position, int columnIncrement, int rowIncrement) {
		if (position == null)
			return false;
		
		int newColumn = position.getColumn() + columnIncrement;
		int newRow = position.getRow() + rowIncrement;
		return isAvailable(newColumn, newRow);
	}

	/**
         * Este método comprueba si la posición introducida es válida
         * @param position
         * @return True si la posición es válida, false si no lo es
         */
	static boolean isAvailable(PiecePosition position) {
		if (position == null)
			return false;
		return isAvailable(position.getColumn(), position.getRow());
	}

	private int column, row;

	/**
         * Constructor para crear una posición
         * @param column
         * @param row 
         */
	public PiecePosition(int column, int row) {
            if (isAvailable(column, row)) {
		this.column = column;
		this.row = row;
            }
	}
	
	/**
         * Este método devuelve el valor column (coordenada x) de la posición
         * @return column
         */
	public int getColumn() {
		return column;
	}

	/**
         * Este método devuelve el valor row (coordenada y) de la posición
         * @return row
         */
	public int getRow() {
		return row;
	}
	
        /**
         * Este método asigna valores a los atributos de la posición
         * @param column
         * @param row
         * @return True si se ha podido modificar, false si no ha sido posible la
         * modificación
         */
	public boolean setValues(int column, int row) {
		if (isAvailable(column, row)) {
			this.column = column;
			this.row = row;			
			return true;
		}
		return false;
	}
	
	/**
         * Este método comprueba que a partir de la posición de la pieza actual 
         * se puede incrementar la posición de la pieza con los valores introducidos
         * @param columnCount
         * @param rowCount
         * @return PiecePosition en caso de que se pueda mover la pieza a la nueva
         * posición, y null, en caso contrario
         */
	public PiecePosition getDisplacedPiece(int columnCount, int rowCount) {		
		if (!isAvailable(this, columnCount, rowCount))
			return null;
		int newColumn = getColumn() + columnCount;
		int newRow = getRow() + rowCount;
		return new PiecePosition(newColumn, newRow);
	}
	
	/**
         * Este método devuelve una copia de la posición actual
         * @return PiecePosition
         */
	public PiecePosition clone() {
		return new PiecePosition(column, row);
	}
	
	/**
         * Este método comprueba si la posición seleccionada es igual a la de la
         * pieza actual
         * @param aPosition
         * @return True si es igual a la posición de la pieza, false si no lo es
         */
	public boolean equals(PiecePosition aPosition) {
		return aPosition.getColumn() == getColumn() && aPosition.getRow() == getRow();
	}
}
