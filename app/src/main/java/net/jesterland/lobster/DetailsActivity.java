package net.jesterland.lobster;

/**
 * Created by jester on 2/2/2017.
 */

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    private TextView titleTextView;
    private TextView plotTextView;
    private TextView voteTextView;
    private TextView releaseTextView;

    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        String title = getIntent().getStringExtra("title");
        String image = getIntent().getStringExtra("image");
        String overview = getIntent().getStringExtra("overview");
        String vote_average = getIntent().getStringExtra("vote_average");
        String release_date = getIntent().getStringExtra("release_date");

        titleTextView = (TextView) findViewById(R.id.grid_item_title);
        plotTextView = (TextView) findViewById(R.id.grid_item_overview);
        voteTextView = (TextView) findViewById(R.id.grid_item_voteaverage);
        releaseTextView = (TextView) findViewById(R.id.grid_item_releasedate);


        imageView = (ImageView) findViewById(R.id.grid_item_image);

        titleTextView.setText(title);
        plotTextView.setText(overview);
        voteTextView.setText(vote_average);
        releaseTextView.setText(release_date);


        Picasso.with(this).load(image).into(imageView);
    }
}
