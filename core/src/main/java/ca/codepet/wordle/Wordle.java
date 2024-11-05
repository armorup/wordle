package ca.codepet.wordle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

import ca.codepet.util.FileUtils;
import ca.codepet.wordle.helpers.WinMessages;
import ca.codepet.wordle.screens.GameScreen;

public final class Wordle {

  private static final int ROWS = 6;
  private static final int COLS = 5;

  Grid grid;

  private static final List<String> challenges = Arrays.asList(
      "beach", "blaze", "cliff", "cloud", "frost", "petal", "onion", "shark", "stone", "river");

  private String targetWord; // The target word for this game
  private final List<Feedback> pastGuesses = new ArrayList<>(); // List of past guesses and their feedback
  private static final List<String> laWords = new ArrayList<>();
  private static final Set<String> taWords = new HashSet<>();

  private String message = "";
  private String winMessage = "";

  private MainGame game;
  private boolean statsRecorded = false;

  /*
   * Load the word lists from files when the class is loaded.
   */
  static {
    FileUtils.loadWordsFromFile("wordle-la.txt", laWords);
    FileUtils.loadWordsFromFile("wordle-ta.txt", taWords);
    System.out.println(laWords.size() + " words loaded");
    System.out.println(taWords.size() + " words loaded");
  }

  /*
   * Constructor for a new Wordle game with a random target word.
   */
  public Wordle(MainGame game) {
    this.game = game;
    restart();
  }

  /**
   * Restart the game with a new target word.
   */
  public void restart() {
    pastGuesses.clear();
    message = "";
    winMessage = "";
    statsRecorded = false;
    grid = new Grid(ROWS, COLS);
    this.targetWord = nextWord();
    System.out.println("Target word: " + targetWord);
  }

  /**
   * Choose the next word
   */
  public String nextWord() {
    int gamesWon = game.userDataManager.getGamesWon();
    String target;
    if (gamesWon == 0) {
      target = challenges.get(0);
    } else if (gamesWon % 3 != 0) {
      target = chooseRandomWord();
    } else {
      target = challenges.get(gamesWon / 3);
    }
    return target.toUpperCase();
  }

  public String getChallengeTexturePath() {
    int gamesWon = game.userDataManager.getGamesWon();
    if (gamesWon == 0) {
      return null;
    }
    int index = gamesWon % 3 == 0 ? gamesWon / 3 - 1 : gamesWon / 3;
    String path = "images/challenges/" + challenges.get(index) + ".png";
    return path;
  }

  /**
   * Choose a random word from the list of words.
   * The random word cannot be one of the challenge words
   */
  private static String chooseRandomWord() {
    Random random = new Random();
    String word = "beach";
    while (challenges.contains(word)) {
      word = laWords.get(random.nextInt(laWords.size()));
    }
    return word;
  }

  /**
   * Check if a word is a valid word in the game.
   */
  private boolean isValidWord(String word) {
    boolean taValid = taWords.contains(word.toUpperCase());
    boolean laValid = laWords.contains(word.toUpperCase());
    return taValid || laValid;
  }

  /**
   * Handle backspace key press.
   */
  public void backspace() {
    clearMessage();
    if (grid.currentGuess().length() > 0) {
      grid.backspace();
    }
  }

  /**
   * Add a letter to the current guess.
   */
  public void addLetter(char letter) {
    clearMessage();
    if (grid.currentGuess().length() >= COLS) {
      return;
    }
    grid.addLetter(letter);
  }

  /**
   * Render the Wordle game grid.
   */
  public void render(float delta, SpriteBatch batch, BitmapFont font) {
    grid.render(delta, batch, font);
  }

  /**
   * Get the current guess as a string.
   */
  public String getCurrentGuess() {
    return grid.currentGuess();
  }

  /**
   * Check if the game is over.
   */
  public boolean isGameOver() {
    boolean gameIsOver = hasWon() || hasLost();
    if (gameIsOver && !statsRecorded) {
      recordStats();
    }
    return gameIsOver;
  }

  /**
   * True if the player has won the game.
   */
  public boolean hasWon() {
    boolean won = !pastGuesses.isEmpty() && pastGuesses.get(pastGuesses.size() - 1).isCorrect();
    if (won) {
      int attempts = pastGuesses.size();
      winMessage = winMessage.isBlank() ? WinMessages.getMessage(attempts) : winMessage;
      message = winMessage;
    }
    return won;
  }

  /**
   * True if the player has lost the game.
   */
  public boolean hasLost() {
    boolean lost = pastGuesses.size() >= ROWS && !hasWon();
    if (lost) {
      message = "The word was: " + targetWord;
    }
    return lost;
  }

