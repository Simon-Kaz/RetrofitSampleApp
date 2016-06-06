package org.simonsays.retrofitguide.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import org.simonsays.retrofitguide.R;
import org.simonsays.retrofitguide.model.GameOverview;
import org.simonsays.retrofitguide.model.Stream;
import org.simonsays.retrofitguide.rest.ApiClient;
import org.simonsays.retrofitguide.rest.TwitchService;
import org.simonsays.retrofitguide.model.TopGamesResp;
import org.simonsays.retrofitguide.model.TopStreamsResp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ProgressBar progressBar;
    private List<GameOverview> gameOverviewList;
    private ArrayList<GameOverview.GameDetails> gameDetailsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rand = getRandomNumberWithMax(2000);
                Log.d("MainActivity", "RAND= " + rand);
                getStreamData(rand);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void topGamesAction() {

        gameOverviewList = new ArrayList<>();

        TwitchService twitchService = ApiClient.getClient().create(TwitchService.class);

        final Call<TopGamesResp> topGamesCall =
                twitchService.topGamesResp();
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        topGamesCall.enqueue(new Callback<TopGamesResp>() {
            @Override
            public void onResponse(Call<TopGamesResp> call, Response<TopGamesResp> response) {
                progressBar.setVisibility(View.GONE);

                // get games from response
                if (response.isSuccessful()) {
                    TopGamesResp result = response.body();
                    Log.d("MainActivity", "response = " + new Gson().toJson(result));
                    gameOverviewList = result.getGameOverview();
                    Log.d("MainActivity", "Items = " + gameOverviewList.size());

                    String gameTitleList = "";
                    int index = 1;
                    gameDetailsList = new ArrayList<>();
                    for (GameOverview gList : gameOverviewList) {
                        gameTitleList += gList.gameDetails.name;
                    }

                    final TextView textView = (TextView) findViewById(R.id.textView);
                    textView.setText(gameTitleList);

                } else {
                    // response received but request not successful (like 400,401,403 etc)
                    //Handle errors

                }

            }

            @Override
            public void onFailure(Call<TopGamesResp> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                final TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText("Something went wrong: " + t.getMessage());
            }
        });
    }

    public void getStreamData(final int channelPos) {

        TwitchService twitchService = ApiClient.getClient().create(TwitchService.class);
        final Call<TopStreamsResp> streamDataCall =
                twitchService.specificStreamResp(channelPos);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        streamDataCall.enqueue(new Callback<TopStreamsResp>() {
            @Override
            public void onResponse(Call<TopStreamsResp> call, Response<TopStreamsResp> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    TopStreamsResp result = response.body();
                    Log.d("MainActivity", "response = " + new Gson().toJson(result));
                    Stream streamResult = result.getStream(0);

                    final TextView textView = (TextView) findViewById(R.id.textView);

                    textView.setText(streamResult.toString());

                    Stream.Channel channelResult = streamResult.getChannel();

                    final TextView textView2 = (TextView) findViewById(R.id.textView2);
                    textView2.setText(channelResult.toString());


                } else {
                    // response received but request not successful (like 400,401,403 etc)
                    //Handle errors
                }
            }

            @Override
            public void onFailure(Call<TopStreamsResp> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                final TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText("Something went wrong: " + t.getMessage());
            }
        });
    }

    private static int getRandomNumberWithMax(int max) {
        Random r = new Random();
        return r.nextInt(max);
    }
}