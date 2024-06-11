package shoppingmall.project.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import shoppingmall.project.exception.MyDbException;

@Aspect
@Component
@Slf4j
public class DbExceptionAspect {

    @Around("shoppingmall.project.aop.Pointcuts.allRepository()")
    public Object dbException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object result = joinPoint.proceed();

            return result;
            //db에서 일어나는 예외를 로그를 남기는 Aspect
        } catch (MyDbException ex) {
            log.error("repository method exception: {}", ex.getMessage());
            throw ex;
        }
    }
}
