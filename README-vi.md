*Read this in other languages:* [🇻🇳 Tiếng Việt](README-vi.md) | [🇬🇧 English](README.md)

# SomethingsAddons

Một bản mod dành cho Minecraft Fabric nhằm bổ sung các cải tiến và tiện ích thú vị cho trò chơi của bạn.

## Tính năng chính
* **Kiểm soát Sát thương & Hồi máu:** Thêm khả năng giới hạn sát thương nhận vào (damage gating) và tính năng hồi máu cho mob (khuyên dùng cho Boss).
* **DangerBoss Mode (Wither & Ender Dragon):** Chế độ thử thách đặc biệt giúp tăng cường chỉ số (Máu, Giáp), sát thương và thêm các chiêu thức tấn công độc nhất cho Wither và Rồng Ender. Mọi thông số đều có thể tùy chỉnh thông qua Mod Menu. 
* **Bảo vệ Khối:** Thêm tính năng bảo vệ block khỏi bị phá hủy (có thể tùy chỉnh trong config).
* **Sửa lỗi & Tương thích:** Fix lỗi liên quan đến boss từ các mod khác và khắc phục một vài bug phá Bedrock.
* **Tắt/Bật Looting:** Ẩn/Nhấp nút Looting trong Giao diện Inventory
* **Giới hạn Phù phép (Enchantment Limiter):** Ngăn chặn việc "nhồi nhét" quá nhiều bùa chú lên một trang bị. Giới hạn số lượng enchant tối đa trên một món đồ, tương thích hoàn toàn với *Enchanting Infuser* và *Anvil* (Đe rèn) mặc định.
* **Chống Bất Tử (Anti-Godmode):** Ngăn chặn việc người chơi lợi dụng *Resistance 255* hoặc stack giáp quá dày. Mọi đòn đánh vật lý hoặc kỹ năng Boss (Warden, Wither, Ender Dragon) đều đảm bảo gây ra một mức **Sát thương tối thiểu (Min Damage)**.
* **Boss Scaling:** Trận chiến cuối game sẽ trở nên căng thẳng hơn! Quái vật càng trâu (Từ 100 máu trở lên), lượng sát thương tối thiểu chúng gây ra càng lớn (Tỉ lệ x1 đến x5).
* **Hiệu ứng Khắc chế (Anti-Heal Debuff):** Ăn đòn từ Boss (Quái có >= 100 HP) sẽ khiến bạn bị giảm 50% toàn bộ khả năng hồi máu và giáp ảo (Tim vàng) trong 5 giây. Không còn trò spam Táo Vàng để cò quay với Boss nữa!
* **An Toàn Với Vanilla:** Tương thích 100% với cơ chế đỡ Khiên (Shield) và hoàn toàn không ảnh hưởng đến sát thương môi trường (Cháy, Ngạt thở, Rơi tự do).

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
