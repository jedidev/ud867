package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;

import com.udacity.gradle.builditbigger.heal.backend.Joke;
import com.udacity.gradle.builditbigger.heal.backend.JokeApi;
import com.udacity.gradle.builditbigger.heal.backend.jokes.Jokes;

public class RemoteJokeAsyncTask extends AsyncTask<Pair<Context, String>, Void, Joke> {

    private Jokes jokeService;
    private Context context;

    @Override
    protected Joke doInBackground(Pair<Context, String>... params) {

        if (jokeService == null) {
            Jokes.Builder = new Jokes.Builder(AndroidHttp
        }
    }
}
