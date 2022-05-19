package xyz.keriteal.sosapi.aspect

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import xyz.keriteal.sosapi.annotation.ValidateSign
import xyz.keriteal.sosapi.model.RequestModel

@Component
@Aspect
class SignValidationAspect @Autowired constructor(

) {
    @Pointcut("@annotation(xyz.keriteal.sosapi.annotation.ValidateSign)")
    fun pointCut() = Unit

    @Before("pointCut() && @annotation(anno)")
    fun beforePointcut(joinPoint: JoinPoint, anno: ValidateSign) {
        val arg0 = joinPoint.args[0]
        if (arg0 !is RequestModel) {
            return
        }
        val requestModel = arg0
        val sign = requestModel.sign
        
    }
}