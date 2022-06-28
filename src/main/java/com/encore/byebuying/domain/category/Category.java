package com.encore.byebuying.domain.category;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "categories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    // 같은 이름의 카테고리는 이상하니깐
    @Column(unique = true)
    private String name;

    // 자기 자신을 참조한다.
    // depth = 상위 id - 자기 id - 하위카테고리가 자신을 참조
    private Long parentId;

    @Builder(builderMethodName = "createCategory")
    public Category(String name, Long parentId) {
        this.name = name;
        this.parentId = parentId;
    }
}