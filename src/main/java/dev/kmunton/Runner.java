package dev.kmunton;

import com.github.lalyos.jfiglet.FigletFont;
import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.days.DefaultDay;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Runner {

  private static final String INPUT_FILE = "input.txt";

  @SuppressWarnings("unchecked")
  public static <T, R> Day<T, R> mapDayToClass(String year, String day) {
    try {
      Class<Day<T, R>> dayClass = (Class<Day<T, R>>) Class.forName("dev.kmunton.year" + year + ".day" + day + ".Day" + day);
      var data = loadData(dayClass);
      Class<?>[] cArg = {List.class};
      return dayClass.getDeclaredConstructor(cArg).newInstance(data);
    } catch (Exception ex) {
      return (Day<T, R>) new DefaultDay();
    }
  }

  public static <T, R> void main(String[] args) throws IOException {
    String asciiArt = FigletFont.convertOneLine("Advent Of Code");
    log.info("\n{}", asciiArt);
    if (args.length == 0) {
      log.error("Provide year and then day number e.g '2024,1'");
      return;
    } else if (args.length > 2) {
      log.error("Too many arguments provided. Provide one year and then one day e.g. '2024,23'");
      return;
    }

    Day<T, R> ans = mapDayToClass(args[0], args[1]);

    final long startTimePart1 = System.currentTimeMillis();
    T part1 = ans.part1();
    final long endTimePart1 = System.currentTimeMillis();

    log.info("Part 1: {}", part1);
    log.info("Total execution time: {} ms", endTimePart1 - startTimePart1);

    final long startTimePart2 = System.currentTimeMillis();
    R part2 = ans.part2();
    final long endTimePart2 = System.currentTimeMillis();

    log.info("Part 2: {}", part2);
    log.info("Total execution time: {} ms", endTimePart2 - startTimePart2);

  }

  public static List<String> loadData(Class<?> clazz) throws FileNotFoundException {
    List<String> data = new ArrayList<>();
    var resource = clazz.getClassLoader().getResource(INPUT_FILE);
    if (Objects.isNull(resource)) {
      throw new FileNotFoundException("File with name [%s] not found".formatted(INPUT_FILE));
    }
    File file = new File(resource.getFile());
    Scanner myReader = new Scanner(file);
    while (myReader.hasNextLine()) {
      String s = myReader.nextLine();
      data.add(s);
    }
    myReader.close();

    return data;
  }
}
