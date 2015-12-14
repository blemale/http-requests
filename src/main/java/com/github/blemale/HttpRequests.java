package com.github.blemale;

import org.apache.http.client.fluent.Request;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.Objects.requireNonNull;

/**
 * A factory class for {@link Request} which enforced sensible {@link Timeouts request timeouts}.
 * <br/>
 * Exemple:
 * <pre>
 * {@code
 * Request request = HttpRequests.Get("www.lesfurets.com", Timeouts.NORMAL);
 * Response response = HttpRequests.executeWithLenientSSL(request);
 * String body = response.returnContent().asString();
 * }
 * </pre>
 */
public class HttpRequests {

    /**
     * Sensible defaults values for http requests timeouts (<a href="http://dev.bizo.com/2013/04/sensible-defaults-for-apache-httpclient.html">source</a>).
     */
    public enum Timeouts {
        /**
         * Timeouts for slow http requests: <br/>
         * <ul>
         *     <li>{@code connectionTimeout}: 1 minutes</li>
         *     <li>{@code socketTimeout}: 5 minutes</li>
         * </ul>
         */
        SLOW(Duration.of(1, MINUTES), Duration.of(5, MINUTES)),
        /**
         * Timeouts for normal http requests: <br/>
         * <ul>
         *     <li>{@code connectionTimeout}: 10 seconds</li>
         *     <li>{@code socketTimeout}: 10 seconds</li>
         * </ul>
         */
        NORMAL(Duration.of(10, SECONDS), Duration.of(10, SECONDS)),
        /**
         * Timeouts for fast http requests: <br/>
         * <ul>
         *     <li>{@code connectionTimeout}: 1 seconds</li>
         *     <li>{@code socketTimeout}: 1 seconds</li>
         * </ul>
         */
        FAST(Duration.of(1, SECONDS), Duration.of(1, SECONDS));

        private final Duration connectionTimeout;
        private final Duration socketTimeout;

        Timeouts(Duration connectionTimeout, Duration socketTimeout) {
            this.connectionTimeout = connectionTimeout;
            this.socketTimeout = socketTimeout;
        }

        public int connectionTimeout() {
            return (int) connectionTimeout.toMillis();
        }

        public int socketTimeout() {
            return (int) socketTimeout.toMillis();
        }
    }

    /**
     * Create a {@code GET} {@link Request} with the given {@link String}s URI and the given {@link Timeouts}
     *
     * @throws NullPointerException if any of the parameters is null
     */
    public static Request Get(String uri, Timeouts timeouts) {
        requireNonNull(uri);
        requireNonNull(timeouts);

        return addTimeouts(Request.Get(uri), timeouts);
    }

    /**
     * Create a {@code POST} {@link Request} with the given {@link String}s URI and the given {@link Timeouts}
     *
     * @throws NullPointerException if any of the parameters is null
     */
    public static Request Post(String uri, Timeouts timeouts) {
        requireNonNull(uri);
        requireNonNull(timeouts);

        return addTimeouts(Request.Post(uri), timeouts);
    }

    /**
     * Create a {@code PUT} {@link Request} with the given {@link String}s URI and the given {@link Timeouts}
     *
     * @throws NullPointerException if any of the parameters is null
     */
    public static Request Put(String uri, Timeouts timeouts) {
        requireNonNull(uri);
        requireNonNull(timeouts);

        return addTimeouts(Request.Put(uri), timeouts);
    }

    /**
     * Create a {@code DELETE} {@link Request} with the given {@link String}s URI and the given {@link Timeouts}
     *
     * @throws NullPointerException if any of the parameters is null
     */
    public static Request Delete(String uri, Timeouts timeouts) {
        requireNonNull(uri);
        requireNonNull(timeouts);

        return addTimeouts(Request.Delete(uri), timeouts);
    }

    private static Request addTimeouts(Request request, Timeouts timeouts) {
        return request.connectTimeout(timeouts.connectionTimeout()).socketTimeout(timeouts.socketTimeout());
    }

}
