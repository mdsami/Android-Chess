package chess.vieck.purdue.edu.chess;

import android.content.ClipData;
import android.content.Context;
import android.util.Log;
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

/**
 * Created by Michael on 4/10/2015.
 */
public class Board_Adapter extends BaseAdapter {
    boolean pieceSelected;
    boolean reset;
    private int fromX, fromY, toX, toY, topCorner, width;

    private Context context;
   // Piece currentPiece = null;


    FrameLayout touchLayout;
    ImageView touchImage;
    private Core core;
    /* Unused
    private Resources resources;
    private Canvas canvas;
    */
    private Integer[] boardSquares = new Integer[64];

    public void setCore(Core core){
        if (core != null) {
            this.core = core;
        }
    }

    Board_Adapter(Context context) {
        this.context = context;
        for (int i = 0; i < 64; i++) {
            if ((i % 2) == 0) {
                boardSquares[i] = R.drawable.blacksquare;
            } else {
                boardSquares[i] = R.drawable.whitesquare;
            }
        }
        pieceSelected = false;
        reset = true;
        fromX = -1;
        fromY = -1;
        toX = -1;
        toY = -1;
        //resources = getResources();

        //setFocusable(true);
        //setFocusableInTouchMode(true);
    }

    protected Integer squareImage(int position) {
        return boardSquares[position];
    }


    public void setLogicEngine(Core logic) {
        if (logic != null) {
            this.core = logic;
        }
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

        if (convertView == null) {
            //Inflate the layout
            final LayoutInflater layoutInflater =
                    LayoutInflater.from(this.context);
            squareContainerView =
                    layoutInflater.inflate(R.layout.square, null);

            // Background
            final ImageView squareView =
                    (ImageView) squareContainerView.findViewById(R.id.square_background);
            squareView.setImageResource(this.squareImage((position + position / 8) % 2));

            // Add The Piece
            final ImageView pieceView =
                    (ImageView) squareContainerView.findViewById(R.id.piece);
            if (reset) {
                if ((position >= 48 && position <= 55)) {
                    pieceView.setImageResource(R.drawable.bpawn);
                    pieceView.setTag(position);
                } else if (position >= 8 && position <= 15) {
                    pieceView.setImageResource(R.drawable.wpawn);
                    pieceView.setTag(position);
                } else if (position == 0 || position == 7) {
                    pieceView.setImageResource(R.drawable.wrook);
                } else if (position == 56 || position == 63) {
                    pieceView.setImageResource(R.drawable.brook);
                } else if (position == 1 || position == 6) {
                    pieceView.setImageResource(R.drawable.wknight);
                } else if (position == 57 || position == 62) {
                    pieceView.setImageResource(R.drawable.bknight);
                } else if (position == 2 || position == 5) {
                    pieceView.setImageResource(R.drawable.wbishop);
                } else if (position == 58 || position == 61) {
                    pieceView.setImageResource(R.drawable.bbishop);
                } else if (position == 3) {
                    pieceView.setImageResource(R.drawable.wqueen);
                } else if (position == 59) {
                    pieceView.setImageResource(R.drawable.bqueen);
                } else if (position == 4) {
                    pieceView.setImageResource(R.drawable.wking);
                } else if (position == 60) {
                    pieceView.setImageResource(R.drawable.bking);
                }
            }
        }
        return squareContainerView;
    }

    private final class ListenForTouch implements OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                ClipData clipData = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new DragShadowBuilder(v);
                v.startDrag(clipData, shadowBuilder,v, 0);
                v.setVisibility(View.INVISIBLE);
                touchLayout = (FrameLayout) v.getParent();
                touchImage = (ImageView) touchLayout.getChildAt(1);
                Log.d("Debug","Screen touched");
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
            if (action == DragEvent.ACTION_DROP) {
                FrameLayout square = (FrameLayout) v.getParent();
                ImageView imageView = (ImageView) square.getChildAt(1);
            }
            return true;
        }
    }
}