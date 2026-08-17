package in.techcamp.furima_c.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import in.techcamp.furima_c.dto.ItemEditDto;
import in.techcamp.furima_c.service.ItemService;
import in.techcamp.furima_c.security.CustomUserDetails;
import in.techcamp.furima_c.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(properties = "spring.flyway.enabled=false")
@AutoConfigureMockMvc(addFilters = false)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    private CustomUserDetails customUserDetails;

    //モックユーザ設定
    @BeforeEach
    void setUp() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        this.customUserDetails = new CustomUserDetails(userEntity);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    @DisplayName("GET /items/{itemId}/edit - 編集画面が正常に表示される")
    void showEditForm_Success() throws Exception {
        ItemEditDto dto = ItemEditDto.builder()
                .id(1L)
                .userId(1L) // 権限検証
                .name("テスト商品名")
                .description("テスト商品説明")
                .price(1000)
                .categoryId(1L)
                .condition(1)
                .shippingPayer(1)
                .prefectureId(13)
                .shippingDays(1)
                .status(0)
                .build();

        when(itemService.getItemForEdit(anyLong())).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/items/1/edit")
                .with(user(customUserDetails))) // モックユーザを設定
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("items/edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("itemEditDto"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("categories"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("conditions"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("deliveryfees"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("prefectures"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("untildelivery"));
    }

    @Test
    @DisplayName("POST /items/{itemId}/edit - 未入力項目がある場合、バリデーションエラーになり編集画面に戻る")
    void updateItem_ValidationError_ReturnsEditView() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/items/1/edit")
                        .with(user(customUserDetails))
                        .param("name", "")
                        .param("description", "")
                        .param("price", "100"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("items/edit"))
                .andExpect(MockMvcResultMatchers.model().hasErrors())
                .andExpect(MockMvcResultMatchers.model().attributeExists("categories"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("conditions"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("deliveryfees"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("prefectures"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("untildelivery"));
    }

    @Test
    @DisplayName("POST /items/{itemId}/edit - 正常的にデータ送信時、詳細画面へリダイレクトされる")
    void updateItem_Success_RedirectsToDetail() throws Exception {
        doNothing().when(itemService).updateItem(anyLong(), any(ItemEditDto.class), anyLong());

        mockMvc.perform(MockMvcRequestBuilders.post("/items/1/edit")
                        .with(user(customUserDetails))
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