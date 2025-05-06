package com.kavindu.commercehub.Cart.Controller;

import com.kavindu.commercehub.Cart.Service.CartService;
import com.kavindu.commercehub.Cart.dtos.CartItemDTO;
import com.kavindu.commercehub.Cart.models.CartItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<CartItem>> getCart(@PathVariable Long customerId) {
        List<CartItem> item= (List<CartItem>) cartService.getCartItems(customerId);
        return ResponseEntity.ok(item);
    }

    @PostMapping("/add")
    public ResponseEntity<CartItem> addToCart(@RequestParam Long customerId, @RequestParam Long productId, @RequestParam int quantity) {
        CartItem item = cartService.addToCart(customerId, productId, quantity);
        return ResponseEntity.ok(item);
    }


    @DeleteMapping("/remove/{itemId}")
    public void removeFromCart(@PathVariable Long itemId) {
        cartService.removeItem(itemId);
    }

    @DeleteMapping("/clear/{customerId}")
    public void clearCart(@PathVariable Long customerId) {
        cartService.clearCart(customerId);
    }
}
