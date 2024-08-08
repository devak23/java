package javacodingproblems;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class StringProblemsMainTest {

    @DisplayName("Checking for duplicates using first method")
    @Test
    public void givenString_checkingForDuplicates_ReturnsDupes1() {
        // given
        String inputString = "characters";
        StringProblemsMain stringProblemsMain = new StringProblemsMain();
        Map<Character, Integer> map = new HashMap<>();
        map.put('a', 2);
        map.put('r', 2);
        map.put('c', 2);

        // when
        Map<Character, Integer> outcome = stringProblemsMain.findDupes1(inputString);
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
        StringProblemsMain stringProblemsMain = new StringProblemsMain();
        Map<String, Long> map = new HashMap<>();
        map.put("a", 2L);
        map.put("r", 2L);
        map.put("c", 2L);

        // when
        Map<String, Long> outcome = stringProblemsMain.findDupes2(inputString);
        // then
        Assertions.assertThat(outcome)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(map);
    }
}