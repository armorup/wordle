package ca.codepet.wordle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

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
    private final ShapeRenderer shapeRenderer; // ShapeRenderer for drawing shapes

    public GameScreen(MainGame game) {
        this.game = game;
        this.wordle = new Wordle();
        this.inputHandler = new InputHandler(wordle, game);
        this.shapeRenderer = new ShapeRenderer();
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

        game.batch.begin();
        // Render a game message
        float y = Gdx.graphics.getHeight() * 0.32f;
        renderMessage(wordle.getMessage(), y);

        // Render help button
        float xHelp = Gdx.graphics.getWidth() - 50;
        float yHelp = Gdx.graphics.getHeight() - 50;
        helpButton(xHelp, yHelp);

        // Render game over and stats buttons
        if (wordle.isGameOver()) {
            // Render the play again button
            playAgainButton(y);
            // Render the stats button
            statsButton(y);

        }

    }

    private void renderMessage(String message, float y) {
        GlyphLayout layout = new GlyphLayout(game.font, message);
        float xMessage = (Gdx.graphics.getWidth() - layout.width) / 2;
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, layout, xMessage, y);
        game.batch.end();
    }

    private void playAgainButton(float y) {
        String playAgainText = "Play Again";
        GlyphLayout playAgainLayout = new GlyphLayout(game.font, playAgainText);
        float xPlayAgain = (Gdx.graphics.getWidth() - playAgainLayout.width) / 2;
        float yPlayAgain = y - 50;
        float padding = 10;
        float rectWidth = playAgainLayout.width + padding * 2;
        float rectHeight = playAgainLayout.height + padding * 2;
        Rectangle playAgainButtonBounds = new Rectangle(xPlayAgain - padding,
                yPlayAgain - playAgainLayout.height - padding, rectWidth, rectHeight);
        inputHandler.setPlayAgainButtonBounds(playAgainButtonBounds);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(xPlayAgain - padding, yPlayAgain - playAgainLayout.height - padding, rectWidth,
                rectHeight);
        shapeRenderer.end();

        // Render the play again button text
        game.batch.begin();
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, playAgainLayout, xPlayAgain, yPlayAgain);
        game.batch.end();
    }

    private void statsButton(float y) {
        String statsText = "Your Stats";
        GlyphLayout statsLayout = new GlyphLayout(game.font, statsText);
        float xStats = (Gdx.graphics.getWidth() - statsLayout.width) / 2;
        float yStats = y - 100;
        float padding = 10;
        float rectWidth = statsLayout.width + padding * 2;
        float rectHeight = statsLayout.height + padding * 2;
        Rectangle statsButtonBounds = new Rectangle(xStats - padding, yStats - statsLayout.height - padding, rectWidth,
                rectHeight);
        inputHandler.setStatsButtonBounds(statsButtonBounds);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(xStats - padding, yStats - statsLayout.height - padding, rectWidth, rectHeight);
        shapeRenderer.end();

        // Render the stats button text
        game.batch.begin();
        game.font.setColor(Color.WHITE);
        game.font.draw(game.batch, statsLayout, xStats, yStats);
        game.batch.end();
    }

    private void helpButton(float x, float y) {
        String text = "?"; // TODO: Use FontAwesome question mark icon
        GlyphLayout layout = new GlyphLayout(game.faSolidFont, text);
        float padding = 10;
        float radius = Math.max(layout.width, layout.height) / 2 + padding;
        float centerX = x;
        float centerY = y;

        // Set the bounds for touch detection
        Rectangle helpButtonBounds = new Rectangle(centerX - radius, centerY - radius, radius * 2, radius * 2);
        inputHandler.setHelpButtonBounds(helpButtonBounds);

        // Render the circular background
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.circle(centerX, centerY, radius);
        shapeRenderer.end();

        // Render the help button text
        game.batch.begin();
        game.faSolidFont.setColor(Color.WHITE);
        game.faSolidFont.draw(game.batch, layout, x - layout.width / 2, y + layout.height / 2);
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
