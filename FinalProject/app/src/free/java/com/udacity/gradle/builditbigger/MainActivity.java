package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.heal.JokesProvider;
import com.udacity.gradle.builditbigger.heal.backend.jokes.model.Joke;
import com.udacity.gradle.builditbigger.heal.jokeview.JokeActivity;

import java.util.concurrent.ExecutionException;

import static com.udacity.gradle.builditbigger.heal.jokeview.JokeActivity.JOKE_TEXT_EXTRA;

public class MainActivity extends AppCompatActivity {

    private JokesProvider jokesProvider;
    private Button remoteButton;
    private Button localButton;
    private InterstitialAd interstitialAd;
    private Intent nextIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                startActivity(nextIntent);
            }
        });

        requestNewInterstitial();

        remoteButton = (Button) findViewById(R.id.remote_joke_button);
        localButton = (Button) findViewById(R.id.local_joke_button);
        jokesProvider = new JokesProvider();
    }

    private void requestNewInterstitial() {

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        interstitialAd.loadAd(adRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        remoteButton.setVisibility(View.VISIBLE);
        remoteButton.setEnabled(true);
        localButton.setEnabled(true);
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

    public void tellLocalJoke(View view) {

        localButton.setEnabled(false);
        remoteButton.setEnabled(false);

        String joke = jokesProvider.getRandomJoke();
        showJoke(joke);
    }

    public void tellRemoteJoke(View view) throws ExecutionException, InterruptedException {

        localButton.setEnabled(false);
        remoteButton.setEnabled(false);
        remoteButton.setVisibility(View.INVISIBLE);
        new RemoteJokeAsyncTask(this).execute();
    }

    public void remoteJokeReceived(Joke joke) {

        if (joke == null) {
            Toast.makeText(this, R.string.could_not_download_joke, Toast.LENGTH_LONG).show();
        } else {
            showJoke(joke.getJokeText());
        }
    }

    private void showJoke(String joke) {

        nextIntent = new Intent(this, JokeActivity.class);
        nextIntent.putExtra(JOKE_TEXT_EXTRA, joke);

        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            startActivity(nextIntent);
        }
    }
}
