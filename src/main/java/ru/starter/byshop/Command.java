package ru.starter.byshop;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Instant;

@Data
public class Command {
    @NotBlank
    @Size(max = 1000)
    private String description;

    private String priority; // "COMMON" или "CRITICAL"

    @NotBlank
    @Size(max = 100)
    private String author;

    private Instant time;
}