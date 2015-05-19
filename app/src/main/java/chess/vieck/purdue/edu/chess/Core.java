package chess.vieck.purdue.edu.chess;

import java.util.ArrayList;

/**
 * Created by Michael on 5/2/2015.
 */
public class Core {
    // this gets used for discarded Pieces
    private int deadCell;
    ;
    private Player white, black;
    ;
    private objectColour turn;
    ;
    private gameState currentGameState;
    ;
    private UserInterfaceBoard UIboard;
    ;


    // .ctor
    Core() {
        // if new game
        deadCell = -1;
        board = new Cell[8][8];
        populateBoard(board);
        turn = objectColour.white;
        currentGameState = gameState.allClear;
    }

    public objectColour getTurn() {
        return turn;
    }

    // checks for check on proposed board.
    private gameState checkForCheck(Cell board[][], objectColour whoseCheck) {
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
            ArrayList<Cell> moves = opponent[i].getAvailableMoves();
            if (moves != null
                    && (!moves.isEmpty() && moves.contains(own[15].getLocation())))
            {
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
        ArrayList<Cell> whiteMoves = new ArrayList<Cell>();
        ArrayList<Cell> blackMoves = new ArrayList<Cell>();

        for (int i = 0; i < 15; i++) {
            ArrayList<Cell> moves = white.getPieces()[i].getAvailableMoves();
            for (int j = 0; j < moves.size(); j++)
                whiteMoves.add(moves.get(j));
            moves = black.getPieces()[i].getAvailableMoves();
            for (int j = 0; j < moves.size(); j++)
                blackMoves.add(moves.get(j));
        }
        if (blackMoves.size() == 0) {
            if (checkForCheck(board, objectColour.black) == gameState.blackCheck)
                return gameState.blackMate;
            else
                return gameState.stalemate;
        }
        if (whiteMoves.size() == 0) {
            if (checkForCheck(board, objectColour.white) == gameState.whiteCheck)
                return gameState.whiteMate;
            else
                return gameState.stalemate;
        }
        if (checkForCheck(board, objectColour.white) == gameState.whiteCheck)
            return gameState.whiteCheck;
        if (checkForCheck(board, objectColour.black) == gameState.blackCheck)
            return gameState.blackCheck;
        return gameState.allClear;
    }

    // TODO: maybe move this into a game class
    public boolean move(int fromX, int fromY, int toX, int toY)
            throws Exception {
        Player currentPlayer = turn == objectColour.white ? white : black;
        Piece Piece = board[fromX][fromY].getPiece();
        boolean success = currentPlayer.move(Piece, board[toX][toY]);
        if (success)
            turn = turn == objectColour.white ? objectColour.black
                    : objectColour.white;
        return success;
    }

    // TODO: finish this function.
    void populateBoard(Cell[][] board) {

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
        black = new Player(objectColour.black, blackPieces);
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

    // MEMBER VARIABLES

    private interface availableMoves {
        public ArrayList<Integer> getAvailableMoves(Piece Piece);
    }

    private class Player {
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

    public class incompatiblePieceTypeConversionException extends Exception {
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


            newPiece.setPiece(this);
            sourceCell.setPiece(null);
            this.location = toLocation;
            this.setPieceState(pieceState.alive);

            gameState tryState = checkForCheck(board, this.colour);

            //revert
            sourceCell.setPiece(this);
            targetCell.setPiece(oldPiece);
            this.location = sourceCell;
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

            if (!isValidMove(moveToLocation))
                return moveStatus.fail;
            // move is valid; apply move and check for promotion if Pawn
            // empty old location
            this.location.setPiece(null);
            // update new location
            moveToLocation.setPiece(this);
            // update self location
            this.location = moveToLocation;

            if (type == pieceType.pawn) {
                return ((location >= 0 && location <= 7) || (location >= 56 || location <= 63)) ? moveStatus.promote : moveStatus.success;
            } else if (type == pieceType.rook) {

            }

            return moveStatus.success;
        }

        public ArrayList<Integer> getAvailableMoves() {
            return availMoves.getAvailableMoves(this);
        }

    }

    protected class Pawn implements availableMoves {
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
                // move forward one Cell
                if (board[currX][currY - 1].getPiece() == null)
                    retList.add(piece.location + 8);

                // check moving left
                if (piece.location % 8 != 0
                        && board[currX - 1][currY - 1].getPiece() != null
                        && board[currX - 1][currY - 1].getPiece()
                        .getPieceColour() == objectColour.black)
                    retList.add(piece.location + 7);

                // check moving right
                if (currX < 7
                        && board[currX + 1][currY - 1].getPiece() != null
                        && board[currX + 1][currY - 1].getPiece()
                        .getPieceColour() == objectColour.black)
                    retList.add(piece.location + 9);

                // default location, making 4 available moves, rather than 3
                if (currY == 6 && board[currX][currY - 1].getPiece() == null
                        && board[currX][currY - 2].getPiece() == null)
                    retList.add(piece.location - (8 * 2));
            } else {
                // COPY PASTA! YAAY! : (
                // move forward one Cell
                if (board[currX][currY + 1].getPiece() == null)
                    retList.add(piece.location + 8);

                // check moving left
                if (currX > 0
                        && board[currX - 1][currY + 1].getPiece() != null
                        && board[currX - 1][currY + 1].getPiece().getPieceColour() == objectColour.white)
                    retList.add(piece.location + 7);

                // check moving right
                if (currX < 7
                        && board[currX + 1][currY + 1].getPiece() != null
                        && board[currX + 1][currY + 1].getPiece().getPieceColour() == objectColour.white)
                    retList.add(piece.location + 9);

                // default location, making 4 available moves, rather than 3
                if (currY == 1 && board[currX][currY + 1].getPiece() == null
                        && board[currX][currY + 2].getPiece() == null)
                    retList.add(piece.location + (8 * 2));
            }
            return retList;
        }
    }

