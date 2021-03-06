package chessdesktop;

import Chess.ChessPiece;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FXMLDocumentController implements Initializable {
	
	ChessBoardRenderer board = new ChessBoardRenderer();

	@FXML
	private Label label;
	@FXML
	private Canvas canvas;
	
	@FXML
	private void handleButtonAction(ActionEvent event) {
		board = new ChessBoardRenderer();
		board.draw(canvas);
                label.setText("");
	}
	
	@FXML
	private void handleSaveButtonAction(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Game");
		File file = fileChooser.showSaveDialog(null);
		if (file != null) {
			Charset charset = Charset.forName("US-ASCII");
                        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), charset)) {
                            String s = "ChessGame\n";
                            writer.write(s);
                            s = board.getCurrentColor() + "\n";
                            writer.write(s, 0, s.length());
                            writer.close();
                            board.getBoard().saveToFile(file);
                        }
                        catch (IOException x) {
                                System.err.format("IOException: %s%n", x);
                        }
                        catch (Exception ex) {
                                System.err.format("Unexpected error: %s%n", ex);
                        }
		}
	}

	@FXML
	private void handleLoadButtonAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Chess Files", "*.chess.xml"));
		File selectedFile = fileChooser.showOpenDialog(null);
		if (selectedFile != null) {
			try {
				Scanner in = new Scanner(selectedFile);
                                String header = in.next();
                                String color = in.next();
                                if (header.equals("ChessGame") && (color.equals("BLACK") ||
                                        color.equals("WHITE"))) {
                                    if (board.getBoard().loadFromFile(selectedFile)) {
                                        board.setCurrentColor(Chess.ChessPiece.Color.valueOf(color));
                                        label.setText("");
                                    }
                                    else {
                                        label.setText("Archivo no válido.");
                                    }
                                }
                                else {
                                    label.setText("Archivo no válido.");
                                }
			} catch (IOException ex) {
				Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
			}
			board.draw(canvas);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		board.draw(canvas);
		
		canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->{
			Chess.ChessPiece piece = board.getPieceAt(canvas, e.getX(), e.getY());
			if (board.getMovingPiece() == piece) {
				board.setMovingPiece(null);
				board.draw(canvas);
				return;
			}
			if (board.getMovingPiece() == null) {
				board.setMovingPiece(piece);
				board.draw(canvas);
				return;
			}
			if (board.movePieceTo(canvas, e.getX(), e.getY())) {
				board.setMovingPiece(null);
				board.draw(canvas);
				if (!board.containsKing(ChessPiece.Color.BLACK) || !board.containsKing(ChessPiece.Color.WHITE)) {
					if (!board.containsKing(ChessPiece.Color.BLACK))
						label.setText("Ganan las blancas");
					else
						label.setText("Ganan las negras");
				}
                                else if (board.isTie())
                                    label.setText("Empate!");
			}
		});
	}	
	
}
