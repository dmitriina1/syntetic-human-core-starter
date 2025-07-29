package ru.starter.synthetichumancorestarter.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Instant;

@Data
public class Command {
    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description must be up to 1000 characters")
    private String description;

    private CommandPriority priority;

    @NotBlank(message = "Author is required")
    @Size(max = 100, message = "Author must be up to 100 characters")
    private String author;

    private Instant time;
}
