package com.example.theme_market.repository;


import com.example.theme_market.dto.ItemSearchDto;
import com.example.theme_market.dto.MainItemDto;
import com.example.theme_market.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    //상품 데이터를 조회하는 메소드
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    //메인 페이지에 보여줄 상품 리스트를 가져오는 메소드
    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

}

