package ca.codepet.wordle;

import com.badlogic.gdx.InputAdapter;

public class InputHandler extends InputAdapter {
  private final Wordle wordle;

  public InputHandler(Wordle wordle) {
    this.wordle = wordle;
  }

  @Override
  public boolean keyTyped(char character) {
    return handleKeyPress(character);
  }

  // Handles key presses and updates the guess
  private boolean handleKeyPress(char character) {
    if (wordle.isGameOver())
      return false;

    // Handle backspace
    String currentGuess = wordle.getCurrentGuess();

    if (character == '\b' && currentGuess.length() > 0) {
      currentGuess = currentGuess.substring(0, currentGuess.length() - 1);
      wordle.setCurrentGuess(currentGuess);
    } else if (character == '\r' || character == '\n') {
      // Handle enter key (submit guess)
      if (currentGuess.length() == 5) {
        wordle.submitGuess();
      }
    } else if (Character.isLetter(character) && currentGuess.length() < 5) {
      currentGuess += Character.toUpperCase(character);
      wordle.setCurrentGuess(currentGuess);
    }
    return true;
  }

}
