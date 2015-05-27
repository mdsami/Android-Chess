package chess.vieck.purdue.edu.chess;

import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Michael on 5/2/2015.
 */
public class Core {

    private Player white, black;

    private objectColour turn;

    private gameState currentGameState;

    public Integer[] boardSquares;

    public Piece[] pieceArray;

    protected enum pieceType {
        pawn, bishop, knight, rook, queen, king
    }

    protected enum objectColour {
        black, white
    }

    protected enum pieceState {
        alive, dead
    }

    protected enum gameState {
        allClear, whiteCheck, whiteMate, blackCheck, blackMate, stalemate
    }

    protected enum moveStatus {
        success, fail, promote
    }

    // .ctor
    public Core() {
        // if new game
        createBoard();
        turn = objectColour.white;
        currentGameState = gameState.allClear;
        white = new Player(objectColour.white);
        black = new Player(objectColour.black);
    }

    public void createBoard() {
        boardSquares = new Integer[64];
        pieceArray = new Piece[64];
        for (int i = 0; i < 64; i++) {
            if ((i % 2) == 0) {
                boardSquares[i] = R.drawable.blacksquare;
            } else {
                boardSquares[i] = R.drawable.whitesquare;
            }
        }
        for (int i = 0; i < 64; i++) {

            if ((i >= 48 && i <= 55)) {
                pieceArray[i] = new Piece(objectColour.white, pieceType.pawn, i, new Pawn(), R.drawable.wpawn);
            } else if (i >= 8 && i <= 15) {
                pieceArray[i] = new Piece(objectColour.black, pieceType.pawn, i, new Pawn(), R.drawable.bpawn);
            } else if (i == 0 || i == 7) {
                pieceArray[i] = new Piece(objectColour.black, pieceType.rook, i, new Rook(), R.drawable.brook);
            } else if (i == 56 || i == 63) {
                pieceArray[i] = new Piece(objectColour.white, pieceType.rook, i, new Rook(), R.drawable.wrook);
            } else if (i == 1 || i == 6) {
                pieceArray[i] = new Piece(objectColour.black, pieceType.knight, i, new Knight(), R.drawable.bknight);
            } else if (i == 57 || i == 62) {
                pieceArray[i] = new Piece(objectColour.white, pieceType.knight, i, new Knight(), R.drawable.wknight);
            } else if (i == 2 || i == 5) {
                pieceArray[i] = new Piece(objectColour.black, pieceType.bishop, i, new Bishop(), R.drawable.bbishop);
            } else if (i == 58 || i == 61) {
                pieceArray[i] = new Piece(objectColour.white, pieceType.bishop, i, new Bishop(), R.drawable.wbishop);
            } else if (i == 3) {
                pieceArray[i] = new Piece(objectColour.black, pieceType.queen, i, new Queen(), R.drawable.bqueen);
            } else if (i == 59) {
                pieceArray[i] = new Piece(objectColour.white, pieceType.queen, i, new Queen(), R.drawable.wqueen);
            } else if (i == 4) {
                pieceArray[i] = new Piece(objectColour.black, pieceType.king, i, new King(), R.drawable.bking);
            } else if (i == 60) {
                pieceArray[i] = new Piece(objectColour.white, pieceType.king, i, new King(), R.drawable.wking);
            } else {
                pieceArray[i] = null;
            }
        }
    }

    public void updateBoard(ArrayList<Integer> availableMoves) {
        for (int i = 0; i < 64; i++) {

        }
    }

    public int getBoardLength() {
        if (boardSquares != null) {
            return boardSquares.length;
        } else {
            return 0;
        }
    }

    public objectColour getTurn() {
        return turn;
    }

    public void setTurn(objectColour turn) {
        this.turn = turn;
    }

    public Piece getPiece(int position){
        return pieceArray[position];
    }

