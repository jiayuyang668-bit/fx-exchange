package com.fx.api.service;

import com.fx.api.model.IncomingBatch;
import com.fx.api.model.IncomingRate;
import com.fx.api.repo.RateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FeedService {

    private static final Logger log = LoggerFactory.getLogger(FeedService.class);

    private final RateRepository rates;
    private final AcceptingState acceptingState;
    private final OrchestratorClient orchestratorClient;

    public FeedService(RateRepository rates, AcceptingState acceptingState, OrchestratorClient orchestratorClient) {
        this.rates = rates;
        this.acceptingState = acceptingState;
        this.orchestratorClient = orchestratorClient;
    }

    public void handle(IncomingBatch batch) {
        log.info("Received batch #{} with {} rates", batch.batchId(), batch.rates().size());
        int stored = 0;
        if (acceptingState.isAccepting()) {
            for (IncomingRate tick : batch.rates()) {
                rates.insert(tick);
                stored++;
            }
        }
        log.info("Stored {} rates for batch #{}", stored, batch.batchId());
        orchestratorClient.ack(batch.batchId(), acceptingState.isAccepting() ? "ACCEPTED" : "DECLINED");
    }
}
