import 'package:get/get.dart';
import 'package:mobile_app/model/ad_banner.dart';
import 'package:mobile_app/service/remote/remote_banner.dart';

class HomeController extends GetxController {
  static HomeController instance = Get.find();
  RxList<AdBanner> bannerList = List<AdBanner>.empty(growable: true).obs;
  RxBool isBannerLoading = false.obs;

  @override
  void onInit() {
    getAdBanners();
    super.onInit();
  }

  void getAdBanners() async{
    try{
      isBannerLoading(true);
      var result = await RemoteBannerService().get();
      if(result != null){
        bannerList.assignAll(adBannerListFromJson(result)); // Chuyển đổi body thành danh sách
      }
    }finally{
      print(bannerList);
      isBannerLoading(false);
    }
  }
}