package ca.codepet.wordle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ca.codepet.wordle.MainGame;

public abstract class BaseScreen extends InputAdapter implements Screen {

  protected final MainGame game;
  protected final Screen nextScreen;
  protected Texture bgTexturePath;
  protected final float bgTextureScale;
  protected final SpriteBatch batch;

  /**
   * If no next screen navigation is needed
   */
  public BaseScreen(MainGame game) {
    this(game, null);
  }

  public BaseScreen(MainGame game, Screen nextScreen) {
    this(game, nextScreen, null, 1f);
  }

  public BaseScreen(MainGame game, Screen nextScreen, String texturePath, float scale) {
    this.game = game;
    this.nextScreen = nextScreen;
    if (texturePath != null) {
      this.bgTexturePath = new Texture(Gdx.files.internal(texturePath));
    }

    this.batch = new SpriteBatch();
    this.bgTextureScale = scale;
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(this);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1); // Set the clear color to black
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen

    if (bgTexturePath != null) {
      // Calculate the scaling factors to fit the image on the screen
      float screenWidth = Gdx.graphics.getWidth();
      float screenHeight = Gdx.graphics.getHeight();
      float imageWidth = bgTexturePath.getWidth();
      float imageHeight = bgTexturePath.getHeight();
      float scaleFactor = Math.min(screenWidth / imageWidth, screenHeight / imageHeight) * bgTextureScale;

      // Calculate the position to center the image
      float x = (screenWidth - imageWidth * scaleFactor) / 2;
      float y = (screenHeight - imageHeight * scaleFactor) / 2;

      // Draw the resized image
      batch.begin();
      batch.draw(bgTexturePath, x, y, imageWidth * scaleFactor, imageHeight * scaleFactor);
      batch.end();
    }
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
    bgTexturePath.dispose();
    batch.dispose();
  }

  @Override
  public boolean keyDown(int keycode) {
    game.setScreen(nextScreen);
    return true;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    if (nextScreen != null && Gdx.input.justTouched()) {
      game.setScreen(nextScreen);
    }
    return true;
  }
}