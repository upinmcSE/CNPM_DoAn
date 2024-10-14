import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:mobile_app/component/main_header.dart';
import 'package:mobile_app/controller/controllers.dart';
import 'package:mobile_app/ui/home/component/carousel_slider/carousel_slider_view.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return SafeArea(
        child: Column(
          children: [
            const MainHeader(),
            Obx(() {
              if(homeController.bannerList.isNotEmpty){
                return CarouselSliderView(bannerList: homeController.bannerList);
              }else{
                return Container();
              }

            })
          ],
        )
    );
  }
}
