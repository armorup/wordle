package ca.codepet.wordle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ca.codepet.util.FontUtil;
import ca.codepet.wordle.helpers.PlayerStats;
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
    public AssetManager assets;
    public PlayerStats playerStats;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = FontUtil.generateFont("fonts/Play-Regular.ttf", 36);
        faRegularFont = FontUtil.generateFont("fonts/FA-Regular-400.otf", 36);
        faSolidFont = FontUtil.generateFont("fonts/FA-Solid-900.otf", 36);
        assets = new AssetManager();
        playerStats = new PlayerStats();

        GameScreen gameScreen = new GameScreen(this);
        SplashScreen splashScreen = new SplashScreen(this, gameScreen);
        setScreen(splashScreen);

        // setScreen(new GameScreen(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        assets.dispose();
        faRegularFont.dispose();
        faSolidFont.dispose();
    }
}