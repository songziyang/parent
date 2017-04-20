package com.ydzb.core.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/** 日志切面 */
@Aspect
public class LogAspectJ {

	// 取得日志记录器Logger
	public Logger logger = Logger.getLogger(LogAspectJ.class);

	// 此方法将用作前置通知
	@Pointcut("execution(* com.ydzb.*.service..*.*(..))")
	public void anyMethod() {
	}

	@Before(value = "anyMethod()")
	public void myBeforeAdvice(JoinPoint joinpoint) {
		String classAndMethod = joinpoint.getTarget().getClass().getName()
				+ "类的" + joinpoint.getSignature().getName();
		if (logger.isInfoEnabled())
			logger.info("  前置通知:" + classAndMethod + "方法开始执行！");
	}

	// 此方法将用作后置通知
	@AfterReturning(value = "anyMethod()")
	public void myAfterReturningAdvice(JoinPoint joinpoint) {
		String classAndMethod = joinpoint.getTarget().getClass().getName()
				+ "类的" + joinpoint.getSignature().getName();
		if (logger.isInfoEnabled())
			logger.info("  后置通知:" + classAndMethod + "方法执行正常结束！");
	}

	// 此方法将用作异常通知
	@AfterThrowing(value = "anyMethod()", throwing = "e")
	public void myAfterThrowingAdvice(JoinPoint joinpoint, Exception e) {
		String classAndMethod = joinpoint.getTarget().getClass().getName()
				+ "类的" + joinpoint.getSignature().getName();
		if (logger.isInfoEnabled())
			logger.info("  异常通知:" + classAndMethod + "方法抛出异常：" + e.getMessage());
	}

	// 此方法将用作最终通知
	@After(value = "anyMethod()")
	public void myAfterAdvice(JoinPoint joinpoint) {
		String classAndMethod = joinpoint.getTarget().getClass().getName()
				+ "类的" + joinpoint.getSignature().getName();
		if (logger.isInfoEnabled())
			logger.info("  最终通知:" + classAndMethod + "方法执行结束！");
	}

	// 此方法将用作环绕通知
	@Around(value = "anyMethod()")
	public Object myAroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		long begintime = System.nanoTime();
		// 传递给连接点对象进行接力处理
		Object result = pjp.proceed();
		long endtime = System.nanoTime();
		String classAndMethod = pjp.getTarget().getClass().getName() + "类的"
				+ pjp.getSignature().getName();
		if (logger.isInfoEnabled())
			logger.info("  环绕通知:" + classAndMethod + "方法执行结束,耗时"
					+ ((endtime - begintime) / 1000000) + "毫秒！");
		return result;
	}
}
