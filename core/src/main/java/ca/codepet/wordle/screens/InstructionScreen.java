package ca.codepet.wordle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import ca.codepet.wordle.MainGame;

/**
 * Instruction screen on how to play the game.
 */
public class InstructionScreen extends BaseScreen {

  public InstructionScreen(MainGame game, Screen previousScreen) {
    super(game, previousScreen, "images/instructions.png", 1);

  }

  @Override
  public void render(float delta) {
    super.render(delta);

    game.batch.begin();
    game.font.draw(game.batch, "x", Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 50);
    game.batch.end();
  }
}