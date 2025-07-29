package ru.starter.byshop;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.starter.synthetichumancorestarter.annotation.WeylandWatchingYou;
import ru.starter.synthetichumancorestarter.model.Command;
import ru.starter.synthetichumancorestarter.model.CommandPriority;
import ru.starter.synthetichumancorestarter.service.CommandExecutorService;

@RestController
@RequiredArgsConstructor
public class AndroidController {

    private final CommandExecutorService executor;

    @PostMapping("/command")
    @WeylandWatchingYou
    public String receiveCommand(@RequestBody @Valid Command command) {
        var cmd = new ru.starter.synthetichumancorestarter.model.Command();
        cmd.setDescription(command.getDescription());
        cmd.setAuthor(command.getAuthor());
        cmd.setTime(command.getTime());
        cmd.setPriority("CRITICAL".equals(command.getPriority())
                ? CommandPriority.CRITICAL : CommandPriority.COMMON);

        executor.submit(cmd);
        return "Command accepted";
    }
}
