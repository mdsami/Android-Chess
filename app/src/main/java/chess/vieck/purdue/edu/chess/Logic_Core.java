package chess.vieck.purdue.edu.chess;

import java.util.ArrayList;

/**
 * Created by Michael on 5/2/2015.
 */
public class Logic_Core {
    // this gets used for discarded pieces
    private cell deadCell;
    ;
    private cell board[][];
    ;
    private player white, black;
    ;
    private pieceColor turn;
    ;
    private gameState currentGameState;
    ;
    private userInterfaceBoard UIboard;

    // .ctor
    Logic_Core() {
        // if new game
        deadCell = new cell(-1, -1);
        board = new cell[8][8];
        populateBoard(board);
        turn = pieceColor.white;
        currentGameState = gameState.allClear;
    }

    public cell[][] getBoard() {
        return board;
    }

    public pieceColor getTurn() {
        return turn;
    }

    // checks for check on proposed board.
    private gameState checkForCheck(cell board[][], pieceColor whoseCheck) {
        piece own[];
        piece opponent[];
        if (whoseCheck == pieceColor.white) {
            own = white.getPieces();
            opponent = black.getPieces();
        } else {
            own = black.getPieces();
            opponent = white.getPieces();
        }
        // i < 16 because we're not checking the king for move possibilities
        // as that would cause an infinite loop since the king checks for
        // checks in available moves;
        for (int i = 0; i < 15; i++) {
            ArrayList<cell> moves = opponent[i].getAvailableMoves();
            if (moves != null
                    && (!moves.isEmpty() && moves.contains(own[15].getLocation()))) {
                if (whoseCheck == pieceColor.white)
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
        ArrayList<cell> whiteMoves = new ArrayList<cell>();
        ArrayList<cell> blackMoves = new ArrayList<cell>();

        for (int i = 0; i < 15; i++) {
            ArrayList<cell> moves = white.getPieces()[i].getAvailableMoves();
            for (int j = 0; j < moves.size(); j++)
                whiteMoves.add(moves.get(j));
            moves = black.getPieces()[i].getAvailableMoves();
            for (int j = 0; j < moves.size(); j++)
                blackMoves.add(moves.get(j));
        }
        if (blackMoves.size() == 0) {
            if (checkForCheck(board, pieceColor.black) == gameState.blackCheck)
                return gameState.blackCheckMate;
            else
                return gameState.stalemate;
        }
        if (whiteMoves.size() == 0) {
            if (checkForCheck(board, pieceColor.white) == gameState.whiteCheck)
                return gameState.whiteCheckMate;
            else
                return gameState.stalemate;
        }
        if (checkForCheck(board, pieceColor.white) == gameState.whiteCheck)
            return gameState.whiteCheck;
        if (checkForCheck(board, pieceColor.black) == gameState.blackCheck)
            return gameState.blackCheck;
        return gameState.allClear;
    }

    // TODO: maybe move this into a game class
    public boolean move(int fromX, int fromY, int toX, int toY)
            throws Exception {
        player currentPlayer = turn == pieceColor.white ? white : black;
        piece piece = board[fromX][fromY].getPiece();
        boolean success = currentPlayer.move(piece, board[toX][toY]);
        if (success)
            turn = turn == pieceColor.white ? pieceColor.black
                    : pieceColor.white;
        return success;
    }

    // TODO: finish this function.
    void populateBoard(cell[][] board) {
        // create player piece arrays
        // first 8 cells are pawns, then rooks, knight, bishops, queen, king
        piece blackPieces[] = new piece[16];
        piece whitePieces[] = new piece[16];

        // init the cells
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                board[x][y] = new cell(x, y);
            }
        }

        // White is on the bottom, black is on top
        // pawns
        for (int i = 0; i < 8; i++) {
            piece whitePawn = new piece(pieceColor.white, pieceType.pawn,
                    board[i][6], new pawn(), R.drawable.wpawn);
            whitePieces[i] = whitePawn;
            board[i][6].setPiece(whitePawn);
            piece blackPawn = new piece(pieceColor.black, pieceType.pawn,
                    board[i][1], new pawn(), R.drawable.bpawn);
            blackPieces[i] = blackPawn;
            board[i][1].setPiece(blackPawn);
        }

        // rooks
        whitePieces[8] = new piece(pieceColor.white, pieceType.rook,
                board[0][7], new rook(), R.drawable.wrook);
        board[0][7].setPiece(whitePieces[8]);
        whitePieces[9] = new piece(pieceColor.white, pieceType.rook,
                board[7][7], new rook(), R.drawable.wrook);
        board[7][7].setPiece(whitePieces[9]);
        blackPieces[8] = new piece(pieceColor.black, pieceType.rook,
                board[0][0], new rook(), R.drawable.brook);
        board[0][0].setPiece(blackPieces[8]);
        blackPieces[9] = new piece(pieceColor.black, pieceType.rook,
                board[7][0], new rook(), R.drawable.brook);
        board[7][0].setPiece(blackPieces[9]);
        // knights
        whitePieces[10] = new piece(pieceColor.white, pieceType.knight,
                board[1][7], new knight(), R.drawable.wknight);
        board[1][7].setPiece(whitePieces[10]);
        whitePieces[11] = new piece(pieceColor.white, pieceType.knight,
                board[6][7], new knight(), R.drawable.wknight);
        board[6][7].setPiece(whitePieces[11]);
        blackPieces[10] = new piece(pieceColor.black, pieceType.knight,
                board[1][0], new knight(), R.drawable.bknight);
        board[1][0].setPiece(blackPieces[10]);
        blackPieces[11] = new piece(pieceColor.black, pieceType.knight,
                board[6][0], new knight(), R.drawable.bknight);
        board[6][0].setPiece(blackPieces[11]);
        // bishops
        whitePieces[12] = new piece(pieceColor.white, pieceType.bishop,
                board[2][7], new bishop(), R.drawable.wbishop);
        board[2][7].setPiece(whitePieces[12]);
        whitePieces[13] = new piece(pieceColor.white, pieceType.bishop,
                board[5][7], new bishop(), R.drawable.wbishop);
        board[5][7].setPiece(whitePieces[13]);
        blackPieces[12] = new piece(pieceColor.black, pieceType.bishop,
                board[2][0], new bishop(), R.drawable.bbishop);
        board[2][0].setPiece(blackPieces[12]);
        blackPieces[13] = new piece(pieceColor.black, pieceType.bishop,
                board[5][0], new bishop(), R.drawable.bbishop);
        board[5][0].setPiece(blackPieces[13]);
        // queens
        whitePieces[14] = new piece(pieceColor.white, pieceType.queen,
                board[3][7], new queen(), R.drawable.wqueen);
        board[3][7].setPiece(whitePieces[14]);
        blackPieces[14] = new piece(pieceColor.black, pieceType.queen,
                board[3][0], new queen(), R.drawable.bqueen);
        board[3][0].setPiece(blackPieces[14]);
        // kings
        whitePieces[15] = new piece(pieceColor.white, pieceType.king,
                board[4][7], new king(), R.drawable.wking);
        board[4][7].setPiece(whitePieces[15]);
        blackPieces[15] = new piece(pieceColor.black, pieceType.king,
                board[4][0], new king(), R.drawable.bking);
        board[4][0].setPiece(blackPieces[15]);

        white = new player(pieceColor.white, whitePieces);
        black = new player(pieceColor.black, blackPieces);
    }

