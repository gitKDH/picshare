package com.example.theme_market.service;



import com.example.theme_market.dto.CartDetailDto;
import com.example.theme_market.dto.CartItemDto;
import com.example.theme_market.dto.CartOrderDto;
import com.example.theme_market.dto.OrderDto;
import com.example.theme_market.entity.Cart;
import com.example.theme_market.entity.CartItem;
import com.example.theme_market.entity.Item;
import com.example.theme_market.entity.Member;
import com.example.theme_market.repository.CartItemRepository;
import com.example.theme_market.repository.CartRepository;
import com.example.theme_market.repository.ItemRepository;
import com.example.theme_market.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    private final OrderService orderService;

    //장바구니 담기
    public Long addCart(CartItemDto cartItemDto, String username) {
        Item item = itemRepository.findById(cartItemDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);

        Cart cart = cartRepository.findByMemberId(member.getId());
        if (cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());
        if (savedCartItem != null) {
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getId();
        } else {
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }

    //장바구니 상품 조회
    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(String username) {

        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
        Cart cart = cartRepository.findByMemberId(member.getId());
        if (cart == null) {
            return cartDetailDtoList;
        }

        cartDetailDtoList = cartItemRepository.findCartDetailDtoList(cart.getId());

        return cartDetailDtoList;
    }

    //현재 로그인한 회원과, 장바구니 상품을 저장한 회원이 같은지 검사
    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String username) {
        Member curMember = memberRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = cartItem.getCart().getMember();

        if (!StringUtils.equals(curMember.getUsername(), savedMember.getUsername())) {
            return false;
        }

        return true;
    }

    //상품의 수량을 업데이트
    public void updateCartItemCount(Long cartItemId, int count) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        cartItem.updateCount(count);
    }

    //장바구니 상품 삭제
    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }

    //장바구니 상품 주문
    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String username) {
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);
            OrderDto orderDto = new OrderDto();
            orderDto.setItemId(cartItem.getItem().getId());
            orderDto.setCount(cartItem.getCount());
            orderDtoList.add(orderDto);
        }

        Long orderId = orderService.orders(orderDtoList, username);

        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new);
            cartItemRepository.delete(cartItem);
        }

        return orderId;
    }

}

