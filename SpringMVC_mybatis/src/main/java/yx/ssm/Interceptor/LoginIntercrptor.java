package yx.ssm.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginIntercrptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String url=httpServletRequest.getRequestURI();
        if(url.indexOf("login.action")>=0){
            return  true;
        }
        HttpSession session=httpServletRequest.getSession();
        if(session.getAttribute("username")!=null){
            return true;
        }
        //执行这里表示用户身份需要认证，跳转登陆页面
        httpServletRequest.getRequestDispatcher("/login.jsp").forward(httpServletRequest,httpServletResponse);

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
