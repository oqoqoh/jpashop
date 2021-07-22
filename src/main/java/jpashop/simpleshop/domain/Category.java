package jpashop.simpleshop.domain;

import jpashop.simpleshop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
        joinColumns =  @JoinColumn(name = "category_id"),
        inverseJoinColumns = @JoinColumn(name = "item_id")) //관계형DB에서는 중간에서 풀어주는 테이블 필요, 필드추가 불가->지양
    private List<Item> items = new ArrayList<>();

}