*Read this in other languages:* [🇻🇳 Tiếng Việt](README-vi.md) | [🇬🇧 English](README.md)

# SomethingsAddons

Một bản mod dành cho Minecraft Fabric nhằm bổ sung các cải tiến và tiện ích thú vị cho trò chơi của bạn.

## Tính năng chính
* **Kiểm soát Sát thương & Hồi máu:** Thêm khả năng giới hạn sát thương nhận vào (damage gating) và tính năng hồi máu cho mob (khuyên dùng cho Boss).
* **Wither DangerBoss:** Thêm chế độ DangerBoss đặc biệt dành cho Wither (có thể tùy chỉnh trong config).
* **Bảo vệ Khối:** Thêm tính năng bảo vệ block khỏi bị phá hủy (có thể tùy chỉnh trong config).
* **Sửa lỗi & Tương thích:** Fix lỗi liên quan đến boss từ các mod khác và khắc phục một vài bug phá Bedrock.

## Yêu cầu hệ thống
Để sử dụng bản mod này, bạn cần cài đặt:
* **Minecraft:** `1.21.1`
* **Fabric Loader:** `>=0.19.2`
* **Fabric API:** Bắt buộc có

## Hướng dẫn cài đặt
1. Đảm bảo bạn đã cài đặt [Fabric Loader](https://fabricmc.net/) cho Minecraft 1.21.1.
2. Tải về phiên bản [Fabric API](https://modrinth.com/mod/fabric-api) phù hợp.
3. Tải về bản phát hành mới nhất của SomethingsAddons.
4. Bỏ các file `.jar` của Fabric API và SomethingsAddons vào thư mục `mods` trong `.minecraft`.
5. Khởi động trò chơi với profile Fabric.

## Dành cho Lập trình viên
Nếu bạn muốn đóng góp hoặc tự build mod từ mã nguồn:

1. Clone kho lưu trữ này:
   ```bash
   git clone https://github.com/vao211/SomethingsAddons.git
   cd SomethingsAddons
   ```
2. Build mod bằng Gradle:
   ```bash
   ./gradlew build
   ```
   Các file `.jar` sau khi build sẽ nằm trong thư mục `build/libs/`.
## Notes
Recommend chơi chung với mod Better Combat và Marium's Soulike Weaponry
## Giấy phép
Dự án này được cấp phép theo giấy phép [CC-BY-NC-4.0](https://creativecommons.org/licenses/by-nc/4.0/).
