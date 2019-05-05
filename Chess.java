import java.util.Set;
import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.BorderLayout;

class Chess {
  Chess() {
    ChessPanel chessPanel = new ChessPanel();
    chessPanel.setBounds(30, 30, 300, 300);
    JFrame f = new JFrame("Chess");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add(chessPanel, BorderLayout.CENTER);
    f.setSize(400, 400);
    f.setLayout(null);
    f.setVisible(true);
  }

  public static void main(String[] args) {
    Engine engine = new Engine(Engine.initPieces());
    System.out.println(engine);

    new Chess();
  }
}

class ChessPanel extends JPanel {
  private int originX = 37;
  private int originY = 27;
  private int cellSide = 31;

  @Override
  public void paintComponent(Graphics g) {
    drawGrid(g);
  }

  private void drawGrid(Graphics g) {
    for (int row = 0; row < 8; row++) {
      for (int col = 0; col < 8; col++) {
        g.setColor((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);
        g.fillRect(originX + col * cellSide, originY + row * cellSide, cellSide, cellSide);
      }
    }
  }
}

class Engine {
  private boolean isWhiteTurn = true;

  private Set<Piece> pieces;

  Engine(Set<Piece> pieces) {
    this.pieces = pieces;
  }
  
  static Set<Piece> initPieces() {
    Set<Piece> pieces = new HashSet<Piece>();
    for (int i = 0; i < 8; i++) {
      pieces.add(new Piece(i, 1, Rank.PAWN, false)); 
      pieces.add(new Piece(i, 6, Rank.PAWN, true)); 
    }
    for (int i = 0; i < 2; i++) {
      pieces.add(new Piece(i * 7, 0, Rank.ROOK, false)); 
      pieces.add(new Piece(i * 7, 7, Rank.ROOK, true)); 
      pieces.add(new Piece(1 + i * 5, 0, Rank.KNIGHT, false)); 
      pieces.add(new Piece(1 + i * 5, 7, Rank.KNIGHT, true)); 
      pieces.add(new Piece(2 + i * 3, 0, Rank.BISHOP, false)); 
      pieces.add(new Piece(2 + i * 3, 7, Rank.BISHOP, true)); 
    }
    pieces.add(new Piece(3, 0, Rank.QUEEN, false)); 
    pieces.add(new Piece(3, 7, Rank.QUEEN, true)); 
    pieces.add(new Piece(4, 0, Rank.KING, false)); 
    pieces.add(new Piece(4, 7, Rank.KING, true)); 
    return pieces;
  }

  private Piece pieceAt(int col, int row) {
    for (Piece p: pieces) {
      if (p.col == col && p.row == row) {
        return p;
      }
    }
    return null;
  }

  public String toString() {
    int rows = 8;
    int cols = 8;
    String brdStr = "";
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        Piece p = pieceAt(c, r);
        if (p== null) {
          brdStr += " .";
        } else {
          switch (p.rank) {
            case PAWN: brdStr += p.isWhite ? " P" : " p"; break;
            case ROOK: brdStr += p.isWhite ? " R" : " r"; break;
            case KNIGHT: brdStr += p.isWhite ? " N" : " n"; break;
            case BISHOP: brdStr += p.isWhite ? " B" : " b"; break;
            case QUEEN: brdStr += p.isWhite ? " Q" : " q"; break;
            case KING: brdStr += p.isWhite ? " K" : " k"; break;
          }
        }
      }
      brdStr += "\n";
    }
    return brdStr;
  } 
}

enum Rank {
  KING,
  QUEEN,
  BISHOP,
  KNIGHT,
  ROOK,
  PAWN,
}

class Piece {
  int col;
  int row;
  Rank rank;
  boolean isWhite;

  Piece(int col, int row, Rank rank, boolean isWhite) {
    this.col = col;
    this.row = row;
    this.rank = rank;
    this.isWhite = isWhite;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof Piece)) return false;
    Piece otherPiece = (Piece)other;
    return otherPiece.col == col && otherPiece.row == row && otherPiece.rank == rank && otherPiece.isWhite == isWhite;
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + col;
    result = 31 * result + row;
    result = 31 * result + rank.hashCode();
    result = 31 * result + (isWhite ? 1 : 0);
    return result;
  }
}
