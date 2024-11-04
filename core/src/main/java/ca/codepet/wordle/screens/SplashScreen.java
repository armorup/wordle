package ca.codepet.wordle.screens;

import com.badlogic.gdx.Screen;
import ca.codepet.wordle.MainGame;

/**
 * The splash screen that is displayed when the game is launched.
 */
public class SplashScreen extends BaseScreen {
  private float timeElapsed = 0;

  public SplashScreen(MainGame game, Screen nextScreen) {
    super(game, nextScreen, "images/splash.png", 0.4f);
  }

  @Override
  public void render(float delta) {
    super.render(delta);

    timeElapsed += delta;

    // Show the next screen after 1.5 seconds
    if (timeElapsed > 1.5f) {
      game.setScreen(nextScreen);
    }

  }
}