package com.github.mkopylec.charon.forwarding.interceptors.rewrite;

import com.github.mkopylec.charon.forwarding.interceptors.HttpRequest;
import com.github.mkopylec.charon.forwarding.interceptors.HttpRequestExecution;
import com.github.mkopylec.charon.forwarding.interceptors.HttpResponse;
import com.github.mkopylec.charon.forwarding.interceptors.RequestForwardingInterceptor;
import org.slf4j.Logger;
import reactor.core.publisher.Mono;

import static org.slf4j.LoggerFactory.getLogger;

class RequestProxyHeadersRewriter extends BasicRequestProxyHeadersRewriter implements RequestForwardingInterceptor {

    private static final Logger log = getLogger(RequestProxyHeadersRewriter.class);

    RequestProxyHeadersRewriter() {
        super(log);
    }

    @Override
    public Mono<HttpResponse> forward(HttpRequest request, HttpRequestExecution execution) {
        logStart(execution.getMappingName());
        rewriteHeaders(request.headers(), request.url());
        return execution.execute(request)
                .doOnSuccess(response -> logEnd(execution.getMappingName()));
    }
}