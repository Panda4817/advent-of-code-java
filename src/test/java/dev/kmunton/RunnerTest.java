package dev.kmunton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.kmunton.utils.days.Day;
import dev.kmunton.utils.days.DefaultDay;
import dev.kmunton.year2024.day1.Day1;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class RunnerTest {

    private static final LogCaptor LOG_CAPTOR_RUNNER = LogCaptor.forClass(Runner.class);
    private static final LogCaptor LOG_CAPTOR_DEFAULT_DAY = LogCaptor.forClass(DefaultDay.class);

    @BeforeEach
    void setUp() {
        LOG_CAPTOR_RUNNER.clearLogs();
        LOG_CAPTOR_DEFAULT_DAY.clearLogs();
    }

    @ParameterizedTest
    @CsvSource({
        "hello,test",
        ",",
        "-1,",
        "1000,26"
    })
    void mapDayToClass_incorrectInput_throwDayException(String input1, String input2) {
        Day<Long, Long> obj = Runner.mapDayToClass(input1, input2);

        assertEquals(DefaultDay.class, obj.getClass());
    }

    @Test
    void mapDayToClass_correctInput_returnDay() {
        Day<Long, Long> obj = Runner.mapDayToClass("2024", "1");

        assertEquals(Day1.class, obj.getClass());
    }

    @Test
    void main_emptyArgs_returnMsg() throws IOException {
        String[] list = {};
        Runner.main(list);

        assertTrue(LOG_CAPTOR_RUNNER.getErrorLogs().contains("Provide year and then day number e.g '2024,1'"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "26", "hello"})
    void main_incorrectDayNumber_printOutErrorMsg(String input) throws IOException {
        String[] list = {"2024", input};
        Runner.main(list);

      assertEquals(2, LOG_CAPTOR_DEFAULT_DAY
          .getErrorLogs().stream().filter(m -> m.contains("Incorrect year and day combination entered, no result")).toList().size());
      assertTrue(LOG_CAPTOR_RUNNER.getInfoLogs().contains("Part 1: -1"));
      assertTrue(LOG_CAPTOR_RUNNER.getInfoLogs().contains("Part 2: -1"));
    }

    @Test
    void main_tooManyArgs_returnErrorMsg() throws IOException {
        String[] list = {"1", "2", "3"};
        Runner.main(list);

        assertTrue(LOG_CAPTOR_RUNNER.getErrorLogs().contains("Too many arguments provided. Provide one year and then one day e.g. '2024,23'"));
    }

    @Test
    void main_correctDayButNoInput_errorMsgNotReturned() throws IOException {
        String[] list = {"2024", "1"};
        Runner.main(list);

        assertFalse(LOG_CAPTOR_DEFAULT_DAY
            .getErrorLogs().contains("Part 1: \nIncorrect year and day combination entered, no result\n\nPart 2: \nIncorrect year and day combination entered, no result"));
    }

    @Test
    void loadData_correctFilename_returnList() throws FileNotFoundException {
        var data = Runner.loadData(getClass());

        assertEquals(List.of("1   1", "2   2", "3   3", "4   4", "5   5"), data);
    }
}
