package ru.tinkoff.edu.java.bot.command;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.edu.java.bot.CommandsHandler;
import ru.tinkoff.edu.java.bot.MessageHandler;
import ru.tinkoff.edu.java.bot.UpdatesHandler;
import ru.tinkoff.edu.java.bot.dto.HandledUpdate;
import ru.tinkoff.edu.java.bot.dto.response.LinkResponse;
import ru.tinkoff.edu.java.bot.meta.State;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UpdatesHandlerTest {

    UpdatesHandler updatesHandler;

    @Mock
    TelegramBot bot;

    @Mock
    CommandsHandler commandsHandler;

    MessageHandler messageHandler;

    @Captor
    ArgumentCaptor<SendMessage> captor;

    @BeforeEach
    void setup() {
        updatesHandler = new UpdatesHandler(bot, commandsHandler, messageHandler);
    }

    @SneakyThrows
    Update getUpdate(Long chatId) {
        Chat chat = new Chat();
        Field id = chat.getClass().getDeclaredField("id");
        id.setAccessible(true);
        id.set(chat, chatId);

        Message message = new Message();
        Field chatField = message.getClass().getDeclaredField("chat");
        chatField.setAccessible(true);
        chatField.set(message, chat);

        Update update = new Update();
        Field messageField = update.getClass().getDeclaredField("message");
        messageField.setAccessible(true);
        messageField.set(update, message);

        return update;
    }

    @Test
    void handle_executeUnknownCommandSendMessageRequest() {
        // given

        long chatId = 123;
        var emptyHandledUpdate = HandledUpdate
                .builder()
                .request(Optional.empty())
                .newState(State.NONE)
                .build();

        Update update = getUpdate(chatId);
        when(commandsHandler.handle(update)).thenReturn(emptyHandledUpdate);

        // when
        updatesHandler.process(List.of(update));
        verify(bot).execute(captor.capture());

        // then
        var requestMessage = captor.getValue().getParameters().get("text");
        assertThat(requestMessage).isEqualTo("Unknown command. Try using /help.");
    }
}