  /**
   * Record the game stats after the game is over.
   */
  public void recordStats() {
    int attempts = pastGuesses.size();

    if (hasWon()) {
      game.userDataManager.guessedIn(attempts);
    } else if (hasLost()) {
      game.userDataManager.guessedIn(0);
    }
    game.userDataManager.save();
    statsRecorded = true;

  }

  /**
   * Get the feedback message to display to the player.
   */
  public String getMessage() {
    return message;
  }

  private void clearMessage() {
    message = "";
  }

  /**
   * Submit the current guess and check it against the target word.
   */
  public boolean submitGuess() {
    clearMessage();
    if (!grid.cursor.pastRight()) {
      return false;
    }

    // Check if already guessed
    for (Feedback pastFeedback : pastGuesses) {
      if (pastFeedback.getGuess().equals(grid.currentGuess())) {
        message = "Already guessed";
        return false;
      }
    }

    // Check if the guess is a valid word
    if (!isValidWord(grid.currentGuess())) {
      message = "Invalid Word";
      return false;
    }

    System.out.println("Submitting guess: " + grid.currentGuess());

    // Perform the logic for checking the guess against the target
    Feedback feedback = checkGuess(grid.currentGuess(), targetWord);
    pastGuesses.add(feedback);

    // Clear the current guess for the next round
    grid.cursor.nextRow();
    return true;
  }

  /**
   * Get list of past guesses.
   */
  public List<Feedback> getPastGuesses() {
    return pastGuesses;
  }

  /**
   * Inner class to represent a feedback for a guess.
   */
  public class Feedback {
    public final String guess;
    public final Color[] colors;
    private final boolean correct;

    public Feedback(String guess, Color[] feedbackColors, boolean correct) {
      this.guess = guess;
      this.colors = feedbackColors;
      this.correct = correct;
    }

    public String getGuess() {
      return guess;
    }

    public Color[] getColors() {
      return colors;
    }

    public boolean isCorrect() {
      return correct;
    }
  }

  /**
   * Check a guess against the target word and return feedback.
   */
  private Feedback checkGuess(String guess, String targetWord) {
    Color[] feedbackColors = new Color[targetWord.length()];
    boolean[] checkedTarget = new boolean[targetWord.length()];
    boolean[] checkedGuess = new boolean[guess.length()];

    // First pass: check for correct letters in the correct position (green)
    for (int i = 0; i < guess.length(); i++) {
      char guessChar = guess.charAt(i);
      char targetChar = targetWord.charAt(i);
      if (guessChar == targetChar) {
        Grid.Cell cell = grid.getCell(grid.cursor.getRow(), i);
        cell.status = Grid.CellStatus.CORRECT;
        cell.color = GameScreen.CORRECT_COLOR;
        grid.setCell(grid.cursor.getRow(), i, cell);
        feedbackColors[i] = GameScreen.CORRECT_COLOR;
        checkedTarget[i] = true;
        checkedGuess[i] = true;
      }
    }

    // Second pass: check for correct letters in the wrong position (yellow)
    for (int i = 0; i < guess.length(); i++) {
      if (!checkedGuess[i]) {
        char guessChar = guess.charAt(i);
        for (int j = 0; j < targetWord.length(); j++) {
          if (!checkedTarget[j] && guessChar == targetWord.charAt(j)) {
            Grid.Cell cell = grid.getCell(grid.cursor.getRow(), i);
            cell.status = Grid.CellStatus.WRONG_POSITION;
            cell.color = GameScreen.WRONG_POSITION_COLOR;
            grid.setCell(grid.cursor.getRow(), i, cell);
            feedbackColors[i] = GameScreen.WRONG_POSITION_COLOR;
            checkedTarget[j] = true;
            break;
          }
        }
      }
    }

    // Mark all unmarked letters as incorrect (gray)
    for (int i = 0; i < feedbackColors.length; i++) {
      if (feedbackColors[i] == null) {
        feedbackColors[i] = GameScreen.INCORRECT_COLOR;
      }
    }

    // Update the grid's cells with feedback colors
    for (int i = 0; i < COLS; i++) {
      Grid.Cell cell = grid.getCell(grid.cursor.getRow(), i);
      cell.color = feedbackColors[i];
      grid.setCell(grid.cursor.getRow(), i, cell);
    }

    // Return the guess and feedback (correct or not)
    return new Feedback(guess, feedbackColors, guess.equals(targetWord));
  }
}

