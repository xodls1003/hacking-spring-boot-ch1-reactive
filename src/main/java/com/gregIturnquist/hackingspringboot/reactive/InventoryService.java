package com.gregIturnquist.hackingspringboot.reactive;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class InventoryService {
    private CartRepository cartRepository;
    private ItemRepository itemRepository;

    public CartRepository getCartRepository() {
        return cartRepository;
    }

    public ItemRepository getItemRepository() {
        return itemRepository;
    }

    public InventoryService(ItemRepository itemRepository, CartRepository cartRepository) {
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
    }
    public Mono<Cart> getCart(String cartId) {
        return this.cartRepository.findById(cartId);
    }

    public Flux<Item> getInventory() {
        return this.itemRepository.findAll();
    }

    Mono<Item> saveItem(Item newItem) {
        return this.itemRepository.save(newItem);
    }

    Mono<Void> deleteItem(String id) {
        return this.itemRepository.deleteById(id);
    }
    public Mono<Cart> addItemToCart(String cartId, String itemId) {
        return this.cartRepository.findById(cartId)
                .log("foundCart")
                .defaultIfEmpty(new Cart(cartId))
                .log("emptyCart")
                .flatMap(cart -> cart.getCartItems().stream()
                        .filter(cartItem -> cartItem.getItem()
                                .getId().equals(itemId))
                        .findAny()
                        .map(cartItem -> {
                            cartItem.increment();
                            return Mono.just(cart).log("newCartItem");
                        })
                        .orElseGet(() -> {
                            return this.itemRepository.findById(itemId)
                                    .log("fetchedItem")
                                    .map(item -> new CartItem(item))
                                    .log("cartItem")
                                    .map(cartItem -> {
                                        cart.getCartItems().add(cartItem);
                                        return cart;
                                    }).log("addedCartItem");
                        }))
                .log("cartWithAnotherItem")
                .flatMap(cart -> this.cartRepository.save(cart))
                .log("savedCart");
    }
}
