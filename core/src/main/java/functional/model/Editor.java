package functional.model;

public final class Editor {

    private Editor() {
        throw new AssertionError("Cannot be instantiated");
    }

    public static String addIntroduction(String article) {
        return STR."\{article}\r\nIntroduction: ...";
    }

    public static String addBody(String article) {
        return STR."\{article}\r\nBody: ...";
    }

    public static String addConclusion(String article) {
        return STR."\{article}\r\nEnd: ...";
    }
}