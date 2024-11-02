package ca.codepet.wordle.helpers;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * The statistics class for the Wordle game.
 */
public class PlayerStats {

  // The number of games guessed correctly in that many tries
  // 1 - 6 tries. Index 0 represents the number of games the player
  // did not guess the word in 6 tries.
  private final int[] stats = new int[7];

  // Save preferences
  private Preferences prefs;

  public PlayerStats() {
    prefs = Gdx.app.getPreferences("Wordle");
  }

  /**
   * Saves the player's statistics
   */
  public void save() {

  }

  /**
   * Increments the number of games won in the given number of attempts.
   *
   * @param attempts The number of attempts it took to guess the word. 0 if
   *                 the player did not guess the word. -1 if void.
   */
  public void guessedIn(int attempts) {
    if (attempts < 0 || attempts > 6) {
      return;
    }
    stats[attempts]++;
  }

  /**
   * Gets the number of games played.
   *
   * @return The number of games played.
   */
  public int gamesPlayed() {
    return Arrays.stream(stats).sum();
  }

  /**
   * Gets the number of games won.
   *
   * @return The number of games won.
   */
  public int getGamesWonByAttempts(int attempts) {
    return stats[attempts];
  }

  /**
   * Gets the number of games won.
   *
   * @return The number of games won.
   */
  public int getGamesWon() {
    return Arrays.stream(stats).sum() - stats[0];
  }

  /**
   * Gets the number of games lost.
   *
   * @return The number of games lost.
   */
  public int getGamesLost() {
    return stats[0];
  }

  /**
   * Gets the win rate of the player.
   *
   * @return The win rate as a percentage.
   */
  public double getWinRate() {
    int gamesPlayed = gamesPlayed();
    if (gamesPlayed == 0) {
      return 0;
    }
    return (double) getGamesWon() / gamesPlayed * 100;
  }
}
