package ca.codepet.wordle.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Key {
  private final String letter;
  private TextureRegion keyTexture; // Background texture for the key
  private Rectangle bounds; // Hitbox for detecting clicks
  private boolean isSelected;

  public Key(String letter, Texture keyTexture, float x, float y, float width, float height) {
    this.letter = letter;
    this.keyTexture = new TextureRegion(keyTexture);
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
