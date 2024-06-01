package assorted;

public class SwitchCase {
    private enum DAYS_OF_THE_WEEK {
        SUN, MON, TUE, WED, THU, FRI, SAT
    }

    public static void main(String[] args) {
        DAYS_OF_THE_WEEK weekend = DAYS_OF_THE_WEEK.FRI;

        String feelingsOfADeveloper = switch (weekend) {
            case MON, TUE, WED, THU -> "Groan! All work and fun makes me a dull guy!";
            case FRI -> "Now Friday is fun. Nights are extremely interesting";
            case SAT -> "Yay!!!!!! Weekend is here";
            case SUN -> "Meh!";
        };

        System.out.println(feelingsOfADeveloper);
    }
}
