The program displays how many times a word appears in a file or between files. The program receives an input file, where each line in this file is the relative path of another file. Each file in the input file may contain words that need to be tracked. The user has the ability to run the program and see a list of all words and their corresponding quantities.

Options and examples:\n
-fl: read a file with a list of paths to other files, Example: 'java Runner -fl test.txt'
-f: read file, Example: 'java Runner -f E: /WordFrequencyMurat/TestFiles/test.txt'
-F: read folder, Example: 'java Runner -f E: / WordFrequencyMurat / TestFiles'
-l: numeric list words. Example: 'java Runner -F E: / WordFrequencyMurat / TestFiles -l'
-i: information about word. Example: 'java Runner -F E: / WordFrequencyMurat / TestFiles -i type'
-h: help   

Bonus:
The user is able to select a word and display from which files the word originated, display how many times the word appeared in each file, and display how many times the word was displayed as a whole.

-i: information about word. Example: 'java Runner -F E: / WordFrequencyMurat / TestFiles -i type'

Tests are given in the file "TestCases.csv"
