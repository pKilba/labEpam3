package com.epam.esm.link;

import com.epam.esm.controller.TagController;
import com.epam.esm.model.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagLinkProvider extends AbstractLinkProvider<Tag> {

    private static final Class<TagController> CONTROLLER_CLASS = TagController.class;

    public void provideLinks(Tag tag) {
        long id = tag.getId();
        if (tag.getLinks().hasSize(0)) {
            provideIdLinks(CONTROLLER_CLASS, tag, id, SELF_LINK, DELETE_LINK);
        }
    }

}
