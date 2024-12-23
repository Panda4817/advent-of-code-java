# Advent of Code - Java

## Create solution
- If this is a new year, add a new package called `year<year_number` e.g. 'year2024'
- Add a class for the day you are completing named `Day<day_number>` under package `day<day_number`, which should be under the year package
- Make sure day class implements `Day` interface
- Add puzzle input to `input.txt` (this file is in gitignore)
- Add tests
- Add a constructor that runs the `processData` method given an input file name
- Implement `processData`, `part1` and `part2` methods of class

## Build
- ```mvn clean install```

## Run
- ```mvn exec:java -Dexec.arguments=<year_number>,<day_number>```
- year_number corresponds to the specific year's puzzle you want to run
- day_number corresponds to the particular day in December that you want to run puzzles for
- Before running, make sure the right input data is in `input.txt`

## Test
- ```mvn test``` run all tests
- ```mvn test -Dtest=<test_class_name>``` run specific test class
