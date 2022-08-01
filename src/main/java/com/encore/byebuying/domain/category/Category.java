package com.encore.byebuying.domain.category;

import com.encore.byebuying.domain.common.BaseTimeEntity;
import com.encore.byebuying.domain.item.Item;
import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "categories")
@Entity
public class Category extends BaseTimeEntity {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    // 같은 이름의 카테고리는 이상하니깐
    @Column(unique = true)
    private String name;

    // 자기 자신을 참조한다.
    // depth = 상위 id - 자기 id - 하위카테고리가 자신을 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    // 셀프 조인 방식으로 변경
    @Builder.Default
    @OneToMany(mappedBy = "parentCategory")
    private List<Category> childCategories = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "category")
    private List<Item> items = new ArrayList<>();

    public static Category createCategory(String name, Category parentCategory) {
        Category category = Category.builder()
                .name(name)
                .parentCategory(parentCategory)
                .build();

        if (parentCategory != null) {
            parentCategory.getChildCategories().add(category);
        }

        return category;
    }
}