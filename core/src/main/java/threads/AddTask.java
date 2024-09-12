package threads;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class AddTask extends RecursiveTask<Integer> {
    int [] tasks;
    int result;

    public AddTask(int [] tasks) {
        this.tasks = tasks;
    }

    @Override
    protected Integer compute() {
        if (tasks.length > 5) {
            int[] subtask1 = Arrays.copyOfRange(tasks, 0, 5);
            int[] subtask2 = Arrays.copyOfRange(tasks, 5, tasks.length);

            AddTask addTask1 = new AddTask(subtask1);
            AddTask addTask2 = new AddTask(subtask2);

            return addTask1.join() + addTask2.join();
        }

        for (int task : tasks) {
            result += task;
        }
        return result;
    }
}
