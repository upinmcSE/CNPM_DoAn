import 'package:get/get.dart';
import 'package:mobile_app/route/app_route.dart';
import 'package:mobile_app/ui/dashboard/dashboard_binding.dart';
import 'package:mobile_app/ui/dashboard/dashboard_screen.dart';

class AppPage {
  static var list = [
    GetPage(
        name: AppRoute.dashboard,
        page: () => const DashboardScreen(),
        binding: DashboardBinding(),

    )
  ];
}