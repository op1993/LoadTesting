package Survey;

/**
 * Created by Oleh on 10-Feb-18.
 */
public class SurveyThemeList {
    private String id;
    private String name;

    public SurveyThemeList(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public SurveyThemeList() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