    private enum pieceType {
        rook, pawn, bishop, knight, queen, king
    }

    private enum pieceColor {
        white, black
    }

    private enum pieceCondition {
        dead, alive
    }

    private enum gameState {
        allClear, whiteCheck, whiteCheckMate, blackCheck, blackCheckMate, stalemate
    }

    // MEMBER VARIABLES

    private enum moveStatus {
        success, fail, promote
    }

    private interface availableMoves {
        public ArrayList<cell> getAvailableMoves(piece piece);
    }

    public class incomptablePieceTypeConversionException extends Exception {
        private static final long serialVersionUID = 1L;

        public incomptablePieceTypeConversionException(String string) {
            super(string);
        }
    }

    private class player {
        private pieceColor color;
        private piece[] pieces;

        player(pieceColor color, piece[] pieces) {
            this.color = color;
            this.pieces = pieces;
        }

        public pieceColor getPieceColor() {
            return color;
        }

        public piece[] getPieces() {
            return pieces;
        }

        public boolean move(piece piece, cell moveTo) throws Exception {
            if (piece == null || piece.getPieceColour() != this.color
                    || moveTo == null || moveTo == deadCell)
                return false;
            ArrayList<cell> availableMoves = piece.getAvailableMoves();
            if (availableMoves.contains(moveTo)) {
                moveStatus status = piece.tryMove(moveTo);
                if (status != moveStatus.promote) {
                    if (status == moveStatus.success) {
                        return true;
                    } else
                        return false;
                } else {
                    try {
                        pieceType type = pieceType.bishop;
                        piece.setPieceType(type);
                        return true;
                    } catch (incomptablePieceTypeConversionException e) {
                        throw new Exception("Hmm...");
                    }
                }
            }
            return false;
        }
    }

