package Authorization;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Oleh on 10-Feb-18.
 */
public class Authorization {
    String url;
    String username;
    String password;
    String ticket;
    String jSessionId;

    public Authorization(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        auth();
    }

    public static void main(String[] args) {
        Authorization authorization = new Authorization("", "", "");
//       authorization.auth();
        System.out.println(authorization.getjSessionId());
        System.out.println(authorization.getTicket());
    }

    public String getTicket() {
        return ticket;
    }

    public String getjSessionId() {
        return jSessionId;
    }

    public void auth() {
        HttpResponse<String> response = null;
        String responseLogin = null;
        String ticket = null;
        List<String> setCookieHeader = new ArrayList<String>();
        String jSessionId = null;
        try {
            response = Unirest.get(this.url + "auth/ticket")
                    .header("accept", "application/json")
                    .basicAuth(this.username, this.password)
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        responseLogin = response.getBody();
        ticket = StringUtils.substringBetween(responseLogin, "\"ticket\":\"", "\"");
        if (ticket == null) {
            try {
                throw new Exception("Access was not granted");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        setCookieHeader = response.getHeaders().get("Set-Cookie");
        String setCookie = CollectionUtils.isNotEmpty(setCookieHeader) ? setCookieHeader.get(0) : null;

        if (setCookie != null) {
            jSessionId = StringUtils.substringBetween(setCookie, "JSESSIONID=", ";");
            if (jSessionId == null) {
                jSessionId = StringUtils.substringAfter(setCookie, "JSESSIONID=");
            }
        }
        this.ticket = ticket;
        this.jSessionId = jSessionId;

    }
}
