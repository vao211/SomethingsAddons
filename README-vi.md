*Read this in other languages:* [🇻🇳 Tiếng Việt](README-vi.md) | [🇬🇧 English](README.md)

# SomethingsAddons

Một bản mod dành cho Minecraft Fabric nhằm bổ sung các cơ chế sinh tồn RPG khắc nghiệt và những tiện ích tuyệt vời cho trò chơi của bạn.

## Tính năng chính
* **Kiểm soát Sát thương & Hồi máu:** Thêm khả năng giới hạn sát thương nhận vào (damage gating) và tính năng hồi máu cho mob.
* **Chống Bất Tử (Anti-Godmode):** Ngăn chặn việc lợi dụng *Resistance 255* hoặc stack giáp quá dày. Mọi đòn đánh đều đảm bảo gây ra một mức **Sát thương tối thiểu (Min Damage)**.
* **Boss Scaling:** Trận chiến cuối game sẽ trở nên căng thẳng hơn! Quái vật càng trâu (Từ 100 máu trở lên), lượng sát thương tối thiểu chúng gây ra càng lớn (Tỉ lệ x1 đến x5).
* **Hiệu ứng Khắc chế (Anti-Heal Debuff):** Ăn đòn từ Boss sẽ khiến bạn bị giảm 50% toàn bộ khả năng hồi máu trong 5 giây. Chấm dứt kỷ nguyên spam Táo Vàng để cò quay với Boss!
* **DangerBoss (Wither & Rồng Ender):** Chế độ thử thách đặc biệt giúp tăng cường chỉ số (Máu, Giáp), sát thương và thêm các chiêu thức tấn công độc nhất.
* **Warden Overhaul:** Trùm cuối giờ đây có cơ chế Cường hóa (Enrage) liên tục phình to kích thước, tăng chỉ số và nổ Sonic Boom đẩy lùi diện rộng mỗi 30 giây. Vật phẩm rơi ra bị giới hạn chặt chẽ 1 con/ngày Minecraft.
* **Lửa Trại Hồi Máu (Bonfire):** Biến Lửa trại thành các trạm hồi phục tĩnh. Tự động hồi máu cho người chơi đứng gần nếu họ đã an toàn thoát khỏi giao tranh.
* **Cơ Chế Sinh Tồn RPG:** Mang đến trải nghiệm nhập vai chân thực với **Trọng Lượng Giáp** (giáp quá 20 điểm sẽ làm giảm tốc chạy nhưng bù lại tăng kháng đẩy lùi), **Chấn Thương Chân** (ngã mất quá 4 tim sẽ bị chóng mặt, chậm chạp nặng) và **Ăn Cố** (cho phép tiếp tục ăn dù đầy thanh đói để tích lũy độ bão hòa ẩn).
* **Thú Cưng Tự Về Nhà:** Chó/Mèo đi lạc (cách xa hơn 100 block) sẽ tự động dịch chuyển về giường của chủ sau 2 phút (Không áp dụng khi thú cưng đang ngồi).
* **Giới hạn Phù phép (Enchantment Limiter):** Ngăn chặn việc "nhồi nhét" quá nhiều bùa chú lên một trang bị.
* **Bảo vệ Khối:** Thêm tính năng bảo vệ block khỏi bị phá hủy bởi quái vật.
* **Tắt/Bật Looting:** Ẩn/Nhấp nút Looting trực tiếp trong Giao diện Túi đồ.
* **An Toàn Với Vanilla:** Tương thích 100% với cơ chế đỡ Khiên (Shield) và hoàn toàn không bị lỗi với sát thương môi trường (Cháy, Ngạt thở, Rơi tự do).

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

1. Clone kho lưu trữ này: `git clone https://github.com/vao211/SomethingsAddons.git`
2. Mở thư mục chứa code: `cd SomethingsAddons`
3. Build mod bằng Gradle: `./gradlew build`
4. Các file `.jar` sau khi build sẽ nằm trong thư mục `build/libs/`.

## Notes
Khuyên dùng chung với mod **Better Combat** và **Marium's Soulike Weaponry** để có trải nghiệm chiến đấu RPG đã tay nhất!

## Giấy phép
Dự án này được cấp phép theo giấy phép [CC-BY-NC-4.0](https://creativecommons.org/licenses/by-nc/4.0/).