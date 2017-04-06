package com.udacity.gradle.builditbigger.test;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.AndroidJUnitRunner;

import com.udacity.gradle.builditbigger.RemoteJokeAsyncTask;
import com.udacity.gradle.builditbigger.heal.backend.jokes.model.Joke;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

@RunWith(AndroidJUnit4.class)
public class GetRemoteJokeTest extends AndroidJUnitRunner {

    @Test
    public void jokeReceivedByClient() throws ExecutionException, InterruptedException {

        final RemoteJokeAsyncTask asyncTask = new RemoteJokeAsyncTask(null);

        asyncTask.execute();

        Joke joke = asyncTask.get();
        Assert.assertNotNull(joke);
        Assert.assertTrue(joke.getJokeText().length() > 0);
    }
}