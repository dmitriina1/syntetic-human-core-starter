package ru.starter.synthetichumancorestarter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.starter.synthetichumancorestarter.config.StarterProperties;
import ru.starter.synthetichumancorestarter.exception.CommandQueueFullException;
import ru.starter.synthetichumancorestarter.model.Command;
import ru.starter.synthetichumancorestarter.model.CommandPriority;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class CommandExecutorService {

    private final BlockingQueue<Runnable> taskQueue;
    private final ThreadPoolExecutor executor;
    private final MetricsService metricsService;

    @Autowired
    public CommandExecutorService(StarterProperties properties, MetricsService metricsService) {
        this.taskQueue = new LinkedBlockingQueue<>(properties.queueCapacity());
        this.executor = new ThreadPoolExecutor(
                1, 1, 30, TimeUnit.SECONDS, taskQueue
        );
        this.metricsService = metricsService;
    }

    public void submit(Command command) {
        if (command.getPriority() == CommandPriority.CRITICAL) {
            executeNow(command);
        } else {
            if (taskQueue.offer(() -> executeNow(command))) {
                metricsService.incrementQueueSize();
            } else {
                throw new CommandQueueFullException("Command queue is full");
            }
        }
    }

    @Async
    public void executeNow(Command command) {
        System.out.printf("[EXECUTING] %s (Author: %s, Time: %s)\n",
                command.getDescription(), command.getAuthor(), command.getTime());
        metricsService.incrementCompletedForAuthor(command.getAuthor());
        metricsService.decrementQueueSize();
    }
}