/**
 * The game grid for Wordle.
 */
class Grid {

  private final int cellSize = 50;
  private final int cellPadding = 5;

  private final int rows;
  private final int cols;
  private final Cell[][] cells;
  final Cursor cursor = new Cursor();
  private final Texture backTexture;

  // Constructor for a new empty grid
  public Grid(int rows, int cols) {
    backTexture = new Texture("images/button_square_flat.png");
    this.rows = rows;
    this.cols = cols;
    this.cells = new Cell[rows][cols];
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        cells[row][col] = new Cell();
      }
    }
  }

  // Return the current guess as a string
  public String currentGuess() {
    StringBuilder sb = new StringBuilder();
    for (int col = 0; col < cols; col++) {
      char letter = cells[cursor.row][col].letter;
      if (letter == ' ')
        break;
      sb.append(letter);
    }
    return sb.toString();
  }

  enum CellStatus {
    UNCHECKED, CORRECT, WRONG_POSITION, INCORRECT
  }

  /**
   * Inner class to represent a cell in the grid.
   */
  public class Cell {
    public char letter;
    public Color color;
    public CellStatus status;

    public Cell() {
      this(' ', Color.WHITE, CellStatus.UNCHECKED);
    }

    public Cell(char letter) {
      this(letter, Color.WHITE, CellStatus.UNCHECKED);
    }

    public Cell(char letter, Color color, CellStatus status) {
      this.letter = letter;
      this.color = color;
      this.status = status;
    }
  }

  public class Cursor {
    private int row;
    private int col;

    public Cursor() {
      this(0, 0);
    }

    public Cursor(int row, int col) {
      this.row = row;
      this.col = col;
    }

    public int getRow() {
      return row;
    }

    public int getCol() {
      return col;
    }

    public void nextRow() {
      row++;
      col = 0;
    }

    public void nextCol() {
      col++;
    }

    public void prevCol() {
      col--;
    }

    public boolean atLeft() {
      return col == 0;
    }

    public boolean atRight() {
      return col == cols - 1;
    }

    public boolean pastRight() {
      return col >= cols;
    }

  }

  // Get the cell at the current cursor position
  public Cell getCell() {
    return getCell(cursor.row, cursor.col);
  }

  public Cell getCell(int row, int col) {
    return cells[row][col];
  }

  // Set cell at the current cursor position
  public void setCell(Cell cell) {
    setCell(cursor.row, cursor.col, cell);
  }

  public void setCell(int row, int col, Cell cell) {
    cells[row][col] = cell;
  }

  public void backspace() {
    if (cursor.atLeft())
      return;

    cursor.prevCol();
    Cell blankCell = new Cell();
    setCell(cursor.row, cursor.col, blankCell);

  }

  // True if letter is added, false otherwise
  public boolean addLetter(char letter) {
    if (cursor.col >= cols) {
      return false;
    }

    char currentLetter = Character.toUpperCase(letter);
    setCell(new Cell(currentLetter));
    cursor.nextCol();
    return true;

  }

  public void render(float delta, SpriteBatch batch, BitmapFont font) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen

    float gridWidth = cols * cellSize + (cols - 1) * cellPadding;
    float gridHeight = rows * cellSize + (rows - 1) * cellPadding;

    float startX = Gdx.graphics.getWidth() * 0.5f - gridWidth * 0.5f;
    float startY = Gdx.graphics.getHeight() * 0.5f + gridHeight * 0.6f;

    // Render the grid tile
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        float x = startX + col * (cellSize + cellPadding);
        float y = startY - row * (cellSize + cellPadding);
        // Draw the cell tile
        batch.setColor(Color.GRAY);
        batch.draw(backTexture, x, y, cellSize, cellSize);
      }
    }

    // Draw the letter in the box
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        float x = startX + col * (cellSize + cellPadding);
        float y = startY - row * (cellSize + cellPadding);

        // Get the cell and check if there's a letter
        Grid.Cell cell = getCell(row, col);
        if (cell.letter != ' ') {
          // Set the font color based on cell's feedback color
          font.setColor(cell.color);

          // Measure the width and height of the letter
          GlyphLayout layout = new GlyphLayout(font, String.valueOf(cell.letter));
          float textWidth = layout.width;
          float textHeight = layout.height;

          // Calculate the position to center the letter
          float textX = x + (cellSize - textWidth) / 2;
          float textY = y + (cellSize + textHeight) / 2;

          // Draw the letter at the calculated centered position
          font.draw(batch, layout, textX, textY);
        }
      }
    }
  }

}
