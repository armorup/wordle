package ca.codepet.wordle.helpers;

/**
 * The statistics class for the Wordle game.
 */
public class PlayerStats {

  // The number of games guessed correctly in that many tries
  // 1 - 6 tries. Index 0 represents the number of games the player
  // did not guess the word in 6 tries.
  private final int[] stats = new int[7];

  /**
   * Increments the number of games lost.
   */
  public void incrementGamesLost() {
    stats[0]++;
  }

  /**
   * Gets the number of games played.
   *
   * @return The number of games played.
   */
  public int gamesPlayed() {
    int total = 0;
    for (int i = 0; i < stats.length; i++) {
      total += stats[i];
    }
    return total;
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
    int gamesLost = getGamesLost();
    int gamesWon = gamesPlayed - gamesLost;
    return (double) gamesWon / gamesPlayed * 100;
  }
}
