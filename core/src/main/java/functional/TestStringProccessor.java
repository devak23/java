package functional;

import functional.spec.StringProcessor;
import functional.spec.TwoArgsProcessor;

public class TestStringProccessor {

    public static void main(String[] args) {
        var upString = new UpperString();
        var lowString = new StringProcessor() {

            @Override
            public String process(String input) {
                return input.toLowerCase();
            }
        };

        System.out.println(upString.process("abhay"));
        System.out.println(lowString.process("ABHAY"));

        //"abhay".chars().mapToObj(i -> (char)i).toList().forEach(System.out::println);
        System.out.println(StringProcessor.isLowerCase("abhay"));
        System.out.println(StringProcessor.isLowerCase("abHay"));

        TwoArgsProcessor<String> appender = new TwoArgsProcessor<String>() {
            @Override
            public String process(String arg1, String arg2) {
                return arg1 + arg2;
            }
        };

        TwoArgsProcessor<Integer> adder = new TwoArgsProcessor<Integer>() {
            @Override
            public Integer process(Integer arg1, Integer arg2) {
                return arg1 + arg2;
            }
        };

        String strResult = appender.process("Abhay", " is cool");
        int intResult = adder.process(1, 2);
        System.out.println(strResult +  intResult);
    }
}
