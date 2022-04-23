package xyz.keriteal.sosapi.aspect

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@Aspect
class SignValidationAspect @Autowired constructor(

) {
    @Pointcut("@annotation(xyz.keriteal.sosapi.annotation.ValidateSign)")
    fun pointcut() = Unit

    @Before("pointCut() && @annotation(anno)")
    fun beforePointcut(anno: JoinPoint) {

    }
}