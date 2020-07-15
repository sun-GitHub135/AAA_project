package com.aaa.sun.annotation;

import com.aaa.sun.model.LoginLog;
import com.aaa.sun.model.User;
import com.aaa.sun.service.IProjectService;
import com.aaa.sun.utils.AddressUtils;
import com.aaa.sun.utils.DateUtils;
import com.aaa.sun.utils.IPUtils;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.tomcat.jni.Address;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import static com.aaa.sun.staticproperties.TimeForatProperties.*;

/**
 * @author sun
 * @Company AAA软件教育
 * @date 2020/7/15 19:16
 * @Description
 *  AOP
 *      @Slf4j
 *          simple log for java
 *
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Autowired
    private IProjectService iProjectService;
    /**
     * @Author sun
     * @Description
     *      定义切点信息
     *      这个时候就不能再按照常规的切点（service/controller）
     *      直接去切自定义注解
     *      也就是说当检测自定义注解存在的时候，切面触发，也就是说AOP才会触发
     * @Date 19:19 2020/7/15
     * @Param []
     * @return void
     */
    @Pointcut("@annotation(com.aaa.sun.annotation.LoginAnnotation)")
    public void pointcut(){
        // TODO noting to do

    }
    /**
     * @Author sun
     * @Description
     *      定义环形切面（就是具体实现业务逻辑的方法）
     *      ProceedingJoinPoint:
     *          封装了目标路径中的所用到的所有参数
     *
     * @Date 19:24 2020/7/15
     * @Param []
     * @return java.lang.Object
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws ClassNotFoundException {
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        }catch (Throwable th){
            th.printStackTrace();
        }

        // 获取Request对象
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
        // 1.获取IP地址
        String ipAddr = IPUtils.getIpAddr(request);// 需要一个httpServletRequest对象
        // 2.获取地理位置
        Map<String, Object> addressMap = AddressUtils.getAddresses(ipAddr, "UTF-8");

        LoginLog loginLog = new LoginLog();
        loginLog.setIp(ipAddr);
        loginLog.setLocation(addressMap.get("propvince")+"|"+addressMap.get("city"));
        loginLog.setLoginTime(DateUtil.formatDate(new Date(),TIME_FORMAT));
        // 3. 获取用户名username---》必须要获取到目标方法的参数值
        Object[] args = proceedingJoinPoint.getArgs();
        User user = (User) args[0];
        loginLog.setUsername(user.getUsername());
        // 4.获取操作类型以及具体的操作内容（反射）
        // 4.1获取类名（全限定名）
        String tarClassname = proceedingJoinPoint.getTarget().getClass().getName();
        String tarMethodName = proceedingJoinPoint.getSignature().getName();
        // 4.2获取类对象
        Class<?> tarClass = Class.forName(tarClassname);
        // 4.3获取类中的所有方法
        Method[] methods = tarClass.getMethods();

        String operationType = "";
        String operationName = "";
        for (Method method: methods) {
            String methodName = method.getName();
            if (tarMethodName.equals(methodName)){
                // 这是虽然已近确定目标方法没有问题，但是有可能会出现方法的重载
                // 还需要进一步判断
                // 4.4 获取目标方法的参数
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == args.length){
                    // 获取目标方法
                    operationType = method.getAnnotation(LoginAnnotation.class).opeationType();
                    operationName = method.getAnnotation(LoginAnnotation.class).opeationName();

                }

            }
        }
        loginLog.setOperationName(operationName);
        loginLog.setOperationType(operationType);
        iProjectService.addLoginLog(loginLog);
        return result;
    }
}
