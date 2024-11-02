package ca.codepet.wordle.screens;

import com.badlogic.gdx.Screen;
import ca.codepet.wordle.MainGame;

public class SplashScreen extends InfoScreen {
  public SplashScreen(MainGame game, Screen previousScreen) {
    super(game, previousScreen, "images/libgdx.png");
  }
}