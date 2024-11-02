package ca.codepet.wordle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ca.codepet.wordle.MainGame;
import ca.codepet.wordle.Wordle;
import ca.codepet.wordle.helpers.InputHandler;
import ca.codepet.wordle.ui.Button;

/**
 * The main game screen where the Wordle game is played.
 */
public class GameScreen implements Screen {

    // Color definitions
    public static final Color CORRECT_COLOR = Color.GREEN; // Correct letter and position
    public static final Color WRONG_POSITION_COLOR = Color.YELLOW; // Correct letter, wrong position
    public static final Color INCORRECT_COLOR = Color.GRAY; // Incorrect letter

    private final MainGame game; // Reference to the main game object
    private final Wordle wordle; // The Wordle game object
    private final InputHandler inputHandler; // Input handler for the game
    private final ShapeRenderer shapeRenderer; // ShapeRenderer for drawing shapes
    private final Button playAgainButton;
    private final Button statsButton;
    private final Button helpButton;

    public GameScreen(MainGame game) {
        this.game = game;
        this.wordle = new Wordle(game);
        this.inputHandler = new InputHandler(wordle, game);
        this.shapeRenderer = new ShapeRenderer();

        // Initialize buttons
        float y = Gdx.graphics.getHeight() * 0.32f;
        playAgainButton = new Button("Play Again", Gdx.graphics.getWidth() / 2, y - 50, game.font, false);
        statsButton = new Button("Your Stats", Gdx.graphics.getWidth() / 2, y - 100, game.font, false);
        helpButton = new Button("?", Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 50,
                game.faSolidFont, true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(inputHandler);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen

        // Render the wordle grid
        wordle.render(delta, game.batch, game.font);

        // Render a game message

        float y = Gdx.graphics.getHeight() * 0.32f;
        renderMessage(wordle.getMessage(), y);

        // Render help button
        helpButton.render(shapeRenderer, game.batch);
        // if (Gdx.input.justTouched()) {
        inputHandler.setHelpButtonBounds(helpButton.getBounds());
        // }

        // Render game over and stats buttons
        if (wordle.isGameOver()) {
            playAgainButton.render(shapeRenderer, game.batch);
            statsButton.render(shapeRenderer, game.batch);
            // Check for touch input on the buttons
            // if (Gdx.input.justTouched()) {
            inputHandler.setPlayAgainButtonBounds(playAgainButton.getBounds());
            inputHandler.setStatsButtonBounds(statsButton.getBounds());
            // }
        }

    }

    private void renderMessage(String message, float y) {
        game.batch.begin();
        GlyphLayout layout = new GlyphLayout(game.font, message);
        float xMessage = (Gdx.graphics.getWidth() - layout.width) / 2;
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, layout, xMessage, y);
        game.batch.end();
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