    public class cell {
        private int x;
        private int y;
        private piece piece;

        cell(int x, int y) {
            this.x = x;
            this.y = y;
            this.piece = null;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public piece getPiece() {
            return piece;
        }

        public void setPiece(piece piece) {
            if (this.piece != null) {
                this.piece.setPieceCondition(pieceCondition.dead);
            }
            this.piece = piece;
        }
    }

    public class piece {
        // colour of the piece
        protected pieceColor colour;
        private cell location;
        private pieceCondition condition;
        private int imageResource;
        // type of piece
        private pieceType type;
        // gets a list of moves that don't go off the board or cause friendly
        // fire
        private availableMoves availMoves;

        // .ctor
        piece(pieceColor colour, pieceType type, cell location,
              availableMoves movementPattern, int imageResource) {
            this.colour = colour;
            this.type = type;
            this.location = location;
            this.availMoves = movementPattern;
            this.imageResource = imageResource;
        }

        public cell getLocation() {
            return location;
        }

        public pieceCondition getPieceCondition() {
            return condition;
        }

        public void setPieceCondition(pieceCondition newCondition) {
            condition = newCondition;
            if (newCondition == pieceCondition.dead) {
                location.piece = null;
                location = deadCell;
            }
        }

        public int getImageResource() {
            return imageResource;
        }

        public pieceColor getPieceColour() {
            return colour;
        }

        // methods

        public pieceType getPieceType() {
            return type;
        }

        public void setPieceType(pieceType newType)
                throws incomptablePieceTypeConversionException {
            if (type != pieceType.pawn)
                throw new incomptablePieceTypeConversionException(
                        "Error: can only convert pawns");
            else {
                switch (newType) {
                    case bishop:
                        availMoves = new bishop();
                        break;
                    case knight:
                        availMoves = new knight();
                        break;
                    case rook:
                        availMoves = new rook();
                        break;
                    case queen:
                        availMoves = new queen();
                        break;
                    case pawn:
                    case king:
                        throw new incomptablePieceTypeConversionException(
                                "Error: invalid conversion from pawn.");
                }

            }
        }

        public boolean isValidMove(cell moveTo) {

            if (moveTo.getPiece() != null)
                if (moveTo.getPiece().getPieceColour() == this.colour)
                    return false;

            // try move
            cell targetCell = board[moveTo.getX()][moveTo.getY()];
            piece oldPiece = targetCell.getPiece();
            cell sourceCell = this.getLocation();
            targetCell.setPiece(this);
            sourceCell.setPiece(null);
            this.location = targetCell;
            this.setPieceCondition(pieceCondition.alive);

            gameState tryState = checkForCheck(board, this.colour);

            //revert
            sourceCell.setPiece(this);
            targetCell.setPiece(oldPiece);
            this.location = sourceCell;
            this.setPieceCondition(pieceCondition.alive);
            if (oldPiece != null) {
                oldPiece.location = targetCell;
                oldPiece.setPieceCondition(pieceCondition.alive);
            }

            // see if move is valid
            // validity of the move is determined by whether the move puts
            // the player into check. We know that the pieces cannot move
            // in invalid patterns because this has already been checked in
            // the only caller, which should be player.move()
            if ((colour == pieceColor.white && tryState == gameState.whiteCheck)
                    || (colour == pieceColor.black && tryState == gameState.blackCheck))
                return false;
            return true;
        }

