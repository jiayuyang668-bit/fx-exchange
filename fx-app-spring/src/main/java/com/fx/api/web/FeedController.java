package com.fx.api.web;

import com.fx.api.model.IncomingBatch;
import com.fx.api.service.FeedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

    private static final Logger log = LoggerFactory.getLogger(FeedController.class);
    private final FeedService feed;

    public FeedController(FeedService feed) { this.feed = feed; }

    @PostMapping("/rates")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void receive(@RequestBody IncomingBatch batch) {
        log.info("POST /api/feed/rates received batch #{}", batch.batchId());
        feed.handle(batch);
    }
}
