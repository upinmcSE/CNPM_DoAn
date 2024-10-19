import React, { useState } from "react";
import Logo from "../../assets/website/coffee_logo.png";
import { FaCoffee } from "react-icons/fa";
import { IoMdLogIn } from "react-icons/io";
import Login from "../Login/Login"; // Cập nhật đường dẫn nếu cần
import { getCart } from "../../services/cartManager";
import ProductListDialog from "../ProductListDialog/ProductListDialog";

const Menu = [
  { id: 1, name: "Home", link: "/#" },
  { id: 2, name: "Recommend", link: "/#services" },
  { id: 3, name: "Products", link: "/#products" },
  { id: 4, name: "About", link: "/#about" },
];

const Navbar = () => {
  const [orderCount, setOrderCount] = useState(0);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [showDropdown, setShowDropdown] = useState(false);
  const [isLoginOpen, setIsLoginOpen] = useState(false); // State để mở dialog login
  const [isCartDialogOpen, setIsCartDialogOpen] = useState(false); // State để mở dialog giỏ hàng

  const handleLoginSuccess = () => {
    setIsLoggedIn(true);
    setShowDropdown(true); // Hiện dropdown sau khi đăng nhập thành công
    setIsLoginOpen(false); // Đóng dialog login
  };

  const handleLogout = () => {
    setIsLoggedIn(false);
    setShowDropdown(false);
  };

  const handleLoginClick = () => {
    if (isLoggedIn) {
      setShowDropdown((prev) => !prev); // Hiện/ẩn dropdown khi đã đăng nhập
    } else {
      setIsLoginOpen(true); // Mở dialog khi chưa đăng nhập
    }
  };

  const handleOrderClick = () => {
    setIsCartDialogOpen(true); // Mở dialog giỏ hàng
  };

  const handleCloseCartDialog = () => {
    setIsCartDialogOpen(false); // Đóng dialog giỏ hàng
  };

  const cartItems = getCart(); // Lấy sản phẩm trong giỏ hàng

  return (
    <div className="bg-gradient-to-r from-secondary to-secondary/90 shadow-md bg-gray-900 text-white">
      <div className="container py-2">
        <div className="flex justify-between items-center">
          <a
            href="#"
            className="font-bold text-2xl sm:text-3xl flex items-center gap-2 tracking-wider"
          >
            <img src={Logo} alt="Logo" className="w-14" />
            Coffee Shop
          </a>

          <div className="flex items-center gap-4">
            <ul className="hidden sm:flex items-center gap-4">
              {Menu.map((menu) => (
                <li key={menu.id}>
                  <a
                    href={menu.link}
                    className="text-xl py-4 px-4 text-white/70 hover:text-white duration-200"
                  >
                    {menu.name}
                  </a>
                </li>
              ))}
            </ul>

            <button
              className="relative bg-primary/70 text-white px-4 py-2 rounded-full flex items-center gap-3"
              onClick={handleOrderClick}
            >
              Order
              <FaCoffee className="text-xl" />
              <span className="absolute -top-2 -right-2 bg-red-500 text-xs font-bold rounded-full h-5 w-5 flex items-center justify-center">
                {orderCount}
              </span>
            </button>

            <div className="relative">
              <button
                className="bg-primary/70 text-white px-4 py-2 rounded-full flex items-center gap-3"
                onClick={handleLoginClick}
              >
                {isLoggedIn ? "Profile" : "Login"}
                <IoMdLogIn className="text-xl" />
              </button>

              {isLoggedIn && showDropdown && (
                <div className="absolute right-0 mt-2 bg-white text-black rounded shadow-lg">
                  <button className="block px-4 py-2" onClick={handleLogout}>
                    Logout
                  </button>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>

      {/* Dialog giỏ hàng */}
      {isCartDialogOpen && (
        <ProductListDialog
          cartItems={cartItems}
          onClose={handleCloseCartDialog}
        />
      )}

      <Login
        isOpen={isLoginOpen}
        onClose={() => setIsLoginOpen(false)}
        onLoginSuccess={handleLoginSuccess}
      />
    </div>
  );
};

export default Navbar;
