package com.udacity.gradle.builditbigger.heal.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.udacity.gradle.builditbigger.heal.JokesProvider;

@Api(
    name = "jokes",
    version = "v1",
    namespace = @ApiNamespace(
        ownerDomain = "backend.heal.builditbigger.gradle.udacity.com",
        ownerName = "backend.heal.builditbigger.gradle.udacity.com",
        packagePath = ""
    )
)

public class JokeApi {

    private JokesProvider jokesProvider = new JokesProvider();

    @ApiMethod(name = "randomjoke")
    public Joke getRandomJoke() {

        jokesProvider.setJokePrefix("Remote ");
        Joke joke = new Joke();
        joke.setJokeText(jokesProvider.getRandomJoke());
        return joke;
    }
}
