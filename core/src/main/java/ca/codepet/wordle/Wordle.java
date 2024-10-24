package ca.codepet.wordle;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Wordle {
  // enum FeedbackStatus {
  // CORRECT, WRONG_POSITION, INCORRECT
  // }

  private static final int ROWS = 6; // Number of guesses allowed
  private static final int COLS = 5; // Number of letters in the word
  private final String targetWord; // The target word for this game
  private final List<Feedback> pastGuesses; // List of past guesses and their feedback
  private String currentGuess = ""; // Current guess being typed by the player

  public Wordle() {
    this("APPLE");
  }

  public Wordle(String targetWord) {
    this.targetWord = targetWord.toUpperCase(); // Ensure the target word is uppercase
    this.pastGuesses = new ArrayList<>();
  }

  // Getters and setters
  public void setCurrentGuess(String currentGuess) {
    this.currentGuess = currentGuess.toUpperCase();
  }

  public String getCurrentGuess() {
    return currentGuess;
  }

  public boolean isGameOver() {
    return pastGuesses.size() >= ROWS || hasWon();
  }

  public boolean hasWon() {
    // Check if the player has guessed the correct word
    return !pastGuesses.isEmpty() && pastGuesses.get(pastGuesses.size() - 1).isCorrect();
  }

  // Handle a new guess and return feedback
  public Feedback submitGuess() {

    if (currentGuess.length() != COLS) {
      throw new IllegalArgumentException("Guess must be " + COLS + " letters long.");
    }

    // Perform the logic for checking the guess against the target
    Feedback feedback = checkGuess(currentGuess);
    pastGuesses.add(feedback);

    // Clear the current guess for the next round
    currentGuess = "";
    return feedback;
  }

  public List<Feedback> getPastGuesses() {
    return pastGuesses;
  }

  public void render(float delta, SpriteBatch batch, BitmapFont font) {
    // Render past guesses with color coding
    for (int i = 0; i < getPastGuesses().size(); i++) {
      Wordle.Feedback feedback = getPastGuesses().get(i);
      for (int j = 0; j < feedback.getGuess().length(); j++) {
        font.setColor(feedback.colors[j]);
        String str = String.valueOf(feedback.guess.charAt(j)).toUpperCase();
        int x = 100 + j * 40;
        int y = 400 - i * 30;
        font.draw(batch, str, x, y);
      }
    }
  }

  // Inner class to encapsulate guess feedback
  public class Feedback {
    public final String guess;
    public final Color[] colors;
    private final boolean correct;

    public Feedback(String guess, Color[] feedbackColors, boolean correct) {
      this.guess = guess;
      this.colors = feedbackColors;
      this.correct = correct;
    }

    public String getGuess() {
      return guess;
    }

    public Color[] getColors() {
      return colors;
    }

    public boolean isCorrect() {
      return correct;
    }
  }

  private Feedback checkGuess(String guess) {
    Color[] feedbackColors = new Color[COLS];
    boolean[] checkedTarget = new boolean[targetWord.length()];
    boolean[] checkedGuess = new boolean[guess.length()];

    // First pass: check for correct letters in the correct position (green)
    for (int i = 0; i < guess.length(); i++) {
      char guessChar = guess.charAt(i);
      char targetChar = targetWord.charAt(i);
      if (guessChar == targetChar) {
        feedbackColors[i] = GameScreen.CORRECT_COLOR;
        checkedTarget[i] = true;
        checkedGuess[i] = true;
      }
    }

    // Second pass: check for correct letters in the wrong position (yellow)
    for (int i = 0; i < guess.length(); i++) {
      if (!checkedGuess[i]) {
        char guessChar = guess.charAt(i);
        for (int j = 0; j < targetWord.length(); j++) {
          if (!checkedTarget[j] && guessChar == targetWord.charAt(j)) {
            feedbackColors[i] = GameScreen.WRONG_POSITION_COLOR;
            checkedTarget[j] = true;
            break;
          }
        }
      }
    }

    // Mark all unmarked letters as incorrect (gray)
    for (int i = 0; i < feedbackColors.length; i++) {
      if (feedbackColors[i] == null) {
        feedbackColors[i] = GameScreen.INCORRECT_COLOR;
      }
    }

    // Return the guess and feedback (correct or not)
    return new Feedback(guess, feedbackColors, guess.equals(targetWord));
  }
}