        // move takes a valid cell on the board and tries to move the
        // piece to it. if the move is successful, returns true, if not
        // returns false
        public moveStatus tryMove(cell moveTo) {

            if (!isValidMove(moveTo))
                return moveStatus.fail;
            // move is valid; apply move and check for promotion if pawn
            // empty old location
            this.location.setPiece(null);
            // update new location
            moveTo.setPiece(this);
            // update self location
            this.location = moveTo;

            if (type == pieceType.pawn) {
                return (location.y == 0 || location.y == 7) ? moveStatus.promote
                        : moveStatus.success;
            }

            return moveStatus.success;
        }

        public ArrayList<cell> getAvailableMoves() {
            return availMoves.getAvailableMoves(this);
        }

    }

    private class pawn implements availableMoves {
        // only piece whose moves depend on colour
        public ArrayList<cell> getAvailableMoves(piece piece) {
            if (piece.getLocation() == deadCell)
                return null;
            ArrayList<cell> retList = new ArrayList<cell>();
            int currX = piece.getLocation().getX();
            int currY = piece.getLocation().getY();

            // this could be better implemented with some sort of
            // direction boolean deciding addition + subtraction, but
            // i don't feel like dicking with it, so here are two
            // separate blocks of code.

            // as standard, white is on bottom, black on top
            // white moves 6 -> 0; black moves 1 -> 7
            if (piece.getPieceColour() == pieceColor.white) {
                // move forward one cell
                if (board[currX][currY - 1].getPiece() == null)
                    retList.add(board[currX][currY - 1]);

                // check moving left
                if (currX > 0
                        && board[currX - 1][currY - 1].getPiece() != null
                        && board[currX - 1][currY - 1].getPiece()
                        .getPieceColour() == pieceColor.black)
                    retList.add(board[currX - 1][currY - 1]);

                // check moving right
                if (currX < 7
                        && board[currX + 1][currY - 1].getPiece() != null
                        && board[currX + 1][currY - 1].getPiece()
                        .getPieceColour() == pieceColor.black)
                    retList.add(board[currX + 1][currY - 1]);

                // default location, making 4 available moves, rather than 3
                if (currY == 6 && board[currX][currY - 1].getPiece() == null
                        && board[currX][currY - 2].getPiece() == null)
                    retList.add(board[currX][currY - 2]);
            }
            if (piece.getPieceColour() == pieceColor.black) {
                // COPY PASTA! YAAY! : (
                // move forward one cell
                if (board[currX][currY + 1].getPiece() == null)
                    retList.add(board[currX][currY + 1]);

                // check moving left
                if (currX > 0
                        && board[currX - 1][currY + 1].getPiece() != null
                        && board[currX - 1][currY + 1].getPiece().getPieceColour() == pieceColor.white)
                    retList.add(board[currX - 1][currY + 1]);

                // check moving right
                if (currX < 7
                        && board[currX + 1][currY + 1].getPiece() != null
                        && board[currX + 1][currY + 1].getPiece().getPieceColour() == pieceColor.white)
                    retList.add(board[currX + 1][currY + 1]);

                // default location, making 4 available moves, rather than 3
                if (currY == 1 && board[currX][currY + 1].getPiece() == null
                        && board[currX][currY + 2].getPiece() == null)
                    retList.add(board[currX][currY + 2]);
            }
            return retList;
        }
    }

