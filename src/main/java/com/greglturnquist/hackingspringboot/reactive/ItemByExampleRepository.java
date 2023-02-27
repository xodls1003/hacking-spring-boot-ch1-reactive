package com.greglturnquist.hackingspringboot.reactive;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

// tag::code[]
public interface ItemByExampleRepository extends ReactiveQueryByExampleExecutor<Item> {

}
// end::code[]
