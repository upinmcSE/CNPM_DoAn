import { useState, useEffect } from "react";
import Logo from "../../assets/website/coffee_logo.png";
import { FaCoffee } from "react-icons/fa";
import { IoMdLogIn } from "react-icons/io";
import Login from "../Login/Login"; // Cập nhật đường dẫn nếu cần
import ProductListDialog from "../ProductListDialog/ProductListDialog";
import Profile from "../Profile/Profile";
import ChangePassword from "../ChangePassword/ChangePassword";
import { createOrder, getCart, getHistoryList } from "../../apis/orderService";
import { introspect, logout } from "../../apis/authService";
import HistoryList from "../History/History";

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
  const [isLoginOpen, setIsLoginOpen] = useState(false); 
  const [isCartDialogOpen, setIsCartDialogOpen] = useState(false); 
  const [isProfileOpen, setIsProfileOpen] = useState(false);
  const [isPasswordDialogOpen, setIsPasswordDialogOpen] = useState(false);
  const [isHistoryDialogOpen, setHistoryDialogOpen] = useState(false);
  const [cartItems, setCartItems] = useState([]);
  const [orders, setOrders] = useState([]);

  // Mock API data
  // const orders = [
  //   {
  //     id: "06460235-caff-445e-9d54-8618355d59fe",
  //     orderLines: [
  //       { id: "4cf0baa3-6ac2-4681-aa6f-813248c210cb", productName: "Cappuccino", amount: 1 },
  //     ],
  //     totalPrice: 40000,
  //     status: "COMPLETED",
  //     createdDate: "2024-11-13",
  //     modifiedDate: "2024-11-13",
  //   },
  //   {
  //     id: "3e38a930-edb7-417b-a4b1-3b1ac48c076e",
  //     orderLines: [
  //       { id: "050c8bc6-6a1b-4c20-aa81-f42bc7d5b127", productName: "Cà Phê Nóng", amount: 3 },
  //       { id: "b0105ba8-4630-437a-b4ab-89bf9e3b5f7a", productName: "Cappuccino", amount: 2 },
  //     ],
  //     totalPrice: 200000,
  //     status: "COMPLETED",
  //     createdDate: "2024-11-13",
  //     modifiedDate: "2024-11-13",
  //   },
  //   {
  //     id: "3e38a930-edb7-417b-a4b1-3b1ac48c076e",
  //     orderLines: [
  //       { id: "050c8bc6-6a1b-4c20-aa81-f42bc7d5b127", productName: "Cà Phê Nóng", amount: 3 },
  //       { id: "b0105ba8-4630-437a-b4ab-89bf9e3b5f7a", productName: "Cappuccino", amount: 2 },
  //     ],
  //     totalPrice: 200000,
  //     status: "COMPLETED",
  //     createdDate: "2024-11-13",
  //     modifiedDate: "2024-11-13",
  //   },
  //   {
  //     id: "3e38a930-edb7-417b-a4b1-3b1ac48c076e",
  //     orderLines: [
  //       { id: "050c8bc6-6a1b-4c20-aa81-f42bc7d5b127", productName: "Cà Phê Nóng", amount: 3 },
  //       { id: "b0105ba8-4630-437a-b4ab-89bf9e3b5f7a", productName: "Cappuccino", amount: 2 },
  //     ],
  //     totalPrice: 200000,
  //     status: "COMPLETED",
  //     createdDate: "2024-11-13",
  //     modifiedDate: "2024-11-13",
  //   },
  //   {
  //     id: "3e38a930-edb7-417b-a4b1-3b1ac48c076e",
  //     orderLines: [
  //       { id: "050c8bc6-6a1b-4c20-aa81-f42bc7d5b127", productName: "Cà Phê Nóng", amount: 3 },
  //       { id: "b0105ba8-4630-437a-b4ab-89bf9e3b5f7a", productName: "Cappuccino", amount: 2 },
  //     ],
  //     totalPrice: 200000,
  //     status: "COMPLETED",
  //     createdDate: "2024-11-13",
  //     modifiedDate: "2024-11-13",
  //   },
  //   {
  //     id: "3e38a930-edb7-417b-a4b1-3b1ac48c076e",
  //     orderLines: [
  //       { id: "050c8bc6-6a1b-4c20-aa81-f42bc7d5b127", productName: "Cà Phê Nóng", amount: 3 },
  //       { id: "b0105ba8-4630-437a-b4ab-89bf9e3b5f7a", productName: "Cappuccino", amount: 2 },
  //     ],
  //     totalPrice: 200000,
  //     status: "COMPLETED",
  //     createdDate: "2024-11-13",
  //     modifiedDate: "2024-11-13",
  //   },
  //   {
  //     id: "3e38a930-edb7-417b-a4b1-3b1ac48c076e",
  //     orderLines: [
  //       { id: "050c8bc6-6a1b-4c20-aa81-f42bc7d5b127", productName: "Cà Phê Nóng", amount: 3 },
  //       { id: "b0105ba8-4630-437a-b4ab-89bf9e3b5f7a", productName: "Cappuccino", amount: 2 },
  //     ],
  //     totalPrice: 200000,
  //     status: "COMPLETED",
  //     createdDate: "2024-11-13",
  //     modifiedDate: "2024-11-13",
  //   },
      
  // ];

  useEffect(() => {
    checkAuthStatus();
  }, []);

  useEffect(() => {
    fetchCartItems();
  }, [isLoggedIn]);

  useEffect(() => {
    fetchHistoryOrders();
  }, [isLoggedIn]);

  // Hàm kiểm tra trạng thái authentication
  const checkAuthStatus = async () => {
    const token = localStorage.getItem('Token');
    if (!token) {
      setIsLoggedIn(false);
      return;
    }
    try {
      const res = await introspect(token); // Gọi API để verify token
      console.log("res: ", res)
      if(res.result.valid === true){
        setIsLoggedIn(true);
      }else{
        localStorage.removeItem('Token');
        setIsLoggedIn(false);
      }
    } catch (error) {
      console.error("Token invalid or expired:", error);
      localStorage.removeItem('Token');
      setIsLoggedIn(false);
      setShowDropdown(false);
    }
  }

  const fetchCartItems = async () => {
    if (isLoggedIn) {
        try {
            const cartItems = await getCart();
            setCartItems(cartItems); 
            setOrderCount(cartItems.length); 
        } catch (error) {
            console.error("Error fetching cart items on render:", error);
        }
    }
  };

  const fetchHistoryOrders = async () => {
    if (isLoggedIn) {
        try {
            const orders = await getHistoryList();
            setOrders(orders); 
        } catch (error) {
            console.error("Error fetching history on render:", error);
        }
    }
  };

  const handleLoginSuccess = async () => {
    setIsLoggedIn(true);
    setShowDropdown(true); // Hiện dropdown sau khi đăng nhập thành công
    setIsLoginOpen(false); // Đóng dialog login

    try{
      console.log(localStorage.getItem('Token'))
      const res = await createOrder()
      console.log("res id: ", res)
    }catch (error) {
      console.error("Error creating order after login: ", error);
    }
  };

  const handleLogout = async () => {
    try{
      const res = await logout()
      console.log("res: ", res.message)
      setIsLoggedIn(false);
      setShowDropdown(false);
    }catch (error) {
      console.error("Error creating order after login: ", error);
    }
  };

  const handleLoginClick = () => {
    if (isLoggedIn) {
      setShowDropdown((prev) => !prev); // Hiện/ẩn dropdown khi đã đăng nhập
    } else {
      setIsLoginOpen(true); // Mở dialog khi chưa đăng nhập
    }
  };

  const handleOrderClick = async () => {
    try {
        // Kiểm tra đăng nhập trước khi lấy giỏ hàng
        if (!isLoggedIn) {
          setIsLoginOpen(true);
          return;
        }
        const cartItems = await getCart();
        setOrderCount(cartItems.length);
        setCartItems(cartItems);
        console.log("cartItems: ", cartItems);
        setIsCartDialogOpen(true); // Mở dialog giỏ hàng
    } catch (error) {
        console.error("Error loading cart items: ", error);
    }
  };

  const handleCloseCartDialog = () => {
    setIsCartDialogOpen(false); // Đóng dialog giỏ hàng
  };

  const handleProfile = () => {
    setIsProfileOpen(true); // Mở dialog profile
    setShowDropdown(false); // Đóng dropdown khi mở dialog profile
  };

  const handleProfileClose = () => {
    setIsProfileOpen(false); // Đóng dialog profile
  };

  const handleChangePassword = () => {
    setIsPasswordDialogOpen(true); // Mở dialog đổi mật khẩu
    setShowDropdown(false);
  };

  const handleHistory = async () => {
    try {
      const response = await getHistoryList(1, 4);
      console.log("response: ", response.data.data);
      setOrders(response.data.data);
      setHistoryDialogOpen(true);
    }catch (error) {
      console.error("Error loading history: ", error);
    }
  }

  const handleHistoryClose = () => {
    setHistoryDialogOpen(false);
  }

  const handleCloseChangePassword = () => setIsPasswordDialogOpen(false); // Đóng dialog đổi mật khẩu



  return (
    <div className="bg-gradient-to-r from-secondary to-secondary/90 shadow-md bg-gray-900 text-white fixed top-0 left-0 right-0 z-50">
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
                <div className="relative">
                  <div
                    className="absolute right-0 mt-2 bg-white text-black rounded shadow-lg z-10"
                    style={{ pointerEvents: 'auto' }} // Đảm bảo dropdown nhận sự kiện chuột
                    onClick={(e) => e.stopPropagation()} // Ngăn sự kiện lan ra ngoài
                  >
                    <button className="inline-block px-4 py-2" onClick={handleProfile}>
                      Profile
                    </button>
                    <button className="inline-block px-4 py-2" onClick={handleChangePassword}>
                      Password
                    </button>
                    <button className="inline-block px-4 py-2" onClick={handleHistory}>
                      History
                    </button>
                    <button className="inline-block px-4 py-2" onClick={handleLogout}>
                      Logout
                    </button>
                  </div>
                </div>
              )}


            </div>
          </div>
        </div>
      </div>

      {/* Dialog profile */}
      <Profile
        isOpen={isProfileOpen}
        onClose={handleProfileClose}
        userInfo={{
          fullName: "Nguyễn Văn A", // Thay thế bằng thông tin thực tế
          gender: "male",
          age: 30,
          email: "nguyenvana@example.com",
          memberLevel: "Menber",
          purchasePoints: 1500,
        }}
        onUpdate={(updatedData) => {
          handleProfileClose(); // Đóng dialog sau khi cập nhật
        }}
      />

      {/* Dialog giỏ hàng */}
      {isCartDialogOpen && (
        <ProductListDialog
          cartItems={cartItems}
          onClose={handleCloseCartDialog}
        />
      )}

      {/* Dialog đổi mật khẩu */}
      <ChangePassword
        isOpen={isPasswordDialogOpen}
        onClose={handleCloseChangePassword}
        onSubmit={(data) => {
          handleCloseChangePassword(); // Đóng dialog sau khi đổi mật khẩu
        }}
      />
      {/* Dialog login */}
      <Login
        isOpen={isLoginOpen}
        onClose={() => setIsLoginOpen(false)}
        onLoginSuccess={handleLoginSuccess}
      />
      {/* Dialog lịch sử đơn hàng */}
      {/* {isHistoryDialogOpen && (
        <HistoryList
          orders={orders}
          onClose={handleHistoryClose}
        />
      )} */}
      <HistoryList
        isOpen={isHistoryDialogOpen}
        onClose={handleHistoryClose}
        orders={orders}
      />
    </div>
  );
};

export default Navbar;
