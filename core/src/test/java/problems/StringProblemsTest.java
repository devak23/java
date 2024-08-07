package problems;


import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.Map;

public class StringProblemsTest {

    @DisplayName("Checking for duplicates using first method")
    @Test
    public void givenString_checkingForDuplicates_ReturnsDupes1() {
        // given
        String inputString = "characters";
        StringProblems stringProblems = new StringProblems();
        Map<Character, Integer> map = new HashMap<>();
        map.put('a', 2);
        map.put('r', 2);
        map.put('c', 2);

        // when
        Map<Character, Integer> outcome = stringProblems.findDupes1(inputString);
        // then
        Assertions.assertThat(outcome)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(map);
    }

    @DisplayName("Checking for duplicates using second method")
    @Test
    public void givenString_checkingForDuplicates_ReturnsDupes2() {
        // given
        String inputString = "characters";
        StringProblems stringProblems = new StringProblems();
        Map<String, Long> map = new HashMap<>();
        map.put("a", 2L);
        map.put("r", 2L);
        map.put("c", 2L);

        // when
        Map<String, Long> outcome = stringProblems.findDupes2(inputString);
        // then
        Assertions.assertThat(outcome)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(map);
    }
}