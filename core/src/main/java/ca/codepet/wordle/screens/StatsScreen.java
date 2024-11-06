package ca.codepet.wordle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import ca.codepet.util.FAIcons;
import ca.codepet.wordle.MainGame;
import ca.codepet.wordle.helpers.UserDataManager;

/**
 * The screen that displays the player's game statistics.
 */
public class StatsScreen extends BaseScreen {

    private int BASE_X = 50;
    private int X_DIFF = 50;
    private int BASE_Y = 100;
    private int Y_DIFF = 50;

    private UserDataManager userDataManager;

    public StatsScreen(MainGame game, Screen previousScreen) {
        super(game, previousScreen, "images/blank.png", 1);
        this.userDataManager = game.userDataManager;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        game.batch.begin();

        game.font.draw(game.batch, "x", Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 50);

        // If the player has not played any games, display a message
        if (game.userDataManager.gamesPlayed() == 0) {
            String message = "No games played";
            GlyphLayout layout = new GlyphLayout(game.font, message);
            float x = (Gdx.graphics.getWidth() - layout.width) / 2;
            float y = (Gdx.graphics.getHeight() + layout.height) / 2;
            game.font.draw(game.batch, layout, x, y);
            game.batch.end();
            return;
        }

        // Render player stats

        game.font.draw(game.batch, "Win distribution", BASE_X, BASE_Y + Y_DIFF * 11);
        game.font.draw(game.batch, " 1: " + game.userDataManager.getGamesWonByAttempts(1), BASE_X,
                BASE_Y + Y_DIFF * 10);
        game.font.draw(game.batch, " 2: " + game.userDataManager.getGamesWonByAttempts(2), BASE_X, BASE_Y + Y_DIFF * 9);
        game.font.draw(game.batch, " 3: " + game.userDataManager.getGamesWonByAttempts(3), BASE_X, BASE_Y + Y_DIFF * 8);
        game.font.draw(game.batch, " 4: " + game.userDataManager.getGamesWonByAttempts(4), BASE_X, BASE_Y + Y_DIFF * 7);
        game.font.draw(game.batch, " 5: " + game.userDataManager.getGamesWonByAttempts(5), BASE_X, BASE_Y + Y_DIFF * 6);
        game.font.draw(game.batch, " 6: " + game.userDataManager.getGamesWonByAttempts(6), BASE_X, BASE_Y + Y_DIFF * 5);
        game.font.draw(game.batch, "Games Played: " + game.userDataManager.gamesPlayed(), BASE_X, BASE_Y + Y_DIFF * 4);
        game.font.draw(game.batch, "Games Won: " + game.userDataManager.getGamesWon(), BASE_X, BASE_Y + Y_DIFF * 3);
        game.font.draw(game.batch, "Games Lost: " + game.userDataManager.getGamesLost(), BASE_X, BASE_Y + Y_DIFF * 2);
        game.font.draw(game.batch,
                "Win Rate: " + String.format("%.1f", game.userDataManager.getWinRate()) + "%", BASE_X,
                BASE_Y + Y_DIFF);
        renderStars();

        game.batch.end();
    }

    public void renderStars() {
        // Draw total stars dimmed
        int maxStars = userDataManager.getMaxStars();
        game.faRegularFont.setColor(0.5f, 0.5f, 0.5f, 0.5f);
        for (int i = 0; i < maxStars; i++) {
            game.faRegularFont.draw(game.batch, FAIcons.STAR.code(), BASE_X + i * X_DIFF, BASE_Y - 5);
        }
        game.faSolidFont.setColor(1, 1, 0, 1f);

        // Draw earned stars
        int starsEarned = userDataManager.getStarsEarned();
        for (int i = 0; i < starsEarned; i++) {
            game.faSolidFont.draw(game.batch, FAIcons.STAR.code(), BASE_X + i * X_DIFF, BASE_Y);
        }
    }

}