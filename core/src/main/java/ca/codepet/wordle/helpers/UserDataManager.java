package ca.codepet.wordle.helpers;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;

/**
 * The statistics class for the Wordle game.
 */
public final class UserDataManager {

  // The number of games guessed correctly in that many tries
  // 1 - 6 tries. Index 0 represents the number of games the player
  // did not guess the word in 6 tries.
  private UserData userData = new UserData();

  // Save preferences
  private final Preferences prefs;
  private final Json json;

  public UserDataManager() {
    prefs = Gdx.app.getPreferences("Wordle");
    json = new Json();
    load();
  }

  /**
   * Saves the player's statistics
   */
  public void save() {
    String jsonData = json.toJson(userData);
    prefs.putString("playerData", jsonData);
    prefs.flush();
  }

  /**
   * Loads the player's statistics
   */
  public void load() {
    String jsonData = prefs.getString("playerData", null);
    if (jsonData != null) {
      userData = json.fromJson(UserData.class, jsonData);
    }
  }

  /**
   * Resets the player's statistics
   */
  public void reset() {
    prefs.clear();
    userData = new UserData();
    save();
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
    userData.stats[attempts]++;
  }

  /**
   * Gets the number of games played.
   *
   * @return The number of games played.
   */
  public int gamesPlayed() {
    return Arrays.stream(userData.stats).sum();
  }

  /**
   * Gets the number of games won.
   *
   * @return The number of games won.
   */
  public int getGamesWonByAttempts(int attempts) {
    return userData.stats[attempts];
  }

  /**
   * Gets the number of games won.
   *
   * @return The number of games won.
   */
  public int getGamesWon() {
    return Arrays.stream(userData.stats).sum() - userData.stats[0];
  }

  /**
   * Gets the number of games lost.
   *
   * @return The number of games lost.
   */
  public int getGamesLost() {
    return userData.stats[0];
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

  /**
   * Get current achievement level
   */
  public int getAchievementLevel() {
    return getGamesWon();
  }

  public int getAchievementOpacity() {
    return getGamesWon() * 3;
  }

}
