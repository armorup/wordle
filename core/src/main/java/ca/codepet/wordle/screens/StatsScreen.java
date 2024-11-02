package ca.codepet.wordle.screens;

import com.badlogic.gdx.Screen;

import ca.codepet.wordle.MainGame;

public class StatsScreen extends InfoScreen {

  public StatsScreen(MainGame game, Screen previousScreen) {
    super(game, previousScreen, null);
  }

  @Override
  public void render(float delta) {
    super.render(delta);

    // Render player stats
    game.batch.begin();
    int y = 100;
    int yDiff = 50;
    game.font.draw(game.batch, "Won in", 50, y + yDiff * 11);
    game.font.draw(game.batch, " 1: " + game.playerStats.getGamesWonByAttempts(1), 50, y + yDiff * 10);
    game.font.draw(game.batch, " 2: " + game.playerStats.getGamesWonByAttempts(2), 50, y + yDiff * 9);
    game.font.draw(game.batch, " 3: " + game.playerStats.getGamesWonByAttempts(3), 50, y + yDiff * 8);
    game.font.draw(game.batch, " 4: " + game.playerStats.getGamesWonByAttempts(4), 50, y + yDiff * 7);
    game.font.draw(game.batch, " 5: " + game.playerStats.getGamesWonByAttempts(5), 50, y + yDiff * 6);
    game.font.draw(game.batch, " 6: " + game.playerStats.getGamesWonByAttempts(6), 50, y + yDiff * 5);
    game.font.draw(game.batch, "Games Played: " + game.playerStats.gamesPlayed(), 50, y + yDiff * 4);
    game.font.draw(game.batch, "Games Won: " + game.playerStats.getGamesWon(), 50, y + yDiff * 3);
    game.font.draw(game.batch, "Games Lost: " + game.playerStats.getGamesLost(), 50, y + yDiff * 2);
    game.font.draw(game.batch, "Win Rate: " + String.format("%.1f", game.playerStats.getWinRate()) + "%", 50,
        y + yDiff);

    game.batch.end();
  }
}