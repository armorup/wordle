package ca.codepet.wordle;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameScreen implements Screen {

    private String targetWord = "apple"; // Example target word
    private String currentGuess = "";
    private List<String> pastGuesses = new ArrayList<>();
    private boolean gameOver = false;

    private final MainGame game;

    public GameScreen(MainGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyTyped(char character) {
                if (gameOver)
                    return false;

                // Handle backspace
                if (character == '\b' && currentGuess.length() > 0) {
                    currentGuess = currentGuess.substring(0, currentGuess.length() - 1);
                } else if (character == '\r' || character == '\n') {
                    // Handle enter key (submit guess)
                    if (currentGuess.length() == 5) {
                        checkGuess(currentGuess);
                        currentGuess = ""; // Clear guess after submission
                    }
                } else if (Character.isLetter(character) && currentGuess.length() < 5) {
                    currentGuess += character;
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        // Render past guesses
        for (int i = 0; i < pastGuesses.size(); i++) {
            game.font.draw(game.batch, pastGuesses.get(i), 100, 400 - i * 30);
        }
        // Show current guess
        game.font.draw(game.batch, "Current Guess: " + currentGuess, 100, 50);

        // Render "game over" message if applicable
        if (gameOver) {
            game.font.draw(game.batch, "You Win!", 100, 100);
        }
        game.batch.end();
    }

    public void checkGuess(String guess) {
        if (guess.equals(targetWord)) {
            gameOver = true;
        }
        pastGuesses.add(guess);
        currentGuess = ""; // Reset guess after checking
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }
}