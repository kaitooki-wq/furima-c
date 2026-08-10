package in.techcamp.furima_c.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import in.techcamp.furima_c.dto.ItemEditForm;
import in.techcamp.furima_c.service.ItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

//　テスト時、Flywayの自動実行を無効化するために、@SpringBootTestにproperties属性を追加
@SpringBootTest(properties = "spring.flyway.enabled=false")
@AutoConfigureMockMvc(addFilters = false) // Spring Securityのフィルターを無効化
class ItemControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ItemService itemService;

  @Test
  @DisplayName("GET /items/{itemId}/edit - 編集画面が正常に表示される")
  void showEditForm_Success() throws Exception {
    ItemEditForm form = ItemEditForm.builder()
        .id(1L)
        .name("テスト商品名")
        .description("テスト商品説明")
        .price(1000)
        .build();

    when(itemService.getItemForEdit(anyLong())).thenReturn(form);

    mockMvc.perform(MockMvcRequestBuilders.get("/items/1/edit"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("items/edit"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("itemForm"));
  }

  @Test
  @DisplayName("POST /items/{itemId}/edit - 未入力項目がある場合、バリデーションエラーになり編集画面に戻る")
  void updateItem_ValidationError_ReturnsEditView() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/items/1/edit")
            .param("name", "")
            .param("description", "")
            .param("price", "100"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("items/edit"))
        .andExpect(MockMvcResultMatchers.model().hasErrors());
  }

  @Test
  @DisplayName("POST /items/{itemId}/edit - 正常的にデータ送信時、詳細画面へリダイレクトされる")
  void updateItem_Success_RedirectsToDetail() throws Exception {
    doNothing().when(itemService).updateItem(anyLong(), any());

    mockMvc.perform(MockMvcRequestBuilders.post("/items/1/edit")
            .param("name", "修正された商品名")
            .param("description", "修正された商品説明")
            .param("categoryId", "1")
            .param("price", "15000")
            .param("condition", "1")
            .param("shippingPayer", "1")
            .param("prefectureId", "13")
            .param("shippingDays", "1")
            .param("status", "0"))
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.redirectedUrl("/items/1"));
  }
}