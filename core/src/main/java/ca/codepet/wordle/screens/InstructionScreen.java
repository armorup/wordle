package ca.codepet.wordle.screens;

import com.badlogic.gdx.Screen;
import ca.codepet.wordle.MainGame;

/**
 * Instruction screen on how to play the game.
 */
public class InstructionScreen extends BaseScreen {

  public InstructionScreen(MainGame game, Screen previousScreen) {
    super(game, previousScreen, "images/instructions.png", 1);
  }
}