    public void setPiece(int loc, Piece piece){
        pieceArray[loc] = piece;
    }
    public Integer squareImage(int position) {
        return boardSquares[position % 2];
    }

    public Integer pieceImage(int position) {
        if (pieceArray[position] != null)
            return pieceArray[position].getImageResource();
        return null;
    }

    // move: moves the Piece if the move is valid; returns false otherwise
    public boolean move(int from, int to) throws Exception {
        Player currentPlayer = (turn == objectColour.white) ? white : black;
        Log.d("Move","pieceArray["+from+"]:"+pieceArray[from].getPieceColour() + " currentPlayer:" + currentPlayer.getColour());
        if (pieceArray[from] == null || pieceArray[from].getPieceColour() != currentPlayer.getColour() || to < 0 || to > 63)
            return false;
        // TODO: cache this so that we're not constantly re-populating
        ArrayList<Integer> availableMoves = pieceArray[from].getAvailableMoves();
        if (availableMoves.contains(to)) {
            moveStatus status = pieceArray[from].tryMove(to);
            if (status != moveStatus.promote) {
                if (status == moveStatus.success) {
                    return true;
                } else
                    return false;
            } else {

                try {
                    pieceType type = pieceType.bishop;
                    // TODO: promotion logic
                    // promotion code: UI, etc.
                    pieceArray[from].setPieceType(type);
                    return true;
                } catch (incompatiblePieceTypeConversionException ex) {
                    throw new Exception(
                            "MAN, WHAT THE FUCK!? Promotion code is seriously jacked up.");
                }
            }
        }
        return false;
    }


    private static gameState checkForCheck(Piece[] pieceArray, objectColour whoseCheck) {
   /*     Piece own[];
        Piece opponent[];
        if (whoseCheck == objectColour.white) {
            own = white.getPieces();
            opponent = black.getPieces();
        } else {
            own = black.getPieces();
            opponent = white.getPieces();
        }
        for (int i = 0; i < 15; i++) {
            ArrayList<Integer> moves = opponent[i].getAvailableMoves();
            if (moves != null
                    && (!moves.isEmpty() && moves.contains(own[15].getLocation()))) {
                if (whoseCheck == objectColour.white)
                    return gameState.whiteCheck;
                else
                    return gameState.blackCheck;
            }
        }*/
        return gameState.allClear;
    }

    private gameState checkForMate() {
        // TODO: check for stalemate here
        /*
        ArrayList<Integer> whiteMoves = new ArrayList<Integer>();
        ArrayList<Integer> blackMoves = new ArrayList<Integer>();

        for (int i = 0; i < 15; i++) {
            ArrayList<Integer> moves = white.getPieces()[i].getAvailableMoves();
            for (int j = 0; j < moves.size(); j++)
                whiteMoves.add(moves.get(j));
            moves = black.getPieces()[i].getAvailableMoves();
            for (int j = 0; j < moves.size(); j++)
                blackMoves.add(moves.get(j));
        }
        if (blackMoves.size() == 0) {
            if (checkForCheck(pieceArray, objectColour.black) == gameState.blackCheck)
                return gameState.blackMate;
            else
                return gameState.stalemate;
        }
        if (whiteMoves.size() == 0) {
            if (checkForCheck(pieceArray, objectColour.white) == gameState.whiteCheck)
                return gameState.whiteMate;
            else
                return gameState.stalemate;
        }
        if (checkForCheck(pieceArray, objectColour.white) == gameState.whiteCheck)
            return gameState.whiteCheck;
        if (checkForCheck(pieceArray, objectColour.black) == gameState.blackCheck)
            return gameState.blackCheck;
            */
        return gameState.allClear;
    }

    private interface availableMoves {
        public ArrayList<Integer> getAvailableMoves(Piece Piece);
    }

    private static class Player {
        // properties
        private objectColour colour;

        // .ctor
        Player(objectColour colour) {
            this.colour = colour;
        }

        public objectColour getColour() {
            return colour;
        }


    }

