package com.encore.byebuying.domain.category;

import com.encore.byebuying.domain.common.BaseTimeEntity;
import com.encore.byebuying.domain.item.Item;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "categories",
indexes = {
        @Index(name = "_name", columnList = "name")
})
@Entity
public class Category extends BaseTimeEntity {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    
    // 같은 이름의 카테고리는 이상하니깐
    @Column(unique = true, nullable = false, length = 191)
    private String name;

    // 자기 자신을 참조한다.
    // depth = 상위 id - 자기 id - 하위카테고리가 자신을 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id", referencedColumnName = "category_id")
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

    public void deleteCategory() {
        // 부모에서 자신 삭제
        parentCategory.getChildCategories().remove(this);
        // 자신의 하위 카테고리들 상위 카테고리로 이전
        childCategories.forEach(child -> {
            parentCategory.addCategory(child);
            child.setParentCategory(parentCategory);
        });
        // 자기를 가진 아이템들 상위 카테고리로 변경
        items.forEach(item -> item.setCategory(parentCategory));
        // 상위 카테고리 관계 삭제
        parentCategory = null;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    private void addCategory(Category category) {
        childCategories.add(category);
    }

}