package org.delivery.db.store.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreCategory {

    CHINESE("중식", "중국 요리 전문점"),
    WESTERN("양식", "서양 요리 전문점"),
    KOREAN("한식", "한국 요리 전문점"),
    JAPANESE("일식", "일본 요리 전문점"),
    CHICKEN("치킨", "치킨 전문점"),
    PIZZA("피자", "피자 전문점"),
    BURGER("햄버거", "햄버거 전문점"),
    COFFEE("커피", "커피 전문점");

    ;
    private String display;
    private String description;
}