    private class bishop implements availableMoves {
        public ArrayList<cell> getAvailableMoves(piece piece) {
            if (piece.getLocation() == deadCell)
                return null;
            ArrayList<cell> retList = new ArrayList<cell>();
            int currX = piece.getLocation().getX();
            int currY = piece.getLocation().getY();
            // set i to 1 because there's no point in checking whether
            // staying in place is a valid move. that's fucking stupid
            // even with colour checking
            int i = 1;

            // check the diagonal until you see a piece.
            // right, down
            while ((currX + i < 8 && currY + i < 8)
                    && (board[currX + i][currY + i].getPiece() == null || board[currX
                    + i][currY + i].getPiece().getPieceColour() != piece.getPieceColour())) {
                // if the piece is one of the opponent's, it is a valid move
                if (board[currX + i][currY + i].getPiece() != null
                        && board[currX + i][currY + i].getPiece().getPieceColour() != piece.getPieceColour()) {
                    retList.add(board[currX + i][currY + i]);
                    break;
                }
                retList.add(board[currX + i][currY + i]);
                i++;
            }

            i = 1;
            // right, up
            while ((currX + i < 8 && currY - i > -1)
                    && (board[currX + i][currY - i].getPiece() == null || board[currX
                    + i][currY - i].getPiece().getPieceColour() != piece.getPieceColour())) {
                if (board[currX + i][currY - i].getPiece() != null
                        && board[currX + i][currY - i].getPiece().getPieceColour() != piece.getPieceColour()) {
                    retList.add(board[currX + i][currY - i]);
                    break;
                }
                retList.add(board[currX + i][currY - i]);
                i++;
            }

            i = 1;
            // left, down
            while ((currX - i > -1 && currY + i < 8)
                    && (board[currX - i][currY + i].getPiece() == null || board[currX
                    - i][currY + i].getPiece().getPieceColour() != piece.getPieceColour())) {
                if (board[currX - i][currY + i].getPiece() != null
                        && board[currX - i][currY + i].getPiece().getPieceColour() != piece.getPieceColour()) {
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
                    - i][currY - i].getPiece().getPieceColour() != piece.getPieceColour())) {
                if (board[currX - i][currY - i].getPiece() != null
                        && board[currX - i][currY - i].getPiece().getPieceColour() != piece.getPieceColour()) {
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

    private class knight implements availableMoves {
        public ArrayList<cell> getAvailableMoves(piece piece) {
            if (piece.getLocation() == deadCell)
                return null;
            ArrayList<cell> retList = new ArrayList<cell>();
            int currX = piece.getLocation().getX();
            int currY = piece.getLocation().getY();
            // the knight has 8 moves, so we'll just explicitly define them
            // since it's not that much more verbose than the alternateive
            // and much easier to write.
            // TODO: think about optimizing this. not a priority at the moment
            // like, this is a circular pattern, or check the rectangle 2 away,
            // skipping every other cell

            if (currX > 1
                    && currY > 0
                    && (board[currX - 2][currY - 1].getPiece() == null || board[currX - 2][currY - 1].getPiece().getPieceColour() != piece.getPieceColour()))
                retList.add(board[currX - 2][currY - 1]);

            if (currX > 0
                    && currY > 1
                    && (board[currX - 1][currY - 2].getPiece() == null || board[currX - 1][currY - 2].getPiece().getPieceColour() != piece.getPieceColour()))
                retList.add(board[currX - 1][currY - 2]);

            if (currX < 7
                    && currY > 1
                    && (board[currX + 1][currY - 2].getPiece() == null || board[currX + 1][currY - 2].getPiece().getPieceColour() != piece.getPieceColour()))
                retList.add(board[currX + 1][currY - 2]);

            if (currX < 6
                    && currY > 0
                    && (board[currX + 2][currY - 1].getPiece() == null || board[currX + 2][currY - 1].getPiece().getPieceColour() != piece.getPieceColour()))
                retList.add(board[currX + 2][currY - 1]);

            if (currX < 6
                    && currY < 7
                    && (board[currX + 2][currY + 1].getPiece() == null || board[currX + 2][currY + 1].getPiece().getPieceColour() != piece.getPieceColour()))
                retList.add(board[currX + 2][currY + 1]);

            if (currX < 7
                    && currY < 6
                    && (board[currX + 1][currY + 2].getPiece() == null || board[currX + 1][currY + 2].getPiece().getPieceColour() != piece.getPieceColour()))
                retList.add(board[currX + 1][currY + 2]);

            if (currX > 0
                    && currY < 6
                    && (board[currX - 1][currY + 2].getPiece() == null || board[currX - 1][currY + 2].getPiece().getPieceColour() != piece.getPieceColour()))
                retList.add(board[currX - 1][currY + 2]);

            if (currX > 1
                    && currY < 7
                    && (board[currX - 2][currY + 1].getPiece() == null || board[currX - 2][currY + 1].getPiece().getPieceColour() != piece.getPieceColour()))
                retList.add(board[currX - 2][currY + 1]);

            return retList;
        }
    }

    private class rook implements availableMoves {
        public ArrayList<cell> getAvailableMoves(piece piece) {
            if (piece.getLocation() == deadCell)
                return null;
            ArrayList<cell> retList = new ArrayList<cell>();
            int currX = piece.getLocation().getX();
            int currY = piece.getLocation().getY();
            int i = 1;

            // right
            while (currX + i < 8
                    && (board[currX + i][currY].getPiece() == null || board[currX
                    + i][currY].getPiece().getPieceColour() != piece.getPieceColour())) {
                if (board[currX + i][currY].getPiece() != null
                        && board[currX + i][currY].getPiece().getPieceColour() != piece.getPieceColour()) {
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
                    - i][currY].getPiece().getPieceColour() != piece.getPieceColour())) {
                if (board[currX - i][currY].getPiece() != null
                        && board[currX - i][currY].getPiece().getPieceColour() != piece.getPieceColour()) {
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
                    + i].getPiece().getPieceColour() != piece.getPieceColour())) {
                if (board[currX][currY + i].getPiece() != null
                        && board[currX][currY + i].getPiece().getPieceColour() != piece.getPieceColour()) {
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
                    - i].getPiece().getPieceColour() != piece.getPieceColour())) {
                if (board[currX][currY - i].getPiece() != null
                        && board[currX][currY - i].getPiece().getPieceColour() != piece.getPieceColour()) {
                    retList.add(board[currX][currY - i]);
                    break;
                }
                retList.add(board[currX][currY - i]);
                i++;
            }
            return retList;
        }
    }

