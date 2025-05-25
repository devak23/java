package assorted;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

// Run the program with the following VM arguments
// -XX:+HeapDumpOnOutOfMemoryError -Xmx256m -XX:+UseG1GC -XX:+UseStringDeduplication -XX:+PrintStringDeduplicationStatistics
public class LotsOfStrings {
	private static final LinkedList<String> LOTS_OF_STRINGS = new LinkedList<>();
	
	public static void main(String... args) throws Exception {
		int iteration = 0;
		while (true) {
			for (int i = 0; i < 100; i++) {
				for (int j = 0; j < 1000; j++) {
					LOTS_OF_STRINGS.add(new String("String " + j));
				}
			}
			iteration++;
			System.out.println("Survived iteration: " + iteration);
			Thread.sleep(100);
			TimeUnit.MINUTES.sleep(1);
		}
	}

}
