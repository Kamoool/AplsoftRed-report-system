package pl.edu.agh.mwo.reporter.model;

import org.junit.*;

import static org.junit.Assert.*;

import java.time.LocalDate;

public class TaskTest {

    private Task task1;
    private Task task2;
    private Task task3;
    private Task task4;

    @Before
    public void setUp() {
        task1 = new Task("name1", LocalDate.of(2020, 10, 10), 5);
        task2 = new Task("name1", LocalDate.of(2021, 11, 11), 6);
        task3 = new Task("name2", LocalDate.of(2020, 10, 10), 7);
        task4 = new Task("name3", LocalDate.of(2019, 9, 9), 5);
    }

    @Test
    public void tasksEqualNotEqual() {
        assertTrue(task1.equals(task2));
        assertFalse(task1.equals(task3));
        assertFalse(task1.equals(task4));
    }

}
