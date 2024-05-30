package com.example.shopdunkproject.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String operatingSystem; // Hệ điều hành
    private String specialFeatures; // Tiện ích đặc biệt
    private String systemRequirements; // Yêu cầu hệ thống
    private String color; // Màu sắc
    private String storage; // Dung lượng
    private String dimensionsAndWeight; // Kích thước và Trọng lượng
    private String screen; // Màn hình
    private String screenResolution; // Độ phân giải màn hình
    private String waterAndDustResistance; // Chống nước - Chống bụi
    private String chip; // Chip
    private String rearCamera; // Camera sau
    private String frontCamera; // Camera trước
    private String videoRecording; // Quay video
    private String security; // Bảo mật
    private String networkConnectivity; // Kết nối mạng
    private String audioTechnology; // Công nghệ âm thanh
    private String videoViewing; // Xem video
    private String charging; // Sạc
    private String batteryAndPower; // Pin và nguồn điện
    private String sensors; // Cảm biến
    private String sim; // Sim



}
