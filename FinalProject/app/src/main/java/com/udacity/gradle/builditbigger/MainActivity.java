package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.udacity.gradle.builditbigger.heal.JokesProvider;
import com.udacity.gradle.builditbigger.heal.backend.jokes.model.Joke;
import com.udacity.gradle.builditbigger.heal.jokeview.JokeActivity;

import java.util.concurrent.ExecutionException;

import static com.udacity.gradle.builditbigger.heal.jokeview.JokeActivity.JOKE_TEXT_EXTRA;

public class MainActivity extends AppCompatActivity {

    private JokesProvider jokesProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jokesProvider = new JokesProvider();
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

        String joke = jokesProvider.getRandomJoke();
        Intent intent = new Intent(this, JokeActivity.class);
        intent.putExtra(JOKE_TEXT_EXTRA, joke);

        startActivity(intent);
    }

    public void tellRemoteJoke(View view) throws ExecutionException, InterruptedException {

        new RemoteJokeAsyncTask(this).execute();
    }

    public void remoteJokeReceived(Joke joke) {

        if (joke == null) {
            Toast.makeText(this, R.string.could_not_download_joke, Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, JokeActivity.class);
        intent.putExtra(JOKE_TEXT_EXTRA, joke.getJokeText());

        startActivity(intent);
    }
}
