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

import chess.vieck.purdue.edu.chess.Core.Piece;

/**
 * Created by Michael on 4/10/2015.
 */
public class Board_Adapter extends BaseAdapter {
    boolean pieceSelected;
    boolean reset;
    FrameLayout touchLayout;
    ImageView touchImage;
    private Context context;
    private Core core;

    Board_Adapter(Context context) {
        this.context = context;
        pieceSelected = false;
        reset = false;
    }

    protected void setCore(Core core){
        this.core = core;
    }

    @Override
    public int getCount() {
        return core.getBoardLength();
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
            viewHolder.square.setImageResource(core.squareImage(position+position/8%2));
            viewHolder.piece = (ImageView) squareContainerView.findViewById(R.id.Piece);
            if (this.core.pieceImage(position) != null) {
                viewHolder.piece.setImageResource(core.pieceImage(position));
                viewHolder.piece.setOnTouchListener(new ListenForTouch());
                viewHolder.square.setOnDragListener(new ListenForDrag());
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
                Core.Piece pieceAtSquare = (Piece) board.getTag();
                if (pieceAtSquare != null) {
                    //if (piece.getAvailableMoves().contains(pieceAtSquare.getLocation()))
                }
            }
            return true;
        }
    }
}