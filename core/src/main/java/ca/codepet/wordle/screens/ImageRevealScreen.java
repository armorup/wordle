package ca.codepet.wordle.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import ca.codepet.wordle.MainGame;

public class ImageRevealScreen extends InfoScreen {

  private final int ROWS = 10;
  private final int COLS = 10;

  private Texture originalTexture;
  private Pixmap maskPixmap;
  private Texture maskTexture;

  public ImageRevealScreen(MainGame game, Screen previousScreen) {
    super(game, previousScreen, getImagePath(game), 1);

    originalTexture = new Texture("images/background.png");

    maskPixmap = new Pixmap(originalTexture.getWidth(), originalTexture.getHeight(), Pixmap.Format.RGBA8888);
    maskPixmap.setColor(0, 0, 0, 1);
    maskPixmap.fill();

    maskTexture = new Texture(maskPixmap);

  }

  private static String getImagePath(MainGame game) {
    return "images/background.png";
  }

  private void revealImage(int wins) {
    int sectionsToReveal = Math.min(wins, 5); // Number of sections to reveal
    Random random = new Random(game.SEED);

    for (int i = 0; i < sectionsToReveal; i++) {
      int x = random.nextInt(originalTexture.getWidth() - 50);
      int y = random.nextInt(originalTexture.getHeight() - 50);
      int size = random.nextInt(100) + 50;

      maskPixmap.setColor(1, 1, 1, 0); // Set color to transparent
      maskPixmap.fillRectangle(x, y, size, size);
    }

    maskTexture.draw(maskPixmap, 0, 0);
  }

  @Override
  public void render(float delta) {
    super.render(delta);

    game.batch.begin();

    // Enable blending to handle transparency
    Gdx.gl.glEnable(GL20.GL_BLEND);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

    // Draw the mask texture
    game.batch.draw(maskTexture, 0, 0);

    // Disable blending
    Gdx.gl.glDisable(GL20.GL_BLEND);

    game.batch.end();
  }

  @Override
  public void dispose() {
    super.dispose();
    originalTexture.dispose();
    maskPixmap.dispose();
    maskTexture.dispose();
  }
}