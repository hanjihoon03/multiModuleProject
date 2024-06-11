package shoppingmall.project.additional.intercepter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import shoppingmall.project.additional.web.session.SessionConst;
import shoppingmall.project.domain.User;

/**
 * 사용자 정보로 admin에 대해 admin에 대한 접근 권한을 판단하는 인터셉터
 */
@Slf4j
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 세션에서 사용자 정보를 가져옵니다.
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionConst.ADMIN);
        log.info("user={}", user.getLoginId());

        // 사용자 정보가 없거나 사용자가 관리자(admin)가 아닌 경우
        if (user == null || !user.getLoginId().equals("admin")) {
            response.sendRedirect("/home"); // 관리자가 아닌 경우 홈페이지로 리다이렉트
            return false; // 접근을 막음
        }

        return true; // 관리자인 경우 접근 허용
    }
}
