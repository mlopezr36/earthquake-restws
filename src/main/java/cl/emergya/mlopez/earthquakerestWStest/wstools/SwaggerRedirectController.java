package cl.emergya.mlopez.earthquakerestWStest.wstools;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //note - this is a spring-boot controller, not @RestController
public class SwaggerRedirectController
{
    @RequestMapping("/earthquakes/swagger-ui.html")
    public String redirectSwaggerURL()
    {
        return "redirect:/swagger-ui.html";
    }
}
