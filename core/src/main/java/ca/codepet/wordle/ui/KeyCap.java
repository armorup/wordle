package ca.codepet.wordle.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import ca.codepet.wordle.helpers.LetterStatus;

public class KeyCap {
  private static final float KEY_WIDTH = 40;
  private static final float KEY_HEIGHT = 50;

  private final String letter;
  private final BitmapFont font;
  private Texture keyTexture; // Background texture for the key
  private final Rectangle bounds; // Hitbox for detecting clicks
  private LetterStatus status;

  public KeyCap(String letter, BitmapFont font, float x, float y) {
    this(letter, font, x, y, KEY_WIDTH, KEY_HEIGHT);
  }

  public KeyCap(String letter, BitmapFont font, float x, float y, float width, float height) {
    this.letter = letter;
    this.font = font;
    this.keyTexture = new Texture("images/button_square_gradient.png");
    this.bounds = new Rectangle(x, y, width, height);
    this.status = LetterStatus.UNSELECTED;
  }

  public void render(SpriteBatch batch) {
    // Draw the key texture
    batch.draw(keyTexture, bounds.x, bounds.y, bounds.width, bounds.height);
    // Draw the letter on top of the key
    font.setColor(0.2f, 0.2f, 0.2f, 1);
    font.getData().setScale(0.5f);

    // Calculate the position to center the letter
    GlyphLayout layout = new GlyphLayout(font, letter);
    float textX = bounds.x + (bounds.width - layout.width) / 2;
    float textY = bounds.y + (bounds.height + layout.height) / 2;

    font.draw(batch, layout, textX, textY);

    // Optionally change color or texture based on the state
    switch (status) {
      case UNSELECTED:
        // Do nothing
        break;
      case CORRECT:
        // Change the key texture to a green gradient
        keyTexture = new Texture("images/button_square_green.png");
        break;
      case WRONG_POSITION:
        // Change the key texture to a yellow gradient
        keyTexture = new Texture("images/button_square_yellow.png");
        break;
      case INCORRECT:
        // Change the key texture to a red gradient
        keyTexture = new Texture("images/button_square_red.png");
        break;

    }

  }

  public void update() {
    // Update logic if needed
  }

  public boolean isClicked(float mouseX, float mouseY) {
    return bounds.contains(mouseX, mouseY);
  }

  public String getLetter() {
    return letter;
  }
}
