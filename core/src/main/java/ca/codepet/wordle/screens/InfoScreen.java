package ca.codepet.wordle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ca.codepet.wordle.MainGame;

public abstract class InfoScreen extends InputAdapter implements Screen {

  protected final MainGame game;
  protected final Screen previousScreen;
  protected final Texture infoTexture;
  protected final SpriteBatch batch;

  public InfoScreen(MainGame game, Screen previousScreen, String texturePath) {
    this.game = game;
    this.previousScreen = previousScreen;
    this.infoTexture = (texturePath == null) ? null : new Texture(Gdx.files.internal(texturePath));
    this.batch = new SpriteBatch();
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(this);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1); // Set the clear color to black
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen

    if (infoTexture != null) {
      // Calculate the scaling factors to fit the image on the screen
      float screenWidth = Gdx.graphics.getWidth();
      float screenHeight = Gdx.graphics.getHeight();
      float imageWidth = infoTexture.getWidth();
      float imageHeight = infoTexture.getHeight();
      float scale = Math.min(screenWidth / imageWidth, screenHeight / imageHeight);

      // Calculate the position to center the image
      float x = (screenWidth - imageWidth * scale) / 2;
      float y = (screenHeight - imageHeight * scale) / 2;

      // Draw the resized image
      batch.begin();
      batch.draw(infoTexture, x, y, imageWidth * scale, imageHeight * scale);
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
    infoTexture.dispose();
    batch.dispose();
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