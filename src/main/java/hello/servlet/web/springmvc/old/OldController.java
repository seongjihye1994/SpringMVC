package hello.servlet.web.springmvc.old;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// OldController 가 스프링 빈으로 등록될 때의 이름을 /springmvc/old-controller 로 등록
// 스프링 빈의 이름을 url 패턴으로 맞춘것
// 고객이 /springmvc/old-controller 로 요청하면 이 스프링 빈으로 등록된 OldController가 호출된다.
@Component("/springmvc/old-controller")
public class OldController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("OldController.handleRequest");

        return new ModelAndView("new-form");

    }
}
