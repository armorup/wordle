package ca.codepet.util;

import java.util.List;
import java.util.Set;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class FileUtils {
  /*
   * TODO: move into utility class
   * Load words from a file into a list.
   */
  public static void loadWordsFromFile(String fileName, List<String> wordList) {
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

  /*
   * Load words from a file into a set.
   */
  public static void loadWordsFromFile(String fileName, Set<String> wordSet) {
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
}
