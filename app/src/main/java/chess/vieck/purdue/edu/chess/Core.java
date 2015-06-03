package chess.vieck.purdue.edu.chess;

import java.util.ArrayList;

public class Core {

    public Integer[] boardSquares;
    public Piece[] pieceArray;
    protected int bkingpos = 4;
    protected int wkingpos = 60;
    ArrayList<Integer> availableMoves;
    boolean check = false;
    private Player white, black;
    private objectColour turn;

    public Core() {
        createBoard();
        turn = objectColour.white;
        white = new Player(objectColour.white);
        black = new Player(objectColour.black);
    }

    public objectColour getTurn() {
        return turn;
    }

    public void setTurn() {
        turn = (turn == objectColour.white) ? objectColour.black : objectColour.white;
    }

    public Piece getPiece(int position) {
        return pieceArray[position];
    }

    public void setPiece(int loc, Piece piece) {
        pieceArray[loc] = piece;
    }

    public Integer squareImage(int position) {
        return boardSquares[position % 2];
    }

    public Integer getPieceImage(int position) {
        if (pieceArray[position] != null)
            return pieceArray[position].getImageResource();
        return null;
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

    // move: moves the Piece if the move is valid; returns false otherwise
    public boolean move(int from, int to) throws Exception {

        Player currentPlayer = (getTurn() == objectColour.white) ? white : black;
        if (pieceArray[from] == null || pieceArray[from].getPieceColour() != currentPlayer.getColour() || to < 0 || to > 63)
            return false;
        availableMoves = pieceArray[from].getAvailableMoves();

        if (availableMoves.contains(to)) {
            return true;
        } else if (pieceArray[from].getPieceType() == pieceType.king) {
            //pieceType type = pieceType.bishop;
            // TODO: promotion logic

        }
        return false;
    }

    public boolean testCheck(int location) {
        if (getTurn() == objectColour.white) {
            if (pieceArray[location].getAvailableMoves().contains(bkingpos)) {
                check = true;
                return true;
            }
        } else {
            if (pieceArray[location].getAvailableMoves().contains(wkingpos)) {
                check = true;
                return true;
            }
        }
        return false;
    }

    public boolean testCheckMate(int location) {
        return pieceArray[location].getAvailableMoves() == null;
    }

    protected enum pieceType {
        pawn, bishop, knight, rook, queen, king
    }

    protected enum objectColour {
        black, white
    }


    private interface availableMoves {
        ArrayList<Integer> getAvailableMoves(Piece Piece);
    }

    private static class Player {

        private objectColour colour;

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

        protected objectColour colour;

        private int location;

        private int imageResource;

        private pieceType type;

        private availableMoves availMoves;

        Piece(objectColour colour, pieceType type, int location,
              availableMoves movementPattern, int imageResource) {
            this.colour = colour;
            this.type = type;
            this.location = location;
            this.availMoves = movementPattern;
            this.imageResource = imageResource;
        }

        public int getLocation() {
            return location;
        }

        public void setLocation(int location) {
            this.location = location;
        }

        public int getImageResource() {
            return imageResource;
        }

        /*public void setImageResource(int imageResource) {
            this.imageResource = imageResource;
        }*/

        public objectColour getPieceColour() {
            return colour;
        }

        public pieceType getPieceType() {
            return type;
        }

        public void setPieceType(pieceType newType)
                throws incompatiblePieceTypeConversionException {
           /* if (type != pieceType.pawn)
                throw new incompatiblePieceTypeConversionException(
                        "Error: can only convert pawns");
            else {*/
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

        public ArrayList<Integer> getAvailableMoves() {
            return availMoves.getAvailableMoves(this);
        }

    }

    public class Pawn implements availableMoves {
        // only Piece whose moves depend on colour
        public ArrayList<Integer> getAvailableMoves(Piece piece) {

            ArrayList<Integer> retList = new ArrayList<>();
            int currLocation = piece.getLocation();

            if (piece.getPieceColour() == objectColour.white) {
                if (currLocation > 7) {
                    // move forward one Cell
                    if (pieceArray[currLocation - 8] == null) {
                        if (pieceArray[currLocation - 16] == null && currLocation >= 48 && currLocation <= 55) {
                            retList.add(currLocation - 16);
                        }
                        retList.add(currLocation - 8);
                    }

                    // check moving left
                    if (currLocation % 8 != 0
                            && pieceArray[currLocation - 9] != null
                            && pieceArray[currLocation - 9].getPieceColour() == objectColour.black)
                        retList.add(currLocation - 9);

                    // check moving right
                    if ((currLocation - 7) % 8 != 0
                            && pieceArray[currLocation - 7] != null
                            && pieceArray[currLocation - 7].getPieceColour() == objectColour.black)
                        retList.add(currLocation - 7);
                }
            } else {
                if (currLocation < 56) {
                    // check moving left
                    if (currLocation % 8 != 0
                            && pieceArray[currLocation + 7] != null
                            && pieceArray[currLocation + 7]
                            .getPieceColour() == objectColour.white)
                        retList.add(currLocation + 7);

                    // check moving right
                    if ((currLocation - 7) % 8 != 0
                            && pieceArray[(currLocation + 9)] != null
                            && pieceArray[(currLocation + 9)].getPieceColour() == objectColour.white)
                        retList.add(currLocation + 9);

                    // default location, making 4 available moves, rather than 3
                    if (pieceArray[currLocation + 8] == null) {
                        if (currLocation >= 8 && currLocation <= 15) {
                            retList.add(currLocation + 16);
                        }
                        retList.add(currLocation + 8);
                    }
                }
            }
            return retList;
        }
    }

    protected class Bishop implements availableMoves {
        public ArrayList<Integer> getAvailableMoves(Piece piece) {

            ArrayList<Integer> retList = new ArrayList<>();
            int currLocation = piece.getLocation();
            int i = 7;

            // right, up
            while (currLocation - i >= 0 && (currLocation - i) % 8 != 0) {
                // if the Piece is one of the opponent's, it is a valid move
                // if (currLocation - i - (currLocation/8)) % 8 != 0)
                if (pieceArray[currLocation - i] != null) {
                    if (pieceArray[currLocation - i].getPieceColour() != piece.getPieceColour()) {
                        retList.add(currLocation - i);
                    }
                    break;
                }
                retList.add(currLocation - i);
                i += 7;
            }

            i = 9;
            // right, down
            while (currLocation + i <= 63 && (currLocation + i - 16) % 8 != 0) {
                if (pieceArray[currLocation + i] != null) {
                    if (pieceArray[currLocation + i].getPieceColour() != piece.getPieceColour()) {
                        retList.add(currLocation + i);

                    }
                    break;
                }
                retList.add(currLocation + i);
                i += 9;
            }

            i = 7;
            // left, down
            while (currLocation + i <= 63 && (currLocation + i - 7) % 8 != 0) {
                if (pieceArray[currLocation + i] != null) {
                    if (pieceArray[currLocation + i].getPieceColour() != piece.getPieceColour()) {
                        retList.add(currLocation + i);
                    }
                    break;
                }
                retList.add(currLocation + i);
                i += 7;
            }

            i = 9;
            // left, up
            while (currLocation - i >= 0 && (currLocation - i + 9) % 8 != 0) {
                if (pieceArray[currLocation - i] != null) {
                    if (pieceArray[currLocation - i].getPieceColour() != piece.getPieceColour()) {
                        retList.add(currLocation - i);
                    }
                    break;
                }
                retList.add(currLocation - i);
                i += 9;
            }

            return retList;
        }
    }

    protected class Knight implements availableMoves {
        public ArrayList<Integer> getAvailableMoves(Piece piece) {

            ArrayList<Integer> retList = new ArrayList<>();
            int currLocation = piece.getLocation();

            //Two back and one down
            if (currLocation < 56 && (currLocation % 8) != 0 && (currLocation - 1 % 8) != 0 && (currLocation - 2) % 8 != 0
                    && (pieceArray[currLocation + 6] == null || pieceArray[currLocation + 6].getPieceColour() != piece.getPieceColour()))
                retList.add(currLocation + 6);

            //Two over and one down
            if (currLocation < 54 && (currLocation - 6) % 8 != 0 && (currLocation - 7) % 8 != 0
                    && (pieceArray[currLocation + 10] == null || pieceArray[currLocation + 10].getPieceColour() != piece.getPieceColour()))
                retList.add(currLocation + 10);

            //One back and two down
            if (currLocation < 48 && currLocation % 8 != 0
                    && (pieceArray[currLocation + 15] == null || pieceArray[currLocation + 15].getPieceColour() != piece.getPieceColour()))
                retList.add(currLocation + 15);

            //One over and two down
            if (currLocation < 47 && (currLocation - 7) % 8 != 0
                    && (pieceArray[currLocation + 17] == null || pieceArray[currLocation + 17].getPieceColour() != piece.getPieceColour()))
                retList.add(currLocation + 17);

            //Two over and one up
            if (currLocation > 14 && (currLocation - 6) % 8 != 0 && (currLocation - 7) % 8 != 0
                    && (pieceArray[currLocation - 6] == null || pieceArray[currLocation - 6].getPieceColour() != piece.getPieceColour())) {
                retList.add(currLocation - 6);
            }


            //Two back and one up
            if (currLocation > 9 && currLocation % 8 != 0 && (currLocation - 1) % 8 != 0
                    && (pieceArray[currLocation - 10] == null || pieceArray[currLocation - 10].getPieceColour() != piece.getPieceColour())) {
                retList.add(currLocation - 10);
            }

            //One over and two up
            if (currLocation > 15 && (currLocation - 7) % 8 != 0
                    && (pieceArray[currLocation - 15] == null || pieceArray[currLocation - 15].getPieceColour() != piece.getPieceColour())) {
                retList.add(currLocation - 15);
            }

            //One back and two up
            if (currLocation > 16 && currLocation % 8 != 0
                    && (pieceArray[currLocation - 17] == null || pieceArray[currLocation - 17].getPieceColour() != piece.getPieceColour())) {
                retList.add(currLocation - 17);
            }
            return retList;
        }
    }

    protected class Rook implements availableMoves {
        public ArrayList<Integer> getAvailableMoves(Piece piece) {
            ArrayList<Integer> retList = new ArrayList<Integer>();
            int currLocation = piece.getLocation();
            int i = 1;

            // right
            while ((currLocation + i - 1 - 7) % 8 != 0) {
                if (pieceArray[currLocation + i] != null) {
                    if (pieceArray[currLocation + i].getPieceColour() != piece.getPieceColour())
                        retList.add(currLocation + i);
                    break;
                }
                retList.add(currLocation + i);
                i++;
            }

            i = 1;
            // left
            while ((currLocation - i + 1) % 8 != 0) {
                if (pieceArray[currLocation - i] != null) {
                    if (pieceArray[currLocation - i].getPieceColour() != piece.getPieceColour())
                        retList.add(currLocation - i);
                    break;
                }
                retList.add(currLocation - i);
                i++;
            }

            i = 8;
            // down
            while (currLocation + i <= 63
                    && (pieceArray[currLocation + i] == null || pieceArray[currLocation + i].getPieceColour() != piece.getPieceColour())) {
                if (pieceArray[currLocation + i] != null) {
                    if (pieceArray[currLocation + i].getPieceColour() != piece.getPieceColour())
                        retList.add(currLocation + i);
                    break;
                }
                retList.add(currLocation + i);
                i += 8;
            }

            i = 8;
            // up
            while (currLocation - i >= 0
                    && (pieceArray[currLocation - i] == null || pieceArray[currLocation - i].getPieceColour() != piece.getPieceColour())) {
                if (pieceArray[currLocation - i] != null) {
                    if (pieceArray[currLocation - i].getPieceColour() != piece.getPieceColour())
                        retList.add(currLocation - i);
                    break;
                }
                retList.add(currLocation - i);
                i += 8;
            }

            return retList;
        }
    }

    protected class Queen implements availableMoves {
        private Rook horizontalVerical;
        private Bishop diagonal;

        public ArrayList<Integer> getAvailableMoves(Piece piece) {
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
        private Rook crossCheck;
        private Bishop diagonalCheck;

        public ArrayList<Integer> getAvailableMoves(Piece piece) {
            ArrayList<Integer> retList = new ArrayList<>();
            int currLocation = piece.getLocation();
            //Todo: Check outward for a piece of the opposite color in every direction except the one you came from
            /*
                    int tempLoc = currLocation - 9;
                    boolean valid = true;
                    int i = 1;
                    // right
                    while ((tempLoc + i - 1 - 7) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i++;
                    }

                    i = 1;
                    // left
                    while ((tempLoc - i + 1) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i++;
                    }

                    i = 8;
                    // down
                    while (tempLoc + i <= 63
                            && (pieceArray[tempLoc + i] == null || pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour())) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 8;
                    }

                    i = 8;
                    // up
                    while (tempLoc - i >= 0
                            && (pieceArray[tempLoc - i] == null || pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour())) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 8;
                    }

                    i = 7;

                    // right, up
                    while (tempLoc - i >= 0 && (tempLoc - i) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 7;
                    }

                    i = 9;
                    // right, down
                    while (tempLoc + i <= 63 && (tempLoc + i - 16) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 9;
                    }

                    i = 7;
                    // left, down
                    while (tempLoc + i <= 63 && (tempLoc + i - 7) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        retList.add(currLocation + i);
                        i += 7;
                    }

                    i = 9;
                    // left, up
                    while (tempLoc - i >= 0 && (tempLoc - i + 9) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 9;
                    }
             */
            if (currLocation >= 8) {
                //Foward Right
                if ((currLocation - 7) % 8 != 0 && pieceArray[currLocation - 7] == null) {

                    int i = 1;
                    int tempLoc = currLocation - 7;
                    boolean valid = true;

                    while ((tempLoc + i - 1 - 7) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i++;
                    }

                    i = 1;
                    // left
                    while (valid && (tempLoc - i + 1) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i++;
                    }

                    i = 8;
                    // down
                    while (valid && tempLoc + i <= 63
                            && (pieceArray[tempLoc + i] == null || pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour())) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 8;
                    }

                    i = 8;
                    // up
                    while (valid && tempLoc - i >= 0
                            && (pieceArray[tempLoc - i] == null || pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour())) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 8;
                    }

                    i = 7;

                    // right, up
                    while (valid && tempLoc - i >= 0 && (tempLoc - i) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 7;
                    }

                    i = 9;
                    // right, down
                    while (valid && tempLoc + i <= 63 && (tempLoc + i - 16) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 9;
                    }

                    i = 9;
                    // left, up
                    while (valid && tempLoc - i >= 0 && (tempLoc - i + 9) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 9;
                    }

                    if (valid)
                        retList.add(tempLoc);

                }

                //Foward Left
                if (currLocation % 8 != 0 && (pieceArray[currLocation - 9] == null)) {
                    int tempLoc = currLocation - 9;
                    boolean valid = true;
                    int i = 1;
                    // right
                    while ((tempLoc + i - 1 - 7) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i++;
                    }

                    i = 1;
                    // left
                    while (valid && (tempLoc - i + 1) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i++;
                    }

                    i = 8;
                    // down
                    while (valid && tempLoc + i <= 63
                            && (pieceArray[tempLoc + i] == null || pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour())) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 8;
                    }

                    i = 8;
                    // up
                    while (tempLoc - i >= 0
                            && (pieceArray[tempLoc - i] == null || pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour())) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 8;
                    }

                    i = 7;

                    // right, up
                    while (tempLoc - i >= 0 && (tempLoc - i) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 7;
                    }


                    i = 9;
                    // left, up
                    while (tempLoc - i >= 0 && (tempLoc - i + 9) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 9;
                    }

                    i = 7;
                    // left, down
                    while (tempLoc + i <= 63 && (tempLoc + i - 7) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        retList.add(currLocation + i);
                        i += 7;
                    }
                    if (valid)
                        retList.add(currLocation - 9);
                }
                //Foward Straight
                if (pieceArray[currLocation - 8] == null) {
                    int tempLoc = currLocation - 8;
                    boolean valid = true;
                    int i = 1;
                    // right
                    while ((tempLoc + i - 1 - 7) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i++;
                    }

                    i = 1;
                    // left
                    while (valid && (tempLoc - i + 1) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i++;
                    }

                    i = 8;
                    // up
                    while (valid && tempLoc - i >= 0
                            && (pieceArray[tempLoc - i] == null || pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour())) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 8;
                    }

                    i = 7;

                    // right, up
                    while (valid && tempLoc - i >= 0 && (tempLoc - i) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 7;
                    }

                    i = 9;
                    // right, down
                    while (valid && tempLoc + i <= 63 && (tempLoc + i - 16) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 9;
                    }

                    i = 7;
                    // left, down
                    while (valid && tempLoc + i <= 63 && (tempLoc + i - 7) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        retList.add(currLocation + i);
                        i += 7;
                    }

                    i = 9;
                    // left, up
                    while (valid && tempLoc - i >= 0 && (tempLoc - i + 9) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 9;
                    }
                    if (valid)
                        retList.add(tempLoc);
                }
                //Right
                if ((currLocation - 7) % 8 != 0 && pieceArray[currLocation + 1] == null) {
                    int tempLoc = currLocation + 1;
                    boolean valid = true;
                    int i = 1;
                    // right
                    while ((tempLoc + i - 1 - 7) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i++;
                    }

                    i = 8;
                    // down
                    while (valid && tempLoc + i <= 63
                            && (pieceArray[tempLoc + i] == null || pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour())) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 8;
                    }

                    i = 8;
                    // up
                    while (valid && tempLoc - i >= 0
                            && (pieceArray[tempLoc - i] == null || pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour())) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 8;
                    }

