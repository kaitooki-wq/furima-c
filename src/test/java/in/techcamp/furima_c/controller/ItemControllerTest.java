package in.techcamp.furima_c.controller;

import in.techcamp.furima_c.dto.ItemEditForm;
import in.techcamp.furima_c.service.ItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemService itemService;

    @Test
    @DisplayName("編集画面の表示が成功すること")
    void editFormTest() throws Exception {
        // given
        ItemEditForm form = ItemEditForm.builder().id(1L).name("テスト").build();
        given(itemService.getItemForEdit(anyLong())).willReturn(form);

        // when & then
        mockMvc.perform(get("/items/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("items/edit"))
                .andExpect(model().attributeExists("itemForm"));
    }

    @Test
    @DisplayName("タイトルが空の場合、編集画面に戻ること")
    void updateItem_validationFail() throws Exception {
        // when & then (タイトルが空の場合)
        mockMvc.perform(post("/items/1/edit")
                        .param("name", "")
                        .param("description", "설명")
                        .param("price", "10000"))
                .andExpect(status().isOk())
                .andExpect(view().name("items/edit"))
                .andExpect(model().hasErrors());
    }

    @Test
    @DisplayName("編集処理が成功すること")
    void updateItem_success() throws Exception {
        // when & then
        mockMvc.perform(post("/items/1/edit")
                        .param("name", "new name")
                        .param("description", "new description")
                        .param("categoryId", "1")
                        .param("price", "15000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/items/1"));
    }
}