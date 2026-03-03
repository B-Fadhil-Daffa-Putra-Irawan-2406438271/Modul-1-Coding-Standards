package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.CarWriterService;
import id.ac.ui.cs.advprog.eshop.service.ProductReaderService;
import id.ac.ui.cs.advprog.eshop.service.ProductWriterService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    private static final String ATTR_PRODUCT = "product";
    private static final String ATTR_PRODUCTS = "products";
    private static final String PARAM_ID = "id";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_QTY = "quantity";

    private static final String TEST_ID = "P001";
    private static final String NAME_KEYBOARD = "Keyboard";
    private static final String NAME_MOUSE = "Mouse";
    private static final String QTY_10 = "10";
    private static final String QTY_5 = "5";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductReaderService serviceRead;

    @MockBean
    private ProductWriterService serviceWriter;

    @MockBean
    private CarWriterService carService;

    @Test
    void getCreatePage_shouldReturnCreateProductView_andPutEmptyProductInModel() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateProduct"))
                .andExpect(model().attributeExists(ATTR_PRODUCT))
                .andExpect(model().attribute(ATTR_PRODUCT, org.hamcrest.Matchers.instanceOf(Product.class)));

        verifyNoInteractions(serviceWriter);
    }

    @Test
    void postCreate_shouldCallServiceCreate_andRedirectToList() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param(PARAM_ID, TEST_ID)
                        .param(PARAM_NAME, NAME_KEYBOARD)
                        .param(PARAM_QTY, QTY_10))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:list"));

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(serviceWriter, times(1)).create(captor.capture());
        Product sent = captor.getValue();
        assertThat(sent).isNotNull();
        verifyNoMoreInteractions(serviceWriter);
    }

    @Test
    void getList_shouldReturnProductListView_andPutProductsInModel() throws Exception {
        Product p1 = new Product();
        Product p2 = new Product();
        List<Product> products = Arrays.asList(p1, p2);

        when(serviceRead.findAll()).thenReturn(products);

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ProductList"))
                .andExpect(model().attributeExists(ATTR_PRODUCTS))
                .andExpect(model().attribute(ATTR_PRODUCTS, products));

        verify(serviceRead, times(1)).findAll();
        verifyNoMoreInteractions(serviceRead);
    }

    @Test
    void getEdit_whenProductNotFound_shouldRedirectToList() throws Exception {
        when(serviceRead.findById("NOPE")).thenReturn(null);

        mockMvc.perform(get("/product/edit/NOPE"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:../list"));

        verify(serviceRead, times(1)).findById("NOPE");
        verifyNoMoreInteractions(serviceRead);
    }

    @Test
    void getEdit_whenProductFound_shouldReturnEditProductView_andPutProductInModel() throws Exception {
        Product found = new Product();
        when(serviceRead.findById(TEST_ID)).thenReturn(found);

        mockMvc.perform(get("/product/edit/" + TEST_ID))
                .andExpect(status().isOk())
                .andExpect(view().name("EditProduct"))
                .andExpect(model().attributeExists(ATTR_PRODUCT))
                .andExpect(model().attribute(ATTR_PRODUCT, found));

        verify(serviceRead, times(1)).findById(TEST_ID);
        verifyNoMoreInteractions(serviceRead);
    }

    @Test
    void postEdit_shouldCallServiceEdit_andRedirectToList() throws Exception {
        mockMvc.perform(post("/product/edit")
                        .param(PARAM_ID, TEST_ID)
                        .param(PARAM_NAME, NAME_MOUSE)
                        .param(PARAM_QTY, QTY_5))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:list"));

        verify(serviceWriter, times(1)).update(any(Product.class));
        verifyNoMoreInteractions(serviceWriter);
    }

    @Test
    void getDelete_shouldCallServiceDelete_andRedirectToList() throws Exception {
        mockMvc.perform(get("/product/delete/" + TEST_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:../list"));

        verify(serviceWriter, times(1)).deleteProductById(TEST_ID);
        verifyNoMoreInteractions(serviceWriter);
    }
}