                    i = 7;

                    // right, up
                    while (valid && tempLoc - i >= 0 && (tempLoc - i) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 7;
                    }

                    i = 9;
                    // right, down
                    while (valid && tempLoc + i <= 63 && (tempLoc + i - 16) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 9;
                    }

                    i = 7;
                    // left, down
                    while (valid && tempLoc + i <= 63 && (tempLoc + i - 7) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 7;
                    }

                    i = 9;
                    // left, up
                    while (valid && tempLoc - i >= 0 && (tempLoc - i + 9) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 9;
                    }
                    if (valid)
                        retList.add(tempLoc);
                }
                //Left
                if (currLocation % 8 != 0 && pieceArray[currLocation - 1] == null) {
                    int tempLoc = currLocation - 1;
                    boolean valid = true;

                    int i = 1;
                    // left
                    while ((tempLoc - i + 1) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i++;
                    }

                    i = 8;
                    // down
                    while (valid && tempLoc + i <= 63
                            && (pieceArray[tempLoc + i] == null || pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour())) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 8;
                    }

                    i = 8;
                    // up
                    while (valid && tempLoc - i >= 0
                            && (pieceArray[tempLoc - i] == null || pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour())) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 8;
                    }

                    i = 7;

                    // right, up
                    while (valid && tempLoc - i >= 0 && (tempLoc - i) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 7;
                    }

                    i = 9;
                    // right, down
                    while (valid && tempLoc + i <= 63 && (tempLoc + i - 16) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 9;
                    }

                    i = 7;
                    // left, down
                    while (valid && tempLoc + i <= 63 && (tempLoc + i - 7) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        retList.add(currLocation + i);
                        i += 7;
                    }

                    i = 9;
                    // left, up
                    while (valid && tempLoc - i >= 0 && (tempLoc - i + 9) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 9;
                    }
                    if (valid)
                        retList.add(tempLoc);
                }
            } else if (currLocation <= 55) {
                //Back Left
                if (currLocation % 8 != 0 && pieceArray[currLocation + 7] == null) {
                    int tempLoc = currLocation + 7;
                    boolean valid = true;
                    int i = 1;
                    // right
                    while ((tempLoc + i - 1 - 7) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i++;
                    }

                    i = 1;
                    // left
                    while (valid && (tempLoc - i + 1) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i++;
                    }

                    i = 8;
                    // down
                    while (valid && tempLoc + i <= 63
                            && (pieceArray[tempLoc + i] == null || pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour())) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 8;
                    }

                    i = 8;
                    // up
                    while (valid && tempLoc - i >= 0
                            && (pieceArray[tempLoc - i] == null || pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour())) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 8;
                    }

                    i = 7;

                    // right, up
                    while (valid && tempLoc - i >= 0 && (tempLoc - i) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 7;
                    }

                    i = 9;
                    // right, down
                    while (valid && tempLoc + i <= 63 && (tempLoc + i - 16) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 9;
                    }

                    i = 7;
                    // left, down
                    while (valid && tempLoc + i <= 63 && (tempLoc + i - 7) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        retList.add(currLocation + i);
                        i += 7;
                    }

                    if (valid)
                        retList.add(tempLoc);
                }

                //Back Right
                if (currLocation - 7 % 8 != 0
                        && pieceArray[currLocation + 9] == null) {
                    int tempLoc = currLocation + 9;
                    boolean valid = true;
                    int i = 1;
                    // right
                    while ((tempLoc + i - 1 - 7) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i++;
                    }

                    i = 1;
                    // left
                    while (valid && (tempLoc - i + 1) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i++;
                    }

                    i = 8;
                    // down
                    while (valid && tempLoc + i <= 63
                            && (pieceArray[tempLoc + i] == null || pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour())) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 8;
                    }

                    i = 8;
                    // up
                    while (valid && tempLoc - i >= 0
                            && (pieceArray[tempLoc - i] == null || pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour())) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 8;
                    }

                    i = 7;

                    // right, up
                    while (valid && tempLoc - i >= 0 && (tempLoc - i) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 7;
                    }

                    i = 9;
                    // right, down
                    while (valid && tempLoc + i <= 63 && (tempLoc + i - 16) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 9;
                    }

                    i = 7;
                    // left, down
                    while (valid && tempLoc + i <= 63 && (tempLoc + i - 7) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        retList.add(currLocation + i);
                        i += 7;
                    }

                    if (valid)
                        retList.add(tempLoc);
                }
                //Back Straight
                if (pieceArray[currLocation + 8] == null) {
                    int tempLoc = currLocation + 8;
                    boolean valid = true;
                    int i = 1;
                    // right
                    while ((tempLoc + i - 1 - 7) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i++;
                    }

                    i = 1;
                    // left
                    while (valid && (tempLoc - i + 1) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i++;
                    }

                    i = 8;
                    // down
                    while (valid && tempLoc + i <= 63
                            && (pieceArray[tempLoc + i] == null || pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour())) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 8;
                    }

                    i = 7;

                    // right, up
                    while (valid && tempLoc - i >= 0 && (tempLoc - i) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 7;
                    }

                    i = 9;
                    // right, down
                    while (valid && tempLoc + i <= 63 && (tempLoc + i - 16) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 9;
                    }

                    i = 7;
                    // left, down
                    while (valid && tempLoc + i <= 63 && (tempLoc + i - 7) % 8 != 0) {
                        if (pieceArray[tempLoc + i] != null) {
                            if (pieceArray[tempLoc + i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc + i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        retList.add(currLocation + i);
                        i += 7;
                    }

                    i = 9;
                    // left, up
                    while (valid && tempLoc - i >= 0 && (tempLoc - i + 9) % 8 != 0) {
                        if (pieceArray[tempLoc - i] != null) {
                            if (pieceArray[tempLoc - i].getPieceColour() != piece.getPieceColour()) {
                                if (pieceArray[tempLoc - i].getAvailableMoves().contains(tempLoc))
                                    valid = false;
                            }
                            break;
                        }
                        i += 9;
                    }
                    if (valid)
                        retList.add(tempLoc);
                }
            }

            return retList;
        }
    }
}
