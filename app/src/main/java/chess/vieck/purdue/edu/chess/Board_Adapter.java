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
import android.widget.Toast;

import chess.vieck.purdue.edu.chess.Core.Piece;

/**
 * Created by Michael on 4/10/2015.
 */
public class Board_Adapter extends BaseAdapter {
    boolean pieceSelected;
    boolean reset;
    FrameLayout touchLayout;
    ImageView touchImage;
    Piece currentPiece;
    int currentLocation;
    private Context context;
    private Core core;

    Board_Adapter(Context context) {
        this.context = context;
        currentPiece = null;
        pieceSelected = false;
        reset = false;
    }

    protected void setCore(Core core) {
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
        if (squareContainerView == null) {
            //Inflate the layout
            final LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            squareContainerView = layoutInflater.inflate(R.layout.square, null);

            // Background
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.square = (ImageView) squareContainerView.findViewById(R.id.square);
            viewHolder.square.setImageResource(core.squareImage(position + position / 8 % 2));
            viewHolder.piece = (ImageView) squareContainerView.findViewById(R.id.Piece);
            if (this.core.pieceImage(position) != null) {
                viewHolder.piece.setImageResource(core.pieceImage(position));
            }
            viewHolder.location = position;
            viewHolder.piece.setOnTouchListener(new ListenForTouch());
            viewHolder.square.setOnDragListener(new ListenForDrag());
            squareContainerView.setTag(viewHolder);
            // ViewHolder viewHolder = (ViewHolder) squareContainerView.getTag(pieceImage(position));
            viewHolder.piece.setTag(viewHolder);
        }
        ViewHolder viewHolder = (ViewHolder) squareContainerView.getTag();
        return squareContainerView;
    }

    static class ViewHolder {
        public ImageView square;
        public ImageView piece;
        public int location;
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
                ViewHolder viewHolder = (ViewHolder) touchImage.getTag();
                currentLocation = viewHolder.location;
                currentPiece = core.getPiece(currentLocation);
                return true;
            } else {
                return false;
            }
        }
    }

    private final class ListenForDrag implements OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent dragEvent) {
            int dragAction = dragEvent.getAction();
            View dragView = (View) dragEvent.getLocalState();
            FrameLayout square;
            ImageView board;
            //Log.d("DEBUG","Name:"+v.getResources().getResourceName(v.getId()));
            switch (dragAction) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    square = (FrameLayout) v.getParent();
                    board = (ImageView) square.getChildAt(1);
                    ViewHolder viewHolder = (ViewHolder) board.getTag();
                    int destinationLocation = viewHolder.location;

                    Toast.makeText(context, "" + currentLocation + " " + destinationLocation,
                            Toast.LENGTH_SHORT).show();
                    if (currentPiece != null && currentPiece.getLocation() != destinationLocation) {
                        Piece destinationPiece = core.getPiece(destinationLocation);
                        core.setPiece(destinationLocation, currentPiece);
                        core.setPiece(currentLocation, null);
                        board.setVisibility(View.VISIBLE);
                        viewHolder.piece = destinationPiece.getImageResource();
                        try {
                            //if (core.move(currentPiece.getLocation(), piece.getLocation())) {
                                /*touchImage.setImageResource(currentPiece.getImageResource());
                                touchImage.setVisibility(View.VISIBLE);
                                core.setPiece(piece.getLocation(), destPiece);
                                core.setPiece(currentPiece.getLocation(),currentPiece);
                                */
                            board.setTag(viewHolder);
                            Log.d("Move","PieceArray["+destinationLocation+"]:"+core.getPiece(destinationLocation));
                            return true;
                            //}
                        } catch (Exception e) {
                            Log.d("DEBUG", "Piece move failed.");
                        }
                        return false;
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
            }
            return true;
        }
    }
}