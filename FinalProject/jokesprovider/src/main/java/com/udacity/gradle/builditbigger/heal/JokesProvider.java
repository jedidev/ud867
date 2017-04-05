package com.udacity.gradle.builditbigger.heal;

public class JokesProvider {

    private String jokePrefix = "Local ";

    /**
     * @param jokeId
     * @return Joke
     */
    private String getJoke(int jokeId) {

        return jokePrefix + " Joke " + jokeId;
    }

    /**
     * @return number of jokes
     */
    private int getJokeCount() {

        return 10;
    }

    /**
     * @return random joke
     */
    public String getRandomJoke() {

        int random = (int)(Math.random() * (getJokeCount() - 1));
        return getJoke(random);
    }

    /**
     * @param jokePrefix
     */
    public void setJokePrefix(String jokePrefix) {
        this.jokePrefix = jokePrefix;
    }
}
