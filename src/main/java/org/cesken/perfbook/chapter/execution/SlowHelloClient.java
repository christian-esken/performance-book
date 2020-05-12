package org.cesken.perfbook.chapter.execution;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The SlowHelloClient shows the performance effects of ExecutorService implmentations.
 */
public class SlowHelloClient {
    final static int INVOCATIONS = 10;
    final static int THREAD_COUNT = 3;
    final static int RESPONSE_DELAY_MS = 1000;
    final static String BASEURL = "http://localhost:8080/hello/user";
    final static ExecutorServiceType executorServiceType = ExecutorServiceType.SINGLE;

    enum ExecutorServiceType { SINGLE, POOL, SCHEDULED};

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = selectExecutorService(executorServiceType);
        log("Start");
        for (int i = 0; i < INVOCATIONS; i++) {
            final int iFinal = i;
            executorService.submit(() -> httpRequest(iFinal));
        }

        log("Submitted ... awaitTermination");
        executorService.shutdown();
        boolean done = executorService.awaitTermination(10, TimeUnit.SECONDS);
        log("Finished: " + (done ? "COMPLETED" : "CANCELLED"));
        executorService.shutdownNow();
    }

    private static ExecutorService selectExecutorService(ExecutorServiceType executorServiceType) {
        switch (executorServiceType) {
            case POOL:
                return Executors.newFixedThreadPool(THREAD_COUNT);
            case SINGLE:
                return Executors.newSingleThreadExecutor();
            case SCHEDULED:
                return Executors.newSingleThreadScheduledExecutor();
            default:
                throw new IllegalArgumentException("Unknown executor type:" + executorServiceType);
        }
    }

    /**
     * Executest a HTTP request, and returns the Repsonse.
     * @param iFinal counter, used to construct unioque URL's
     * @return THe hello service response
     */
    private static String httpRequest(int iFinal) {
        try {
            String url = BASEURL + iFinal + "?delay=" + RESPONSE_DELAY_MS;
            log(url);
            HttpResponse.BodyHandler<String> responseBodyHandler = HttpResponse.BodyHandlers.ofString();
            HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).header("Accept", "text/plain").build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, responseBodyHandler);
            String body = response.body();
            return body;
        } catch (Exception exc) {
            return null;
        }
    }


    /**
     * Logs the message to STDOUT
     * @param message message
     */
    private static void log(String message) {
        System.out.println(LocalTime.now() + ": " + message);
    }
}