package Survey;

/**
 * Created by Oleh on 10-Feb-18.
 */
public class SurveyFilter {
    private String code;
    private String name;
    private String values[];

    public SurveyFilter(String code, String name, String[] values) {
        this.code = code;
        this.name = name;
        this.values = values;
    }

    public SurveyFilter() {
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String[] getValues() {
        return values;
    }
}
