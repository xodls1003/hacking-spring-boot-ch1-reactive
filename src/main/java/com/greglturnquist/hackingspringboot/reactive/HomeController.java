package com.greglturnquist.hackingspringboot.reactive;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

// tag::1[]
@Controller
public class HomeController {

    private InventoryService inventoryService;

    public HomeController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
    // end::1[]

    // tag::2[]
    @GetMapping
    Mono<Rendering> home(Authentication auth) { // <1>
        return Mono.just(Rendering.view("home.html") // <2>
                .modelAttribute("items", this.inventoryService.getInventory()) // <3>
                .modelAttribute("cart", this.inventoryService.getCart(cartName(auth)) // <4>
                        .defaultIfEmpty(new Cart(cartName(auth))))
                .modelAttribute("auth", auth)
                .build());
    }
    // end::2[]

    @PostMapping("/add/{id}")
    Mono<String> addToCart(Authentication auth, @PathVariable String id) {
        return this.inventoryService.addItemToCart(cartName(auth), id)
                .thenReturn("redirect:/");
    }

    @DeleteMapping("/remove/{id}")
    Mono<String> removeFromCart(Authentication auth,  @PathVariable String id) {
        return this.inventoryService.removeOneFromCart(cartName(auth), id)
                .thenReturn("redirect:/");
    }

    @PostMapping
    Mono<String> createItem(@ModelAttribute Item newItem) {
        return this.inventoryService.saveItem(newItem) //
                .thenReturn("redirect:/");
    }

    @DeleteMapping("/delete/{id}")
    Mono<String> deleteItem(@PathVariable String id) {
        return this.inventoryService.deleteItem(id) //
                .thenReturn("redirect:/");
    }
    private static String cartName(Authentication auth) {
        return auth.getName() + "'s Cart";
    }
}
