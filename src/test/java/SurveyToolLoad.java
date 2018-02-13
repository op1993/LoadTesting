import Authorization.Authorization;
import Survey.DataTransferChartFilters;
import Survey.SurveyFilter;
import Survey.SurveyQuestion;
import Survey.SurveyThemeList;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import static Utils.ApiMethods.post;

public class SurveyToolLoad {

    static int THREAD_COUNT = 2;
    static String URL = "";
    static String USERNAME = "";
    static String PASSWORD = "";
    private static String SURVEY_ID = "bfe95357-f2ff-4dc5-8e1c-ca199ac2425e";
    private static DataTransferChartFilters dt = new DataTransferChartFilters(URL, SURVEY_ID, USERNAME, PASSWORD);
    private static SurveyFilter[] surveyFilters = dt.getSurveyFilters();
    private static SurveyQuestion[] surveyQuestions = dt.getSurveyQuestions();
    private static SurveyThemeList[] surveyThemeLists = dt.getSurveyThemeLists();
    private static List<String> simpleQuestionsId = dt.questions(surveyQuestions, "simple");
    private static List<String> textQuestionsId = dt.questions(surveyQuestions, "text");
    private static List<String> themelistId = dt.themelistId(surveyThemeLists);


    public static void main(String[] args) throws InterruptedException, IOException {
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        System.out.println("Start from main...");
        System.out.println(simpleQuestionsId.size());
        System.out.println("-----------------");
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(new MyRunnable(latch, i)).start();
        }
        latch.await();
//            System.out.println("Exit from main");
    }

    static class MyRunnable implements Runnable {
        private CountDownLatch latch;
        private int userPostfix;

        public MyRunnable(CountDownLatch latch, int postfix) {
            this.latch = latch;
            this.userPostfix = postfix;
        }

        @Override
        public void run() {

            HttpResponse<String> jsonResponse = null;
            System.out.println("Start " + Thread.currentThread().getName());
            long start = 0;
            boolean textQuest = new Random().nextBoolean();
            System.out.println(textQuest);
            try {
                Authorization authorization = new Authorization(URL, USERNAME, PASSWORD);
                start = System.currentTimeMillis();
                Unirest.setTimeouts(60000 * 7, 60000 * 7);
                if (textQuest == true) {
                    HttpRequest response = post(
                            URL + "ws/st/analyse/survey/chart/compare",
                            authorization,
                            "{\"questionFilter\":[]," +
                                    "\"surveyId\":\"" + SURVEY_ID + "\"," +
                                    "\"questionId\":\"" + simpleQuestionsId.get(new Random().nextInt(simpleQuestionsId.size())) + "\"," +
                                    "\"percentage\":" + new Random().nextBoolean() + "}");
                    simpleQuestionsId.get(new Random().nextInt(simpleQuestionsId.size()));
                    jsonResponse = response.asString();
                    System.out.println(jsonResponse.getBody());
                } else {
                    HttpRequest response = post(
                            URL + "ws/st/analyse/survey/chart/compare",
                            authorization,
                            "{\"surveyId\":\"" + SURVEY_ID + "\"," +
                                    "\"questionId\":\"" + textQuestionsId.get(new Random().nextInt(textQuestionsId.size())) + "\"," +
                                    "\"questionFilter\":[],\"percentage\":false," +
                                    "\"numberOfThemes\":10,\"themeListId\":\"" + themelistId.get(new Random().nextInt(themelistId.size())) + "\"}");


                    simpleQuestionsId.get(new Random().nextInt(simpleQuestionsId.size()));
                    jsonResponse = response.asString();
                    System.out.println(jsonResponse.getBody());
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
//                    System.out.println(this.userPostfix);
//                    System.out.println("QUESTION " + question);
//                    System.out.println("ThemeList " + themeList);
//                    System.out.println(jsonResponse.getBody());
                //    System.out.println(jsonResponse.getStatus());
//                    System.out.println("-----------");
                System.out.println(Thread.currentThread().getName() + ": it took time in sec " + (System.currentTimeMillis() - start) / 1000);
                latch.countDown();
            }
//            System.out.println("End " + Thread.currentThread().getName());
            System.out.println("--------------------------------------------------------------------------------------------------------------------------");
        }
    }

}

