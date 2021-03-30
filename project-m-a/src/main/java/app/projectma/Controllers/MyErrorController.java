package app.projectma.Controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String msg = "";
        String title = "Error";
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                msg = "This page does not exist...";
                title = "404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                msg = "500 Internal server error.\nWe are working to fix it...";
                title = "500";
            }
        }
        model.addAttribute("message", msg);
        model.addAttribute("title", title);
        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }

}
