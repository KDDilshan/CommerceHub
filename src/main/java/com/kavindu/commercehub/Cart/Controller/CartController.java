package com.kavindu.commercehub.Cart.Controller;

import com.kavindu.commercehub.Cart.Service.CartService;
import com.kavindu.commercehub.Cart.dtos.CartItemDTO;
import com.kavindu.commercehub.Cart.dtos.DisplayCartDto;
import com.kavindu.commercehub.Cart.models.CartItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<List<DisplayCartDto>> getCart(@PathVariable UUID customerId) {
        List<DisplayCartDto> item = cartService.getCartItems(customerId);
        return ResponseEntity.ok(item);
    }

//    @PostMapping("/add")
//    public ResponseEntity<CartItem> addToCart(@RequestParam Long customerId, @RequestParam Long productId, @RequestParam int quantity) {
//        CartItem item = cartService.addToCart(customerId, productId, quantity);
//        return ResponseEntity.ok(item);
//    }


//    @DeleteMapping("/remove/{itemId}")
//    public ResponseEntity<Void> removeFromCart(@PathVariable Long itemId) {
//        cartService.removeItem(itemId);
//        return ResponseEntity.noContent().build(); // 204 No Content
//    }

//    @DeleteMapping("/clear/{customerId}")
//    public ResponseEntity<Void> clearCart(@PathVariable Long customerId) {
//        cartService.clearCart(customerId);
//        return ResponseEntity.noContent().build(); // 204 No Content
//    }

}
