package com.kavindu.commercehub.Cart.Service;

import com.kavindu.commercehub.Authentication.models.AppUser;
import com.kavindu.commercehub.Cart.models.CartItem;
import com.kavindu.commercehub.Cart.repositories.CartRepository;
import com.kavindu.commercehub.Product.Repositories.ProductRepository;
import com.kavindu.commercehub.Product.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public CartItem addToCart(Long customerId, Long productId, int quantity) {
        Optional<CartItem> existingItem = cartRepository.findByCustomerIdAndProductId(customerId, productId);
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            return cartRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setUser(new AppUser(customerId));
            newItem.setProduct(new Product(productId));
            newItem.setQuantity(quantity);
            return cartRepository.save(newItem);
        }

    }

    public void removeItem(Long itemId) {
        cartRepository.deleteById(itemId);
    }

    public void clearCart(Long customerId) {
        List<CartItem> items = cartRepository.findByCustomerId(customerId);
        cartRepository.deleteAll(items);
    }
}
