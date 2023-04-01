package ru.tinkoff.edu.java.bot.bot.command;


import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.bot.meta.Command;
import ru.tinkoff.edu.java.bot.bot.MessageSender;
import ru.tinkoff.edu.java.bot.bot.meta.State;


@Component
@RequiredArgsConstructor
public class ListCommandHandler implements CommandHandler<SendMessage, SendResponse> {
    private final MessageSender messageSender;

    @Override
    public Command command() {
        return Command.LIST;
    }

    @Override
    public State state() {
        return State.NONE;
    }

    @Override
    public SendMessage handle(Update update) {
        return messageSender.send(update, "There are all you links below!");
    }
}
