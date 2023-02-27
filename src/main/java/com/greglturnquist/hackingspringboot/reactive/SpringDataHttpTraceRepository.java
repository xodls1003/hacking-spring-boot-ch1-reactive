package com.greglturnquist.hackingspringboot.reactive;

import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;

import java.util.List;
import java.util.stream.Collectors;

// tag::code[]
public class SpringDataHttpTraceRepository implements HttpTraceRepository {

    private final HttpTraceWrapperRepository repository;

    public SpringDataHttpTraceRepository(HttpTraceWrapperRepository repository) {
        this.repository = repository; // <1>
    }

    @Override
    public List<HttpTrace> findAll() {
        return repository.findAll() //
                .map(HttpTraceWrapper::getHttpTrace) // <2>
                .collect(Collectors.toList());
    }

    @Override
    public void add(HttpTrace trace) {
        repository.save(new HttpTraceWrapper(trace)); // <3>
    }
}
// end::code[]
