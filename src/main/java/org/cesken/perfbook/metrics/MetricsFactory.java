package org.cesken.perfbook.metrics;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micrometer.jmx.JmxConfig;
import io.micrometer.jmx.JmxMeterRegistry;

public class MetricsFactory {
    public enum PublisherType { NONE, JMX }

    public static MeterRegistry create(PublisherType publisherType) {
        switch (publisherType) {
            case NONE: return new SimpleMeterRegistry();
            case JMX:  return new JmxMeterRegistry(new JmxConfig() {
                @Override
                public String get(String s) {  return null; }
            }, Clock.SYSTEM);
            default: throw new IllegalArgumentException("Unsupported PublisherType: " + publisherType);
        }
    }
}
