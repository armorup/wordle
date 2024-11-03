package ca.codepet.wordle.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import ca.codepet.util.Draw;

public class Button {
  private final String text;
  private final float x;
  private final float y;
  private final BitmapFont font;
  private final Color textColor;
  private final Color backgroundColor;
  private final float padding;
  private Rectangle bounds;
  private final boolean isCircular;

  // Constructor with all parameters
  public Button(String text, float x, float y, BitmapFont font, Color textColor, Color backgroundColor, float padding,
      boolean isCircular) {
    this.text = text;
    this.x = x;
    this.y = y;
    this.font = font;
    this.textColor = textColor;
    this.backgroundColor = backgroundColor;
    this.padding = padding;
    this.isCircular = isCircular;
    calculateBounds();
  }

  // Overloaded constructor with default values
  public Button(String text, float x, float y, BitmapFont font, boolean isCircular) {
    this(text, x, y, font, Color.WHITE, Color.DARK_GRAY, 10, isCircular);
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

  public void render(ShapeRenderer shapeRenderer, SpriteBatch batch) {
    // Draw the button background
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(backgroundColor);
    if (isCircular) {
      shapeRenderer.circle(x, y, bounds.width / 2);
    } else {
      Draw.roundedRect(shapeRenderer, bounds.x, bounds.y, bounds.width, bounds.height, 10);
    }
    shapeRenderer.end();

    // Draw the button text
    batch.begin();
    font.setColor(textColor);
    GlyphLayout layout = new GlyphLayout(font, text);
    if (isCircular) {
      font.draw(batch, layout, x - layout.width / 2, y + layout.height / 2);
    } else {
      font.draw(batch, layout, x - layout.width / 2, y + layout.height / 2 - padding);
    }
    batch.end();
  }

  public Rectangle getBounds() {
    return bounds;
  }
}