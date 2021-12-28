package br.com.ecommerce.fixture;

import br.com.six2six.fixturefactory.loader.TemplateLoader;
import org.springframework.http.HttpHeaders;

public interface ClienteFixture extends TemplateLoader {

    String AUTHORIZATION_HEADER = "Authorization";
    String BEARER_TOKEN =
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiaWF0IjoxNTE2MjM5MDIyLCJpcCI6IjE5Mi4xNjguMC4xIiwicm9sZXMiOiIifQ.0luAj0haGR1yEb0SVsRbm_in6z_igIkbnee_znofuyw";

    String ID = "0d25b4ed-f641-4220-8365-7ad4add4ea63";

    static HttpHeaders authHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION_HEADER, BEARER_TOKEN);
        return headers;
    }
}
