package com.example.shopdunkproject.controller;

import com.example.shopdunkproject.model.*;
import com.example.shopdunkproject.repository.ICategoryRepository;
import com.example.shopdunkproject.repository.IProductDetailRepository;
import com.example.shopdunkproject.repository.IProductRepository;
import com.example.shopdunkproject.service.ICategoryService;
import com.example.shopdunkproject.service.IProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static String UPLOAD_DIRECTORY = "C:\\Users\\namca\\Downloads_Git\\DuAn_ShopDunk\\src\\main\\resources\\static\\image\\";

    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    @GetMapping("")
    public ModelAndView showAllProducts(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("index");
        Page<Product> productPage = iProductRepository.findAll(pageable);

        DecimalFormat df = new DecimalFormat("#,###.00");

        List<Product> formattedProducts = new ArrayList<>();
        for (Product product : productPage.getContent()) {
            product.setFormattedPrice(df.format(product.getPrice()));
            formattedProducts.add(product);
        }

        Page<Product> formattedProductPage = new PageImpl<>(formattedProducts, pageable, productPage.getTotalElements());
        modelAndView.addObject("listProduct", formattedProductPage);
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
    public ModelAndView createProduct(@RequestParam(value = "name", required = false) String name,
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
        modelAndView.addObject("successMessage", "Cập nhật sản phẩm thành công.");
        modelAndView.setViewName("redirect:/products");
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
    public ModelAndView showInfo(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("view1");
        modelAndView.addObject("product", iProductRepository.findById(id).get());
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
            modelAndView.addObject("error", "Không tìm thấy sản phẩm.");
            return modelAndView;
        }

        Product product = productOptional.get();
        Optional<Category> categoryOptional = iCategoryRepository.findById(categoryId);
        if (!categoryOptional.isPresent()) {
            modelAndView.addObject("error", "Không tìm thấy danh mục.");
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
        modelAndView.addObject("successMessage", "Cập nhật sản phẩm thành công.");
        return modelAndView;
    }


    @GetMapping("/update/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView("update");
        Optional<Product> productOptional = iProductRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            modelAndView.addObject("product", product);

            modelAndView.addObject("colors", Arrays.asList("Xanh", "Đỏ", "Vàng"));
            modelAndView.addObject("operatingSystem", Arrays.asList("Windows", "macOS", "Linux"));
            modelAndView.addObject("chips", Arrays.asList("Intel", "AMD", "ARM"));
            modelAndView.addObject("storages", Arrays.asList("32G", "64G", "128G", "256G", "521G", "1T"));
            modelAndView.addObject("dimensionsAndWeights", Arrays.asList("", "150.9 x 75.7 x 8.3 mm  194g", "144 x 71.4 x 8.1 mm  188g", "158 x 77.8 x 8.1 mm  226g"));
            modelAndView.addObject("rearCameras", Arrays.asList("12 MP", "16 MP", "20 MP"));
            modelAndView.addObject("frontCameras", Arrays.asList("8 MP", "12 MP", "16 MP"));
            modelAndView.addObject("videoRecordings", Arrays.asList("1080p", "4K", "8K"));
            modelAndView.addObject("screenResolutions", Arrays.asList("720p (HD)", "1080p (Full HD)", "1440p (Quad HD, 2K)","2160p (Ultra HD, 4K)","4320p (8K Ultra HD)"));
            modelAndView.addObject("screens", Arrays.asList("LCD","LED","OLED","AMOLED","QLED","MicroLED","Plasma","E-Ink","TN","IPS","VA","Mini-LED"));
            modelAndView.addObject("categories", iCategoryRepository.findAll());
        } else {
            modelAndView.setViewName("redirect:/products");
            modelAndView.addObject("error", "Product not found.");
        }
        return modelAndView;
    }

        @GetMapping("/search")
        public ModelAndView searchProducts(
                @RequestParam(value = "name", required = false) String name,
                @RequestParam(value = "priceRange", required = false) String priceRange,
                @RequestParam(value = "sortType", required = false) String sortType,
                @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

            ModelAndView modelAndView = new ModelAndView("index");
            Page<Product> resultPage;

            // Determine sorting
            Sort sort = Sort.by("id").descending();
            if ("priceAsc".equals(sortType)) {
                sort = Sort.by("price").ascending();
            } else if ("priceDesc".equals(sortType)) {
                sort = Sort.by("price").descending();
            }

            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

            Double minPrice = null;
            Double maxPrice = null;

            // Determine price range
            if (priceRange != null && !priceRange.isEmpty()) {
                switch (priceRange) {
                    case "below5":
                        maxPrice = 5000000.0;
                        break;
                    case "5to10":
                        minPrice = 5000000.0;
                        maxPrice = 10000000.0;
                        break;
                    case "10to15":
                        minPrice = 10000000.0;
                        maxPrice = 15000000.0;
                        break;
                    case "15to20":
                        minPrice = 15000000.0;
                        maxPrice = 20000000.0;
                        break;
                    case "20to30":
                        minPrice = 20000000.0;
                        maxPrice = 30000000.0;
                        break;
                    case "above30":
                        minPrice = 30000000.0;
                        break;
                    default:
                        break;
                }
            }

            // Construct the query based on provided parameters
            if (name != null && !name.isEmpty() && minPrice != null && maxPrice != null) {
                resultPage = iProductRepository.findByNameContainingIgnoreCaseAndPriceBetween(name, minPrice, maxPrice, pageable);
            } else if (name != null && !name.isEmpty() && minPrice != null) {
                resultPage = iProductRepository.findByNameContainingIgnoreCaseAndPriceGreaterThanEqual(name, minPrice, pageable);
            } else if (name != null && !name.isEmpty() && maxPrice != null) {
                resultPage = iProductRepository.findByNameContainingIgnoreCaseAndPriceLessThanEqual(name, maxPrice, pageable);
            } else if (name != null && !name.isEmpty()) {
                resultPage = iProductRepository.findByNameContainingIgnoreCase(name, pageable);
            } else if (minPrice != null && maxPrice != null) {
                resultPage = iProductRepository.findByPriceBetween(minPrice, maxPrice, pageable);
            } else if (minPrice != null) {
                resultPage = iProductRepository.findByPriceGreaterThanEqual(minPrice, pageable);
            } else if (maxPrice != null) {
                resultPage = iProductRepository.findByPriceLessThanEqual(maxPrice, pageable);
            } else {
                resultPage = iProductRepository.findAll(pageable);
            }

            // Check if results are empty and return a different view if so
            if (resultPage.isEmpty()) {
                ModelAndView noResultsModelAndView = new ModelAndView("no-results");
                noResultsModelAndView.addObject("message", "Không tìm thấy kết quả.");
                return noResultsModelAndView;
            }

            modelAndView.addObject("listProduct", resultPage);
            modelAndView.addObject("name", name);
            modelAndView.addObject("priceRange", priceRange);
            modelAndView.addObject("sortType", sortType);

            return modelAndView;
        }

    }