    private class queen implements availableMoves {
        private rook horizontalVerical;
        private bishop diagonal;

        public ArrayList<cell> getAvailableMoves(piece piece) {
            if (piece.getLocation() == deadCell)
                return null;
            horizontalVerical = new rook();
            diagonal = new bishop();
            ArrayList<cell> retList = horizontalVerical
                    .getAvailableMoves(piece);
            ArrayList<cell> moreMoves = diagonal.getAvailableMoves(piece);
            for (int i = 0; i < moreMoves.size(); i++) {
                retList.add(moreMoves.get(i));
            }
            return retList;
        }
    }

    private class king implements availableMoves {
        public ArrayList<cell> getAvailableMoves(piece piece) {
            if (piece.getLocation() == deadCell)
                return null;
            ArrayList<cell> retList = new ArrayList<cell>();
            int currX = piece.getLocation().getX();
            int currY = piece.getLocation().getY();
            // if the king moves and there is a check, regardless of the colour
            // it is an invalid move.
            // TODO:implement king moves in a way that won't cause infinite
            // loops
            // for example: checkForCheck doesn't look at the king. That would
            // do it
            // Also: don't check for mate here.

            if (currX > 0 && currY > 0
                    && (piece.isValidMove(board[currX - 1][currY - 1])))
                retList.add(board[currX - 1][currY - 1]);

            if (currY > 0 && (piece.isValidMove(board[currX][currY - 1])))
                retList.add(board[currX][currY - 1]);

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

    private class userInterfaceBoard {
        // .ctor
        userInterfaceBoard() {
        }

        public cell getTargetCell() {
            // TODO: write UI interaction
            cell retCell = deadCell;
            return retCell;
        }

        public cell getSourceCell() {
            // TODO: write UI interaction
            cell retCell = deadCell;
            return retCell;
        }

        public piece getPiece() {
            // TODO: i'm not sure that this should really come from the source
            // cell.
            // we'll see
            piece retPiece = getSourceCell().getPiece();
            return retPiece;
        }
    }
}
