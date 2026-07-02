//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        Random rand = new Random();

        System.out.println("Enter the array size :");
        int num = input.nextInt();
        char[][] array = new char[num+2][num+2];

        System.out.println("Enter your number :");
        int num2 = input.nextInt();
        int count = 0;

        int row ;
        int col;
        int rownew ;
        int colnew ;

        char move;

        int Score=0;

        File file = new File("seve.txt");
        file.createNewFile();

        if( (num*num) < num2)
        {
            System.out.println("ERROR" + "\n" + "Again enter number :");
            num2 = input.nextInt();
        }

        else {
            for (int i = 0; i < num + 2; i++) {
                for (int j = 0; j < num + 2; j++) {

                    if (i==0 || j==0 || i==num+1 || j==num+1) array[i][j] = '*';
                    else array[i][j] = ' ';
                }

            }

            while(count < num2){
                row = rand.nextInt(num+2);
                col = rand.nextInt(num+2);
                if(array[row][col] == ' ')
                {
                    array[row][col] = '.';
                    count++;
                }
            }

        }

        while (true)
        {
            row = rand.nextInt(num+2);
            col = rand.nextInt(num+2);
            if(array[row][col] == (' '))
            {
                array[row][col] = 'X';
                break;
            }
        }

        for(int i=0 ;i<num+2; i++) {
            for (int j = 0; j < num + 2; j++){
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
        long start = System.currentTimeMillis();
        while(true) {

            move = input.next().charAt(0);
            rownew = row;
            colnew = col;
            switch (move) {
                case 'w':
                    rownew = row - 1;
                    break;
                case 'd':
                    colnew = col + 1;
                    break;
                case 's':
                    rownew = row + 1;
                    break;
                case 'a':
                    colnew = col - 1;
                    break;
            }
            if (array[rownew][colnew] == ' ')
            {
                array[row][col] = ' ';
                array[rownew][colnew] = 'X';


                row = rownew;
                col = colnew;

                for(int k=0 ; k<50 ; k++)
                {
                    System.out.println();
                }
                for(int i=0 ;i<num+2; i++) {
                    for (int j = 0; j < num + 2; j++){
                        System.out.print(array[i][j]);
                    }
                    System.out.println();
                }
            }
            else if (array[rownew][colnew] == '.')
            {
                array[row][col] = ' ';
                array[rownew][colnew] = 'X';
                Score += 10;


                row = rownew;
                col = colnew;

                for(int k=0 ; k<50 ; k++)
                {
                    System.out.println();
                }
                for(int i=0 ;i<num+2; i++) {
                    for (int j = 0; j < num + 2; j++){
                        System.out.print(array[i][j]);
                    }
                    System.out.println();
                }

            }
            else if (array[rownew][colnew] == '*') {
                System.out.println("hitting the game wal");
                break;
            }
            System.out.println("Score = " + Score);
            try{
                Thread.sleep(1000);
            } catch (Exception e) {}


        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;


        FileWriter writer = new FileWriter("save.txt" , true);
        writer.write( "Time : " + (timeElapsed/1000.0) + " seconds\n");
        writer.write("Score : " + Score + "\n\n\n");
        writer.close();

        Scanner reader = new Scanner(new File("save.txt"));
        while(reader.hasNextLine())
        {
            System.out.println(reader.nextLine());
        }
        reader.close();




    }
}