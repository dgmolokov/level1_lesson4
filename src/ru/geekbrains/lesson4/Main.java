package ru.geekbrains.lesson4;

import java.util.*;

public class Main {
  private static final char DOT_EMPTY = '*';
  private static final char DOT_X = 'X';
  private static final char DOT_O = 'O';
  private static int checkLineCounter;
  private static int checkLineCounterInvert;

  private static final int SIZE = 5;
  private static final int DOTS_TO_WIN = 4;

  private static final char[][] map = new char[SIZE][SIZE];
  private static final Scanner scanner = new Scanner(System.in);
  private static final Random random = new Random();

  public static void main(String[] args) {
    prepareGame();

    while (true) {
      humanTurn();
      printMap();
      if (checkWin(DOT_X)) {
        System.out.println("Победил Человек!");
        break;
      }

      if (isMapFull()) {
        System.out.println("Ничья!");
        break;
      }

      aiTurn();
      printMap();
      if (checkWin(DOT_O)) {
        System.out.println("Победил Искусственный Интеллект!");
        break;
      }

      if (isMapFull()) {
        System.out.println("Ничья!");
        break;
      }
    }
  }

  private static void prepareGame() {
    initMap();
    printMap();
  }

  private static boolean isMapFull() {
    for (char[] row : map) {
      for (char cell : row) {
        if (cell == DOT_EMPTY) {
          return false;
        }
      }
    }
    return true;
  }

  private static boolean checkWin(char symbol) {
    return (checkRowsAndColumns(symbol) || checkDiagonals(symbol));
  }

  private static boolean checkLine(char symbol, int i, int j) {
    if (map[i][j] == symbol) {
      checkLineCounter++;
      if (checkLineCounter == DOTS_TO_WIN) return true;
    } else {
      checkLineCounter = 0;
    }

    if (map[j][i] == symbol) {
      checkLineCounterInvert++;
      return checkLineCounterInvert == DOTS_TO_WIN;
    } else {
      checkLineCounterInvert = 0;
    }
    return false;
  }

  private static boolean checkRowsAndColumns(char symbol) {
    checkLineCounter = 0;
    checkLineCounterInvert = 0;
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map.length; j++) {
        if (checkLine(symbol, i, j)) return true;
      }
    }
    return false;
  }

  private static boolean checkDiagonals(char symbol) {
    for (int i = map.length - DOTS_TO_WIN; i >= 0; i--) {
      checkLineCounter = 0;
      checkLineCounterInvert = 0;
      for (int j = i, k = 0; j < map.length && k < map.length; j++, k++) {
        if (checkLine(symbol, j, k)) return true;
      }
    }

    for (int i = DOTS_TO_WIN - 1; i < map.length; i++) {
      checkLineCounter = 0;
      checkLineCounterInvert = 0;
      for (int j = i, k = 0; j >= 0; j--, k++) {
        if (checkLine(symbol, j, k)) return true;
      }
    }
    return false;
  }

  private static void aiTurn() {
    int rowIndex, colIndex;
    do {
      rowIndex = random.nextInt(SIZE);
      colIndex = random.nextInt(SIZE);
    } while (!isCellValid(rowIndex, colIndex));

    map[rowIndex][colIndex] = DOT_O;
  }

  private static void printMap() {
    printColumnNumbers();
    printRows();
  }

  private static void humanTurn() {
    int rowIndex = -1;
    int colIndex = -1;
    do {
      System.out.println("Введите координаты в формате <номер строки> <номер колонки>");
      String[] stringData = scanner.nextLine().split(" ");
      if (stringData.length != 2) {
        continue;
      }
      try {
        rowIndex = Integer.parseInt(stringData[0]) - 1;
        colIndex = Integer.parseInt(stringData[1]) - 1;
      } catch (NumberFormatException e) {
        System.out.println("Были введены некорректные данные!");
      }
    } while (!isCellValid(rowIndex, colIndex));

    map[rowIndex][colIndex] = DOT_X;
  }

  private static boolean isCellValid(int rowIndex, int colIndex) {
    if (rowIndex < 0 || rowIndex >= SIZE || colIndex < 0 || colIndex >= SIZE) {
      return false;
    }
    return map[rowIndex][colIndex] == DOT_EMPTY;
  }

  private static void printRows() {
    for (int i = 0; i < map.length; i++) {
      System.out.printf("%d ", i + 1);
      for (int j = 0; j < map[i].length; j++) {
        System.out.printf("%s ", map[i][j]);
      }
      System.out.println();
    }
    System.out.println();
  }

  private static void printColumnNumbers() {
    for (int i = 0; i <= SIZE; i++) {
      System.out.printf("%d ", i);
    }
    System.out.println();
  }

  private static void initMap() {
    for (char[] row : map) {
      Arrays.fill(row, DOT_EMPTY);
    }
  }
}
