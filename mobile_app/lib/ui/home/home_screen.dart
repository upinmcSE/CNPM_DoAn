import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:mobile_app/component/main_header.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return SafeArea(
        child: Column(
          children: [
            const MainHeader(),
            Obx(() {
              
            })
          ],
        )
    );
  }
}
