import sun.security.krb5.internal.crypto.Des;

import javax.security.auth.Destroyable;

/**
 * Created by noyz on 3/25/17.
 */

class ChessDad {
    final int boardWidth = 8;
    final int boardHeight = 8;
}

public class Chess extends ChessDad {
    int state; // 0 = normal // 1 = check// 2 = checkMath// 3 = stalemate
    Player player1;
    Player player2;
    String[][] boardArray;
    void boardReloader(){
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                boardArray[i][j]="X";
            }
        }
        for (int i = 0; i < player1.pieces.length; i++) {
            Piece thisPiece= player1.pieces[i];
            if(thisPiece.isAlive) {
                boardArray[thisPiece.location.x][thisPiece.location.y]=thisPiece.type;
            }
        }
        for (int i = 0; i < player2.pieces.length; i++) {
            Piece thisPiece= player2.pieces[i];
            if(thisPiece.isAlive) {
                boardArray[thisPiece.location.x][thisPiece.location.y]=thisPiece.type;
            }
        }
    }
    void boardDrawer(){
        boardReloader();
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
               System.out.print(boardArray[i][j] + " ");
            }
            System.out.println();
        }
    }
    public Chess() {

        state = 0;
        player1 = new Player(true);
        player2 = new Player(false);
        boardArray = new String[boardWidth][boardHeight];
    }
    public Piece pieceFinder(Location loc){
        for (int i = 0; i < player1.pieces.length; i++) {
            Piece thisPiece= player1.pieces[i];
            if(thisPiece.isAlive) {
                if((thisPiece.location.x==loc.x)&&(thisPiece.location.y==loc.y)){
                    return thisPiece;
                }
            }
        }
        for (int i = 0; i < player2.pieces.length; i++) {
            Piece thisPiece= player2.pieces[i];
            if(thisPiece.isAlive) {
                if((thisPiece.location.x==loc.x)&&(thisPiece.location.y==loc.y)){
                    return thisPiece;
                }
            }
        }
        return null;
    }
    public void Mover(Location source, Location Destination){
        boardReloader();
        Piece sourcePiece=null;
        if(boardArray[source.x][source.y]!="X"){
          sourcePiece=  pieceFinder(source);
        }
        if(sourcePiece!=null) {
            if (sourcePiece.isPossibleMove(Destination, boardArray)) {
                sourcePiece.location.x = Destination.x;
                sourcePiece.location.y = Destination.y;
                Piece Dest=pieceFinder(Destination);
                if(Dest!=null){
                    Dest.isAlive=false;
                    Dest.location=null;
                }
            }
        }
    }

}
class Player extends ChessDad {
    final int numberOfPieces = 16;
    final int numberOfPawns = 8;
    final int numberOfKnights = 2;
    final int numberOfQueens = 1;
    final int numberOfKings = 1;
    final int numberOfRooks = 2;
    final int numberOfBishops = 2;
    boolean isPlayerOne;
    Piece[] pieces;

    public Player(boolean isPlayerOne) {
        this.isPlayerOne = isPlayerOne;
        pieces = new Piece[numberOfPieces];
        int y = 7;
        int pawnY = 6;
        if (isPlayerOne) {
            y = 0;
            pawnY = 1;
        }

        int count = 0;
        for (int i = 0; i < numberOfPawns; i++, count++) {
            Location temp_loc = new Location(i, pawnY);
            pieces[count] = new Pawn(isPlayerOne, temp_loc);
        }
        Location temp_loc = new Location(0, y);
        pieces[8] = new Rook(isPlayerOne, temp_loc);
        temp_loc.x = 7;
        pieces[9] = new Rook(isPlayerOne, temp_loc);
        temp_loc.x = 1;
        pieces[10] = new Knight(isPlayerOne, temp_loc);
        temp_loc.x = 6;
        pieces[11] = new Knight(isPlayerOne, temp_loc);
        temp_loc.x = 2;
        pieces[12] = new Bishop(isPlayerOne, temp_loc);
        temp_loc.x = 5;
        pieces[13] = new Bishop(isPlayerOne, temp_loc);
        temp_loc.x = 3;
        pieces[14] = new King(isPlayerOne, temp_loc);
        temp_loc.x = 4;
        pieces[15] = new Queen(isPlayerOne, temp_loc);
    }
}

class Piece extends ChessDad {
    boolean isInMap(Location Destination) {
        if (Destination.x < 0 || Destination.x > (boardWidth - 1) || Destination.y < 0 || Destination.y > (boardHeight - 1))
            return false;
        return true;
    }

    boolean isFoPlayerOne; // Player one is down and player 2 is up
    String type = null;
    Location location;
    boolean isAlive;

    boolean isValidMove(Location Destination, String[][] board) {
        return false;
    }
    boolean isPossibleMove(Location Destination, String[][] board) {
        return false;
    }

