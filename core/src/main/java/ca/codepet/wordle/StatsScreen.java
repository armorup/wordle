package ca.codepet.wordle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class StatsScreen extends InputAdapter implements Screen {

  private final MainGame game;
  private final Screen previousScreen;

  public StatsScreen(MainGame game, Screen previousScreen) {
    this.game = game;
    this.previousScreen = previousScreen;
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(this);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 1, 1); // Set the clear color to blue
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
  }

  @Override
  public void resize(int width, int height) {
    // Resize your screen here. The parameters represent the new window size.
  }

  @Override
  public void pause() {
    // Invoked when your application is paused.
  }

  @Override
  public void resume() {
    // Invoked when your application is resumed after pause.
  }

  @Override
  public void hide() {
    // This method is called when another screen replaces this one.
  }

  @Override
  public void dispose() {
    // Destroy screen's assets here.
  }

  @Override
  public boolean keyDown(int keycode) {
    game.setScreen(previousScreen);
    return true;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    game.setScreen(previousScreen);
    return true;
  }
}