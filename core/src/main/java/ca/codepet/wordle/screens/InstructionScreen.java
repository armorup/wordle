package ca.codepet.wordle.screens;

import com.badlogic.gdx.Screen;
import ca.codepet.wordle.MainGame;

public class InstructionScreen extends BaseScreen {

  public InstructionScreen(MainGame game, Screen previousScreen) {
    super(game, previousScreen, "images/instructions.png", 1);
  }
}