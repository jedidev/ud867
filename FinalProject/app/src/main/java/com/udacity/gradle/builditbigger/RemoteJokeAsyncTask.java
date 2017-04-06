package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.heal.backend.jokes.Jokes;
import com.udacity.gradle.builditbigger.heal.backend.jokes.model.Joke;

import java.io.IOException;

public class RemoteJokeAsyncTask extends AsyncTask<Void, Void, Joke> {

    private Jokes jokeService;
    private MainActivity callback;

    public RemoteJokeAsyncTask(MainActivity callback) {

        this.callback = callback;
    }

    @Override
    protected Joke doInBackground(Void... voids) {

        if (jokeService == null) {
            Jokes.Builder jokesBuilder = new Jokes.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.1.4:8080/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });

            jokeService = jokesBuilder.build();
        }

        try {
            return jokeService.randomjoke().execute();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Joke joke) {

        if (callback != null) {
            callback.remoteJokeReceived(joke);
        }
    }
}
