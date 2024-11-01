package ca.codepet.wordle;

import com.badlogic.gdx.InputAdapter;

public class InputHandler extends InputAdapter {
  private final Wordle wordle;

  public InputHandler(Wordle wordle) {
    this.wordle = wordle;
  }

  @Override
  public boolean keyTyped(char character) {
    if (wordle.isGameOver())
      return false;

    System.out.println(character);
    // Handle backspace
    if (character == '\b') {
      wordle.backspace();

    } else if (character == '\r' || character == '\n') {
      // Enter key pressed
      System.out.println("Enter pressed");
      if (wordle.submitGuess()) {
        System.out.println("Submitted");
      }

    } else if (Character.isLetter(character)) {
      // Letter typed
      wordle.addLetter(character);
    }
    return true;

  }

}
