package org.cesken.perfbook.chapter.sli;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.cesken.perfbook.metrics.MetricsFactory;
import org.cesken.perfbook.metrics.MetricsFactory.PublisherType;
import org.cesken.perfbook.model.AccountInfoResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class AccountInfoApplication {
    Counter accountinfoFailCounter;
    Counter accountinfoRequestCounter;

    @PostConstruct
    void constrcut() {
        MeterRegistry meterRegistry = MetricsFactory.create(PublisherType.JMX);
        accountinfoRequestCounter = meterRegistry.counter("accountinfo.requests");
        accountinfoFailCounter = meterRegistry.counter("accountinfo.failed");
    }

    @GetMapping(value = "/accountinfo/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountInfoResponse analyze(@PathVariable long accountId) {
        accountinfoRequestCounter.increment();
        try {
            return new AccountInfoResponse(accountId, 1000);
        } catch (Exception exc) {
            accountinfoFailCounter.increment();
            throw exc;
        }
    }
}
