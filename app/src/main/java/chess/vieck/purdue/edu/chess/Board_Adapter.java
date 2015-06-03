package chess.vieck.purdue.edu.chess;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

/**
 * Created by Michael on 4/10/2015.
 */
public class Board_Adapter extends BaseAdapter {
    FrameLayout touchLayout;
    ImageView touchImage;
    Core.Piece currentPiece;
    ViewHolder curHolder, destHolder = null;
    View rootView;
    private Context context;
    private Core core;

    Board_Adapter(Context context) {
        this.context = context;
        currentPiece = null;
        rootView = ((Activity) context).getWindow().getDecorView().getRootView();
    }

    protected void setCore(Core core) {
        this.core = core;
    }

    @Override
    public int getCount() {
        return 64;
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
        View squareContainerView;
        if (convertView == null) {
            //Inflate the layout
            final LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            squareContainerView = layoutInflater.inflate(R.layout.square, parent, false);

            // Background
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.square = (ImageView) squareContainerView.findViewById(R.id.square);
            viewHolder.square.setImageResource(core.squareImage(position + position / 8 % 2));
            viewHolder.piece = (ImageView) squareContainerView.findViewById(R.id.Piece);
            if (this.core.getPieceImage(position) != null) {
                viewHolder.piece.setImageResource(core.getPieceImage(position));
            }
            viewHolder.location = position;
            viewHolder.piece.setOnTouchListener(new ListenForTouch());
            viewHolder.square.setOnDragListener(new ListenForDrag());
            squareContainerView.setTag(viewHolder);
            viewHolder.piece.setTag(viewHolder);
        } else {
            squareContainerView = convertView;
        }
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
                curHolder = (ViewHolder) v.getTag();
                currentPiece = core.getPiece(curHolder.location);
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
            FrameLayout square;
            ImageView piece;

            switch (dragAction) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    square = (FrameLayout) v.getParent();
                    piece = (ImageView) square.getChildAt(1);
                    destHolder = (ViewHolder) square.getTag();
                    try {
                        if (currentPiece != null && curHolder.location != destHolder.location) {
                            if (core.move(curHolder.location, destHolder.location)) {
                                if (core.testCheckMate(destHolder.location)) {
                                    context.startActivity(new Intent(context, StartActivity.class));
                                }
                                TextView txt_check = (TextView) rootView.findViewById(R.id.txt_inCheck);
                                txt_check.setText("");
                                core.setPiece(destHolder.location, currentPiece);
                                core.setPiece(curHolder.location, null);
                                core.pieceArray[destHolder.location].setLocation(destHolder.location);
                                curHolder.piece = touchImage;
                                destHolder.piece.setImageResource(core.getPiece(destHolder.location).getImageResource());
                                square.setTag(destHolder);
                                piece.setVisibility(View.VISIBLE);
                                if (core.testCheck(destHolder.location)) {
                                    txt_check.setText("In Check");
                                    if (core.testCheckMate(destHolder.location)) {
                                        context.startActivity(new Intent(context, StartActivity.class));
                                    }
                                }
                                core.setTurn();
                                TextView txt_turn = (TextView) rootView.findViewById(R.id.txt_turn);
                                txt_turn.setText("" + core.getTurn());

                                return true;
                            } else if (core.testCheck(curHolder.location)) {
                                if (core.testCheckMate(destHolder.location)) {
                                    context.startActivity(new Intent(context, StartActivity.class));
                                } else {
                                    TextView txt_check = (TextView) rootView.findViewById(R.id.txt_inCheck);
                                    txt_check.setText("In Check");
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.d("Move", "Move Exception Thrown");
                    }
                    touchImage.setVisibility(View.VISIBLE);
                    return false;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
            }
            return true;
        }
    }
}