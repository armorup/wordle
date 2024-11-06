package ca.codepet.wordle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ca.codepet.wordle.MainGame;

public class SplashScreen extends BaseScreen {
  private float timeElapsed = 0;
  private final ShapeRenderer shapeRenderer;
  private float progress;

  public SplashScreen(MainGame game, Screen nextScreen) {
    super(game, nextScreen, "images/teamh.png", 1f);
    shapeRenderer = new ShapeRenderer();
    progress = 0f;
  }

  @Override
  public void render(float delta) {
    super.render(delta);

    timeElapsed += delta;

    // Update the progress bar
    progress = game.assetManager.getProgress();

    // Check if assets have finished loading
    if (game.assetManager.update()) {
      // Show the next screen after 2 seconds
      if (timeElapsed > 2f) {
        game.setScreen(new GameScreen(game));
      }
    }

    // Render the progress bar
    renderProgressBar();
  }

  private void renderProgressBar() {
    float progressBarWidth = Gdx.graphics.getWidth() * 0.6f;
    float progressBarHeight = 25f;
    float progressBarX = (Gdx.graphics.getWidth() - progressBarWidth) / 2;
    float progressBarY = Gdx.graphics.getHeight() * 0.1f;

    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(Color.DARK_GRAY);
    shapeRenderer.rect(progressBarX, progressBarY, progressBarWidth, progressBarHeight);

    shapeRenderer.setColor(Color.GREEN);
    shapeRenderer.rect(progressBarX, progressBarY, progressBarWidth * progress, progressBarHeight);
    shapeRenderer.end();
  }

  @Override
  public void dispose() {
    super.dispose();
    shapeRenderer.dispose();
  }
}