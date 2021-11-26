package com.gregIturnquist.hackingspringboot.reactive;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static reactor.core.publisher.Flux.generate;

@Service
public class KitchenService {

    Flux<Dish> getDishes(){
        return Flux.<Dish> generate (sink-> sink.next(randomDish()))
                .delayElements(Duration.ofMillis(250));
    }

    private Dish randomDish(){
        return menu.get(picker.nextInt(menu.size()));
    }
    private List<Dish> menu = Arrays.asList(
            new Dish("sesame chicken"),
            new Dish("lo mein noodles, plain"),
            new Dish("sweet & sour beef")
    );
    private Random picker = new Random();
}
