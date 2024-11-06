package ca.codepet.wordle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ca.codepet.util.FontUtil;
import ca.codepet.wordle.helpers.UserDataManager;
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

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = FontUtil.generateFont("fonts/Play-Regular.ttf", 36);
        faRegularFont = FontUtil.generateFontAwesome("fonts/FA-Regular-400.otf", 30);
        faSolidFont = FontUtil.generateFontAwesome("fonts/FA-Solid-900.otf", 36);
        assetManager = new AssetManager();
        userDataManager = new UserDataManager();

        // Reset user data
        userDataManager.reset();

        // Load sounds and images
        loadAudio();
        loadImages();

        // Load the splash screen
        setScreen(new SplashScreen(this, null));
    }

    private void loadAudio() {
        assetManager.load("audio/click-a.ogg", com.badlogic.gdx.audio.Sound.class);
        assetManager.load("audio/click-b.ogg", com.badlogic.gdx.audio.Sound.class);
        assetManager.load("audio/music.mp3", com.badlogic.gdx.audio.Sound.class);
    }

    private void loadImages() {
        // Button images
        assetManager.load("images/button_round_gradient.png", Texture.class);
        assetManager.load("images/button_square_flat.png", Texture.class);
        assetManager.load("images/button_rectangle_flat.png", Texture.class);

        // Challenge images
        assetManager.load("images/challenges/beach.png", Texture.class);
        assetManager.load("images/challenges/blaze.png", Texture.class);
        assetManager.load("images/challenges/cliff.png", Texture.class);
        assetManager.load("images/challenges/cloud.png", Texture.class);
        assetManager.load("images/challenges/frost.png", Texture.class);
        assetManager.load("images/challenges/onion.png", Texture.class);
        assetManager.load("images/challenges/petal.png", Texture.class);
        assetManager.load("images/challenges/river.png", Texture.class);
        assetManager.load("images/challenges/shark.png", Texture.class);
        assetManager.load("images/challenges/stone.png", Texture.class);
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