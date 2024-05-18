package com.example.shopdunkproject.controller;

import com.example.shopdunkproject.model.*;
import com.example.shopdunkproject.repository.IAttributeRepository;
import com.example.shopdunkproject.repository.ICategoryRepository;
import com.example.shopdunkproject.repository.IProductAttributeRepository;
import com.example.shopdunkproject.repository.IProductRepository;
import com.example.shopdunkproject.service.IAttributeService;
import com.example.shopdunkproject.service.ICategoryService;
import com.example.shopdunkproject.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/products")
public class AdminController {
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IProductRepository iProductRepository;

    @Autowired
    private IProductAttributeRepository iProductAttributeRepository;
    @Autowired
    private ICategoryRepository iCategoryRepository;
    @Autowired
    private ICategoryService iCategoryService;
    @Autowired
    private IAttributeRepository iAttributeRepository;
    @Autowired
    private IAttributeService iAttributeService;
    @Autowired
    private IProductAttributeRepository productAttributeRepository;


    public static String UPLOAD_DIRECTORY = "/Users/lengoclinh/Desktop/DuAnShopDunk/DuAn_ShopDunk/src/main/resources/static/image/";

    @GetMapping("")
    public ModelAndView showAllProduct(@PageableDefault(2) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("listValue",iProductAttributeRepository.findAll());
        modelAndView.addObject("listProduct", iProductRepository.findAll(pageable));
        return modelAndView;
    }

//    @GetMapping("/delete/{id}")
//    public ModelAndView showDeleteForm(@PathVariable long id) {
//        ModelAndView modelAndView = new ModelAndView("delete");
//        return modelAndView;
//    }
//
//    // Xử lý xóa hình ảnh
//    @PostMapping("/delete")
//    public String deleteCustomer(@ModelAttribute("picture") Product product, RedirectAttributes redirect) {
//        iProductRepository.deleteById(product.getId());
//        redirect.addFlashAttribute("message", "Delete image successfully");
//        return "redirect:/product";
//    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/products");
        // Tìm sản phẩm theo ID
        Optional<Product> productOptional = iProductRepository.findById(id);
        if (productOptional.isPresent()) {
            // Nếu sản phẩm tồn tại, xóa sản phẩm
            iProductService.delete(id);
            // Bạn có thể thêm thông báo hoặc trạng thái nếu cần
            modelAndView.addObject("message", "Sản phẩm đã được xóa thành công.");
        } else {
            // Xử lý nếu sản phẩm không tồn tại (nếu cần)
            modelAndView.addObject("error", "Sản phẩm không tồn tại.");
        }
        // Trả về ModelAndView để chuyển hướng về trang danh sách sản phẩm
        return modelAndView;
    }
//    @PostMapping("/delete/{id}")
//    public ModelAndView deleteProduct(@PathVariable long id){
//        ModelAndView modelAndView = new ModelAndView("redirect:/products");
//        iProductService.delete(id);
//        return modelAndView;
//    }
    @GetMapping("/addAttributes")
    public String addProductAttributes() {
        productAttributeRepository.save(new ProductAttribute());
        return "redirect:/products";
    }

    @GetMapping("/create")
    public ModelAndView showProducts(Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("categories", iCategoryService.findAll(pageable));
        modelAndView.addObject("attributes", iAttributeService.findAll(pageable));
        return modelAndView;
    }
    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
    @PostMapping("/save")
    public ModelAndView createProduct(@RequestParam("file") MultipartFile file,@ModelAttribute("product") Product productDto,@RequestParam Map<String, String> productAttributes) throws IOException {
        Product product = iProductService.save(productDto);
        ModelAndView modelAndView = new ModelAndView("redirect:/products");
        productDto.setImage(uploadImage(file));
        iProductRepository.save(productDto);
        productAttributes.forEach((id, value) -> {
            if(!value.isEmpty() && isNumeric(id)) {
                ProductAttribute productAttribute = new ProductAttribute();
                productAttribute.setValue(value);
                productAttribute.setProduct(product);
                Attribute attribute = iAttributeRepository.findById(Long.parseLong(id)).get();
                productAttribute.setAttribute(attribute);
                ProductAttributeKey productAttributeKey = new ProductAttributeKey(product.getId(), attribute.getId());
                productAttribute.setId(productAttributeKey);
                iProductAttributeRepository.save(productAttribute);
            }
        });
        return modelAndView;
    }

    private String uploadImage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        File uploadDir = new File(UPLOAD_DIRECTORY);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        FileCopyUtils.copy(file.getBytes(), new File(uploadDir.getAbsolutePath() + "//" + fileName));
        return fileName;
    }
    @GetMapping("/view/{id}")
    public ModelAndView showInfo(@PathVariable long id){
        ModelAndView modelAndView = new ModelAndView("view1");
        modelAndView.addObject("productDelete",iProductService.findById(id).get());
        return modelAndView;
    }

}
