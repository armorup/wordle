package ca.codepet.wordle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ca.codepet.util.FontUtil;
import ca.codepet.wordle.helpers.UserDataManager;
import ca.codepet.wordle.screens.GameScreen;
import ca.codepet.wordle.screens.SplashScreen;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class MainGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public BitmapFont faRegularFont;
    public BitmapFont faSolidFont;
    public AssetManager assetManager;
    public UserDataManager userDataManager;
    public final int SEED = 12345;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = FontUtil.generateFont("fonts/Play-Regular.ttf", 36);
        faRegularFont = FontUtil.generateFontAwesome("fonts/FA-Regular-400.otf", 36);
        faSolidFont = FontUtil.generateFontAwesome("fonts/FA-Solid-900.otf", 36);
        assetManager = new AssetManager();
        userDataManager = new UserDataManager();

        GameScreen gameScreen = new GameScreen(this);
        SplashScreen splashScreen = new SplashScreen(this, gameScreen);
        setScreen(splashScreen);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        assetManager.dispose();
        faRegularFont.dispose();
        faSolidFont.dispose();
    }
}