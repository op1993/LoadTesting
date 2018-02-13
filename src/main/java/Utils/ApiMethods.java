package Utils;

import Authorization.Authorization;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import org.apache.http.HttpHeaders;


/**
 * Created by Oleh on 10-Feb-18.
 */
public class ApiMethods {

    private static final String X_AUTH_TICKET = "X-Auth-Ticket";

    public static HttpRequest post(String url, Authorization authorization, String body) {
        return Unirest.post(url)
                .header(X_AUTH_TICKET, authorization.getTicket())
                .header("Cookie", "JSESSIONID=" + authorization.getjSessionId())
                .header(HttpHeaders.ACCEPT, "application/json, text/plain, */*")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(body)
                .getHttpRequest();
    }

    public static HttpResponse<String> get(String url, Authorization authorization) {
        HttpResponse<String> temp = null;
        try {
            temp = Unirest.get(url)
                    .header(X_AUTH_TICKET, authorization.getTicket())
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return temp;
    }
}

