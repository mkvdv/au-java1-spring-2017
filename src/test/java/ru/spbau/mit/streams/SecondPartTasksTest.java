package ru.spbau.mit.streams;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class SecondPartTasksTest {

    @Test
    public void testFindQuotes() {
        assertEquals(
                Arrays.asList("No help here", "help me, please, i'm so stupid"),
                SecondPartTasks.findQuotes(
                        Arrays.asList(
                                "/home/avallon/IdeaProjects/au-java1-spring-2017/f1",
                                "/home/avallon/IdeaProjects/au-java1-spring-2017/f2"
                        ), "help"));

        assertEquals(
                Collections.emptyList(),
                SecondPartTasks.findQuotes(
                        Collections.emptyList(), "help"));
    }

    @Test
    public void testPiDividedBy4() {
        assertEquals(Math.PI * 0.25, SecondPartTasks.piDividedBy4(), 1e-3);
    }

    @Test
    public void testFindPrinter() {
        assertEquals(
                Optional.of("Doncova"),
                SecondPartTasks.findPrinter(ImmutableMap.of(
                        "Doncova", Arrays.asList("afdsfs", "fasfsafsfsaf", "may lampf"),
                        "kuzja prutkov", Collections.singletonList("9 ways to stop stopping"),
                        "artist7", Collections.emptyList())));
        assertEquals(
                Optional.empty(),
                SecondPartTasks.findPrinter(Collections.emptyMap())
        );
    }

    @Test
    public void testCalculateGlobalOrder() {
        assertEquals(
                ImmutableMap.of(
                        "cake", 4,
                        "beef", 1,
                        "pig", 8,
                        "beer", 7,
                        "bob", 3),
                SecondPartTasks.calculateGlobalOrder(Arrays.asList(
                        ImmutableMap.of("cake", 1, "pig", 2, "beer", 3),
                        ImmutableMap.of("beef", 1, "pig", 4, "beer", 4),
                        ImmutableMap.of("cake", 3, "pig", 2, "bob", 3)
                        )
                )
        );


    }
}