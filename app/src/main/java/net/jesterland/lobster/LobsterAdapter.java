package net.jesterland.lobster;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jester on 2/1/2017.
 */

public class LobsterAdapter extends ArrayAdapter<MovieInfo>
{
    private Context mContext;
    private int layoutResource;
    private ArrayList<MovieInfo> movieData = new ArrayList<MovieInfo>();

    public LobsterAdapter(Context context, int resource, ArrayList<MovieInfo> movies){
        super(context,resource,movies);
        this.layoutResource = resource;
        this.mContext = context;
        this.movieData = movies;
    }





     // Update grid data and refresh grid items.

    public void setMovieData(ArrayList<MovieInfo> movieD) {
        this.movieData = movieD;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) row.findViewById(R.id.grid_item_title);
            holder.titleTextView = (TextView) row.findViewById(R.id.grid_item_overview);
            holder.titleTextView = (TextView) row.findViewById(R.id.grid_item_releasedate);
            holder.titleTextView = (TextView) row.findViewById(R.id.grid_item_voteaverage);
            holder.imageView = (ImageView) row.findViewById(R.id.grid_item_image); //here vaeas
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        MovieInfo item = movieData.get(position);

        Picasso.with(mContext).load(item.getImage()).into(holder.imageView);
        return row;
    }

    static class ViewHolder {
        TextView titleTextView;
        ImageView imageView;
    }
}


