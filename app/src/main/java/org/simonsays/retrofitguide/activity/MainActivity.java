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
import android.widget.Toast;

import com.google.gson.Gson;

import org.simonsays.retrofitguide.R;
import org.simonsays.retrofitguide.model.Stream;
import org.simonsays.retrofitguide.rest.ApiClient;
import org.simonsays.retrofitguide.rest.TwitchService;
import org.simonsays.retrofitguide.model.TopStreamsResponse;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button fetchButton;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fetchButton = (Button) findViewById(R.id.fetchButton);
        fetchButton.setOnClickListener(new View.OnClickListener() {
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getStreamData(final int channelPos) {

        // Establish connection with the Twitch.tv API
        TwitchService twitchService = ApiClient.getClient().create(TwitchService.class);
        final Call<TopStreamsResponse> streamDataCall =
                twitchService.singleStreamResponse(channelPos);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        streamDataCall.enqueue(new Callback<TopStreamsResponse>() {
            @Override
            public void onResponse(Call<TopStreamsResponse> call, Response<TopStreamsResponse> response) {
                progressBar.setVisibility(View.GONE);

                TopStreamsResponse result = response.body();
                Log.d("MainActivity", "response = " + new Gson().toJson(result));

                if (response.isSuccessful()) {
                    Stream streamResult = result.getStream(0);
                    Log.d("MainActivity", "streamResult= " + streamResult);

                    //Display stream response in the streamTextView
                    final TextView streamTextView = (TextView) findViewById(R.id.streamTextView);
                    streamTextView.setText(streamResult.toString());

                    //Display channel response in the streamTextView
                    Stream.Channel channelResult = streamResult.getChannel();
                    final TextView channelTextView = (TextView) findViewById(R.id.channelTextView);
                    channelTextView.setText(channelResult.toString());


                } else {
                    // response received but request not successful (like 400,401,403 etc)
                    showToast("Request Failed");
                }
            }

            @Override
            public void onFailure(Call<TopStreamsResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                showToast("Request Failed");
            }
        });
    }

    private static int getRandomNumberWithMax(int max) {
        Random r = new Random();
        return r.nextInt(max);
    }

    private void showToast(String text){
        Toast.makeText(MainActivity.this, text,
                Toast.LENGTH_LONG).show();
    }
}