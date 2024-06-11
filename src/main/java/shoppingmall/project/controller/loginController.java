package shoppingmall.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shoppingmall.project.additional.web.session.SessionConst;
import shoppingmall.project.domain.User;
import shoppingmall.project.form.LoginForm;
import shoppingmall.project.form.UserForm;
import shoppingmall.project.service.UserService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class loginController {

    private final UserService userService;


    @GetMapping("login")
    public String loginPage(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login/login";

    }

    /**
     * 로그인을 db의 정보와 비교해 검증하는 컨트롤러 bindingResult로 에러를 반환해준다.
     * @param loginForm
     * @param bindingResult
     * @param request
     * @param session
     * @param model
     * @return
     */
    @PostMapping("login")
    public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult bindingResult,
                        HttpServletRequest request,
                        HttpSession session,
                        Model model) {

        if (bindingResult.hasErrors()){
            return "login/login";
        }

        User loginUser = userService.login(loginForm.getLoginId(), loginForm.getPassword());

        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디 혹은 비밀번호가 다릅니다.");
            model.addAttribute("error","아이디 혹은 비밀번호가 다릅니다.");
            return "login/login";
        }
        if (loginUser.getLoginId().equals("admin")) {
            session = request.getSession();
            session.setAttribute(SessionConst.ADMIN,loginUser);
            return "admin/adminPage";
        }
        session = request.getSession();

        session.setAttribute(SessionConst.LOGIN_USER,loginUser);


        return "redirect:/";
    }


    @GetMapping("sign-up")
    public String signUp(@ModelAttribute("userForm") UserForm userForm) {
            return "login/sign-up";
    }

    /**
     * 회원 가입 컨트롤러
     * @param userForm
     * @param bindingResult
     * @return
     */
    @PostMapping("sign-up")
    public String createUser(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult) {

        if (userService.existsId(userForm.getLoginId())) {
            bindingResult.rejectValue("loginId", "duplicate", "이미 사용 중인 아이디입니다.");
        }

        if (bindingResult.hasErrors()) {
                return "login/sign-up";
            }
            userService.createSaveUser(userForm);

            return "redirect:/";
    }


    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            //세션 해제
            session.invalidate();
        }
        return "redirect:/";
    }
    //어드민 페이지 이동
    //물품 추가 및 제거 수정
    @GetMapping("/adminPage")
    public String adminPage() {
        return "admin/adminPage";
    }

}
