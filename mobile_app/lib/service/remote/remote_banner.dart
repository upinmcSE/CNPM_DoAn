import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:mobile_app/const.dart' ;


class RemoteBannerService {
  var client = http.Client();
  var remoteUrl = '$baseUrl/api/v1/banners';

  Future<dynamic> get() async {
    final response = await http.get(Uri.parse(remoteUrl));

    if (response.statusCode == 200) {
      // Lấy body dưới dạng chuỗi
      print(response.body);
      return response.body;
    } else {
      throw Exception('Failed to load banners'); // Xử lý lỗi
      return null;
    }

  }

}