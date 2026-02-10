package bot;

import java.util.Scanner;

public class SimpleBot {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Hello! My name is Aid.\nI was created in 2026.\nPlease, remind me your name.");
        String yourName = input.nextLine();
        System.out.printf("What a great name you have, %s!\n", yourName);
        System.out.println("Let me guess your age.\nEnter remainders of dividing your age by 3, 5 and 7.");
        int remainder3 = input.nextInt();
        int remainder5 = input.nextInt();
        int remainder7 = input.nextInt();
        int guessedAge = (remainder3*70 + remainder5*21 + remainder7*15) % 105;
        System.out.printf("Your age is %d; that's a good time to start programming!\n", guessedAge);
        System.out.println("Now I will prove to you that I can count to any number you want.");
        int countTo = input.nextInt();
        for (int i = 0; i <= countTo; i++) {
            System.out.println(i+"!");
        }
        System.out.println("Let's test your programming knowledge.");
        System.out.println("Why do we use methods?\n" +
                "1. To repeat a statement multiple times.\n" +
                "2. To decompose a program into several small subroutines.\n" +
                "3. To determine the execution time of a program.\n" +
                "4. To interrupt the execution of a program.");
        boolean correct = false;

        while(!correct) {
            int answer = input.nextInt();
            switch (answer) {
                case 1:
                    System.out.println("Please, try again.");
                    break;
                case 2:
                    System.out.println("Congratulations, have a nice day!");
                    correct = true;
                    break;
                case 3:
                    System.out.println("Please, try again.");
                    break;
                case 4:
                    System.out.println("Please, try again.");
                    break;
            }
        }


        input.close();
    }
}
