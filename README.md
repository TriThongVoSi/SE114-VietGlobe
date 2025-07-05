# ĐỒ ÁN NHẬP MÔN ỨNG DỤNG DI ĐỘNG
# GVHD: Quan Chí Khánh An
# Ứng dụng Đọc Tin Tức VietGlobe

## Giới thiệu
VietGlobe là ứng dụng đọc tin tức hiện đại, cập nhật nhanh các tin tức mới nhất, hỗ trợ dịch và tóm tắt bài viết, lưu bài viết yêu thích, giao diện thân thiện, dễ sử dụng.

---

## Tính năng chính
- Đăng nhập/Onboarding cho người dùng mới
- Hiển thị danh sách bài viết theo danh mục
- Xem chi tiết bài viết, chia sẻ bài viết
- Bookmark (lưu/xóa bài viết yêu thích)
- Tìm kiếm tin tức theo từ khóa
- Dịch và tóm tắt bài viết
- Cài đặt ngôn ngữ giao diện và tin tức
- Giao diện hỗ trợ Dark Mode

---

## Công nghệ sử dụng
- **Ngôn ngữ:** Kotlin
- **Framework:** Android SDK
- **Kiến trúc:** MVVM
- **Thư viện chính:**
  - Retrofit (gọi API)
  - Room Database (lưu trữ cục bộ)
  - DataStore (lưu cấu hình)
  - Glide (tải ảnh)
  - Lottie (hiệu ứng động)

---

## Hướng dẫn cài đặt

### Yêu cầu hệ thống
- Android Studio (Arctic Fox trở lên)
- JDK 8 trở lên
- Thiết bị/emulator Android API 23 trở lên
- Kết nối Internet

### Các bước cài đặt
1. **Clone project:**
   ```bash
   git clone <link-repo>
   ```
2. **Mở project bằng Android Studio:**
   - Chọn `Open an existing project` và trỏ tới thư mục `DemoMobi3`.
3. **Đồng bộ Gradle:**
   - Android Studio sẽ tự động tải và đồng bộ các thư viện cần thiết.
4. **Cấu hình API Key (nếu cần):**
   - Thêm API Key cho News API vào file cấu hình (nếu có hướng dẫn riêng).
5. **Build và chạy ứng dụng:**
   - Chọn thiết bị/emulator và nhấn Run (Shift+F10).

### Cài đặt APK thủ công
- Vào thư mục `app/build/outputs/apk/` để lấy file APK và cài đặt lên thiết bị Android.

---

## Hướng dẫn sử dụng
1. **Mở ứng dụng lần đầu:**
   - Làm theo hướng dẫn onboarding, chọn ngôn ngữ ưu tiên.
2. **Xem tin tức:**
   - Trang chủ hiển thị các tin mới nhất, có thể chọn danh mục.
3. **Xem chi tiết bài viết:**
   - Nhấn vào bài viết để xem nội dung chi tiết, hình ảnh, nguồn tin.
4. **Bookmark:**
   - Nhấn biểu tượng bookmark để lưu/xóa bài viết yêu thích.
   - Truy cập mục Bookmark để xem lại các bài đã lưu.
5. **Tìm kiếm:**
   - Sử dụng thanh tìm kiếm để tìm bài viết theo từ khóa.
6. **Dịch/tóm tắt:**
   - Trong màn hình chi tiết, chọn chức năng dịch hoặc tóm tắt bài viết.
7. **Cài đặt ngôn ngữ:**
   - Vào mục Cài đặt để thay đổi ngôn ngữ giao diện hoặc tin tức.

---

## Cấu trúc thư mục chính
- `app/src/main/java/com/thecode/vietglobe/`
  - `application/`: Khởi tạo ứng dụng
  - `base/`: Lớp cơ sở dùng chung
  - `data/`: Xử lý dữ liệu (local, remote, repository)
  - `di/`: Cấu hình Dependency Injection
  - `domain/`: Model, use case nghiệp vụ
  - `presentation/`: Giao diện, chia theo chức năng
  - `utils/`: Tiện ích, hằng số
- `app/src/main/res/`: Tài nguyên giao diện (layout, ảnh, icon, string, style,...)

<<<<<<< HEAD
## API Keys

Dự án này sử dụng hai API:
- **Gemini Flash (Google AI)**: Đăng ký và lấy API key tại https://makersuite.google.com/app/apikey
- **DeepSeek AI**: Đăng ký và lấy API key tại https://platform.deepseek.com/api-keys

Sau đó cập nhật file cấu hình (hoặc thay thế trực tiếp trong code) với các giá trị:
```kotlin
val geminiApiKey = "YOUR_API_KEY"
val deepseekKey = "YOUR_API_KEY"
val newsapikey = "YOUR_NEWS_API_KEY"
```
**© 7/2025 VietGlobe News App**
---
Tác giả: [Kim Thành Tiến - Võ Sĩ Trí Thông]
---
Nếu cần file .docx báo cáo đồ án liên hệ qua gmail vosithongtri@gmail.com (hạt dẻ 150 cành cả nhóm)
