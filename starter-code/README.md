# Assignment 1 — JUnit 5 & Selenium WebDriver Automation
**Học phần:** Kiểm thử phần mềm — Trường Đại học Sài Gòn  
**Giảng viên hướng dẫn:** Từ Lãng Phiêu  
**Hệ thống kiểm thử mục tiêu:** SauceDemo (https://www.saucedemo.com)  

---

## 1. Môi trường thực thi khuyến nghị
- **Hệ điều hành:** Windows 10/11 64-bit hoặc Linux/macOS
- **Bộ phát triển Java:** JDK 17 LTS trở lên (đã kiểm thử tương thích hoàn hảo trên OpenJDK Temurin 17 và 21)
- **Công cụ build:** Apache Maven 3.9+
- **Trình duyệt kiểm thử:** Google Chrome (hoặc Mozilla Firefox)
*(Selenium Manager tích hợp sẵn trong Selenium 4.27+ sẽ tự động tải và cấu hình ChromeDriver phù hợp với phiên bản trình duyệt).*

---

## 2. Hướng dẫn chạy kiểm thử tự động

### a) Chạy bộ kiểm thử tiêu chuẩn (Luồng chuẩn - Happy Path & Boundary)
Chạy toàn bộ 30 ca kiểm thử hợp lệ và biên theo đúng đặc tả:
```bash
mvn clean test -DexcludedGroups=known-bug
```
- **Kết quả mong đợi:** `BUILD SUCCESS` (30/30 tests PASS 100%).

### b) Chạy nhóm kiểm thử tái hiện 6 lỗi hệ thống đã biết (Known Bugs)
Chạy các ca kiểm thử được thiết kế riêng để bắt và minh chứng sự tồn tại của 6 lỗi (BUG-01 đến BUG-06):
```bash
mvn test -Dgroups=known-bug
```
- **Kết quả mong đợi:** 8 tests FAIL chính xác nhằm chứng minh sự hiện diện của lỗi hệ thống.

### c) Tùy chọn chạy hiển thị giao diện trình duyệt (Headless = false)
```bash
mvn test -DexcludedGroups=known-bug -Dheadless=false
```

---

## 3. Kiến trúc mã nguồn (Page Object Model - POM)
- `vn.edu.softwaretesting.core`: Lớp cơ sở `WebTestBase` (khởi tạo trình duyệt độc lập `@BeforeEach`, đóng `@AfterEach`) và `ScreenshotWatcher` (Extension chụp ảnh tự động khi FAIL).
- `vn.edu.softwaretesting.pages`: Chứa các lớp đại diện cho từng trang: `LoginPage`, `InventoryPage`, `CartPage`, `CheckoutPage`.
- `vn.edu.softwaretesting.tests`: Chứa các lớp kịch bản kiểm thử:
  - `LoginTest`: Phân lớp tương đương (EP) qua file CSV `login-data.csv` và Bảng quyết định (DT).
  - `CartTest`: Phân tích giá trị biên (BVA) số lượng giỏ hàng $[0, 6]$ và ca sử dụng UC2.
  - `CheckoutTest`: Kịch bản Use Case UC1 và BVA tính thuế 8%.
  - `SortTest`: Phân lớp tương đương 4 tiêu chí sắp xếp và săn lỗi BUG-02, BUG-06.
  - `PerformanceTest`: Đo kiểm yêu cầu phi chức năng NFR-01 (bắt lỗi trễ 5s trên `performance_glitch_user` - BUG-01).
  - `CheckoutProblemUserTest` & `CheckoutErrorUserTest`: Bắt lỗi BUG-03 và BUG-04.

---

## 4. Các cấu hình mở rộng đạt điểm Bonus (Mục 7.7 đề bài)
1. **JUnit 5 Extension (`TestWatcher`):** Lớp `ScreenshotWatcher` tự động chụp ảnh màn hình trình duyệt tại thời điểm kiểm thử bị FAIL và lưu trữ vào thư mục `target/screenshots/`.
2. **GitHub Actions CI:** Thiết lập pipeline CI hoàn chỉnh qua file `.github/workflows/test.yml`, tự động chạy kiểm thử và lưu trữ báo cáo `target/surefire-reports/` làm artifact tải về.
