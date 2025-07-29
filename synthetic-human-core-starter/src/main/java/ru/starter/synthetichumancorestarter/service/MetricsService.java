package ru.starter.synthetichumancorestarter.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MetricsService {

    private final MeterRegistry registry;
    private final AtomicInteger queueSize = new AtomicInteger(0);
    private final ConcurrentHashMap<String, Counter> authorCounters = new ConcurrentHashMap<>();

    public MetricsService(MeterRegistry registry) {
        this.registry = registry;

        Gauge.builder("android.queue.size", queueSize, AtomicInteger::get)
                .description("Current number of tasks in the queue")
                .register(registry);

        Gauge.builder("android.queue.capacity", this, s -> 100.0 * queueSize.get() / 100)
                .description("Queue utilization percentage")
                .register(registry);
    }

    public void incrementQueueSize() {
        queueSize.incrementAndGet();
    }

    public void decrementQueueSize() {
        queueSize.decrementAndGet();
    }

    public void incrementCompletedForAuthor(String author) {
        authorCounters.computeIfAbsent(author, a ->
                Counter.builder("android.commands.completed")
                        .tag("author", a)
                        .description("Number of completed commands by author")
                        .register(registry)
        ).increment();
    }
}
