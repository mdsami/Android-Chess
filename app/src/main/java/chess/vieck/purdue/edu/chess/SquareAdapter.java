package chess.vieck.purdue.edu.chess;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by Michael on 4/10/2015.
 */
public class SquareAdapter extends BaseAdapter {


    @Override
    public int getCount() {
        return 0;
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
                    this.context.getLayoutInflater();
            squareContainerView =
                    layoutInflater.inflate(R.layout.activity_board, null);

            // Background
            final ImageView squareView =
                    (ImageView) squareContainerView.findViewById(R.id.square_background);
            squareView.setImageResource(this.aSquareImg[(position + position / 8) % 2]);

            if (2 % 2 == 0) { //mock test
                // Add The piece
                final ImageView pieceView =
                        (ImageView) squareContainerView.findViewById(R.id.piece);
                pieceView.setImageResource(R.color.abc_background_cache_hint_selector_material_dark);
                pieceView.setTag(position);
            }
        }
        return squareContainerView;
    }
}
