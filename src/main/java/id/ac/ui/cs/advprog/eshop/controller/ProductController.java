package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductReaderService;
import id.ac.ui.cs.advprog.eshop.service.ProductWriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    private ProductWriterService serviceWrite;
    private ProductReaderService serviceRead;

    @Autowired
    private ProductController(ProductWriterService serviceWrite, ProductReaderService serviceRead){
        this.serviceRead = serviceRead;
        this.serviceWrite = serviceWrite;
    }

    @GetMapping("/create")
    public String createProductPage(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "CreateProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model){
        serviceWrite.create(product);
        return "redirect:list";
    }

    @GetMapping("/list")
    public String productListPage(Model model){
        List<Product> allProducts = serviceRead.findAll();
        model.addAttribute("products", allProducts);
        return "ProductList";
    }

    @GetMapping("/edit/{productId}")
    public String editProductPage(@PathVariable("productId") String id, Model model) {
        Product product = serviceRead.findById(id);

        if (product == null) {
            return "redirect:../list";
        }

        model.addAttribute("product", product);
        return "EditProduct";
    }

    @PostMapping("/edit")
    public String editProductPost(@ModelAttribute Product product) {
        serviceWrite.update(product);
        return "redirect:list";
    }

    @GetMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable("productId") String productId) {
        serviceWrite.deleteProductById(productId);
        return "redirect:../list";
    }
}

