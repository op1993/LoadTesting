package Survey;

import Authorization.Authorization;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Utils.ApiMethods.get;

/**
 * Created by Oleh on 11-Feb-18.
 */
public class DataTransferChartFilters {

    SurveyFilter[] surveyFilters;
    SurveyQuestion[] surveyQuestions;
    SurveyThemeList[] surveyThemeLists;

    private ObjectMapper mapper = new ObjectMapper();

    public DataTransferChartFilters(String url, String surveyId, String username, String password) {
        collectSurveyInfo(url, surveyId, username, password);
    }

    private void collectSurveyInfo(String URL, String surveyId, String username, String password) {
        HttpResponse<String> response = get(URL +
                        "ws/st/analyse/survey/" + surveyId + "/chart-filters",
                new Authorization(URL, username, password));
        String filters = StringUtils.substringBetween(response.getBody(), "\"filters\":", "],");
        filters = filters + "]";
        try {
            surveyFilters = mapper.readValue(filters, SurveyFilter[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String questions = StringUtils.substringBetween(response.getBody(), "\"questions\":", "],");
        questions = questions + "]";
        try {
            surveyQuestions = mapper.readValue(questions, SurveyQuestion[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String themelist = StringUtils.substringBetween(response.getBody(), "\"surveyThemeLists\":", "],");
        themelist = themelist + "]";
        try {
            surveyThemeLists = mapper.readValue(themelist, SurveyThemeList[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //  return  response.getBody();
    }

    public SurveyFilter[] getSurveyFilters() {
        return surveyFilters;
    }

    public SurveyQuestion[] getSurveyQuestions() {
        return surveyQuestions;
    }

    public SurveyThemeList[] getSurveyThemeLists() {
        return surveyThemeLists;
    }

    public List<String> questions(SurveyQuestion[] surveyQuestion, String questionType) {
        List<String> textQuestions = new ArrayList<>();
        List<String> simpleQuestions = new ArrayList<>();
        for (SurveyQuestion question : surveyQuestion) {
            if (question.getType().equals("TEXT")) {
                textQuestions.add(question.getId());
            } else simpleQuestions.add(question.getId());
        }

        if (questionType.toLowerCase().equals("simple")) {
            return simpleQuestions;
        } else return textQuestions;
    }

    public List<String> themelistId(SurveyThemeList[] themeList) {
        List<String> tempId = new ArrayList<>();
        for (SurveyThemeList surveyThemeList : themeList) {
            tempId.add(surveyThemeList.getId());
        }
        return tempId;
    }
}


