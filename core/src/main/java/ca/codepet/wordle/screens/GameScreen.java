package ca.codepet.wordle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ca.codepet.wordle.MainGame;
import ca.codepet.wordle.Wordle;
import ca.codepet.wordle.helpers.InputHandler;
import ca.codepet.wordle.ui.Button;
import ca.codepet.wordle.ui.KeyCap;
import ca.codepet.wordle.ui.Keyboard;

/**
 * The main game screen where the Wordle game is played.
 */
public class GameScreen extends BaseScreen {

  // Color definitions
  public static final Color CORRECT_COLOR = Color.GREEN; // Correct letter and position
  public static final Color WRONG_POSITION_COLOR = Color.YELLOW; // Correct letter, wrong position
  public static final Color INCORRECT_COLOR = Color.GRAY; // Incorrect letter

  private final Wordle wordle; // The Wordle game object
  private final InputHandler inputHandler; // Input handler for the game
  private final ShapeRenderer shapeRenderer; // ShapeRenderer for drawing shapes
  private final Button playAgainButton;
  private final Button statsButton;
  private final Button helpButton;
  private final Keyboard keyboard;

  public GameScreen(MainGame game) {
    super(game);
    this.wordle = new Wordle(game);
    this.keyboard = new Keyboard();
    this.inputHandler = new InputHandler(wordle, game);
    this.shapeRenderer = new ShapeRenderer();

    // Initialize buttons
    float y = Gdx.graphics.getHeight() * 0.32f;
    playAgainButton = new Button("Play Again", Gdx.graphics.getWidth() / 2, y - 50, game.font, Color.WHITE,
        Color.SKY, 10, false);
    statsButton = new Button("S", Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 120, game.faSolidFont,
        true);
    helpButton = new Button("?", Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 50,
        game.faSolidFont, true);

  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(inputHandler);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // Render the wordle grid
    wordle.render(delta, game.batch, game.font);

    // Render the keyboard
    game.batch.begin();
    // keyboard.render(game.batch);
    KeyCap keyCap = new KeyCap("A", 100, 100);
    keyCap.render(game.batch);
    game.batch.end();

    // Render a game message
    float y = Gdx.graphics.getHeight() * 0.32f;
    renderMessage(wordle.getMessage(), y);

    // Render help button
    helpButton.render(shapeRenderer, game.batch);
    inputHandler.setHelpButtonBounds(helpButton.getBounds());

    // Render stats button
    statsButton.render(shapeRenderer, game.batch);
    inputHandler.setStatsButtonBounds(statsButton.getBounds());

    // Render play again button if the game is over
    if (wordle.isGameOver()) {
      playAgainButton.render(shapeRenderer, game.batch);
      inputHandler.setPlayAgainButtonBounds(playAgainButton.getBounds());
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

}
