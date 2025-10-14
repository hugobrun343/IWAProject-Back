package com.iwaproject.gateway.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Rate limiting filter to prevent abuse.
 * Limits requests per IP address.
 */
@Component
public class RateLimitFilter implements HandlerInterceptor {

    /**
     * Maximum requests per minute per IP.
     */
    private static final int MAX_REQUESTS_PER_MINUTE = 100;

    /**
     * HTTP status code for too many requests.
     */
    private static final int HTTP_TOO_MANY_REQUESTS = 429;

    /**
     * Milliseconds in one minute.
     */
    private static final long ONE_MINUTE_MS = 60000L;

    /**
     * Request counter per IP address.
     */
    private final Map<String, RequestCounter> requestCounts =
            new ConcurrentHashMap<>();

    /**
     * Pre-handle method to check rate limit.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @param handler the handler
     * @return true if request is allowed, false if rate limit exceeded
     * @throws Exception if an error occurs
     */
    @Override
    public boolean preHandle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Object handler) throws Exception {

        String clientIp = getClientIp(request);

        // Clean up old entries
        cleanupOldEntries();

        // Get or create counter for this IP
        RequestCounter counter = requestCounts.computeIfAbsent(
                clientIp,
                k -> new RequestCounter()
        );

        // Check if rate limit exceeded
        if (counter.increment() > MAX_REQUESTS_PER_MINUTE) {
            response.setStatus(HTTP_TOO_MANY_REQUESTS);
            response.getWriter().write(
                    "{\"error\":\"Rate limit exceeded. "
                    + "Try again later.\"}"
            );
            return false;
        }

        return true;
    }

    /**
     * Get client IP address.
     *
     * @param request the HTTP request
     * @return client IP address
     */
    private String getClientIp(final HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    /**
     * Clean up old request counters.
     */
    private void cleanupOldEntries() {
        long now = System.currentTimeMillis();
        requestCounts.entrySet().removeIf(
                entry -> now - entry.getValue().getTimestamp()
                        > ONE_MINUTE_MS
        );
    }

    /**
     * Request counter for rate limiting.
     */
    private static class RequestCounter {
        /**
         * Request count.
         */
        private final AtomicInteger count = new AtomicInteger(0);

        /**
         * Timestamp of first request in current window.
         */
        private final long timestamp = System.currentTimeMillis();

        /**
         * Increment request count.
         *
         * @return current count
         */
        public int increment() {
            return count.incrementAndGet();
        }

        /**
         * Get timestamp.
         *
         * @return timestamp
         */
        public long getTimestamp() {
            return timestamp;
        }
    }
}

