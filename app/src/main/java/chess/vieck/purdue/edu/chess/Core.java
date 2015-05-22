package chess.vieck.purdue.edu.chess;

import java.util.ArrayList;

/**
 * Created by Michael on 5/2/2015.
 */
public class Core {
    protected static Piece[] pieceArray;
    ;
    // this gets used for discarded Pieces
    private static int deadCell;
    ;
    private static Player white, black;
    ;
    private static objectColour turn;
    ;
    private static gameState currentGameState;

    // .ctor
    public Core() {
        // if new game
        deadCell = -1;
        populateBoard(pieceArray);
        turn = objectColour.white;
        currentGameState = gameState.allClear;
    }

    public void setPieceArray(Piece[] pieceArray) {
        this.pieceArray = pieceArray;
    }

    public objectColour getTurn() {
        return turn;
    }

    // checks for check on proposed board.
    private static gameState checkForCheck(Piece[] pieceArray, objectColour whoseCheck) {
        Piece own[];
        Piece opponent[];
        if (whoseCheck == objectColour.white) {
            own = white.getPieces();
            opponent = black.getPieces();
        } else {
            own = black.getPieces();
            opponent = white.getPieces();
        }
        // i < 16 because we're not checking the King for move possibilities
        // as that would cause an infinite loop since the King checks for
        // checks in available moves;
        for (int i = 0; i < 15; i++) {
            ArrayList<Integer> moves = opponent[i].getAvailableMoves();
            if (moves != null
                    && (!moves.isEmpty() && moves.contains(own[15].getLocation()))) {
                if (whoseCheck == objectColour.white)
                    return gameState.whiteCheck;
                else
                    return gameState.blackCheck;
            }
        }
        return gameState.allClear;
    }

    // checks for mate on the board as it exists
    // TODO: finish this. Not important at the moment.
    private gameState checkForMate() {
        // TODO: check for stalemate here
        // mate = check & no valid moves as defined above(somewhere)
        // stalemate = no available moves and no check
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
        return gameState.allClear;
    }

    // TODO: maybe move this into a game class
    public boolean move(int oldLocation, int newLocation)
            throws Exception {
        Player currentPlayer = turn == objectColour.white ? white : black;
        Piece piece = pieceArray[oldLocation];
        boolean success = currentPlayer.move(piece, newLocation);
        if (success)
            turn = turn == objectColour.white ? objectColour.black
                    : objectColour.white;
        return success;
    }

    // TODO: finish this function.
    void populateBoard(Piece[] board) {
/*
        // White is on the bottom, black is on top
        // pawns
        for (int i = 0; i < 8; i++) {
            Piece whitePawn = new Piece(objectColour.white, pieceType.pawn,
                    board[i][6], new Pawn(), R.drawable.wpawn);
            whitePieces[i] = whitePawn;
            board[i][6].setPiece(whitePawn);
            Piece blackPawn = new Piece(objectColour.black, pieceType.pawn,
                    board[i][1], new Pawn(), R.drawable.bpawn);
            blackPieces[i] = blackPawn;
            board[i][1].setPiece(blackPawn);
        }

        // rooks
        whitePieces[8] = new Piece(objectColour.white, pieceType.rook,
                board[0][7], new Rook(), R.drawable.wrook);
        board[0][7].setPiece(whitePieces[8]);
        whitePieces[9] = new Piece(objectColour.white, pieceType.rook,
                board[7][7], new Rook(), R.drawable.wrook);
        board[7][7].setPiece(whitePieces[9]);
        blackPieces[8] = new Piece(objectColour.black, pieceType.rook,
                board[0][0], new Rook(), R.drawable.brook);
        board[0][0].setPiece(blackPieces[8]);
        blackPieces[9] = new Piece(objectColour.black, pieceType.rook,
                board[7][0], new Rook(), R.drawable.brook);
        board[7][0].setPiece(blackPieces[9]);
        // knights
        whitePieces[10] = new Piece(objectColour.white, pieceType.knight,
                board[1][7], new Knight(), R.drawable.wknight);
        board[1][7].setPiece(whitePieces[10]);
        whitePieces[11] = new Piece(objectColour.white, pieceType.knight,
                board[6][7], new Knight(), R.drawable.wknight);
        board[6][7].setPiece(whitePieces[11]);
        blackPieces[10] = new Piece(objectColour.black, pieceType.knight,
                board[1][0], new Knight(), R.drawable.bknight);
        board[1][0].setPiece(blackPieces[10]);
        blackPieces[11] = new Piece(objectColour.black, pieceType.knight,
                board[6][0], new Knight(), R.drawable.bknight);
        board[6][0].setPiece(blackPieces[11]);
        // bishops
        whitePieces[12] = new Piece(objectColour.white, pieceType.bishop,
                board[2][7], new Bishop(), R.drawable.wbishop);
        board[2][7].setPiece(whitePieces[12]);
        whitePieces[13] = new Piece(objectColour.white, pieceType.bishop,
                board[5][7], new Bishop(), R.drawable.wbishop);
        board[5][7].setPiece(whitePieces[13]);
        blackPieces[12] = new Piece(objectColour.black, pieceType.bishop,
                board[2][0], new Bishop(), R.drawable.bbishop);
        board[2][0].setPiece(blackPieces[12]);
        blackPieces[13] = new Piece(objectColour.black, pieceType.bishop,
                board[5][0], new Bishop(), R.drawable.bbishop);
        board[5][0].setPiece(blackPieces[13]);
        // queens
        whitePieces[14] = new Piece(objectColour.white, pieceType.queen,
                board[3][7], new Queen(), R.drawable.wqueen);
        board[3][7].setPiece(whitePieces[14]);
        blackPieces[14] = new Piece(objectColour.black, pieceType.queen,
                board[3][0], new Queen(), R.drawable.bqueen);
        board[3][0].setPiece(blackPieces[14]);
        // kings
        whitePieces[15] = new Piece(objectColour.white, pieceType.king,
                board[4][7], new King(), R.drawable.wking);
        board[4][7].setPiece(whitePieces[15]);
        blackPieces[15] = new Piece(objectColour.black, pieceType.king,
                board[4][0], new King(), R.drawable.bking);
        board[4][0].setPiece(blackPieces[15]);

        white = new Player(objectColour.white, whitePieces);
        black = new Player(objectColour.black, blackPieces);*/
    }

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

