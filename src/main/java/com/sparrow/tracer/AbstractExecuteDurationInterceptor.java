package com.sparrow.tracer;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;


public abstract class AbstractExecuteDurationInterceptor {
    protected abstract TracerAccessor getTracerAccessor(Object arg);

    private TracerAccessor getTracerAccessor(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            if (arg instanceof TracerAccessor) {
                return (TracerAccessor) arg;
            }
            TracerAccessor tracerAccessor = this.getTracerAccessor(arg);
            if (tracerAccessor != null) {
                return tracerAccessor;
            }
        }
        return null;
    }

    protected abstract void alarm(Logger logger, Throwable e, TracerAccessor tracerAccessor);

    protected abstract void alarm(Logger logger, TracerAccessor tracerAccessor, String format, Object... args);

    @Around("executeDuration()")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        TracerAccessor tracerAccessor = this.getTracerAccessor(pjp);
        Tracer tracer;
        Class<?> classTarget = pjp.getTarget().getClass();
        Logger logger = LoggerFactory.getLogger(classTarget);
        Span span = null;
        if (tracerAccessor != null) {
            tracer = tracerAccessor.getTracer();
            if (tracer != null) {
                String methodName = pjp.getSignature().getName();
                Class<?>[] par = ((MethodSignature) pjp.getSignature()).getParameterTypes();
                Method targetMethod = classTarget.getMethod(methodName, par);
                ExecuteDuration executeDuration = targetMethod.getAnnotation(ExecuteDuration.class);
                span = tracer.spanBuilder().asChild().name(executeDuration.spanName()).start();
            }
        }
        Object result;
        try {
            result = pjp.proceed();
            return result;
        } catch (Throwable e) {
            this.alarm(logger, e, tracerAccessor);
            throw e;
        } finally {
            if (span != null) {
                span.finish();
                long configTimeout = tracerAccessor.getAlarmTimeout();
                if (configTimeout > 0 && span.duration() > configTimeout) {
                    this.alarm(logger, tracerAccessor, "%s time out %sms", span.getName(), span.duration() + "");
                }
            }
        }
    }
}