package ru.starter.synthetichumancorestarter.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.starter.synthetichumancorestarter.config.StarterProperties;

@Aspect
@Component
public class AuditAspect {

    private static final Logger log = LoggerFactory.getLogger(AuditAspect.class);

    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private StarterProperties properties;

    @Around("@annotation(ru.starter.synthetichumancorestarter.annotation.WeylandWatchingYou)")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        String auditMessage = String.format("Method: %s, Args: %s", methodName, java.util.Arrays.toString(args));

        if ("kafka".equals(properties.auditMode()) && kafkaTemplate != null) {
            kafkaTemplate.send(properties.kafkaTopic(), auditMessage);
        } else {
            log.info("[AUDIT] {}", auditMessage);
        }

        Object result = joinPoint.proceed();
        log.info("[AUDIT RESULT] Method: {}, Result: {}", methodName, result);
        return result;
    }
}