    public static class incompatiblePieceTypeConversionException extends Exception {
        private static final long serialVersionUID = 1L;

        public incompatiblePieceTypeConversionException(String string) {
            super(string);
        }
    }

    public class Piece {
        // colour of the Piece
        protected objectColour colour;
        // properties
        private int location;
        // state of the Piece
        private pieceState state;
        private ImageView imageResource;
        // type of Piece
        private pieceType type;

        // gets a list of moves that don't go off the board or cause friendly
        // fire
        private availableMoves availMoves;

        Piece(objectColour colour, pieceType type, int location,
              availableMoves movementPattern, ImageView imageResource) {
            this.colour = colour;
            this.type = type;
            this.location = location;
            this.availMoves = movementPattern;
            this.imageResource = imageResource;
            this.state = pieceState.alive;
        }

        public int getLocation() {
            return location;
        }

        public pieceState getPieceState() {
            return state;
        }

        public void setPieceState(pieceState newState) {
            state = newState;
            if (newState == pieceState.dead) {
                location = -1;
            }
        }

        public ImageView getImageResource() {
            return imageResource;
        }

        public void setImageResource(ImageView imageResource) {
            this.imageResource = imageResource;
        }

        public objectColour getPieceColour() {
            return colour;
        }

        public pieceType getPieceType() {
            return type;
        }

        public void setPieceType(pieceType newType)
                throws incompatiblePieceTypeConversionException {
            if (type != pieceType.pawn)
                throw new incompatiblePieceTypeConversionException(
                        "Error: can only convert pawns");
            else {
                switch (newType) {
                    case bishop:
                        availMoves = new Bishop();
                        break;
                    case knight:
                        availMoves = new Knight();
                        break;
                    case rook:
                        availMoves = new Rook();
                        break;
                    case queen:
                        availMoves = new Queen();
                        break;
                    case pawn:
                    case king:
                        throw new incompatiblePieceTypeConversionException(
                                "Error: invalid conversion from Pawn.");
                }

            }
        }

        public boolean isValidMove(int toLocation) {
            int fromLocation = this.getLocation();
            Piece oldPiece = pieceArray[fromLocation];
            Piece newPiece = pieceArray[toLocation];

            if (newPiece != null)
                if (newPiece.getPieceColour() == this.colour)
                    return false;


            newPiece = oldPiece;
            oldPiece = null;
            this.location = toLocation;
            this.setPieceState(pieceState.alive);

            gameState tryState = null;//= checkForCheck(pieceArray, this.colour);

            //revert
            oldPiece = newPiece;
            newPiece = null;
            this.location = oldPiece.getLocation();
            this.setPieceState(pieceState.alive);
            if (oldPiece != null) {
                oldPiece.location = toLocation;
                oldPiece.setPieceState(pieceState.alive);
            }

            if ((colour == objectColour.white && tryState == gameState.whiteCheck)
                    || (colour == objectColour.black && tryState == gameState.blackCheck))
                return false;
            return true;
        }

        public moveStatus tryMove(int moveToLocation) {
            //Todo: create a better move checker.
            if (!isValidMove(moveToLocation))
                return moveStatus.fail;

            pieceArray[moveToLocation] = pieceArray[location];
            pieceArray[location] = null;

            // update self location
            this.location = moveToLocation;

            if (type == pieceType.pawn) {
                return ((location >= 0 && location <= 7) || (location >= 56 || location <= 63)) ? moveStatus.promote : moveStatus.success;
            } else if (type == pieceType.rook) {
                return moveStatus.success;
            }

            return moveStatus.success;
        }

        public ArrayList<Integer> getAvailableMoves() {
            return availMoves.getAvailableMoves(this);
        }

    }

