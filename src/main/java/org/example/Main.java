package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    protected static AtomicInteger threeLength = new AtomicInteger(0);
    protected static AtomicInteger fourLength = new AtomicInteger(0);
    protected static AtomicInteger fiveLength = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[1000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));

        }

        Thread thread1 = new Thread(() -> {
            for (String text : texts) {
                boolean isPalindrom = true;
                for (int i = 0; i < text.length() / 2; i++) {
                    if (text.charAt(i) != text.charAt(text.length() - i - 1)) {
                        isPalindrom = false;
                        break;
                    }
                }
                if (isPalindrom) {
                    lengthCounter(text);
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                char letter = text.charAt(0);
                boolean sameLetter = true;
                for (int j = 0; j < text.length(); j++) {
                    if (text.charAt(j) != letter) {
                        sameLetter = false;
                        break;
                    }
                }
                if (sameLetter) {
                    lengthCounter(text);
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                boolean inAscending = true;
                for (int j = 0; j < text.length() - 1; j++) {
                    if (text.charAt(j) > text.charAt(j + 1)) {
                        inAscending = false;
                        break;
                    }
                }
                if (inAscending) {
                    lengthCounter(text);
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("Красивых слов с длиной 3: " + threeLength + " шт.");
        System.out.println("Красивых слов с длиной 4: " + fourLength + " шт.");
        System.out.println("Красивых слов с длиной 5: " + fiveLength + " шт.");
    }

    public static void lengthCounter(String text) {
        if (text.length() == 3) {
            threeLength.incrementAndGet();
        } else if (text.length() == 4) {
            fourLength.incrementAndGet();
        } else {
            fiveLength.incrementAndGet();
        }
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}

