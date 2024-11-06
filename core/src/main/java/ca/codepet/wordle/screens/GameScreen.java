package ca.codepet.wordle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import ca.codepet.util.FAIcons;
import ca.codepet.wordle.MainGame;
import ca.codepet.wordle.Wordle;
import ca.codepet.wordle.helpers.InputHandler;
import ca.codepet.wordle.ui.Button;

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
  private final Button playAgainButton;
  private final Button statsButton;
  private final Button helpButton;
  private Texture backgroundTexture;

  public GameScreen(MainGame game) {
    super(game);
    this.wordle = new Wordle(game);
    this.inputHandler = new InputHandler(wordle, game);

    // Set the background texture from wordle challenges
    updateChallengeImage();

    // Initialize buttons
    float y = Gdx.graphics.getHeight() * 0.32f;
    playAgainButton = new Button(game, "Play Again", Gdx.graphics.getWidth() / 2, y - 50, game.font, Color.WHITE,
        Color.SKY, 10, false);
    statsButton = new Button(game, FAIcons.CHART.code(), Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 120,
        game.faSolidFont,
        true);
    helpButton = new Button(game, FAIcons.HELP.code(), Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 50,
        game.faSolidFont, true);
    // imageRevealButton = new Button(game, "!", Gdx.graphics.getWidth() - 50,
    // Gdx.graphics.getHeight() - 180,
    // game.faSolidFont, true);

  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(inputHandler);
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen

    game.batch.begin();

    // Render the background
    renderBackground();

    // Render the wordle grid
    wordle.render(delta, game.batch, game.font);

    // Render a game message
    float y = Gdx.graphics.getHeight() * 0.32f;
    renderMessage(wordle.getMessage(), y);

    // Render help button
    helpButton.render(game.batch);
    inputHandler.setHelpButtonBounds(helpButton.getBounds());

    // Render stats button
    statsButton.render(game.batch);
    inputHandler.setStatsButtonBounds(statsButton.getBounds());

    // // Render imageReveal button
    // imageRevealButton.render(game.batch);
    // inputHandler.setImageRevealButtonBounds(imageRevealButton.getBounds());

    if (wordle.isGameOver()) {
      // Render play again button if the game is over
      playAgainButton.render(game.batch);
      inputHandler.setPlayAgainButtonBounds(playAgainButton.getBounds());
      updateChallengeImage();
    }
    game.batch.end();
  }

  private void renderMessage(String message, float y) {
    GlyphLayout layout = new GlyphLayout(game.font, message);
    float xMessage = (Gdx.graphics.getWidth() - layout.width) / 2;
    game.font.setColor(Color.WHITE);
    game.font.draw(game.batch, layout, xMessage, y);
  }

  private void updateChallengeImage() {
    String path = wordle.getChallengeTexturePath();
    if (path == null) {
      return;
    }
    backgroundTexture = game.assetManager.get(path);
  }

  private void renderBackground() {
    if (backgroundTexture != null) {
      game.batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
  }

}