    public class Pawn implements availableMoves {
        // only Piece whose moves depend on colour
        public ArrayList<Integer> getAvailableMoves(Piece piece) {
            if (piece.getLocation() == 0)
                return null;
            ArrayList<Integer> retList = new ArrayList<Integer>();
            int currLocation = piece.getLocation();

            //TODO: fix pieceArray and do not check for white and black, just opposite
            // as standard, white is on bottom, black on top
            // white moves 6 -> 0; black moves 1 -> 7
            if (piece.getPieceColour() == objectColour.white) {
                // check moving left
                if (currLocation % 8 != 0
                        && pieceArray[currLocation + 7] != null
                        && pieceArray[currLocation + 7]
                        .getPieceColour() == objectColour.black)
                    retList.add(currLocation + 7);

                // check moving right
                if (currLocation - 7 % 8 != 0
                        && pieceArray[currLocation + 9] != null
                        && pieceArray[currLocation + 9].getPieceColour() == objectColour.black)
                    retList.add(currLocation + 9);

                // default location, making 4 available moves, rather than 3
                if (pieceArray[currLocation + 1] == null) {
                    if (currLocation >= 8 && currLocation <= 15) {
                        retList.add(currLocation + 16);
                    } else {
                        retList.add(currLocation + 16);
                    }
                } else {
                    // COPY PASTA! YAAY! : (
                    // move forward one Cell
                    if (currLocation > 0 && pieceArray[currLocation - 1] == null) {
                        if (currLocation >= 56 && currLocation <= 63) {
                            retList.add(currLocation - 16);
                        } else {
                            retList.add(currLocation - 8);
                        }
                    }

                    // check moving left
                    if (currLocation > 0 && currLocation % 8 != 0
                            && pieceArray[currLocation - 9] != null
                            && pieceArray[currLocation - 7].getPieceColour() == objectColour.white)
                        retList.add(currLocation - 9);

                    // check moving right
                    if (currLocation > 0 && currLocation - 7 % 8 != 0
                            && pieceArray[currLocation - 7] != null
                            && pieceArray[currLocation - 7].getPieceColour() == objectColour.white)
                        retList.add(currLocation - 7);
                }
            }
            return retList;
        }
    }

    protected class Bishop implements availableMoves {
        public ArrayList<Integer> getAvailableMoves(Piece piece) {
            if (piece.getLocation() == 0)
                return null;
            ArrayList<Integer> retList = new ArrayList<>();
            int currLocation = piece.getLocation();
            int i = 0;

            // right, up
            while ((currLocation + i != 7 * (currLocation / 8) + (currLocation % 7)) && currLocation < 56
                    && pieceArray[currLocation + i] == null || pieceArray[currLocation + i].getPieceColour() != piece.getPieceColour()) {
                // if the Piece is one of the opponent's, it is a valid move
                if (pieceArray[currLocation + i] != null
                        && pieceArray[currLocation + i].getPieceColour() != piece.getPieceColour()) {
                    retList.add(currLocation + i);
                    break;
                }
                retList.add(currLocation + i);
                i += 9;
            }

            i = 0;
            // right, down
            while ((currLocation - i != 7 * (currLocation / 8) + (currLocation % 7)) && currLocation > 7
                    && (pieceArray[currLocation - i] == null || pieceArray[currLocation - i].getPieceColour() != piece.getPieceColour())) {
                if (pieceArray[currLocation - i] != null
                        && pieceArray[currLocation - i].getPieceColour() != piece.getPieceColour()) {
                    retList.add(currLocation - i);
                    break;
                }
                retList.add(currLocation - i);
                i += 7;
            }

            i = 0;
            // left, down
            while ((currLocation > -1 && currLocation - i != 8 * (currLocation / 8) + (currLocation % 8) && currLocation > 7)
                    && (pieceArray[currLocation - i] == null || pieceArray[currLocation - i].getPieceColour() != piece.getPieceColour())) {
                if (pieceArray[currLocation - i] != null
                        && pieceArray[currLocation - i].getPieceColour() != piece.getPieceColour()) {
                    retList.add(currLocation - i);
                    break;
                }
                retList.add(currLocation - i);
                i += 9;
            }

            i = 0;
            // left, up
            while ((currLocation < 55 && currLocation + i != 8 * (currLocation / 8) + (currLocation % 8))
                    && (pieceArray[currLocation + i] == null || pieceArray[currLocation + i].getPieceColour() != piece.getPieceColour())) {
                if (pieceArray[currLocation + i] != null
                        && pieceArray[currLocation + i].getPieceColour() != piece.getPieceColour()) {
                    retList.add(currLocation + i);
                    break;
                }
                retList.add(currLocation + i);
                i += 7;
            }

            return retList;
        }
    }

