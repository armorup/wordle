package ca.codepet.wordle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import ca.codepet.wordle.screens.GameScreen;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class MainGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public BitmapFont faRegularFont;
    public BitmapFont faSolidFont;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = generateFont("fonts/Play-Regular.ttf", 36);
        faRegularFont = generateFont("fonts/FA-Regular-400.otf", 36);
        faSolidFont = generateFont("fonts/FA-Solid-900.otf", 36);

        // Start the game with the GameScreen
        setScreen(new GameScreen(this));
    }

    /**
     * Generates a BitmapFont from the specified font file and font size.
     *
     * @param fontFilePath the path to the font file
     * @param fontSize     the font size
     * @return the generated BitmapFont
     */
    private BitmapFont generateFont(String fontFilePath, int fontSize) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontFilePath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;
        BitmapFont generatedFont = generator.generateFont(parameter);
        generator.dispose();
        return generatedFont;
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        faRegularFont.dispose();
        faSolidFont.dispose();
    }
}