    protected class Bishop implements availableMoves {
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
            while ((currLocation + i != 7 * (currLocation / 8) + (currLocation % 7)) && currLocation < 64
                    && pieceArray[currLocation + i].getPiece() == null || pieceArray[currLocation + i].getPiece().getPieceColour() != piece.getPieceColour()) {
                // if the Piece is one of the opponent's, it is a valid move
                if (pieceArray[currLocation + i].getPiece() != null
                        && pieceArray[currLocation + i].getPiece().getPieceColour() != piece.getPieceColour()) {
                    retList.add(pieceArray[currLocation + i]);
                    break;
                }
                retList.add(pieceArray[currLocation + i]);
                i += 9;
                count++;
                row++;
            }

            i = 0;
            // right, down
            while ((currLocation - i != 7 * (currLocation / 8) + (currLocation % 7)) && currLocation > -1
                    && (pieceArray[currLocation - i].getPiece() == null || board[currX
                    + i][currY - i].getPiece().getPieceColour() != Piece.getPieceColour())) {
                if (board[currX + i][currY - i].getPiece() != null
                        && board[currX + i][currY - i].getPiece().getPieceColour() != Piece.getPieceColour()) {
                    retList.add(board[currX + i][currY - i]);
                    break;
                }
                retList.add(board[currX + i][currY - i]);
                i += 9;
            }

            i = 1;
            // left, down
            while ((currX - i > -1 && currY + i < 8)
                    && (board[currX - i][currY + i].getPiece() == null || board[currX
                    - i][currY + i].getPiece().getPieceColour() != Piece.getPieceColour())) {
                if (board[currX - i][currY + i].getPiece() != null
                        && board[currX - i][currY + i].getPiece().getPieceColour() != Piece.getPieceColour()) {
                    retList.add(board[currX - i][currY + i]);
                    break;
                }
                retList.add(board[currX - i][currY + i]);
                i++;
            }

            i = 1;
            // left, up
            while ((currX - i > -1 && currY - i > -1)
                    && (board[currX - i][currY - i].getPiece() == null || board[currX
                    - i][currY - i].getPiece().getPieceColour() != Piece.getPieceColour())) {
                if (board[currX - i][currY - i].getPiece() != null
                        && board[currX - i][currY - i].getPiece().getPieceColour() != Piece.getPieceColour()) {
                    retList.add(board[currX - i][currY - i]);
                    break;
                }
                retList.add(board[currX - i][currY - i]);
                i++;
            }

