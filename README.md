# Software Testing — Assignment 1 (2026)

Dự án Bài tập lớn số 1 môn **Kiểm thử phần mềm** — Khoa Công nghệ Thông tin, Trường Đại học Sài Gòn (SGU).
- **Giảng viên hướng dẫn:** Thầy Từ Lãng Phiêu
- **Hệ thống kiểm thử mục tiêu:** [SauceDemo](https://www.saucedemo.com/)

---

## 📁 Cấu trúc kho lưu trữ

```text
.
├── Assigment_template_SGU/             # Mẫu báo cáo LaTeX chuẩn của khoa CNTT SGU
├── starter-code/                       # Dự án mã nguồn kiểm thử tự động (Java, Maven, Selenium, JUnit 5)
│   ├── pom.xml
│   └── src/test/
│       ├── java/vn/edu/softwaretesting/
│       │   ├── core/                   # Cấu hình WebDriver (WebTestBase)
│       │   ├── pages/                  # Các Page Object (LoginPage, InventoryPage, CartPage, CheckoutPage)
│       │   └── tests/                  # Các lớp kiểm thử (LoginTest, CartTest, CheckoutTest, SortTest, PerformanceTest)
│       └── resources/                  # Dữ liệu kiểm thử (login-data.csv,...)
├── Phan_Cong_Cong_Viec_Assignment1.xlsx # Bảng quản lý và phân công công việc chi tiết cho nhóm 5 thành viên
├── assignment1.pdf                     # Đề bài đặc tả chi tiết môn học
└── .gitignore                          # Cấu hình bỏ qua các tệp tạm thời, target/, IDE...
```

---

## 🛠️ Yêu cầu môi trường

- **Java Development Kit (JDK):** Version 17 trở lên
- **Apache Maven:** Version 3.9 trở lên
- **Trình duyệt:** Google Chrome hoặc Mozilla Firefox

---

## 🚀 Hướng dẫn chạy kiểm thử tự động

Di chuyển vào thư mục mã nguồn:
```bash
cd starter-code
```

### 1. Chạy toàn bộ kiểm thử ở chế độ ngầm (Headless)
```bash
mvn clean test
```

### 2. Chạy mở giao diện trực quan trên Google Chrome
```bash
mvn test -Dheadless=false -Dbrowser=chrome
```

### 3. Chạy trên Mozilla Firefox
```bash
mvn test -Dbrowser=firefox -Dheadless=false
```

### 4. Chạy kiểm thử loại trừ lỗi đã biết (@Tag("known-bug"))
```bash
mvn test -DexcludedGroups=known-bug
```

### 5. Chạy riêng nhóm kiểm thử liên quan đến lỗi hệ thống (@Tag("known-bug"))
```bash
mvn test -Dgroups=known-bug
```

---

## 📌 Quy định & Checklist cho các thành viên

1. **Tuyệt đối không dùng `Thread.sleep()`**: Bắt buộc sử dụng `WebDriverWait` (Explicit Wait).
2. **Đồng bộ mã định danh Test Case**: Mã test case trong Excel, Báo cáo và `@DisplayName` trong code Java phải khớp 100% (ví dụ: `TC-CHK-01`, `TC-CART-B1`...).
3. **Quy tắc phân loại lỗi**:
   - Nếu test thất bại do lỗi kịch bản kiểm thử $\rightarrow$ Sửa code test.
   - Nếu test thất bại do lỗi hệ thống (như `performance_glitch_user` mất hơn 3s) $\rightarrow$ Giữ nguyên kết quả FAIL, gắn tag `@Tag("known-bug")` và lập báo cáo lỗi `BUG-xx`.
4. **Không push thư mục `target/`**: Thư mục biên dịch đã được cấu hình loại trừ trong `.gitignore`.