    private interface availableMoves {
        public ArrayList<Integer> getAvailableMoves(Piece Piece);
    }

    private static class Player {
        // properties
        private objectColour colour;
        private Piece[] Pieces;
        private Piece piece;

        // .ctor
        Player(objectColour colour, Piece[] Pieces) {
            this.colour = colour;
            this.Pieces = Pieces;
        }

        // methods

        public objectColour getColour() {
            return colour;
        }

        public Piece[] getPieces() {
            return Pieces;
        }

        // move: moves the Piece if the move is valid; returns false otherwise
        public boolean move(Piece Piece, int moveToLocation) throws Exception {
            if (Piece == null || Piece.getPieceColour() != this.colour || moveToLocation > 0 || moveToLocation < 63
                    || moveToLocation == deadCell)
                return false;
            // TODO: cache this so that we're not constantly re-populating
            ArrayList<Integer> availableMoves = Piece.getAvailableMoves();
            if (availableMoves.contains(moveToLocation)) {
                moveStatus status = Piece.tryMove(moveToLocation);
                if (status != moveStatus.promote) {
                    if (status == moveStatus.success) {
                        return true;
                    } else
                        return false;
                } else {
                    // TODO: decide: do we want to explode on incorrect
                    // promotion, or do we want
                    // to eat the exception and loop until a valid selection is
                    // made.
                    // For now, it'll explode under the assumption that the
                    // input is correct and
                    // the code isn't complex enough to really have surprising
                    // results.
                    try {
                        pieceType type = pieceType.bishop;
                        // TODO: promotion logic
                        // promotion code: UI, etc.
                        Piece.setPieceType(type);
                        return true;
                    } catch (incompatiblePieceTypeConversionException ex) {
                        throw new Exception(
                                "MAN, WHAT THE FUCK!? Promotion code is seriously jacked up.");
                    }
                }
            }
            return false;
        }

        public Piece getPiece() {
            return piece;
        }

        public void setPiece(Piece piece) {
            if (this.piece != null) {
                this.piece.setPieceState(pieceState.dead);
                this.piece = piece;
            }
        }
    }

    public static class incompatiblePieceTypeConversionException extends Exception {
        private static final long serialVersionUID = 1L;

        public incompatiblePieceTypeConversionException(String string) {
            super(string);
        }
    }

    public static class Piece {


        // colour of the Piece
        protected objectColour colour;
        // properties
        private int location;
        // state of the Piece
        private pieceState state;
        private int imageResource;
        // type of Piece
        private pieceType type;
        // gets a list of moves that don't go off the board or cause friendly
        // fire
        private availableMoves availMoves;

        // .ctor
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

        public pieceState getPieceState() {
            return state;
        }

        public void setPieceState(pieceState newState) {
            state = newState;
            if (newState == pieceState.dead) {
                location = -1;
                location = deadCell;
            }
        }

