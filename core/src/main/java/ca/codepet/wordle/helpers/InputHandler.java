package ca.codepet.wordle.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ca.codepet.wordle.MainGame;
import ca.codepet.wordle.Wordle;
import ca.codepet.wordle.screens.InstructionScreen;
import ca.codepet.wordle.screens.StatsScreen;

public class InputHandler extends InputAdapter {
  private final MainGame game;
  private final Wordle wordle;
  private Rectangle playAgainButtonBounds;
  private Rectangle statsButtonBounds;
  private Rectangle helpButtonBounds;

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
    if (playAgainButtonBounds == null) {
      System.out.println("Play again button bounds set");
      playAgainButtonBounds = bounds;
    }

  }

  public void setStatsButtonBounds(Rectangle bounds) {
    if (statsButtonBounds == null) {
      System.out.println("Stats button bounds set");
      statsButtonBounds = bounds;
    }
  }

  public void setHelpButtonBounds(Rectangle bounds) {
    if (helpButtonBounds == null) {
      helpButtonBounds = bounds;
      System.out.println("Help button bounds set");
    }
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    // Check if the help button was pressed
    if (helpButtonBounds != null) {
      Vector2 touchPos = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
      if (helpButtonBounds.contains(touchPos.x, touchPos.y)) {
        // Show the help screen
        game.setScreen(new InstructionScreen(game, game.getScreen()));
        return false;
      }
    }

    // Check if the game is over
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
