package ca.codepet.wordle.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import ca.codepet.wordle.MainGame;

public class Button {
  private final String text;
  private final float x;
  private final float y;
  private final BitmapFont font;
  private final Color textColor;
  private final float padding;
  private Rectangle bounds;
  private final boolean isCircular;

  private final Texture circularButtonTexture;
  private final Texture rectangularButtonTexture;

  private final MainGame game;

  // Constructor with all parameters
  public Button(MainGame game, String text, float x, float y, BitmapFont font, Color textColor, Color backgroundColor,
      float padding,
      boolean isCircular) {
    this.game = game;
    this.text = text;
    this.x = x;
    this.y = y;
    this.font = font;
    this.textColor = textColor;
    this.padding = padding;
    this.isCircular = isCircular;
    circularButtonTexture = new Texture("images/button_round_gradient.png");
    rectangularButtonTexture = new Texture("images/button_rectangle_flat.png");
    calculateBounds();
  }

  // Overloaded constructor with default values
  public Button(MainGame game, String text, float x, float y, BitmapFont font, boolean isCircular) {
    this(game, text, x, y, font, Color.WHITE, Color.DARK_GRAY, 10, isCircular);
  }

  private void calculateBounds() {
    GlyphLayout layout = new GlyphLayout(font, text);
    float rectWidth = layout.width + padding * 2;
    float rectHeight = layout.height + padding * 2;
    if (isCircular) {
      float diameter = Math.max(rectWidth, rectHeight);
      bounds = new Rectangle(x - diameter / 2, y - diameter / 2, diameter, diameter);
    } else {
      bounds = new Rectangle(x - rectWidth / 2, y - layout.height - padding, rectWidth, rectHeight);
    }
  }

  public void render(SpriteBatch batch) {
    // Draw the button background
    if (isCircular) {
      batch.draw(circularButtonTexture, bounds.x, bounds.y, bounds.width, bounds.height);
    } else {
      batch.draw(rectangularButtonTexture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    // Draw the button text
    font.setColor(textColor);
    GlyphLayout layout = new GlyphLayout(font, text);
    if (isCircular) {
      font.draw(batch, layout, x - layout.width / 2, y + layout.height / 2);
    } else {
      font.draw(batch, layout, x - layout.width / 2, y + layout.height / 2 - padding);
    }
  }

  public Rectangle getBounds() {
    return bounds;
  }
}