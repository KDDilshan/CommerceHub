package com.kavindu.commercehub.Cart.Service;

import com.kavindu.commercehub.Cart.models.CartItem;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisCartService {

    private static final String CART_PREFIX = "cart:";
    private final RedisTemplate<String,Object> redisTemplate;

    public RedisCartService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveCart(int userID, List<CartItem> items){
        redisTemplate.opsForValue().set(CART_PREFIX + userID, items, 30, TimeUnit.MINUTES);
    }   

    public List<CartItem> getCart(int userId) {
        return (List<CartItem>) redisTemplate.opsForValue().get(CART_PREFIX + userId);
    }

    public void clearCart(int userId) {
        redisTemplate.delete(CART_PREFIX + userId);
    }
}
