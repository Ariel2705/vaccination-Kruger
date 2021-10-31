package ec.com.kruger.vaccination.security;

import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

public class Authorization {
    private String username;
    private String pwd;

    @Value("${domainKruger}")
    private String domainKruger;

    public Authorization(String username, String pwd) {
        this.username = username;
        this.pwd = pwd;
    }

    public String tokenAuthorize() throws Exception {
        try {
            JSONObject userAuthorize = new JSONObject();
            userAuthorize.put("username", this.username);
            userAuthorize.put("pwd", this.pwd);
            String authorization = Unirest.post(domainKruger + "security/login")
                    .header("Content-Type", "application/json")
                    .body(userAuthorize).asJson().getBody().getObject().getString("token");
            return authorization;
        } catch (Exception e) {
            throw new Exception("Error");
        }
    }
}
