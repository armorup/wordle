package ca.codepet.wordle;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameScreen implements Screen {

    // Add color definitions
    private static final Color CORRECT_COLOR = Color.GREEN; // Correct letter and position
    private static final Color WRONG_POSITION_COLOR = Color.YELLOW; // Correct letter, wrong position
    private static final Color INCORRECT_COLOR = Color.GRAY; // Incorrect letter

    private final String targetWord = "apple"; // Example target word
    private String currentGuess = "";
    private final List<GuessFeedback> pastGuesses = new ArrayList<>();
    private final boolean gameOver = false;

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
                    currentGuess += Character.toUpperCase(character);
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen

        game.batch.begin();

        // Render past guesses with color coding
        for (int i = 0; i < pastGuesses.size(); i++) {
            GuessFeedback feedback = pastGuesses.get(i);
            for (int j = 0; j < feedback.guess.length(); j++) {
                game.font.setColor(feedback.colors[j]);
                String str = String.valueOf(feedback.guess.charAt(j)).toUpperCase();
                int x = 100 + j * 40;
                int y = 400 - i * 30;
                game.font.draw(game.batch, str, x, y);
            }
        }

        // Show current guess (uncolored for now)
        game.font.setColor(Color.WHITE); // Reset to default color for current guess
        game.font.draw(game.batch, "Your Guess: " + currentGuess.toUpperCase(), 100, 50);

        game.batch.end();
    }

    public void checkGuess(String guess) {
        Color[] feedbackColors = new Color[guess.length()];
        boolean[] checkedTarget = new boolean[targetWord.length()];
        boolean[] checkedGuess = new boolean[guess.length()];

        // First pass: check for correct letters in the correct position (green)
        for (int i = 0; i < guess.length(); i++) {
            char guessChar = Character.toUpperCase(guess.charAt(i));
            char targetChar = Character.toUpperCase(targetWord.charAt(i));
            if (guessChar == targetChar) {
                feedbackColors[i] = CORRECT_COLOR;
                checkedTarget[i] = true; // Mark this letter in target as checked
                checkedGuess[i] = true; // Mark this letter in guess as checked
            } else {
                feedbackColors[i] = null; // Mark as not checked yet
            }
        }

        // Second pass: check for correct letters in the wrong position (yellow)
        for (int i = 0; i < guess.length(); i++) {
            if (!checkedGuess[i]) { // Only check unmarked guesses
                char guessChar = Character.toUpperCase(guess.charAt(i)); // Get the guess character
                for (int j = 0; j < targetWord.length(); j++) {
                    char targetChar = Character.toUpperCase(targetWord.charAt(j)); // Use j to get the target character
                    if (!checkedTarget[j] && guessChar == targetChar) {
                        feedbackColors[i] = WRONG_POSITION_COLOR;
                        checkedTarget[j] = true; // Mark this letter in target as checked
                        break; // Exit the loop after marking
                    }
                }
            }
        }

        // Set any remaining null colors to incorrect (gray)
        for (int i = 0; i < feedbackColors.length; i++) {
            if (feedbackColors[i] == null) {
                feedbackColors[i] = INCORRECT_COLOR;
            }
        }

        // Add to past guesses
        pastGuesses.add(new GuessFeedback(guess, feedbackColors));
        currentGuess = ""; // Clear the current guess after checking
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

// Create a class to hold guess and feedback
class GuessFeedback {
    String guess;
    Color[] colors;

    GuessFeedback(String guess, Color[] colors) {
        this.guess = guess;
        this.colors = colors;
    }
}
