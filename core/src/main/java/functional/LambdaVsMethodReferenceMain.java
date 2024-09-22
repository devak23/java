package functional;

import functional.model.Print;

public class LambdaVsMethodReferenceMain {
    public static void main(String[] args) {
        Print print = new Print();
        print.start();
    }
}


// OUTPUT: Scenario 1
// 22:27:36.154 [main] INFO functional.model.Print -- Constructor: Starting Printer...
// 22:27:36.158 [main] INFO functional.model.Print -- p1:
// 22:27:36.158 [main] INFO functional.model.Print -- Printing (with reset)...: 998351292
// 22:27:36.159 [main] INFO functional.model.Print -- p1:
// 22:27:36.159 [main] INFO functional.model.Print -- Printing (with reset)...: 998351292
// 22:27:36.159 [main] INFO functional.model.Print -- p2:
// 22:27:36.159 [main] INFO functional.model.Print -- Constructor: Starting Printer...
// 22:27:36.159 [main] INFO functional.model.Print -- Printing (with reset)...: 998351292
// 22:27:36.159 [main] INFO functional.model.Print -- p2:
// 22:27:36.159 [main] INFO functional.model.Print -- Constructor: Starting Printer...
// 22:27:36.159 [main] INFO functional.model.Print -- Printing (with reset)...: 998351292
// 22:27:36.160 [main] INFO functional.model.Print -- p1:
// 22:27:36.160 [main] INFO functional.model.Print -- Printing (with reset)...: 998351292
// 22:27:36.160 [main] INFO functional.model.Print -- p2:
// 22:27:36.160 [main] INFO functional.model.Print -- Constructor: Starting Printer...
// 22:27:36.160 [main] INFO functional.model.Print -- Printing (with reset)...: 998351292

// This output is caused by the method reference, p1. The Printer constructor is invoked right away, even if we didn’t
// call the run() method. Because p2 (the lambda) is lazy, the Printer constructor is not called until we call the run()
// method. we can see that the Printer constructor is called each time the lambda (p2.run()) is executed. On the other
// hand, for the method reference (p1.run()), the Printer constructor is not called. It was called a single time, at the
// p1 declaration. So p1 prints without resetting the printer.


// Scenario 2
// 22:28:29.423 [main] INFO functional.model.Print -- p1:
// 22:28:29.425 [main] INFO functional.model.Print -- Printing (no reset)...: 998351292
// 22:28:29.426 [main] INFO functional.model.Print -- p1:
// 22:28:29.426 [main] INFO functional.model.Print -- Printing (no reset)...: 998351292
// 22:28:29.426 [main] INFO functional.model.Print -- p2:
// 22:28:29.426 [main] INFO functional.model.Print -- Printing (no reset)...: 998351292
// 22:28:29.426 [main] INFO functional.model.Print -- p2:
// 22:28:29.426 [main] INFO functional.model.Print -- Printing (no reset)...: 998351292
// 22:28:29.426 [main] INFO functional.model.Print -- p1:
// 22:28:29.426 [main] INFO functional.model.Print -- Printing (no reset)...: 998351292
// 22:28:29.426 [main] INFO functional.model.Print -- p2:
// 22:28:29.426 [main] INFO functional.model.Print -- Printing (no reset)...: 998351292

// The printNoReset() is a static method, so the Printer constructor is not invoked. We can interchangeably use p1 or p2
// without having any difference in behavior. So, in this case, it is just a matter of preference.

// Conclusion
// When calling non-static methods, there is one main difference between a method reference and a lambda. A method
// reference calls the constructor immediately and only once (at method invocation (run()), the constructor is not called).
// On the other hand, lambdas are lazy. They call the constructor only at method invocation and at each such invocation (run()).