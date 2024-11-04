package ca.codepet.wordle.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Keyboard {
  private final List<KeyCap> keys;

  public Keyboard() {
    keys = new ArrayList<>();
    createKeys();
  }

  private void createKeys() {
    String[] row1 = { "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P" };
    String[] row2 = { "A", "S", "D", "F", "G", "H", "J", "K", "L" };
    String[] row3 = { "Enter", "Z", "X", "C", "V", "B", "N", "M", "<-" };

    float keyWidth = 40; // Set your key width
    float keyHeight = 50; // Set your key height
    float startX = 100; // Starting X position
    float startY = 100; // Starting Y position

    // Create keys for row 1
    for (int i = 0; i < row1.length; i++) {
      keys.add(new KeyCap(row1[i], startX + i * keyWidth, startY, keyWidth, keyHeight));
    }

    // Create keys for row 2
    startY -= keyHeight; // Move to the next row
    for (int i = 0; i < row2.length; i++) {
      keys.add(new KeyCap(row2[i], startX + i * keyWidth, startY, keyWidth, keyHeight));
    }

    // Create keys for row 3
    startY -= keyHeight; // Move to the next row
    for (int i = 0; i < row3.length; i++) {
      keys.add(new KeyCap(row3[i], startX + i * keyWidth, startY, keyWidth, keyHeight));
    }

    // Add special keys for Enter and Backspace
    keys.add(new KeyCap("Enter", startX, startY - keyHeight, keyWidth * 2, keyHeight));
    keys.add(new KeyCap("Back", startX + keyWidth * 3, startY - keyHeight, keyWidth * 2, keyHeight));
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
