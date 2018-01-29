package com.wearewaes.event.listener;

import com.wearewaes.event.ResourceCreatedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Component
public class ResourceCreatedListener implements ApplicationListener<ResourceCreatedEvent> {

    @Override
    public void onApplicationEvent(ResourceCreatedEvent resourceCreatedEvent) {
        HttpServletResponse response = resourceCreatedEvent.getResponse();
        Long code = resourceCreatedEvent.getCode();
        adicionarHeaderLocation(response, code);
    }

    private void adicionarHeaderLocation(HttpServletResponse response, Long code) {
        URI uri =ServletUriComponentsBuilder.fromCurrentContextPath().path("/diff/{id}").buildAndExpand(code).toUri();
        response.setHeader("Location", uri.toASCIIString());
    }

}
