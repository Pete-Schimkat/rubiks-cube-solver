import main.Algorithms.KorfCubeSolver;

import java.util.Scanner;

public class SolverDemo {
    private static KorfCubeSolver solver = new KorfCubeSolver(1);
    public static void main(String[] args) {
        solver.initialize();
        Scanner scanner = new Scanner(System.in);
        String input = "";

        System.out.println("Rubiks Cube Solver - Java. ");
        System.out.println("Input 'help' to show available commands.");
        while(true) {
            input = scanner.nextLine();
            switch(input){
                case "help":
                    System.out.println("help - show commands");
            }

        }
    }
}
