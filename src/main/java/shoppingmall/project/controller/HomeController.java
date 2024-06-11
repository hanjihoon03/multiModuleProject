package shoppingmall.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shoppingmall.project.additional.annotation.Login;
import shoppingmall.project.domain.User;

@Controller
@RequiredArgsConstructor
public class HomeController {

    /**
     * 로그인 여부에 따라 기본 페이지를 나누기 위한 컨트롤러
     * @param loginUser 로그인 된 User 객체
     * @param model 로그인 된 user 정보를 담을 모델
     * @return
     */
    @GetMapping("")
    public String home(@Login User loginUser, Model model) {


        if (loginUser == null) {
            return "home";
        }

        model.addAttribute("user", loginUser);
        return "loginHome";
    }
}
