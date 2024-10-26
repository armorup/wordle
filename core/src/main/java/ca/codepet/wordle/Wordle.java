package ca.codepet.wordle;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Wordle {
  // enum FeedbackStatus {
  // CORRECT, WRONG_POSITION, INCORRECT
  // }

  private static final int ROWS = 6;
  private static final int COLS = 5;

  public Grid grid;

  // public Cell[][] grid = new Cell[ROWS][COLS];
  // public Color[][] gridColors = new Color[ROWS][COLS]; // To hold colors for
  // each letter

  private final String targetWord; // The target word for this game
  private final List<Feedback> pastGuesses; // List of past guesses and their feedback
  private String currentGuess = ""; // Current guess being typed by the player

  public Wordle() {
    this("APPLE");
  }

  public Wordle(String targetWord) {
    this.targetWord = targetWord.toUpperCase(); // Ensure the target word is uppercase
    this.pastGuesses = new ArrayList<>();
    grid = new Grid(ROWS, COLS);
  }

  // Backspace the last letter of the current guess
  public void backspace() {
    System.out.println(currentGuess);
    if (currentGuess.length() > 0) {
      currentGuess = currentGuess.substring(0, currentGuess.length() - 1);
      grid.backspace();
    }
  }

  public void addLetter(char letter) {
    if (currentGuess.length() < COLS) {
      currentGuess += letter;
      grid.addLetter(letter);
    }
  }

  public void render(float delta, SpriteBatch batch, BitmapFont font) {
    grid.render(delta, batch, font);
  }

  // Getters and setters
  public void setCurrentGuess(String currentGuess) {
    this.currentGuess = currentGuess.toUpperCase();
  }

  public String getCurrentGuess() {
    return currentGuess;
  }

  public boolean isGameOver() {
    return pastGuesses.size() >= ROWS || hasWon();
  }

  public boolean hasWon() {
    // Check if the player has guessed the correct word
    return !pastGuesses.isEmpty() && pastGuesses.get(pastGuesses.size() - 1).isCorrect();
  }

  // Handle a new guess and return feedback
  public boolean submitGuess() {
    if (grid.cursor.col != COLS) {
      return false;
    }

    // Perform the logic for checking the guess against the target
    Feedback feedback = checkGuess(currentGuess);
    pastGuesses.add(feedback);

    // Clear the current guess for the next round
    grid.cursor.nextRow();
    currentGuess = "";
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

  private Feedback checkGuess(String guess) {
    Color[] feedbackColors = new Color[COLS];
    boolean[] checkedTarget = new boolean[targetWord.length()];
    boolean[] checkedGuess = new boolean[guess.length()];

    // First pass: check for correct letters in the correct position (green)
    for (int i = 0; i < guess.length(); i++) {
      char guessChar = guess.charAt(i);
      char targetChar = targetWord.charAt(i);
      if (guessChar == targetChar) {
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

    // Return the guess and feedback (correct or not)
    return new Feedback(guess, feedbackColors, guess.equals(targetWord));
  }
}

/**
 * Represents the game grid for Wordle.
 */
class Grid {

  private final int cellSize = 50;
  private final int cellPadding = 5;

  private final int rows;
  private final int cols;
  private final Cell[][] cells;
  public final Cursor cursor = new Cursor();

  private final ShapeRenderer shape = new ShapeRenderer();

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

  // Inner classes for Cell and Cursor
  public class Cell {
    public char letter;
    public Color color;

    public Cell() {
      this(' ', Color.WHITE);
    }

    public Cell(char letter, Color color) {
      this.letter = letter;
      this.color = color;
    }
  }

  public class Cursor {
    public int row;
    public int col;

    public Cursor() {
      this(0, 0);
    }

    public Cursor(int row, int col) {
      this.row = row;
      this.col = col;
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
    Cell newCell = new Cell(currentLetter, Color.WHITE);
    setCell(cursor.row, cursor.col, newCell);
    cursor.nextCol();
    return true;

  }

  public void render(float delta, SpriteBatch batch, BitmapFont font) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen

    float gridWidth = cols * cellSize + (cols - 1) * cellPadding;
    float gridHeight = rows * cellSize + (rows - 1) * cellPadding;

    float startX = Gdx.graphics.getWidth() / 2 - gridWidth / 2;
    float startY = Gdx.graphics.getHeight() / 2 + gridHeight / 2;

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

        // Get the cell to check if there's a letter
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