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
 * The main game
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
<<<<<<< HEAD
        faRegularFont = FontUtil.generateFontAwesome("fonts/FA-Regular-400.otf", 36);
        faSolidFont = FontUtil.generateFontAwesome("fonts/FA-Solid-900.otf", 36);
        assetManager = new AssetManager();
        userDataManager = new UserDataManager();

        GameScreen gameScreen = new GameScreen(this);
        SplashScreen splashScreen = new SplashScreen(this, gameScreen);
        setScreen(splashScreen);
=======
        faRegularFont = FontUtil.generateFont("fonts/FA-Regular-400.otf", 36);
        faSolidFont = FontUtil.generateFont("fonts/FA-Solid-900.otf", 36);
        assetManager = new AssetManager();
        userDataManager = new UserDataManager();

        // Load sounds into asset manager
        assetManager.load("audio/click-a.ogg", com.badlogic.gdx.audio.Sound.class);
        assetManager.load("audio/click-b.ogg", com.badlogic.gdx.audio.Sound.class);
        assetManager.load("audio/music.mp3", com.badlogic.gdx.audio.Sound.class);

        assetManager.load("images/button_round_gradient.png", com.badlogic.gdx.graphics.Texture.class);

        GameScreen gameScreen = new GameScreen(this);
        setScreen(new SplashScreen(this, gameScreen));
>>>>>>> origin/main
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