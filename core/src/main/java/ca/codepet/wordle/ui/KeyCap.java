package ca.codepet.wordle.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class KeyCap {
  private static final float KEY_WIDTH = 30;
  private static final float KEY_HEIGHT = 40;

  private final String letter;
  private Texture keyTexture; // Background texture for the key
  private final Rectangle bounds; // Hitbox for detecting clicks
  private boolean isSelected;

  public KeyCap(String letter, float x, float y) {
    this(letter, x, y, KEY_WIDTH, KEY_HEIGHT);
  }

  public KeyCap(String letter, float x, float y, float width, float height) {
    this.letter = letter;
    this.keyTexture = new Texture("images/button_square_gradient.png");
    this.bounds = new Rectangle(x, y, width, height);
    this.isSelected = false;

  }

  public void render(SpriteBatch batch) {
    // Draw the key texture
    batch.draw(keyTexture, bounds.x, bounds.y, bounds.width, bounds.height);
    // Optionally change color or texture based on the state
    if (isSelected) {
      // Change texture or color to indicate the key was pressed
    }
  }

  public void update() {
    // Update logic if needed
  }

  public boolean isClicked(float mouseX, float mouseY) {
    return bounds.contains(mouseX, mouseY);
  }

  public void setSelected(boolean selected) {
    this.isSelected = selected;
  }

  public String getLetter() {
    return letter;
  }
}
