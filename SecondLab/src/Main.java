public class Main {
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                throw new InvalidBracketsException("There are no elements in command line");
            }
            String input = String.join(" ", args);
            System.out.println("Source string: " + input);

            String result = removeInnermostContent(input);
            System.out.println("Result: " + result);
        }
        catch (InvalidBracketsException e) {
            System.err.println(e.getMessage());
        }
    }

    public static String removeInnermostContent(String input) {
        StringBuilder result = new StringBuilder(input);
        int currentDepth = 0;
        int maxDepth = 0;

        // Первая итерация: найти максимальную глубину вложенности
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(') {
                currentDepth++;
                if (currentDepth > maxDepth) {
                    maxDepth = currentDepth;
                }
            } else if (input.charAt(i) == ')') {
                currentDepth--;
            }
        }

        // Если скобок нет, возвращаем исходную строку
        if (maxDepth == 0) {
            return input;
        }

        currentDepth = 0;
        StringBuilder finalResult = new StringBuilder();
        int lastOpenIndex = -1;

        // Вторая итерация: удалить текст между всеми скобками на максимальной глубине
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '(') {
                currentDepth++;
                if (currentDepth == maxDepth) {
                    lastOpenIndex = finalResult.length(); // Запоминаем индекс последней открывающей скобки
                }
                finalResult.append(c);
            } else if (c == ')') {
                if (currentDepth == maxDepth) {
                    // Удаляем текст между скобками, начиная с последней открывающей скобки
                    finalResult.delete(lastOpenIndex + 1, finalResult.length());
                    lastOpenIndex = -1;
                }
                currentDepth--;
                finalResult.append(c);
            } else {
                if (currentDepth < maxDepth || lastOpenIndex == -1) {
                    finalResult.append(c); // Добавляем символы, если не на уровне максимальной глубины
                }
            }
        }

        return finalResult.toString();
    }
}

class InvalidBracketsException extends Exception {
    public InvalidBracketsException(String message) {
        super(message);
    }
}