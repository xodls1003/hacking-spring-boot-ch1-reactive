package com.greglturnquist.hackingspringboot.reactive;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
public class HomeController {

    private ItemRepository itemRepository;
    private CartRepository cartRepository;
    private CartService cartService;
    private InventoryService inventoryService;


    public HomeController (ItemRepository itemRepository, CartRepository cartRepository){
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    @GetMapping
    Mono<Rendering> home(){

        return Mono.just(Rendering.view("home.html")
                .modelAttribute("items", //
                        this.itemRepository.findAll().doOnNext(System.out::println))
                .modelAttribute("cart", //
                        this.cartRepository.findById("My Cart")
                                .defaultIfEmpty(new Cart("My Cart")))
                .build());
    }

//    @PostMapping("/add/{id}")
//    Mono<String> addToCart(@PathVariable String id){
//        return this.cartService.addToCart("My Cart", id)
//                .thenReturn("redirect:/");
//    }
    @PostMapping("/add/{id}")
    // <1>
    Mono<String> addToCart(@PathVariable String id) { // <2>
        return this.cartRepository.findById("My Cart") // <3>
            .defaultIfEmpty(new Cart("My Cart")) //
            .flatMap(cart -> cart.getCartItems().stream() // <4>
                    .filter(cartItem -> cartItem.getItem() //
                            .getId().equals(id)) //
                    .findAny() //
                    .map(cartItem -> {
                        cartItem.increment();
                        return Mono.just(cart);
                    }).orElseGet(() -> { // <5>
                        return this.itemRepository.findById(id) //
                                .map(item -> new CartItem(item)) //
                                .map(cartItem -> {
                                    cart.getCartItems().add(cartItem);
                                    return cart;
                                });
                    }))
            .flatMap(cart -> this.cartRepository.save(cart)) // <6>
            .thenReturn("redirect:/"); // <7>
    }
        // end::3[]
    
    @GetMapping("/search")
    Mono<Rendering>search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam boolean useAnd){
        return Mono.just(Rendering.view("home.html")
                .modelAttribute("items", 
                        inventoryService.searchByExample(name,description,useAnd))
                .modelAttribute("cart", 
                        this.cartRepository.findById("My Cart")
                                .defaultIfEmpty(new Cart("My Cart")))
                .build());
    }
    

}
