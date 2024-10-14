import 'dart:convert';

List<AdBanner> adBannerListFromJson(String val) => List<AdBanner>.from(
    json.decode(val).map((banner) => AdBanner.fromJson(banner))
);

class AdBanner {
  final int id;
  final String imageUrl;

  AdBanner({
    required this.id,
    required this.imageUrl,
  });

  // Factory method để tạo đối tượng từ JSON
  factory AdBanner.fromJson(Map<String, dynamic> data) {
    return AdBanner(
      id: data['id'],
      imageUrl: data['urlImage'] ?? '',
    );
  }

  @override
  String toString() {
    return 'AdBanner{id: $id, imageUrl: $imageUrl}';
  }
}