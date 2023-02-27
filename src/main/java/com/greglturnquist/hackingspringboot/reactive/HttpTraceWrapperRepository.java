package com.greglturnquist.hackingspringboot.reactive;

import org.springframework.data.repository.Repository;

import java.util.stream.Stream;


// tag::code[]
public interface HttpTraceWrapperRepository extends //
        Repository<HttpTraceWrapper, String> {

    Stream<HttpTraceWrapper> findAll(); // <1>

    void save(HttpTraceWrapper trace); // <2>
}
// end::code[]
