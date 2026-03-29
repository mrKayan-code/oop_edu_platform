package view;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class ConsoleView {
    private final Scanner scanner;

    public ConsoleView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void print(String message) {
        System.out.print(message);
    }

    public void printHeader(String header) {
        println("\n==========================");
        println("   " + header);
        println("==========================\n");
    }

    public void printSubHeader(String header) {
        println("\n---> " + header + " <---\n");
    }

    public void printOptions(List<String> options) {
        println("\nДоступные действия:");
        for (int i = 0; i < options.size(); i++) {
            println(String.format("  [%d] %s", i + 1, options.get(i)));
        }
        println("[0] На выход");
    }

    public <T> void printList(List<T> list, Function<T, String> displayFunc) {
        if (list.isEmpty()) {
            printInfo("Список пуст");
            return;
        }
        println("");
        for (int i = 0; i < list.size(); i++) {
            T item = list.get(i);
            println(String.format("  %2d. %s", i + 1, displayFunc.apply(item)));
        }
        println("");
    }

    public void printError(String message) {
        println("ERROR: " + message);
    }

    public void printSuccess(String message) {
        println("SUCCES: " + message);
    }

    public void printInfo(String message) {
        println("INFO: " + message);
    }

    public String readLine() {
        print("->");
        return scanner.nextLine().trim();
    }

    public String readLine(String prompt) {
        print(prompt + "->");
        return scanner.nextLine().trim();
    }

    public String readRequired(String prompt) {
        while (true) {
            String input = readLine(prompt);
            if (!input.isBlank()) return input;
            printError("Это поле обязательно");
        }
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                String input = readLine(prompt);
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                printError("Введите корректное число");
            }
        }
    }

    public boolean readBoolean(String prompt) {
        while (true) {
            String input = readLine(prompt + " (д/н)").toLowerCase();
            if (input.equalsIgnoreCase("д") || input.equalsIgnoreCase("y")) return true;
            if (input.equalsIgnoreCase("н") || input.equalsIgnoreCase("n")) return false;
            printError("Введите 'д' или 'н'");
        }
    }

    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void awaitContinue() {
        println("\nНажмите что-то для продолжения...");
        scanner.nextLine();
        clear();
    }

    public boolean isExitCommand(String input) {
        return input.equals("0") || input.equalsIgnoreCase(":e") || input.equalsIgnoreCase("exit");
    }
}