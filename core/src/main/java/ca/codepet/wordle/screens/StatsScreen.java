package ca.codepet.wordle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import ca.codepet.wordle.MainGame;

/**
 * The screen that displays the player's game statistics.
 */
public class StatsScreen extends BaseScreen {

    public StatsScreen(MainGame game, Screen previousScreen) {
        super(game, previousScreen, "images/blank.png", 1);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        // If the player has not played any games, display a message
        if (game.userDataManager.gamesPlayed() == 0) {
            String message = "No games played";
            GlyphLayout layout = new GlyphLayout(game.font, message);
            float x = (Gdx.graphics.getWidth() - layout.width) / 2;
            float y = (Gdx.graphics.getHeight() + layout.height) / 2;

            game.batch.begin();
            game.font.draw(game.batch, layout, x, y);
            game.batch.end();
            return;
        }

        // Render player stats
        game.batch.begin();
        int y = 100;
        int yDiff = 50;
        game.font.draw(game.batch, "Win distribution", 50, y + yDiff * 11);
        game.font.draw(game.batch, " 1: " + game.userDataManager.getGamesWonByAttempts(1), 50, y + yDiff * 10);
        game.font.draw(game.batch, " 2: " + game.userDataManager.getGamesWonByAttempts(2), 50, y + yDiff * 9);
        game.font.draw(game.batch, " 3: " + game.userDataManager.getGamesWonByAttempts(3), 50, y + yDiff * 8);
        game.font.draw(game.batch, " 4: " + game.userDataManager.getGamesWonByAttempts(4), 50, y + yDiff * 7);
        game.font.draw(game.batch, " 5: " + game.userDataManager.getGamesWonByAttempts(5), 50, y + yDiff * 6);
        game.font.draw(game.batch, " 6: " + game.userDataManager.getGamesWonByAttempts(6), 50, y + yDiff * 5);
        game.font.draw(game.batch, "Games Played: " + game.userDataManager.gamesPlayed(), 50, y + yDiff * 4);
        game.font.draw(game.batch, "Games Won: " + game.userDataManager.getGamesWon(), 50, y + yDiff * 3);
        game.font.draw(game.batch, "Games Lost: " + game.userDataManager.getGamesLost(), 50, y + yDiff * 2);
        game.font.draw(game.batch,
                "Win Rate: " + String.format("%.1f", game.userDataManager.getWinRate()) + "%", 50,
                y + yDiff);

        game.batch.end();
    }
}