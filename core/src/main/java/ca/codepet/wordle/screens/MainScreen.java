package ca.codepet.wordle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import ca.codepet.wordle.MainGame;

public class MainScreen implements Screen {
  private Stage stage;
  private Skin skin;
  private Table mainTable;
  private StringBuilder inputText = new StringBuilder();
  private MainGame game;

  public MainScreen(MainGame game) {
    this.game = game;

    // Initialize stage and skin
    stage = new Stage(new ScreenViewport());
    Gdx.input.setInputProcessor(stage);
    skin = new Skin(Gdx.files.internal("uiskin.json"));

    // Create main layout table
    mainTable = new Table();
    mainTable.setFillParent(true);
    stage.addActor(mainTable);

    // Add Wordle grid and keyboard to the main table
    Table gridTable = createWordleGrid();
    mainTable.add(gridTable).padBottom(20); // Add padding below the grid
    mainTable.row(); // Move to next row for keyboard
    Table keyboardTable = createKeyboard();
    mainTable.add(keyboardTable).expandX().bottom();
  }

  // Create a dummy Wordle grid table
  private Table createWordleGrid() {
    Table gridTable = new Table();
    for (int i = 0; i < 5; i++) { // Assuming 5 rows for Wordle guesses
      for (int j = 0; j < 5; j++) { // 5 letters per word
        TextButton cell = new TextButton("", skin); // Empty cell for each letter
        gridTable.add(cell).size(50, 50).pad(3);
      }
      gridTable.row();
    }
    return gridTable;
  }

  // Create the on-screen keyboard
  private Table createKeyboard() {
    Table keyboardTable = new Table();

    // First row (QWERTY)
    String[] firstRowKeys = { "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P" };
    addKeysToTable(keyboardTable, firstRowKeys);

    // Second row (ASDF)
    String[] secondRowKeys = { "A", "S", "D", "F", "G", "H", "J", "K", "L" };
    keyboardTable.row();
    addKeysToTable(keyboardTable, secondRowKeys);

    // Third row (Enter, ZXCV, Backspace)
    String[] thirdRowKeys = { "Enter", "Z", "X", "C", "V", "B", "N", "M", "Back" };
    keyboardTable.row();
    addKeysToTable(keyboardTable, thirdRowKeys);

    return keyboardTable;
  }

  private void addKeysToTable(Table table, String[] keys) {
    for (String key : keys) {
      TextButton button = new TextButton(key, skin);

      // Add listener for button click
      button.addListener(event -> {
        if (event.isHandled()) {
          handleKeyPress(key);
        }
        return true;
      });

      table.add(button).pad(5); // Add padding around buttons
    }
  }

  private void handleKeyPress(String key) {
    if (key.equals("Enter")) {
      // Handle Enter key press
      System.out.println("Enter key pressed");
    } else if (key.equals("Backspace")) {
      // Handle Backspace key press
      if (inputText.length() > 0) {
        inputText.deleteCharAt(inputText.length() - 1);
      }
    } else {
      // Handle letter key press
      inputText.append(key);
    }
    System.out.println("Current input: " + inputText);
  }

  @Override
  public void show() {
    // This method is called when the screen becomes visible
  }

  @Override
  public void render(float delta) {
    // Clear screen
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // Update and draw stage
    stage.act(delta);
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    // Update stage viewport on resize
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void hide() {
    // Called when this screen is no longer the current screen
  }

  @Override
  public void dispose() {
    stage.dispose();
    skin.dispose();
  }
}
