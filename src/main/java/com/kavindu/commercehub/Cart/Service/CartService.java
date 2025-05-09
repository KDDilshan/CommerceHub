package com.kavindu.commercehub.Cart.Service;

import com.kavindu.commercehub.Authentication.models.AppUser;
import com.kavindu.commercehub.Cart.dtos.CartItemDTO;
import com.kavindu.commercehub.Cart.dtos.DisplayCartDto;
import com.kavindu.commercehub.Cart.models.CartItem;
import com.kavindu.commercehub.Cart.repositories.CartRepository;
import com.kavindu.commercehub.Exceptions.ProductNotFoundException;
import com.kavindu.commercehub.Product.Repositories.ProductRepository;
import com.kavindu.commercehub.Product.models.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public List<DisplayCartDto> getCartItems(UUID customerId){
        List<CartItem> cartItem=cartRepository.findByCustomerId(customerId);
        if(cartItem.isEmpty()){
            throw new ProductNotFoundException("no Products in the cart for "+customerId);
        }
        List<DisplayCartDto> displayCart=cartItem
                .stream()
                .map(cart->new DisplayCartDto(
                    cart.getId(),
                    cart.getProduct().getImageName(),
                    cart.getProduct().getDescription(),
                    cart.getProduct().getPrice(),
                    cart.getQuantity()
                ))
                .collect(Collectors.toList());

        return displayCart;
    }

    public String addToCart(CartItemDTO cartItemDTO){
        0
    }

//    public void removeItem(Long itemId) {
//        cartRepository.deleteById(itemId);
//    }
//
////    public void clearCart(Long customerId) {
////        List<CartItem> items = cartRepository.findByCustomerId(customerId);
////        cartRepository.deleteAll(items);
////    }
//

}
