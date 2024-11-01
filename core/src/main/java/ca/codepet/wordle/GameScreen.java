package ca.codepet.wordle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

/**
 * The main game screen where the Wordle game is played.
 */
public class GameScreen implements Screen {

    // Color definitions
    static final Color CORRECT_COLOR = Color.GREEN; // Correct letter and position
    static final Color WRONG_POSITION_COLOR = Color.YELLOW; // Correct letter, wrong position
    static final Color INCORRECT_COLOR = Color.GRAY; // Incorrect letter

    private final MainGame game; // Reference to the main game object
    private final Wordle wordle; // The Wordle game object
    private final InputHandler inputHandler; // Input handler for the game

    public GameScreen(MainGame game) {
        this.game = game;
        this.wordle = new Wordle();
        this.inputHandler = new InputHandler(wordle);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputHandler);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen

        // Render the wordle
        wordle.render(delta, game.batch, game.font);

    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
    }
}
