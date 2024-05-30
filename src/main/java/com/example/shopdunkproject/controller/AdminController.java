package com.example.shopdunkproject.controller;

import com.example.shopdunkproject.model.*;
import com.example.shopdunkproject.repository.ICategoryRepository;
import com.example.shopdunkproject.repository.IProductDetailRepository;
import com.example.shopdunkproject.repository.IProductRepository;
import com.example.shopdunkproject.service.ICategoryService;
import com.example.shopdunkproject.service.IProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import java.io.File;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;
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
    private ICategoryRepository iCategoryRepository;
    @Autowired
    private ICategoryService iCategoryService;
    @Autowired
    private IProductDetailRepository iProductDetailRepository;


    public static String UPLOAD_DIRECTORY = "/Users/lengoclinh/Desktop/DuAnShopDunk/DuAn_ShopDunk/src/main/resources/static/image/";

    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
    @GetMapping("")
    public ModelAndView showAllProducts(@PageableDefault(5) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("listProduct", iProductRepository.findAll(pageable));
        return modelAndView;
    }



    @GetMapping("/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/products");
        // Tìm sản phẩm theo ID
        Optional<Product> productOptional = iProductRepository.findById(id);
        if (productOptional.isPresent()) {
            // Nếu sản phẩm tồn tại, xóa sản phẩm
            iProductRepository.deleteById(id);
            // Bạn có thể thêm thông báo hoặc trạng thái nếu cần
            modelAndView.addObject("message", "Sản phẩm đã được xóa thành công.");
        } else {
            // Xử lý nếu sản phẩm không tồn tại (nếu cần)
            modelAndView.addObject("error", "Sản phẩm không tồn tại.");
        }
        // Trả về ModelAndView để chuyển hướng về trang danh sách sản phẩm
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showProducts(Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("categories", iCategoryRepository.findAll(pageable));
        return modelAndView;
    }

    @PostMapping("/save")
    public ModelAndView createProduct(@RequestParam(value = "name" , required = false) String name,
                                      @RequestParam(value = "quantity", required = false) int quantity,
                                      @RequestParam(value = "price", required = false) double price,
                                      @RequestParam(value = "file", required = false) MultipartFile imageFile,
                                      @RequestParam(value = "description", required = false) String description,
                                      @RequestParam(value = "discount",required = false) int discount,
                                      @RequestParam(value = "category",required = false) long categoryId,
                                      @RequestParam(value = "operatingSystem",required = false) String operatingSystem,
                                      @RequestParam(value = "specialFeatures",required = false) String specialFeatures,
                                      @RequestParam(value = "systemRequirements",required = false) String systemRequirements,
                                      @RequestParam(value = "color",required = false) String color,
                                      @RequestParam(value = "storage",required = false) String storage,
                                      @RequestParam(value = "dimensionsAndWeight",required = false) String dimensionsAndWeight,
                                      @RequestParam(value = "screen",required = false) String screen,
                                      @RequestParam(value = "screenResolution",required = false) String screenResolution,
                                      @RequestParam(value = "waterAndDustResistance",required = false) String waterAndDustResistance,
                                      @RequestParam(value = "chip",required = false) String chip,
                                      @RequestParam(value = "rearCamera",required = false) String rearCamera,
                                      @RequestParam(value = "frontCamera",required = false) String frontCamera,
                                      @RequestParam(value = "videoRecording",required = false) String videoRecording,
                                      @RequestParam(value = "security",required = false) String security,
                                      @RequestParam(value = "networkConnectivity",required = false) String networkConnectivity,
                                      @RequestParam(value = "audioTechnology",required = false) String audioTechnology,
                                      @RequestParam(value = "videoViewing",required = false) String videoViewing,
                                      @RequestParam(value = "charging",required = false) String charging,
                                      @RequestParam(value = "batteryAndPower",required = false) String batteryAndPower,
                                      @RequestParam(value = "sensors",required = false) String sensors,
                                      @RequestParam(value = "sim", required = false) String sim
    )
            throws IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:/products");


        // Find category by ID
        Optional<Category> categoryOptional = iCategoryRepository.findById(categoryId);
        if (!categoryOptional.isPresent()) {
            modelAndView.addObject("error", "Category not found.");
            return modelAndView;
        }
        // Create and save ProductDetail
        ProductDetail productDTO = new ProductDetail();
        productDTO.setOperatingSystem(operatingSystem);
        productDTO.setSpecialFeatures(specialFeatures);
        productDTO.setSystemRequirements(systemRequirements);
        productDTO.setColor(color);
        productDTO.setStorage(storage);
        productDTO.setDimensionsAndWeight(dimensionsAndWeight);
        productDTO.setScreen(screen);
        productDTO.setScreenResolution(screenResolution);
        productDTO.setWaterAndDustResistance(waterAndDustResistance);
        productDTO.setChip(chip);
        productDTO.setRearCamera(rearCamera);
        productDTO.setFrontCamera(frontCamera);
        productDTO.setVideoRecording(videoRecording);
        productDTO.setSecurity(security);
        productDTO.setNetworkConnectivity(networkConnectivity);
        productDTO.setAudioTechnology(audioTechnology);
        productDTO.setVideoViewing(videoViewing);
        productDTO.setCharging(charging);
        productDTO.setBatteryAndPower(batteryAndPower);
        productDTO.setSensors(sensors);
        productDTO.setSim(sim);
        iProductDetailRepository.save(productDTO);
        // Create and save Product
        Product product = new Product();
        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);
        product.setDescription(description);
        product.setDiscount(discount);
        product.setCategory(categoryOptional.get());
        product.setProductDetail(productDTO);
        product.setImage(uploadImage(imageFile));
        iProductRepository.save(product);
        modelAndView.addObject("message", "Product created successfully.");
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
        modelAndView.addObject("product",iProductRepository.findById(id).get());
        modelAndView.addObject("productDetail", iProductDetailRepository.findById(id).get());
        return modelAndView;
    }





    @PostMapping("/update/{id}")
    public ModelAndView updateProduct(@PathVariable("id") long id,
                                      @RequestParam(value = "name", required = false) String name,
                                      @RequestParam(value = "quantity", required = false) int quantity,
                                      @RequestParam(value = "price", required = false) double price,
                                      @RequestParam(value = "file", required = false) MultipartFile imageFile,
                                      @RequestParam(value = "description", required = false) String description,
                                      @RequestParam(value = "discount", required = false) int discount,
                                      @RequestParam(value = "category", required = false) long categoryId,
                                      @RequestParam(value = "operatingSystem", required = false) String operatingSystem,
                                      @RequestParam(value = "specialFeatures", required = false) String specialFeatures,
                                      @RequestParam(value = "systemRequirements", required = false) String systemRequirements,
                                      @RequestParam(value = "color", required = false) String color,
                                      @RequestParam(value = "storage", required = false) String storage,
                                      @RequestParam(value = "dimensionsAndWeight", required = false) String dimensionsAndWeight,
                                      @RequestParam(value = "screen", required = false) String screen,
                                      @RequestParam(value = "screenResolution", required = false) String screenResolution,
                                      @RequestParam(value = "waterAndDustResistance", required = false) String waterAndDustResistance,
                                      @RequestParam(value = "chip", required = false) String chip,
                                      @RequestParam(value = "rearCamera", required = false) String rearCamera,
                                      @RequestParam(value = "frontCamera", required = false) String frontCamera,
                                      @RequestParam(value = "videoRecording", required = false) String videoRecording,
                                      @RequestParam(value = "security", required = false) String security,
                                      @RequestParam(value = "networkConnectivity", required = false) String networkConnectivity,
                                      @RequestParam(value = "audioTechnology", required = false) String audioTechnology,
                                      @RequestParam(value = "videoViewing", required = false) String videoViewing,
                                      @RequestParam(value = "charging", required = false) String charging,
                                      @RequestParam(value = "batteryAndPower", required = false) String batteryAndPower,
                                      @RequestParam(value = "sensors", required = false) String sensors,
                                      @RequestParam(value = "sim", required = false) String sim) throws IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:/products");

        Optional<Product> productOptional = iProductRepository.findById(id);
        if (!productOptional.isPresent()) {
            modelAndView.addObject("error", "Product not found.");
            return modelAndView;
        }
        Product product = productOptional.get();
        Optional<Category> categoryOptional = iCategoryRepository.findById(categoryId);
        if (!categoryOptional.isPresent()) {
            modelAndView.addObject("error", "Category not found.");
            return modelAndView;
        }
        ProductDetail productDetail = product.getProductDetail();
        productDetail.setOperatingSystem(operatingSystem);
        productDetail.setSpecialFeatures(specialFeatures);
        productDetail.setSystemRequirements(systemRequirements);
        productDetail.setColor(color);
        productDetail.setStorage(storage);
        productDetail.setDimensionsAndWeight(dimensionsAndWeight);
        productDetail.setScreen(screen);
        productDetail.setScreenResolution(screenResolution);
        productDetail.setWaterAndDustResistance(waterAndDustResistance);
        productDetail.setChip(chip);
        productDetail.setRearCamera(rearCamera);
        productDetail.setFrontCamera(frontCamera);
        productDetail.setVideoRecording(videoRecording);
        productDetail.setSecurity(security);
        productDetail.setNetworkConnectivity(networkConnectivity);
        productDetail.setAudioTechnology(audioTechnology);
        productDetail.setVideoViewing(videoViewing);
        productDetail.setCharging(charging);
        productDetail.setBatteryAndPower(batteryAndPower);
        productDetail.setSensors(sensors);
        productDetail.setSim(sim);
        iProductDetailRepository.save(productDetail);
        product.setName(name);
        product.setQuantity(quantity);
        product.setPrice(price);
        product.setDescription(description);
        product.setDiscount(discount);
        product.setCategory(categoryOptional.get());
        if (!imageFile.isEmpty()) {
            product.setImage(uploadImage(imageFile));
        }
        iProductRepository.save(product);
        modelAndView.addObject("message", "Product updated successfully.");
        return modelAndView;
    }


    @GetMapping("/update/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView("update");
        Optional<Product> productOptional = iProductRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            modelAndView.addObject("product", product);
            modelAndView.addObject("categories", iCategoryRepository.findAll());
        } else {
            modelAndView.setViewName("redirect:/products");
            modelAndView.addObject("error", "Product not found.");
        }
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView searchProducts(@RequestParam(value = "name", required = false) String name,
                                       @RequestParam(value = "price", required = false) Double price,
                                       @PageableDefault(3) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("index");
        Page<Product> resultPage;
        if (name != null && !name.isEmpty() && price != null) {
            String[] keywords = name.split("\\s+"); // Tách các từ khóa trong chuỗi tìm kiếm
            List<Product> resultList = new ArrayList<>();
            for (String keyword : keywords) {
                resultList.addAll(iProductRepository.findByNameContainingIgnoreCaseAndPriceLessThanEqual(keyword, price, pageable).getContent());
            }
            resultPage = new PageImpl<>(resultList, pageable, resultList.size());
        } else if (name != null && !name.isEmpty()) {
            String[] keywords = name.split("\\s+"); // Tách các từ khóa trong chuỗi tìm kiếm
            List<Product> resultList = new ArrayList<>();
            for (String keyword : keywords) {
                resultList.addAll(iProductRepository.findByNameContainingIgnoreCase(keyword, pageable).getContent());
            }
            resultPage = new PageImpl<>(resultList, pageable, resultList.size());
        } else if (price != null) {
            resultPage = iProductRepository.findByPriceLessThanEqual(price, pageable);
        } else {
            resultPage = iProductRepository.findAll(pageable);
        }

        if (resultPage.isEmpty()) {
            // Nếu không tìm thấy kết quả, hiển thị thông báo và giữ lại trang hiện tại
            modelAndView.addObject("message", "Không tìm thấy kết quả.");
            resultPage = iProductRepository.findAll(pageable); // Giữ lại trang hiện tại
        }
        modelAndView.addObject("listProduct", resultPage);
        return modelAndView;
    }


}