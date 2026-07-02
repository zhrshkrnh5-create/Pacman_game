//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.io.FileWriter;
import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;

public class Main {
    static char direction = ' ';
    static int row;
    static int col;
    static int rownew;
    static int colnew;
    static int Score = 0;
    static int dot;
    static boolean gameRunning = true;
    static Timer time;
    static final int TIMER_DELAY = 500;
    static final int DOT_SCORE = 10;

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        Random rand = new Random();

        System.out.println("Enter the array size :");
        int num = input.nextInt();
        char[][] array = new char[num + 2][num + 2];

        System.out.println("Enter your number :");
        int num2 = input.nextInt();
        int count = 0;


        File file = new File("save.txt");
        file.createNewFile();

        if ((num * num) < num2) {
            System.out.println("ERROR" + "\n" + "Again enter number :");
            num2 = input.nextInt();
        } else {
            dot = num2;
            for (int i = 0; i < num + 2; i++) {
                for (int j = 0; j < num + 2; j++) {

                    if (i == 0 || j == 0 || i == num + 1 || j == num + 1) array[i][j] = '*';
                    else array[i][j] = ' ';
                }

            }

            while (count < num2) {
                row = rand.nextInt(num + 2);
                col = rand.nextInt(num + 2);
                if (array[row][col] == ' ') {
                    array[row][col] = '.';
                    count++;
                }
            }

        }

        while (true) {
            row = rand.nextInt(num + 2);
            col = rand.nextInt(num + 2);
            if (array[row][col] == (' ')) {
                array[row][col] = 'X';
                break;
            }
        }


        for (int i = 0; i < num + 2; i++) {
            for (int j = 0; j < num + 2; j++) {
                System.out.print(array[i][j]);
            }
            System.out.println();
        }

        long start = System.currentTimeMillis();
        Thread inputThead = new Thread(() -> {
            while (gameRunning) {
                char c = input.next().charAt(0);
                if (c == 'p') {
                    time.stop();
                } else if (c == 'r') {
                    time.start();
                } else {
                    direction = c;
                }
            }
        });
        inputThead.start();


        time = new Timer(TIMER_DELAY, e -> {
            if (direction == ' ') {
                return;
            } else {
                int[] result = move(row, col, direction);
                rownew = result[0];
                colnew = result[1];
                if (array[rownew][colnew] == ' ') {
                    array[row][col] = ' ';
                    array[rownew][colnew] = 'X';


                    row = rownew;
                    col = colnew;

                    repaint();
                    for (int i = 0; i < num + 2; i++) {
                        for (int j = 0; j < num + 2; j++) {
                            System.out.print(array[i][j]);
                        }
                        System.out.println();
                    }
                } else if (array[rownew][colnew] == '.') {
                    array[row][col] = ' ';
                    array[rownew][colnew] = 'X';
                    Score += DOT_SCORE;
                    dot--;
                    if (dot == 0) {
                        gameRunning = false;
                        time.stop();
                        long finish = System.currentTimeMillis();
                        long timeElapsed = finish - start;
                        System.out.println("Time = " + timeElapsed / 1000.0);
                        System.out.println("GAME OVER !");
                        try {
                            System.out.println("ALL SCORE ");
                            Scanner reader = new Scanner(new File("save.txt"));
                            while (reader.hasNextLine()) {
                                System.out.println(reader.nextLine());
                            }
                            reader.close();

                            FileWriter writer = new FileWriter("save.txt", true);
                            writer.write("Time : " + (timeElapsed / 1000.0) + " seconds\n");
                            writer.write("Score : " + Score + "\n\n");
                            writer.close();
                        } catch (IOException Error) {
                            Error.printStackTrace();
                        }

                        return;

                    }


                    row = rownew;
                    col = colnew;

                    repaint();
                    for (int i = 0; i < num + 2; i++) {
                        for (int j = 0; j < num + 2; j++) {
                            System.out.print(array[i][j]);
                        }
                        System.out.println();
                    }

                } else if (array[rownew][colnew] == '*') {
                    array[row][col] = ' ';
                    int[] pos = handleCrossBorder(rownew, colnew, num);
                    row = pos[0];
                    col = pos[1];
                    array[row][col] = 'X';
                    for (int i = 0; i < num + 2; i++) {
                        for (int j = 0; j < num + 2; j++) {
                            System.out.print(array[i][j]);
                        }
                        System.out.println();
                    }

                }
                System.out.println("Score = " + Score);
            }


        });

        time.start();


    }

    public static int[] handleCrossBorder(int Row, int Col, int WidthHieght) {
        if (Row > WidthHieght) {
            Row = 1;
        } else if (Row <= 0) {
            Row = WidthHieght;
        }
        if (Col > WidthHieght) {
            Col = 1;
        } else if (Col <= 0) {
            Col = WidthHieght;
        }
        return new int[]{Row, Col};


    }

    public static void repaint() {
        for (int k = 0; k < 50; k++) {
            System.out.println();
        }

    }

    public static int[] move(int Row, int Col, char direction) {
        switch (direction) {
            case 'w':
                Row--;
                break;
            case 'd':
                Col++;
                break;
            case 's':
                Row++;
                break;
            case 'a':
                Col--;
                break;
        }
        return new int[]{Row, Col};
    }

}