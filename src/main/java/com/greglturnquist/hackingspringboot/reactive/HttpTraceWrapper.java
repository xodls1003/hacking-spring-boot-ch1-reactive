package com.greglturnquist.hackingspringboot.reactive;

import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.data.annotation.Id;

// tag::code[]
public class HttpTraceWrapper {

    private @Id String id; // <1>

    private HttpTrace httpTrace; // <2>

    public HttpTraceWrapper(HttpTrace httpTrace) { // <3>
        this.httpTrace = httpTrace;
    }

    public HttpTrace getHttpTrace() { // <4>
        return httpTrace;
    }
}
// end::code[]
