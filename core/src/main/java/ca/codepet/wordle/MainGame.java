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
        // userDataManager.reset();

        // Load sounds and images
        loadAudio();
        loadInitialAssets();

        // Load the splash screen
        setScreen(new SplashScreen(this, null));
    }

    public void loadAudio() {
        assetManager.load("audio/click-a.ogg", com.badlogic.gdx.audio.Sound.class);
        assetManager.load("audio/click-b.ogg", com.badlogic.gdx.audio.Sound.class);
        assetManager.load("audio/music.mp3", com.badlogic.gdx.audio.Sound.class);
    }

    public void loadInitialAssets() {
        // Button images
        loadImage("images/button_round_gradient.png");
        loadImage("images/button_square_flat.png");
        loadImage("images/button_rectangle_flat.png");

        // Load the current challenge image
        int starsEarned = userDataManager.getStarsEarned();
        int index = 0;
        if (starsEarned >= 1) {
            index = starsEarned - 1;
        }
        String challengeWord = Wordle.challenges.get(index);
        loadImage("images/challenges/" + challengeWord + ".png");
    }

    public void loadAllImages() {
        // Load the remaining challenge images
        loadImage("images/challenges/beach.png");
        loadImage("images/challenges/blaze.png");
        loadImage("images/challenges/cliff.png");
        loadImage("images/challenges/cloud.png");
        loadImage("images/challenges/frost.png");
        loadImage("images/challenges/onion.png");
        loadImage("images/challenges/petal.png");
        loadImage("images/challenges/river.png");
        loadImage("images/challenges/shark.png");
        loadImage("images/challenges/stone.png");
    }

    private void loadImage(String path) {
        if (!assetManager.isLoaded(path, Texture.class)) {
            assetManager.load(path, Texture.class);
        }
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