package ca.codepet.wordle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class InputHandler extends InputAdapter {
  private final Wordle wordle;
  private Rectangle playAgainButtonBounds;

  public InputHandler(Wordle wordle) {
    this.wordle = wordle;
  }

  @Override
  public boolean keyTyped(char character) {
    if (wordle.isGameOver())
      return false;

    // Handle backspace
    if (character == '\b') {
      wordle.backspace();

    } else if (character == '\r' || character == '\n') {
      // Enter key pressed
      System.out.println("Enter pressed");
      wordle.submitGuess();

    } else if (Character.isLetter(character)) {
      // Letter typed
      wordle.addLetter(character);
    }
    return true;

  }

  public void setPlayAgainButtonBounds(Rectangle bounds) {
    playAgainButtonBounds = bounds;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    if (playAgainButtonBounds != null && wordle.isGameOver()) {
      Vector2 touchPos = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
      if (playAgainButtonBounds.contains(touchPos.x, touchPos.y)) {
        // Restart the game
        wordle.restart();
        return true;
      }
    }
    return false;
  }

}