    protected class Knight implements availableMoves {
        public ArrayList<Integer> getAvailableMoves(Piece piece) {
            if (piece.getLocation() == 0)
                return null;
            ArrayList<Integer> retList = new ArrayList<Integer>();
            int currLocation = piece.getLocation();

            //Two over and one up
            if (currLocation < 54 && currLocation - 6 % 8 != 0 && currLocation - 7 % 8 != 0
                    && (pieceArray[currLocation + 10] == null || pieceArray[currLocation + 10].getPieceColour() != piece.getPieceColour()))
                retList.add(currLocation + 10);

            //Two back and one up
            if (currLocation % 8 != 0 && (currLocation - 1 % 8) != 0
                    && (pieceArray[currLocation + 6] == null || pieceArray[currLocation + 6].getPieceColour() != piece.getPieceColour()))
                retList.add(currLocation + 6);

            //One over and two up
            if (currLocation < 47 && currLocation - 7 % 8 != 0
                    && (pieceArray[currLocation + 17] == null || pieceArray[currLocation + 17].getPieceColour() != piece.getPieceColour()))
                retList.add(currLocation + 17);

            //One over and two up
            if (currLocation % 8 != 0 && currLocation < 48
                    && (pieceArray[currLocation + 15] == null || pieceArray[currLocation + 15].getPieceColour() != piece.getPieceColour()))
                retList.add(currLocation + 15);

            //Two over and one down
            if (currLocation >= 13 && currLocation - 6 % 8 != 0 && currLocation - 7 % 8 != 0
                    && (pieceArray[currLocation - 6] == null || pieceArray[currLocation - 6].getPieceColour() != piece.getPieceColour()))
                retList.add(currLocation - 6);

            //Two back and one down
            if (currLocation >= 10 && currLocation % 8 != 0 && currLocation - 1 % 8 != 0
                    && (pieceArray[currLocation - 10] == null || pieceArray[currLocation - 10].getPieceColour() != piece.getPieceColour()))
                retList.add(currLocation - 10);

            //One over and two down
            if (currLocation >= 16 && currLocation - 7 % 8 != 0
                    && (pieceArray[currLocation - 15] == null || pieceArray[currLocation - 15].getPieceColour() != piece.getPieceColour()))
                retList.add(currLocation - 15);

            //One back and two down
            if (currLocation > 17 && currLocation % 8 != 0
                    && (pieceArray[currLocation - 17] == null || pieceArray[currLocation - 17].getPieceColour() != piece.getPieceColour()))
                retList.add(currLocation - 17);

            return retList;
        }
    }

