package ca.codepet.wordle.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ca.codepet.wordle.MainGame;

public class Keyboard {
  private final List<KeyCap> keys;
  private final MainGame game;
  private final BitmapFont font;

  public Keyboard(MainGame game) {
    this.game = game;
    this.font = game.faSolidFont;
    keys = new ArrayList<>();
    createKeys();
  }

  private void createKeys() {
    String[] row1 = { "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P" };
    String[] row2 = { "A", "S", "D", "F", "G", "H", "J", "K", "L" };
    String[] row3 = { "Ent", "Z", "X", "C", "V", "B", "N", "M", "<" };

    float keyWidth = 40; // Set your key width
    float keyHeight = 50; // Set your key height
    float startX = 100; // Starting X position
    float startY = 150; // Starting Y position

    // Create keys for row 1
    for (int i = 0; i < row1.length; i++) {
      keys.add(new KeyCap(row1[i], font, startX + i * keyWidth, startY, keyWidth, keyHeight));
    }

    // Create keys for row 2
    startY -= keyHeight; // Move to the next row
    startX += keyWidth / 2; // Offset the starting X position
    for (int i = 0; i < row2.length; i++) {
      keys.add(new KeyCap(row2[i], font, startX + i * keyWidth, startY, keyWidth, keyHeight));
    }

    // Create keys for row 3
    startY -= keyHeight; // Move to the next row
    startX -= keyWidth / 2; // Offset the starting X position
    for (int i = 0; i < row3.length; i++) {
      // Make the first and last keys wider
      float width = keyWidth;
      if (i == 0 || i == row3.length - 1) {
        width *= 1.5f;
      }
      keys.add(new KeyCap(row3[i], font, startX, startY, width, keyHeight));
      startX = startX + width;
    }
  }

  public void render(SpriteBatch batch) {
    for (KeyCap key : keys) {
      key.render(batch);
    }
  }

  public void handleClick(float mouseX, float mouseY) {
    for (KeyCap key : keys) {
      if (key.isClicked(mouseX, mouseY)) {
        key.setSelected(true); // Update the key state as needed
        // Handle key press logic here (e.g., update game state)
      } else {
        key.setSelected(false); // Reset the key state if not clicked
      }
    }
  }
}
