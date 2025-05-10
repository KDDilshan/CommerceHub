package com.kavindu.commercehub.Cart.Service;

import com.kavindu.commercehub.Authentication.models.AppUser;
import com.kavindu.commercehub.Authentication.repositories.UserRepository;
import com.kavindu.commercehub.Cart.dtos.CartItemDTO;
import com.kavindu.commercehub.Cart.dtos.CartSummeryDto;
import com.kavindu.commercehub.Cart.dtos.DisplayCartDto;
import com.kavindu.commercehub.Cart.models.CartItem;
import com.kavindu.commercehub.Cart.repositories.CartRepository;
import com.kavindu.commercehub.Exceptions.ProductNotFoundException;
import com.kavindu.commercehub.Exceptions.ProductNotValidException;
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
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public CartSummeryDto getCartItems(UUID customerId) {
        List<CartItem> cartItems = cartRepository.findByCustomerId(customerId);

        if (cartItems.isEmpty()) {
            throw new ProductNotFoundException("No products in the cart for " + customerId);
        }

        List<DisplayCartDto> displayCart = cartItems.stream()
                .map(cart -> {
                    double unitPrice = cart.getProduct().getPrice();
                    int quantity = cart.getQuantity();
                    double subtotal = unitPrice * quantity;

                    return new DisplayCartDto(
                            cart.getId(),
                            cart.getProduct().getImageName(),
                            cart.getProduct().getDescription(),
                            unitPrice,
                            quantity,
                            subtotal
                    );
                })
                .collect(Collectors.toList());

        double grandTotal = displayCart.stream()
                .mapToDouble(DisplayCartDto::getTotal_price)
                .sum();

        return new CartSummeryDto(displayCart, grandTotal);
    }


    public String addToCart(CartItemDTO cartItemDTO) throws Exception {

        //get product
        Product product=productRepository.findById(cartItemDTO.getProductId())
                .orElseThrow(()->new Exception("product not found"));

        //get customer
        AppUser user=userRepository.findById(cartItemDTO.getCustomerId())
                .orElseThrow(()->new Exception("customer not found"));


        if(cartItemDTO.getQuantity()>product.getQuantity()){
            throw new Exception("maximum quantity is "+product.getQuantity());
        }

        CartItem cartItem=new CartItem();
        cartItem.setCustomer(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemDTO.getQuantity());

        cartRepository.save(cartItem);

        return "cart Items are saved successfully";

    }

    public void removeItem(int itemId) {
        if (!cartRepository.existsById(itemId)) {
            throw new RuntimeException("Cart item not found");
        }
        cartRepository.deleteById(itemId);
    }

    public void clearCart(UUID customerId) {
        List<CartItem> items = cartRepository.findByCustomerId(customerId);
        cartRepository.deleteAll(items);
    }


}
