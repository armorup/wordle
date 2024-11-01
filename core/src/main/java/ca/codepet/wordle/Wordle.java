package ca.codepet.wordle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public final class Wordle {

  private static final int ROWS = 6;
  private static final int COLS = 5;

  Grid grid;

  // public Cell[][] grid = new Cell[ROWS][COLS];
  // public Color[][] gridColors = new Color[ROWS][COLS]; // To hold colors for
  // each letter

  private final String targetWord; // The target word for this game
  private final List<Feedback> pastGuesses = new ArrayList<>(); // List of past guesses and their feedback
  private static final List<String> laWords = new ArrayList<>();
  private static final Set<String> taWords = new HashSet<>();

  private String message = "Guess the word!"; // Message from wordle

  static {
    loadWordsFromFile("wordle-la.txt", laWords);
    loadWordsFromFile("wordle-ta.txt", taWords);
    System.out.println(laWords.size() + " words loaded");
    System.out.println(taWords.size() + " words loaded");
  }

  /*
   * Constructor for a new Wordle game with a random target word.
   */
  public Wordle() {
    this(chooseRandomWord());
    System.out.println("Target word: " + targetWord);
  }

  /*
   * Constructor for a new Wordle game with a specified target word.
   */
  public Wordle(String targetWord) {
    this.targetWord = targetWord.toUpperCase(); // Ensure the target word is uppercase
    grid = new Grid(ROWS, COLS);
  }

  private static void loadWordsFromFile(String fileName, List<String> wordList) {
    FileHandle file = Gdx.files.internal(fileName);
    try (Scanner scanner = new Scanner(file.readString())) {
      while (scanner.hasNextLine()) {
        String word = scanner.nextLine().trim();
        if (word.length() == 5) {
          wordList.add(word.toUpperCase());
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("Error reading words file: " + fileName, e);
    }

    if (wordList.isEmpty()) {
      throw new RuntimeException("No 5-letter words found in the file: " + fileName);
    }
  }

  private static void loadWordsFromFile(String fileName, Set<String> wordSet) {
    FileHandle file = Gdx.files.internal(fileName);
    try (Scanner scanner = new Scanner(file.readString())) {
      while (scanner.hasNextLine()) {
        String word = scanner.nextLine().trim();
        if (word.length() == 5) {
          wordSet.add(word.toUpperCase());
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("Error reading words file: " + fileName, e);
    }

    if (wordSet.isEmpty()) {
      throw new RuntimeException("No 5-letter words found in the file: " + fileName);
    }
  }

  private static String chooseRandomWord() {
    Random random = new Random();
    return laWords.get(random.nextInt(laWords.size()));
  }

  private boolean isValidWord(String word) {
    boolean taValid = taWords.contains(word.toUpperCase());
    boolean laValid = laWords.contains(word.toUpperCase());
    return taValid || laValid;
  }

  // Backspace the last letter of the current guess
  public void backspace() {
    clearMessage();
    if (grid.currentGuess().length() > 0) {
      grid.backspace();
    }
  }

  public void addLetter(char letter) {
    clearMessage();
    if (grid.currentGuess().length() >= COLS) {
      return;
    }

    grid.addLetter(letter);
    System.out.println("Current guess: " + grid.currentGuess());

  }

  public void render(float delta, SpriteBatch batch, BitmapFont font) {
    grid.render(delta, batch, font);
  }

  public String getCurrentGuess() {
    return grid.currentGuess();
  }

  public boolean isGameOver() {
    return pastGuesses.size() >= ROWS || hasWon();
  }

  public boolean hasWon() {
    // Check if the player has guessed the correct word
    boolean won = pastGuesses.size() > 0 && pastGuesses.get(pastGuesses.size() - 1).isCorrect();
    if (won) {
      message = "You won!";
    }
    return won;
  }

  public String getMessage() {
    return message;
  }

  public void clearMessage() {
    message = "";
  }

  // Handle a new guess and return feedback
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

  public List<Feedback> getPastGuesses() {
    return pastGuesses;
  }

  // Inner class to encapsulate guess feedback
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

    // Output all grid cells
    for (int i = 0; i < COLS; i++) {
      Grid.Cell cell = grid.getCell(grid.cursor.getRow(), i);
      System.out.println("Cell " + i + ": " + cell.letter + " " + cell.color);
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

  private final ShapeRenderer shape = new ShapeRenderer();

  // Constructor for a new empty grid
  public Grid(int rows, int cols) {
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

  // Inner classes for Cell and Cursor
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
    shape.begin(ShapeRenderer.ShapeType.Filled);
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        float x = startX + col * (cellSize + cellPadding);
        float y = startY - row * (cellSize + cellPadding);
        // Draw the cell tile
        shape.setColor(Color.DARK_GRAY);
        shape.rect(x, y, cellSize, cellSize);
      }
    }
    shape.end();

    // Render the letters
    batch.begin();

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

    batch.end();
  }

}