import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.*;
import java.io.*;


public class Runner {
    static ArrayList<WordInfo> listWords = new ArrayList<>();
    static  ArrayList<String> dbFilesToRead = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        String help = "/*\n* Programm paramms:\n" +
                "* -fl    : read a file with a list of paths to other files, Example: 'java Runner -fl test.txt'" +
                "\n* -f    : read file, Example: 'java Runner -f E:/WordFrequencyMurat/TestFiles/test.txt'" +
                "\n* -F    : read folder, Example: 'java Runner -f E:/WordFrequencyMurat/TestFiles'" +
                "\n* -l    : numeric list words. Example: 'java Runner -F E:/WordFrequencyMurat/TestFiles -l'" +
                "\n* -i    : information about word. Example: 'java Runner -F E:/WordFrequencyMurat/TestFiles -i type'" +
                "\n* -h    : help" +
                "\n*" +
                "\n */";

        if (args.length > 0) {
            String param = args[0];
            if (args.length > 1 && args.length < 5) {
                String path = args[1];
                switch (param) {
                    case "-f":
                        FileReader(path);
                        Print(args);
                        break;
                    case "-fl":
                        PathFileReader(path);
                        Print(args);
                        break;
                    case "-F":
                        final File folderToRead = new File(path);
                        ParseFolder(folderToRead, path);
                        ReadAllFiles();
                        Print(args);
                        break;
                    default:
                        System.out.println("Parameter error, please use -h option to help out.");
                        break;
                }
            }
            else if (args.length == 1) {
                if (param.equals("-h"))
                    System.out.println(help);
                else
                    System.out.println("Parameter error, please use the -h option to help out.");
            } else
                System.out.println("Parameter error, please use the -h option to help out.");
        } else {
            System.out.println("Parameter error, please use the -h option to help out.");
        }
    }

    private static void Print(String[] args)
    {
        if(args.length == 3 && args[2].equals("-l"))
            PrintResult(true, false, null);
        else if(args.length == 4 && args[2].equals("-i"))
        {

            String word = args[3];
            PrintResult(false, true, word);
        }
        else if(args.length == 2)
            PrintResult(false, false, null);
        else
            System.out.println("Parameter error, please use the -h option to help out.");
    }

    private static void PathFileReader(String path) throws FileNotFoundException {
        ArrayList<String> files = GetFiles(path);
        files.forEach((filePath) -> {
            try {
                FileReader(filePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    private static ArrayList<String> GetFiles(String path) throws FileNotFoundException {

        File file = new File(path);
        ArrayList<String> strArray = new ArrayList<>();
        if (file.isFile()) {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                strArray.add(scanner.nextLine());
            }
            scanner.close();
            return strArray;
        }
        else {
            System.out.println("Not a File, please use the -h option to help out.");
            return strArray;
        }

    }

    private static void PrintResult(boolean numeric, boolean info, String word) {
        int n = 1;
        if(info)
        {
            boolean find = false;
            for(int i = 0; i < listWords.size(); i++)
            {
                if(listWords.get(i).word.equals(word)) {
                    find = true;
                    System.out.print("Info word: \"" + word + "\" : ");
                    System.out.print(", count: " + listWords.get(i).totalCount);
                    System.out.println();
                    for (Map.Entry<String, Integer> entry : listWords.get(i).countWordToFile.entrySet()) {
                        String key = entry.getKey();
                        int value = entry.getValue();
                        System.out.format("%-10s - %-15s%n", key, value);
                    }
                }
            }
            if(!find)
            {
                System.out.println("Word '" + word +  "' not' found in files.");
            }
        }
        else
        {
            for(int i = 0; i < listWords.size(); i++)
            {
                if(numeric)
                    System.out.format("%s)%-10s - %-15s%n",n , listWords.get(i).word, listWords.get(i).totalCount);
                else
                    System.out.format("%-10s - %-15s%n", listWords.get(i).word, listWords.get(i).totalCount);
                n++;
            }
        }
    }
    private static void FileReader(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        if (file.isFile()) {
            Scanner scanner = new Scanner(file);
            String line = "";
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] strArray = line.split(" ");
                for (int i = 0; i < strArray.length; i++) {
                    checkWord(strArray[i],filePath);
                }
            }
            scanner.close();
        }
        else
            System.out.println("Not a File, please use the -h option to help out.");
    }

    private static void checkWord(String word, String filePath)
    {
        boolean find = false;
        for(int i = 0; i < listWords.size(); i++)
        {
            if(listWords.get(i).word.equals(word)){
                listWords.get(i).totalCount++;
                find = true;
                if(listWords.get(i).countWordToFile.containsKey(filePath)) {
                    listWords.get(i).countWordToFile.put(filePath, listWords.get(i).countWordToFile.get(filePath) + 1);
                }
                else
                {
                    listWords.get(i).countWordToFile.put(filePath, 1);
                }
            }
        }
        if(!find)
        {
            Hashtable<String,Integer> newWord = new Hashtable<>();
            newWord.put(filePath,1);
            listWords.add(new WordInfo(word, 1, newWord));
        }
    }

    private static void ParseFolder(final File folder, String folderPath) {
        if (folder.isDirectory()) {
            for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
                if (fileEntry.isDirectory()) {
                    ParseFolder(fileEntry, folderPath);
                } else {
                    dbFilesToRead.add(folderPath + File.separator + fileEntry.getName());
                }
            }
        }
        else {
            System.out.println("This is not folder, please use the -h option to help out.");
        }
    }

    private static void ReadAllFiles() throws IOException {
        for (String filePath : dbFilesToRead)
        {
            FileReader(filePath);
        }
    }
}

class WordInfo {
    String word;
    int totalCount;
    Hashtable<String, Integer> countWordToFile = new Hashtable<>();
    public WordInfo(String word, int totalCount, Hashtable<String, Integer> countWordToFile)
    {
        this.word = word;
        this.totalCount = totalCount;
        this.countWordToFile = countWordToFile;
    }
}