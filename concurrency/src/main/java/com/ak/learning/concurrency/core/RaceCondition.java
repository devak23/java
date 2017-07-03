package com.ak.learning.concurrency.core;

/**
 * If two threads compete for a resource, we have a RaceCondition
 */
public class RaceCondition {
	private static boolean done = false;
	
	public static void main(String... args) throws InterruptedException {
		new Thread(() -> {
			int i = 0;
			while (!done) {
				i++;
			}
			System.out.println("Done!");
		}).start();
		System.out.println("OS: " + System.getProperty("os.name"));
		Thread.sleep(2000);
		done = true;
		System.out.println("Done flag was set");
	}
}

/**
 * On my machine 64 bit linux (antergos) with a 64 bit Java, following is the output:
 * 	OS: Linux
 *	Done flag was set
 *	^C⏎
 * If the program was run using a 32 bit Java (ex: java -d32 $1), then it would have printed
 * 	OS: Linux
 * 	Done!
 *	Done flag was set
 * By default Java runs on a 32 bit client mode on a Windows machine and in server mode on a linux
 * machine. The program terminates in the client mode and does not terminate in the server mode
 * When run in server mode, the second thread never see's the update made to the variable. This is
 * because of the optimizations in the JIT compiler. The compiler sees that the flag is never updated
 * in the context of the thread there by re-arranging the code: while (!done) to while (true).
 * Besides, even if the main thread did the update, the second thread may simply read the value from
 * the cache or its registers and therefore may never receive a "true" update.
 * The quick fix to the problem is by marking the variable volatile. Volatile signals the second
 * thread that it should not read the variables value from the cache. It also signals the JIT
 * compiler to prevent any optimizations thus forcing the VM to read the value from the main memory.
 */