        public int getImageResource() {
            return imageResource;
        }

        public void setImageResource(int imageResource) {
            this.imageResource = imageResource;
        }

        public objectColour getPieceColour() {
            return colour;
        }


        // methods

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

            gameState tryState = checkForCheck(pieceArray, this.colour);

            //revert
            oldPiece = newPiece;
            newPiece = null;
            this.location = oldPiece.getLocation();
            this.setPieceState(pieceState.alive);
            if (oldPiece != null) {
                oldPiece.location = toLocation;
                oldPiece.setPieceState(pieceState.alive);
            }

            // see if move is valid
            // validity of the move is determined by whether the move puts
            // the Player into check. We know that the Pieces cannot move
            // in invalid patterns because this has already been checked in
            // the only caller, which should be Player.move()
            if ((colour == objectColour.white && tryState == gameState.whiteCheck)
                    || (colour == objectColour.black && tryState == gameState.blackCheck))
                return false;
            return true;
        }

        // move takes a valid Cell on the board and tries to move the
        // Piece to it. if the move is successful, returns true, if not
        // returns false
        public moveStatus tryMove(int moveToLocation) {
            //Todo: create a better move checker.
            if (!isValidMove(moveToLocation))
                return moveStatus.fail;
            // move is valid; apply move and check for promotion if Pawn
            // empty old location=
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

    public static class Pawn implements availableMoves {
        // only Piece whose moves depend on colour
        public ArrayList<Integer> getAvailableMoves(Piece piece) {
            if (piece.getLocation() == deadCell)
                return null;
            ArrayList<Integer> retList = new ArrayList<Integer>();
            int currLocation = piece.getLocation();

            // this could be better implemented with some sort of
            // direction boolean deciding addition + subtraction, but
            // i don't feel like dicking with it, so here are two
            // separate blocks of code.
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


        protected static class Bishop implements availableMoves {
            public ArrayList<Integer> getAvailableMoves(Piece piece) {
                if (piece.getLocation() == deadCell)
                    return null;
                ArrayList<Integer> retList = new ArrayList<>();
                int currLocation = piece.getLocation();
                // set i to 1 because there's no point in checking whether
                // staying in place is a valid move. that's fucking stupid
                // even with colour checking
                int i = 0;
                // check the diagonal until you see a Piece.
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

        // METHODS

        protected static class Knight implements availableMoves {
            public ArrayList<Integer> getAvailableMoves(Piece piece) {
                if (piece.getLocation() == deadCell)
                    return null;
                ArrayList<Integer> retList = new ArrayList<Integer>();
                int currLocation = piece.getLocation();
                // the Knight has 8 moves, so we'll just explicitly define them
                // since it's not that much more verbose than the alternateive
                // and much easier to write.
                // TODO: think about optimizing this. not a priority at the moment
                // like, this is a circular pattern, or check the rectangle 2 away,
                // skipping every other Cell

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

        protected static class Rook implements availableMoves {
            public ArrayList<Integer> getAvailableMoves(Piece piece) {
                if (piece.getLocation() == deadCell)
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

        protected static class Queen implements availableMoves {
            private Rook horizontalVerical;
            private Bishop diagonal;

            public ArrayList<Integer> getAvailableMoves(Piece piece) {
                if (piece.getLocation() == deadCell)
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

        protected static class King implements availableMoves {
            public ArrayList<Integer> getAvailableMoves(Piece piece) {
                if (piece.getLocation() == deadCell)
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

                if (currLocation < 63
                        && (pieceArray[currLocation + 9] == null))
                    retList.add(currLocation + 9);

                if (currLocation % 8 != 0 && (pieceArray[currLocation + 6] != null))
                    retList.add(currLocation + 6);

                if (currX < 7 && currY > 0
                        && (piece.isValidMove(board[currX + 1][currY - 1])))
                    retList.add(board[currX + 1][currY - 1]);

                if (currX < 7 && (piece.isValidMove(board[currX + 1][currY])))
                    retList.add(board[currX + 1][currY]);

                if (currX < 7 && currY < 7
                        && (piece.isValidMove(board[currX + 1][currY + 1])))
                    retList.add(board[currX + 1][currY + 1]);

                if (currY < 7 && (piece.isValidMove(board[currX][currY + 1])))
                    retList.add(board[currX][currY + 1]);

                if (currX > 0 && currY < 7
                        && (piece.isValidMove(board[currX - 1][currY + 1])))
                    retList.add(board[currX - 1][currY + 1]);

                if (currX > 0 && piece.isValidMove(board[currX - 1][currY]))
                    retList.add(board[currX - 1][currY]);

                return retList;
            }
        }
    }
