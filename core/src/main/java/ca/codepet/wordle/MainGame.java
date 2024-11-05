package ca.codepet.wordle;

import java.util.Random;

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

    private final int SEED = 12345;
    public Random random;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = FontUtil.generateFont("fonts/Play-Regular.ttf", 36);
        faRegularFont = FontUtil.generateFont("fonts/FA-Regular-400.otf", 36);
        faSolidFont = FontUtil.generateFont("fonts/FA-Solid-900.otf", 36);
        assetManager = new AssetManager();
        userDataManager = new UserDataManager();
        random = new Random(SEED);

        // Reset user data
        userDataManager.reset();

        // Load sounds into asset manager
        assetManager.load("audio/click-a.ogg", com.badlogic.gdx.audio.Sound.class);
        assetManager.load("audio/click-b.ogg", com.badlogic.gdx.audio.Sound.class);
        assetManager.load("audio/music.mp3", com.badlogic.gdx.audio.Sound.class);
        assetManager.load("images/button_round_gradient.png", com.badlogic.gdx.graphics.Texture.class);

        // Load challenge images
        loadImages();

        GameScreen gameScreen = new GameScreen(this);
        setScreen(new SplashScreen(this, gameScreen));
    }

    private void loadImages() {
        assetManager.load("images/challenges/beach.png", com.badlogic.gdx.graphics.Texture.class);
        assetManager.load("images/challenges/blaze.png", com.badlogic.gdx.graphics.Texture.class);
        assetManager.load("images/challenges/cliff.png", com.badlogic.gdx.graphics.Texture.class);
        assetManager.load("images/challenges/cloud.png", com.badlogic.gdx.graphics.Texture.class);
        assetManager.load("images/challenges/frost.png", com.badlogic.gdx.graphics.Texture.class);
        assetManager.load("images/challenges/onion.png", com.badlogic.gdx.graphics.Texture.class);
        assetManager.load("images/challenges/petal.png", com.badlogic.gdx.graphics.Texture.class);
        assetManager.load("images/challenges/river.png", com.badlogic.gdx.graphics.Texture.class);
        assetManager.load("images/challenges/shark.png", com.badlogic.gdx.graphics.Texture.class);
        assetManager.load("images/challenges/stone.png", com.badlogic.gdx.graphics.Texture.class);
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