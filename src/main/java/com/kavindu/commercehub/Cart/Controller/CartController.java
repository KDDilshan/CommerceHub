package com.kavindu.commercehub.Cart.Controller;

import com.kavindu.commercehub.Cart.Service.CartService;
import com.kavindu.commercehub.Cart.dtos.CartItemDTO;
import com.kavindu.commercehub.Cart.dtos.CartSummeryDto;
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
    public ResponseEntity<CartSummeryDto> getCart(@PathVariable int customerId) {
        CartSummeryDto item = cartService.getCartItems(customerId);
        return ResponseEntity.ok(item);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartItemDTO cartItemDTO) throws Exception {
        String  cartMessage=cartService.addToCart(cartItemDTO);
        return ResponseEntity.ok(cartMessage);
    }


    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable int itemId) {
        cartService.removeItem(itemId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @DeleteMapping("/clear/{customerId}")
    public ResponseEntity<Void> clearCart(@PathVariable int customerId) {
        cartService.clearCart(customerId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}
