package ml.miliorini.studies.api;

import ml.miliorini.studies.core.entities.Proxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class RequestHttp {

    private static Logger logger = LoggerFactory.getLogger(RequestHttp.class);

    public HttpResponse<String> get(String url, @Nullable Proxy proxy, @Nullable Integer timeout) {
        HttpResponse<String> response = null;
        HttpClient.Builder clientBuilder = HttpClient.newBuilder();
        HttpRequest.Builder requestBuilder;
        try {
            if (proxy != null) {
                clientBuilder.proxy(ProxySelector.of(new InetSocketAddress(proxy.getIp(), proxy.getPort())));
            }
            requestBuilder = HttpRequest.newBuilder().GET().uri(new URI(url));
            if (timeout != null) {
                requestBuilder.timeout(Duration.ofSeconds(timeout));
            }
            response = clientBuilder.build().send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());
        } catch (Exception exception) {
            logger.error("Error doing request: {}", exception.getMessage());
        }
        return response;
    }
}
