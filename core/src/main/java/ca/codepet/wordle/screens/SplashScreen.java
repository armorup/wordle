package ca.codepet.wordle.screens;

import com.badlogic.gdx.Screen;

import ca.codepet.wordle.MainGame;

public class SplashScreen extends InfoScreen {
  private final Screen nextScreen;
  private float timeElapsed = 0;

  public SplashScreen(MainGame game, Screen nextScreen) {
    super(game, nextScreen, "images/splash.png", 0.4f);
    this.nextScreen = nextScreen;
  }

  @Override
  public void render(float delta) {
    super.render(delta);

    timeElapsed += delta;

    if (game.assetManager.update()) {
      // Assets are loaded
      if (timeElapsed > 2) {
        // Show the next screen
        game.setScreen(nextScreen);
      }
    }

  }
}