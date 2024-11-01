package ca.codepet.wordle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class InputHandler extends InputAdapter {
  private final MainGame game;
  private final Wordle wordle;
  private Rectangle playAgainButtonBounds;
  private Rectangle statsButtonBounds;

  public InputHandler(Wordle wordle, MainGame game) {
    this.wordle = wordle;
    this.game = game;
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

  public void setStatsButtonBounds(Rectangle bounds) {
    statsButtonBounds = bounds;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    if (wordle.isGameOver()) {
      // Check if the play again button was pressed
      if (playAgainButtonBounds != null) {
        Vector2 touchPos = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
        if (playAgainButtonBounds.contains(touchPos.x, touchPos.y)) {
          // Restart the game
          wordle.restart();
          return true;
        }
      }
      // Check if the stats button was pressed
      if (statsButtonBounds != null) {
        Vector2 touchPos = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
        if (statsButtonBounds.contains(touchPos.x, touchPos.y)) {
          // Show the stats screen
          game.setScreen(new StatsScreen(game, game.getScreen()));
          return true;
        }
      }
    }

    return false;
  }

}
