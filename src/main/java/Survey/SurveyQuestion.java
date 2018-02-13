package Survey;


/**
 * Created by Oleh on 10-Feb-18.
 */
public class SurveyQuestion {
    private String content;
    private String id;
    private String type;

    public SurveyQuestion(String content, String id, String type) {
        this.content = content;
        this.id = id;
        this.type = type;
    }

    public SurveyQuestion() {
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
