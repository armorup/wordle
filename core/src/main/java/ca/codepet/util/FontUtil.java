package ca.codepet.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontUtil {
  /**
   * Generates a BitmapFont from the specified font file and font size.
   *
   * @param fontFilePath the path to the font file
   * @param fontSize     the font size
   * @return the generated BitmapFont
   */
  public static BitmapFont generateFont(String fontFilePath, int fontSize) {
    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontFilePath));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    parameter.size = fontSize;
    BitmapFont generatedFont = generator.generateFont(parameter);
    generator.dispose();
    return generatedFont;
  }
}
