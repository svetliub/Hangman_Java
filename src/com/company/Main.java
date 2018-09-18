package com.company;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Map<String, ArrayList<String>> categoriesAndElements = new HashMap<String, ArrayList<String>>();
        ArrayList<String> categories = new ArrayList<>();
        String line;
        String category = "";
        int playerScore = 0;
        int lineNumber = 1;

        File file = new File("resources/files/Dictionary.txt");

        Scanner sc = new Scanner(file);

        while (sc.hasNextLine())
        {
            line = sc.nextLine();

            if(lineNumber == 1){
                line = line.substring(1);
                lineNumber++;
            }

            if (line.startsWith("_"))
            {
                line = line.substring(1);
                category = line.toLowerCase();

                if (!categoriesAndElements.containsKey(category))
                {
                    categoriesAndElements.put(category, new ArrayList<String>());
                    categories.add(line);
                }
            }
            else
            {
                ArrayList<String> elements = categoriesAndElements.get(category);
                elements.add(line);
                categoriesAndElements.put(category, elements);
            }
        }

        sc.close();

        int attempts = 10;
        ArrayList<String> usedLetters = new ArrayList<>();

         while (true)
        {
            System.out.println("Please choose a category:");
            printCategories(categories);

            String chosenCategory = br.readLine().toLowerCase();

            while (true)
            {
                if (!categoriesAndElements.containsKey(chosenCategory))
                {
                    System.out.println("No such category! Please choose another one:");
                    printCategories(categories);
                }
                else
                {
                    break;
                }

                chosenCategory = br.readLine().toLowerCase();
            }

            Random rand = new Random();
            int randomNumber = rand.nextInt(categoriesAndElements.get(chosenCategory).size() + 1);
            String word = categoriesAndElements.get(chosenCategory).get(randomNumber);
            String wordForCheck = word.toLowerCase();

            String transformedWord = transformWord(wordForCheck);
            String[] tempWord = transformedWord.split(" ");

            while (true)
            {
                System.out.println("Attempts left: " + attempts);
                printUsedLetters(usedLetters);
                System.out.println("Current word/phrase: " + transformedWord);
                System.out.println("Please enter a letter:");

                String letter = br.readLine().toLowerCase();
                usedLetters.add(letter);

                if (!wordForCheck.contains(letter))
                {
                    System.out.println("The word/phrase doesn’t have this letter.");
                    attempts--;
                }
                else
                {
                    int startIndex = 0;

                    while (true)
                    {
                        int index = wordForCheck.indexOf(letter, startIndex);

                        tempWord[index] = letter;

                        transformedWord = String.join(" ", tempWord);

                        if (index == wordForCheck.lastIndexOf(letter))
                        {
                            break;
                        }

                        startIndex = index + 1;
                    }
                }

                if (attempts == 0)
                {
                    System.out.println("Game Over!");
                    System.out.println("Final score: " + playerScore);
                    return;
                }

                if (isWordRevealed(wordForCheck, tempWord))
                {
                    playerScore++;
                    attempts = 10;
                    usedLetters = new ArrayList<>();
                    System.out.println("Congratulations you have revealed the word/phrase:");
                    System.out.println(getRevealedWord(word));
                    System.out.println("Current score: " + playerScore);
                    break;
                }
            }
        }
        // write your code here
    }

    private static void printCategories(ArrayList<String> categories)
    {
        for (String categoryKey : categories
             ) {
            System.out.println(categoryKey);
        }
    }

    private static String transformWord(String word)
    {
        String transformedWord = "";
        String tempWord = "";

        for (char letter : word.toCharArray())
        {
            if(letter != ' ')
            {
                tempWord += "_ ";
            }
            else
            {
                tempWord += " ";
            }
        }

        transformedWord = tempWord.trim();

        return transformedWord;
    }

    private static void printUsedLetters(ArrayList<String> usedLetters)
    {
        if (!usedLetters.isEmpty())
        {
            System.out.println("Your used letters till now: " + String.join(", ", usedLetters));
        }
    }

    private static boolean isWordRevealed(String word, String[] revealedWord)
    {
        String tempWord = "";

        for (String letter : revealedWord)
        {
            if (letter.equals(""))
            {
                tempWord += ' ';
            }
            else
            {
                tempWord += letter;
            }
        }

        return word.equals(tempWord);
    }

    private static String getRevealedWord(String word)
    {
        String revealedWord = "";
        String tempWord = "";

        for (char letter : word.toCharArray())
        {
            if (letter != ' ')
            {
                tempWord += letter + " ";
            }
            else
            {
                tempWord += " ";
            }
        }

        revealedWord = tempWord.trim();

        return revealedWord;
    }
}
