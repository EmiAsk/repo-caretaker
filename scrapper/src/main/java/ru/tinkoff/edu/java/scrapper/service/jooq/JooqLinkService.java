package ru.tinkoff.edu.java.scrapper.service.jooq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.exception.NotFoundException;
import ru.tinkoff.edu.java.scrapper.model.Chat;
import ru.tinkoff.edu.java.scrapper.model.Link;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqChatRepository;
import ru.tinkoff.edu.java.scrapper.repository.jooq.JooqLinkRepository;
import ru.tinkoff.edu.java.scrapper.service.LinkService;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
public class JooqLinkService implements LinkService {

    private final JooqLinkRepository linkRepository;
    private final JooqChatRepository chatRepository;

    @Transactional
    @Override
    public Link track(long tgChatId, String url) {
        Link link = linkRepository.findByUrl(url).orElseGet(() -> linkRepository.save(new Link().setUrl(url)));
        Chat chat = chatRepository.findById(tgChatId).orElseThrow(() -> new NotFoundException("Chat not found"));
        linkRepository.addToChat(chat, link);
        return link;
    }

    @Transactional
    @Override
    public Link untrack(long tgChatId, String url) {
        Link link = linkRepository.findByUrl(url).orElseThrow(() -> new NotFoundException("Link not found"));
        Chat chat = chatRepository.findById(tgChatId).orElseThrow(() -> new NotFoundException("Chat not found"));
        linkRepository.removeFromChat(chat, link);
        return link;
    }

    @Transactional
    @Override
    public List<Link> listAll(long tgChatId) {
        var chat = chatRepository.findById(tgChatId).orElseThrow(() -> new NotFoundException("Chat not found"));
        return linkRepository.findAllByChat(chat);
    }
}