            return retList;
        }
    }

    // METHODS

    protected class Knight implements availableMoves {
        public ArrayList<Cell> getAvailableMoves(Piece Piece) {
            if (Piece.getLocation() == deadCell)
                return null;
            ArrayList<Cell> retList = new ArrayList<Cell>();
            int currX = Piece.getLocation().getX();
            int currY = Piece.getLocation().getY();
            // the Knight has 8 moves, so we'll just explicitly define them
            // since it's not that much more verbose than the alternateive
            // and much easier to write.
            // TODO: think about optimizing this. not a priority at the moment
            // like, this is a circular pattern, or check the rectangle 2 away,
            // skipping every other Cell

            if (currX > 1
                    && currY > 0
                    && (board[currX - 2][currY - 1].getPiece() == null || board[currX - 2][currY - 1].getPiece().getPieceColour() != Piece.getPieceColour()))
                retList.add(board[currX - 2][currY - 1]);

            if (currX > 0
                    && currY > 1
                    && (board[currX - 1][currY - 2].getPiece() == null || board[currX - 1][currY - 2].getPiece().getPieceColour() != Piece.getPieceColour()))
                retList.add(board[currX - 1][currY - 2]);

            if (currX < 7
                    && currY > 1
                    && (board[currX + 1][currY - 2].getPiece() == null || board[currX + 1][currY - 2].getPiece().getPieceColour() != Piece.getPieceColour()))
                retList.add(board[currX + 1][currY - 2]);

            if (currX < 6
                    && currY > 0
                    && (board[currX + 2][currY - 1].getPiece() == null || board[currX + 2][currY - 1].getPiece().getPieceColour() != Piece.getPieceColour()))
                retList.add(board[currX + 2][currY - 1]);

            if (currX < 6
                    && currY < 7
                    && (board[currX + 2][currY + 1].getPiece() == null || board[currX + 2][currY + 1].getPiece().getPieceColour() != Piece.getPieceColour()))
                retList.add(board[currX + 2][currY + 1]);

            if (currX < 7
                    && currY < 6
                    && (board[currX + 1][currY + 2].getPiece() == null || board[currX + 1][currY + 2].getPiece().getPieceColour() != Piece.getPieceColour()))
                retList.add(board[currX + 1][currY + 2]);

            if (currX > 0
                    && currY < 6
                    && (board[currX - 1][currY + 2].getPiece() == null || board[currX - 1][currY + 2].getPiece().getPieceColour() != Piece.getPieceColour()))
                retList.add(board[currX - 1][currY + 2]);

            if (currX > 1
                    && currY < 7
                    && (board[currX - 2][currY + 1].getPiece() == null || board[currX - 2][currY + 1].getPiece().getPieceColour() != Piece.getPieceColour()))
                retList.add(board[currX - 2][currY + 1]);

            return retList;
        }
    }

    protected class Rook implements availableMoves {
        public ArrayList<Integer> getAvailableMoves(Piece Piece) {
            if (Piece.getLocation() == deadCell)
                return null;
            ArrayList<Cell> retList = new ArrayList<Cell>();
            int currX = Piece.getLocation().getX();
            int currY = Piece.getLocation().getY();
            int i = 1;

            // right
            while (currX + i < 8
                    && (board[currX + i][currY].getPiece() == null || board[currX
                    + i][currY].getPiece().getPieceColour() != Piece.getPieceColour())) {
                if (board[currX + i][currY].getPiece() != null
                        && board[currX + i][currY].getPiece().getPieceColour() != Piece.getPieceColour()) {
                    retList.add(board[currX + i][currY]);
                    break;
                }
                retList.add(board[currX + i][currY]);
                i++;
            }

            i = 1;
            // left
            while (currX - i > -1
                    && (board[currX - i][currY].getPiece() == null || board[currX
                    - i][currY].getPiece().getPieceColour() != Piece.getPieceColour())) {
                if (board[currX - i][currY].getPiece() != null
                        && board[currX - i][currY].getPiece().getPieceColour() != Piece.getPieceColour()) {
                    retList.add(board[currX - i][currY]);
                    break;
                }
                retList.add(board[currX - i][currY]);
                i++;
            }

            i = 1;
            // down
            while (currY + i < 8
                    && (board[currX][currY + i].getPiece() == null || board[currX][currY
                    + i].getPiece().getPieceColour() != Piece.getPieceColour())) {
                if (board[currX][currY + i].getPiece() != null
                        && board[currX][currY + i].getPiece().getPieceColour() != Piece.getPieceColour()) {
                    retList.add(board[currX][currY + i]);
                    break;
                }
                retList.add(board[currX][currY + i]);
                i++;
            }

            i = 1;
            // up
            while (currY - i > -1
                    && (board[currX][currY - i].getPiece() == null || board[currX][currY
                    - i].getPiece().getPieceColour() != Piece.getPieceColour())) {
                if (board[currX][currY - i].getPiece() != null
                        && board[currX][currY - i].getPiece().getPieceColour() != Piece.getPieceColour()) {
                    retList.add(board[currX][currY - i]);
                    break;
                }
                retList.add(board[currX][currY - i]);
                i++;
            }
            return retList;
        }
    }

    protected class Queen implements availableMoves {
        private Rook horizontalVerical;
        private Bishop diagonal;

        public ArrayList<Cell> getAvailableMoves(Piece Piece) {
            if (Piece.getLocation() == deadCell)
                return null;
            horizontalVerical = new Rook();
            diagonal = new Bishop();
            ArrayList<Cell> retList = horizontalVerical
                    .getAvailableMoves(Piece);
            ArrayList<Cell> moreMoves = diagonal.getAvailableMoves(Piece);
            for (int i = 0; i < moreMoves.size(); i++) {
                retList.add(moreMoves.get(i));
            }
            return retList;
        }
    }

    protected class King implements availableMoves {
        public ArrayList<Cell> getAvailableMoves(Piece Piece) {
            if (Piece.getLocation() == deadCell)
                return null;
            ArrayList<Cell> retList = new ArrayList<Cell>();
            int currX = Piece.getLocation().getX();
            int currY = Piece.getLocation().getY();
            // if the King moves and there is a check, regardless of the colour
            // it is an invalid move.
            // TODO:implement King moves in a way that won't cause infinite
            // loops
            // for example: checkForCheck doesn't look at the King. That would
            // do it
            // Also: don't check for mate here.

            if (currX > 0 && currY > 0
                    && (Piece.isValidMove(board[currX - 1][currY - 1])))
                retList.add(board[currX - 1][currY - 1]);

            if (currY > 0 && (Piece.isValidMove(board[currX][currY - 1])))
                retList.add(board[currX][currY - 1]);

            if (currX < 7 && currY > 0
                    && (Piece.isValidMove(board[currX + 1][currY - 1])))
                retList.add(board[currX + 1][currY - 1]);

            if (currX < 7 && (Piece.isValidMove(board[currX + 1][currY])))
                retList.add(board[currX + 1][currY]);

            if (currX < 7 && currY < 7
                    && (Piece.isValidMove(board[currX + 1][currY + 1])))
                retList.add(board[currX + 1][currY + 1]);

            if (currY < 7 && (Piece.isValidMove(board[currX][currY + 1])))
                retList.add(board[currX][currY + 1]);

            if (currX > 0 && currY < 7
                    && (Piece.isValidMove(board[currX - 1][currY + 1])))
                retList.add(board[currX - 1][currY + 1]);

            if (currX > 0 && Piece.isValidMove(board[currX - 1][currY]))
                retList.add(board[currX - 1][currY]);

            return retList;
        }
    }

    private class UserInterfaceBoard {
        // .ctor
        UserInterfaceBoard() {
        }

        public Cell getTargetCell() {
            // TODO: write UI interaction
            Cell retCell = deadCell;
            return retCell;
        }

        public Cell getSourceCell() {
            // TODO: write UI interaction
            Cell retCell = deadCell;
            return retCell;
        }

        public Piece getPiece() {
            // TODO: i'm not sure that this should really come from the source
            // Cell.
            // we'll see
            Piece retPiece = getSourceCell().getPiece();
            return retPiece;
        }
    }
}