    protected class Rook implements availableMoves {
        public ArrayList<Integer> getAvailableMoves(Piece piece) {
            if (piece.getLocation() == 0)
                return null;
            ArrayList<Integer> retList = new ArrayList<Integer>();
            int currLocation = piece.getLocation();
            int i = 1;

            // right
            while (i < (8 - (currLocation % 8))
                    && (pieceArray[currLocation + i] == null || pieceArray[currLocation + i].getPieceColour() != piece.getPieceColour())) {
                if (pieceArray[currLocation + i] != null
                        && pieceArray[currLocation + i].getPieceColour() != piece.getPieceColour()) {
                    retList.add(currLocation + i);
                    break;
                }
                retList.add(currLocation + i);
                i++;
            }

            i = 1;
            // left
            while (i < (8 - (currLocation % 8))
                    && (pieceArray[currLocation - i] == null || pieceArray[currLocation - i].getPieceColour() != piece.getPieceColour())) {
                if (pieceArray[currLocation - i] != null
                        && pieceArray[currLocation - i].getPieceColour() != piece.getPieceColour()) {
                    retList.add(currLocation - i);
                    break;
                }
                retList.add(currLocation - i);
                i++;
            }

            i = 8;
            // down
            while (currLocation - i > 0
                    && (pieceArray[currLocation - i] == null || pieceArray[currLocation - i].getPieceColour() != piece.getPieceColour())) {
                if (pieceArray[currLocation - i] != null
                        && pieceArray[currLocation - i].getPieceColour() != piece.getPieceColour()) {
                    retList.add(currLocation - i);
                    break;
                }
                retList.add(currLocation - i);
                i += 8;
            }

            i = 1;
            // up
            while (currLocation + i <= 63
                    && (pieceArray[currLocation + i] == null || pieceArray[currLocation + i].getPieceColour() != piece.getPieceColour())) {
                if (pieceArray[currLocation + i] != null
                        && pieceArray[currLocation + i].getPieceColour() != piece.getPieceColour()) {
                    retList.add(currLocation + i);
                    break;
                }
                retList.add(currLocation + i);
                i++;
            }
            return retList;
        }
    }

    protected class Queen implements availableMoves {
        private Rook horizontalVerical;
        private Bishop diagonal;

        public ArrayList<Integer> getAvailableMoves(Piece piece) {
            if (piece.getLocation() == 0)
                return null;
            horizontalVerical = new Rook();
            diagonal = new Bishop();
            ArrayList<Integer> retList = horizontalVerical
                    .getAvailableMoves(piece);
            ArrayList<Integer> moreMoves = diagonal.getAvailableMoves(piece);
            for (int i = 0; i < moreMoves.size(); i++) {
                retList.add(moreMoves.get(i));
            }
            return retList;
        }
    }

    protected class King implements availableMoves {
        public ArrayList<Integer> getAvailableMoves(Piece piece) {
            if (piece.getLocation() == 0)
                return null;
            ArrayList<Integer> retList = new ArrayList<>();
            int currLocation = piece.getLocation();
            // if the King moves and there is a check, regardless of the colour
            // it is an invalid move.
            // TODO:implement King moves in a way that won't cause infinite
            // loops
            // for example: checkForCheck doesn't look at the King. That would
            // do it
            // Also: don't check for mate here.

            //Foward Right
            if (currLocation < 63
                    && (pieceArray[currLocation + 9] == null))
                retList.add(currLocation + 9);
            //Foward Left
            if (currLocation % 8 != 0 && (pieceArray[currLocation + 6] == null))
                retList.add(currLocation + 6);
            //Foward Straight
            if (currLocation < 56
                    && (piece.isValidMove(currLocation + 8)))
                retList.add(currLocation + 8);
            //Right
            if (currLocation - 7 % 8 != 0 && (piece.isValidMove(currLocation + 1)))
                retList.add(currLocation + 1);
            //Left
            if (currLocation % 8 != 0
                    && (piece.isValidMove(currLocation - 1)))
                retList.add(currLocation - 1);
            //Back Left
            if (currLocation >= 9 && currLocation % 8 != 0 && (piece.isValidMove(currLocation - 9)))
                retList.add(currLocation - 9);
            //Back Right
            if (currLocation - 7 % 8 != 0
                    && (piece.isValidMove(currLocation - 7)))
                retList.add(currLocation - 7);
            //Back Straight
            if (currLocation > 7 && piece.isValidMove(currLocation - 1))
                retList.add(currLocation - 1);

            return retList;
        }
    }
}