    public Piece() {
        location = new Location(0,0);
        isAlive = true;
    }

}

class Location extends ChessDad {
    int x;
    int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

}

class King extends Piece {
    public King(boolean isPlayerOne, Location loc) {
        type = "K";
        this.isFoPlayerOne = isPlayerOne;
        this.location.x = loc.x;
        this.location.y = loc.y;
    }

    @Override
    boolean isValidMove(Location Destination, String[][] board) { // checking validation of the piece NOT THE EARTH VACANCY
        if (!isInMap(Destination))
            return false;
        int xDif = Math.abs(this.location.x - Destination.x);
        int yDif = Math.abs(this.location.y - Destination.y);
        if (((xDif + yDif) != 1)&&(yDif<=1)&&(xDif<=1)) {
            return true;
        } else
            return false;
    }
    @Override
    boolean isPossibleMove(Location Destination, String[][] board){ // checking where it is going but not the checking and mating things
        if(!isValidMove(Destination, board))
            return false;
        if()
    }
}

class Queen extends Piece {
    public Queen(boolean isPlayerOne, Location loc) {
        type = "Q";
        this.isFoPlayerOne = isPlayerOne;
        this.location.x = loc.x;
        this.location.y = loc.y;
    }

    @Override
    boolean isValidMove(Location Destination, String[][] board) {
        if (!isInMap(Destination))
            return false;
        int xDif = Math.abs(this.location.x - Destination.x);
        int yDif = Math.abs(this.location.y - Destination.y);
        if (((yDif == 0) && xDif != 0) || ((xDif == 0) && (yDif != 0)) || ((yDif == xDif) && (yDif != 0))) {
            return true;
        } else
            return false;
    }

}

class Rook extends Piece {
    public Rook(boolean isPlayerOne, Location loc) {
        type = "R";
        this.isFoPlayerOne = isPlayerOne;
        this.location.x = loc.x;
        this.location.y = loc.y;
    }

    @Override
    boolean isValidMove(Location Destination, String[][] board) {
        if (!isInMap(Destination))
            return false;
        int xDif = Math.abs(this.location.x - Destination.x);
        int yDif = Math.abs(this.location.y - Destination.y);
        if (((yDif == 0) && xDif != 0) || ((xDif == 0) && (yDif != 0))) {
            return true;
        }
        return false;
    }

}

class Bishop extends Piece {
    public Bishop(boolean isPlayerOne, Location loc) {
        type = "B";
        this.isFoPlayerOne = isPlayerOne;
        this.location.x = loc.x;
        this.location.y = loc.y;
    }

    @Override
    boolean isValidMove(Location Destination, String[][] board) {
        if (!isInMap(Destination))
            return false;
        int xDif = Math.abs(this.location.x - Destination.x);
        int yDif = Math.abs(this.location.y - Destination.y);
        if (((yDif == xDif) && (yDif != 0))) {
            return true;
        }
        return false;
    }

}

class Knight extends Piece {
    public Knight(boolean isPlayerOne, Location loc) {
        type = "N";
        this.isFoPlayerOne = isPlayerOne;
        this.location.x = loc.x;
        this.location.y = loc.y;
    }

    @Override
    boolean isValidMove(Location Destination, String[][] board) {
        if (!isInMap(Destination))
            return false;
        int xDif = Math.abs(this.location.x - Destination.x);
        int yDif = Math.abs(this.location.y - Destination.y);
        if (((xDif == 1) && (yDif == 2)) || ((xDif == 2) && (yDif == 1)))
            return true;
        return false;
    }

}

class Pawn extends Piece { // WARNING : in this class xDif is defined non ABS
    public Pawn(boolean isPlayerOne, Location loc) {
        type = "P";
        this.isFoPlayerOne = isPlayerOne;
        this.location.x = loc.x;
        this.location.y = loc.y;
    }

    @Override
    boolean isValidMove(Location Destination, String[][] board) {
        if (!isInMap(Destination))
            return false;
        // if destination is not in the map return false
        int xDif = -(this.location.x - Destination.x);
        int yDif = -(this.location.y - Destination.y);
        if (xDif != 0) {
            if (Math.abs(xDif) > 1)
                return false;
            if (isFoPlayerOne) {
                if (yDif == 1)
                    return true;

            } else {
                if (yDif == -1)
                    return true;
            }
        } else {
            if (isFoPlayerOne) {
                if (yDif <= 0)
                    return false;
                if (yDif == 1)
                    return true;
                if ((yDif == 2) && (location.y == 1))
                    return true;

            } else {
                if (yDif > 0)
                    return false;
                int yDifABS = Math.abs(yDif);
                if (yDifABS == 1)
                    return true;
                if ((yDifABS == 2) && (location.y == 6))
                    return true;
            }
        }
        return false;
    }
}

class testChess{
    public static void main(String[] args) {
        Chess chess = new Chess();
        chess.boardDrawer();
    }
}