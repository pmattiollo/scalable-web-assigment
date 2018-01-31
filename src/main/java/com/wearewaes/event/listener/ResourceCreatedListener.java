package com.wearewaes.event.listener;

import com.wearewaes.event.ResourceCreatedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

/**
 * Listener that is responsible for receive and treat {@link ResourceCreatedEvent}
 */
@Component
public class ResourceCreatedListener implements ApplicationListener<ResourceCreatedEvent> {

    @Override
    public void onApplicationEvent(ResourceCreatedEvent resourceCreatedEvent) {
        HttpServletResponse response = resourceCreatedEvent.getResponse();
        Long code = resourceCreatedEvent.getCode();
        adicionarHeaderLocation(response, code);
    }

    /**
     * Adds an location to the {@link HttpServletResponse} that can be used for user as next steps
     *
     * @param response that represents the
     * @param code that represents the uploaded data identifier
     */
    private void adicionarHeaderLocation(HttpServletResponse response, Long code) {
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/diff/{id}").buildAndExpand(code).toUri();
        response.setHeader("Location", uri.toASCIIString());
    }

}
