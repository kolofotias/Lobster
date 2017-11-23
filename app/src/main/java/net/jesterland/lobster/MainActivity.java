package net.jesterland.lobster;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private LobsterAdapter adapter;
    private ArrayList<MovieInfo> movieInfos;
    private String API_KEY = BuildConfig.TMDB_API_KEY;
    private GridView gv;
    static boolean sortByPopu = true;
    private String FEED = "http://api.themoviedb.org/3/movie/popular?api_key="+API_KEY;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_main);

        gv = (GridView) findViewById(R.id.grid_view);
        movieInfos = new ArrayList<>();
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Initialize with empty data
        movieInfos = new ArrayList<>();
        adapter = new LobsterAdapter(this, R.layout.movie_item, movieInfos);
        gv.setAdapter(adapter);

        //Grid view click event
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                MovieInfo item = (MovieInfo) parent.getItemAtPosition(position);

               Intent myIntent = new Intent(MainActivity.this, DetailsActivity.class);
                myIntent.putExtra("title", item.getTitle());
                myIntent.putExtra("overview", item.getOverview());
                myIntent.putExtra("release_date", item.getRelease_date());
                myIntent.putExtra("vote_average", item.getRating());
                myIntent.putExtra("image", item.getImage());

                MainActivity.this.startActivity(myIntent);

        }
             });
        //Start download
        new DownloadImageTask().execute(FEED);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        /* Inflate menu layout to this menu */
        inflater.inflate(R.menu.main, menu);
        /* Return true to display menu in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_rating) {
            sortByPopu = false;
            Context context = MainActivity.this;
            recreate();
            String textToShow = "Highest Rated Movies";
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();

            return super.onOptionsItemSelected(item);

        }else{
            sortByPopu = true;
            recreate();
            Context context = MainActivity.this;
            String textToShow = "Most Popular Movies";
            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();

            return super.onOptionsItemSelected(item);

        }

    }

    public class DownloadImageTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                String urlString = null;
                if (sortByPopu) {
                    urlString = "http://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY;
                } else {
                    urlString = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + API_KEY;
                }
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));
                String response = stream2String(inputStream);
                parser(response);
                result = 1;
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }


        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Update UI
            if (result == 1) {
                adapter.setMovieData(movieInfos);
            } else {
                Toast.makeText(MainActivity.this, "Failed to fetch data! Please connect to the Internet!", Toast.LENGTH_SHORT).show();
            }
            mProgressBar.setVisibility(View.GONE);
        }

    }
        String stream2String(InputStream stream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            String line;
            String result1 = "";
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    result1 += line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (null != stream) {
                stream.close();
            }
            return result1;
        }




         // Parsing the feed results and get the list
            private void parser(String result) {
            try {
                JSONObject response = new JSONObject(result);
                JSONArray movies = response.optJSONArray("results");
                MovieInfo item;
                for (int i = 0; i < movies.length(); i++) {
                    JSONObject post = movies.optJSONObject(i);
                    String title = post.optString("original_title");
                    String cover = post.optString("poster_path");
                    String cover2 = "http://image.tmdb.org/t/p/w185/" + cover;
                    String overview = post.optString("overview");
                    String vote_average = post.optString("vote_average");
                    vote_average = vote_average + "/10";
                    String release_date = post.optString("release_date");
                    if (release_date.length() > 4)
                        release_date = release_date.substring(0, 4);

                    item = new MovieInfo();
                    item.setImage(cover2);
                    item.setTitle(title);
                    item.setOverview(overview);
                    item.set_rating(vote_average);
                    item.setRelease_date(release_date);

                    movieInfos.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    private boolean isOnline(Context context) {
        ConnectivityManager mngr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = mngr.getActiveNetworkInfo();
        return !(info == null || (info.getState() != NetworkInfo.State.CONNECTED));
    }


}
