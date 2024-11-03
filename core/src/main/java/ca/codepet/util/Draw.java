package ca.codepet.util;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public final class Draw {
  public static void roundedRect(ShapeRenderer shapeRenderer, float x, float y, float width, float height,
      float radius) {
    // Draw the central rectangle
    shapeRenderer.rect(x + radius, y, width - 2 * radius, height);
    shapeRenderer.rect(x, y + radius, width, height - 2 * radius);

    // Draw the four corner circles
    shapeRenderer.circle(x + radius, y + radius, radius);
    shapeRenderer.circle(x + width - radius, y + radius, radius);
    shapeRenderer.circle(x + width - radius, y + height - radius, radius);
    shapeRenderer.circle(x + radius, y + height - radius, radius);
  }
}
