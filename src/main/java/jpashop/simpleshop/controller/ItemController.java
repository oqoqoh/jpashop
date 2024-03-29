package jpashop.simpleshop.controller;

import jpashop.simpleshop.domain.item.Book;
import jpashop.simpleshop.domain.item.Item;
import jpashop.simpleshop.domain.item.UpdateItemDto;
import jpashop.simpleshop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createItem(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {

        Book book = Book.createBook(form.getName(), form.getPrice(), form.getStockQuantity(), form.getAuthor(), form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);

        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {

        if (itemService.findItem(itemId) instanceof Book) {
            Book book = (Book) itemService.findItem(itemId);
            BookForm form = new BookForm();
            form.setId(book.getId());
            form.setName(book.getName());
            form.setPrice(book.getPrice());
            form.setStockQuantity(book.getStockQuantity());
            form.setAuthor(book.getAuthor());
            form.setIsbn(book.getIsbn());

            model.addAttribute("form", form);

        }
        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String updateItemForm(@PathVariable String itemId, BookForm form) {
        //유저가 itemId에 접근 권한있는지 확인 필요

        //controll에서 직접 Entity 호출 -> DTO나 필요 변수만
        itemService.updateItem(new UpdateItemDto(form));

        return "redirect:/items";
    }
}
