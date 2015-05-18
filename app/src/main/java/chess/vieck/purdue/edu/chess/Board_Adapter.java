package chess.vieck.purdue.edu.chess;

import android.content.ClipData;
import android.content.Context;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Michael on 4/10/2015.
 */
public class Board_Adapter extends BaseAdapter {
    /* Unused
    private Resources resources;
    private Canvas canvas;
    */
    public Integer[] boardSquares;
    public Core.Piece[] pieceArray = new Core.Piece[64];
    boolean pieceSelected;
    boolean reset;
    // Piece currentPiece = null;
    FrameLayout touchLayout;
    ImageView touchImage;
    Core.Piece piece;
    private int fromX, fromY, toX, toY, topCorner, width;
    private Context context;
    private Core core;

    Board_Adapter(Context context) {
        this.context = context;
        boardSquares = new Integer[64];
        pieceArray = new Core.Piece[64];
        for (int i = 0; i < 64; i++) {
            if ((i % 2) == 0) {
                boardSquares[i] = R.drawable.blacksquare;
            } else {
                boardSquares[i] = R.drawable.whitesquare;
            }
        }
        for (int i = 0; i < 64; i++) {

            if ((i >= 48 && i <= 55)) {
                pieceArray[i] = new Core.Piece(Core.objectColour.white, Core.pieceType.rook, i, new Core.Pawn, );
                pieceArray[i].setImageResource(R.drawable.bpawn);
            } else if (i >= 8 && i <= 15) {
                pieceArray[i].setImageResource(R.drawable.wpawn);
            } else if (i == 0 || i == 7) {
                pieceArray[i].setImageResource(R.drawable.wrook);
            } else if (i == 56 || i == 63) {
                pieceArray[i].setImageResource(R.drawable.brook);
            } else if (i == 1 || i == 6) {
                pieceArray[i].setImageResource(R.drawable.wknight);
            } else if (i == 57 || i == 62) {
                pieceArray[i].setImageResource(R.drawable.bknight);
            } else if (i == 2 || i == 5) {
                pieceArray[i].setImageResource(R.drawable.wbishop);
            } else if (i == 58 || i == 61) {
                pieceArray[i].setImageResource(R.drawable.bbishop);
            } else if (i == 3) {
                pieceArray[i].setImageResource(R.drawable.wqueen);
            } else if (i == 59) {
                pieceArray[i].setImageResource(R.drawable.bqueen);
            } else if (i == 4) {
                pieceArray[i].setImageResource(R.drawable.wking);
            } else if (i == 60) {
                pieceArray[i].setImageResource(R.drawable.bking);
            }
        }
        pieceSelected = false;
        reset = true;
        fromX = -1;
        fromY = -1;
        toX = -1;
        toY = -1;
    }

    public void setCore(Core core) {
        if (core != null) {
            this.core = core;
        }
    }

    protected Integer squareImage(int position) {
        return boardSquares[position];
    }

    protected Integer pieceImage(int position) {
        return pieceArray[position].getImageResource();
    }

    private int getBoardXCoor(int x) {
        return x * width / 8;
    }

    private int getBoardYCoor(int y) {
        return y * width / 8 + topCorner;
    }

    @Override
    public int getCount() {
        return boardSquares.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View squareContainerView = convertView;

        if (convertView == null || !reset) {
            //Inflate the layout
            final LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            squareContainerView = layoutInflater.inflate(R.layout.square, null);

            // Background
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.square = (ImageView) squareContainerView.findViewById(R.id.square);
            viewHolder.square.setImageResource(squareImage((position + position / 8) % 2));
            viewHolder.piece = (ImageView) squareContainerView.findViewById(R.id.Piece);
            if (this.pieceImage(position) != null) {
                viewHolder.piece.setImageResource(pieceImage(position));
                viewHolder.piece.setOnTouchListener(new ListenForTouch());
                viewHolder.square.setOnDragListener(new ListenForDrag());
            } else {
                Toast.makeText(context, "No Piece",
                        Toast.LENGTH_SHORT).show();
            }
            squareContainerView.setTag(viewHolder);
        }
        // ViewHolder viewHolder = (ViewHolder) squareContainerView.getTag(pieceImage(position));
        // viewHolder.piece.setTag(boardPieces[position]);
        return squareContainerView;
    }

    static class ViewHolder {
        public ImageView square;
        public ImageView piece;
    }

    private final class ListenForTouch implements OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData clipData = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new DragShadowBuilder(v);
                v.startDrag(clipData, shadowBuilder, v, 0);
                v.setVisibility(View.INVISIBLE);
                touchLayout = (FrameLayout) v.getParent();
                touchImage = (ImageView) touchLayout.getChildAt(1);

                return true;
            } else {
                return false;
            }
        }
    }

    private final class ListenForDrag implements OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            FrameLayout square;
            ImageView board;
            if (action == DragEvent.ACTION_DROP) {
                square = (FrameLayout) v.getParent();
                board = (ImageView) square.getChildAt(1);
                Core.Piece pieceAtSquare = (Core.Piece) board.getTag();
                if (piece.getAvailableMoves().contains(pieceAtSquare.getLocation())) {

                }
            }
            return true;
        }
    }
}