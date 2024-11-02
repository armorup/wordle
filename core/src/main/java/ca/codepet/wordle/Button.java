package ca.codepet.wordle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Button {
  private final String text;
  private final float x;
  private final float y;
  private final BitmapFont font;
  private final Color textColor;
  private final Color backgroundColor;
  private final float padding;
  private Rectangle bounds;

  // Constructor with default values
  public Button(String text, float x, float y, BitmapFont font) {
    this(text, x, y, font, Color.WHITE, Color.DARK_GRAY, 10);
  }

  public Button(String text, float x, float y, BitmapFont font, Color textColor, Color backgroundColor, float padding) {
    this.text = text;
    this.x = x;
    this.y = y;
    this.font = font;
    this.textColor = textColor;
    this.backgroundColor = backgroundColor;
    this.padding = padding;
    calculateBounds();
  }

  private void calculateBounds() {
    GlyphLayout layout = new GlyphLayout(font, text);
    float rectWidth = layout.width + padding * 2;
    float rectHeight = layout.height + padding * 2;
    bounds = new Rectangle(x - padding, y - layout.height - padding, rectWidth, rectHeight);
  }

  public void render(ShapeRenderer shapeRenderer, SpriteBatch batch) {
    // Draw the button background
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(backgroundColor);
    shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    shapeRenderer.end();

    // Draw the button text
    batch.begin();
    font.setColor(textColor);
    GlyphLayout layout = new GlyphLayout(font, text);
    font.draw(batch, layout, x - layout.width / 2, y + layout.height / 2);
    batch.end();
  }

  public Rectangle getBounds() {
    return bounds;
  }
}