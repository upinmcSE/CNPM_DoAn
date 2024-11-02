import React, {useState, useEffect } from 'react';
import { removeItemFromOrder, getCart } from '../../apis/orderService';
import { useNavigate } from 'react-router-dom';
const ProductListDialog = ({ cartItems, onClose}) => {
  console.log("cartItems: ", cartItems);
  const [Items, setItems] = useState(cartItems);
  const navigate = useNavigate();

  useEffect(() => {
    setItems(cartItems); // Update the Items state whenever cartItems prop changes
  }, [cartItems]);
  
  const loadItems = async () => {
    try{
      const Items = await getCart();
      setItems(Items);
    }catch(error){
      console.error("Error loading cart items: ", error);
    }
  }
  const handleRemoveFromCart = async (orderLineId) => {
    try{
      const orderId = localStorage.getItem('orderId');
      await removeItemFromOrder(orderId, orderLineId);
      loadItems();
    }catch(error){
      console.error("Error removing product from order: ", error);
      throw error;
    }
  }
  const handleCheckout = () => {
    navigate('/payment');
  }

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
      <div className="p-6 bg-white rounded-lg shadow-lg w-11/12 max-w-3xl relative">
        <button 
          className="absolute top-2 right-2 text-gray-500 hover:text-gray-700 text-2xl"
          onClick={onClose}
        >
          &times;
        </button>
        <h2 className="text-2xl font-bold mb-4">Giỏ hàng của bạn</h2>
        {Items.length === 0 ? (
          <p className="text-gray-500">Giỏ hàng của bạn đang trống.</p>
        ) : (
          <ul className="space-y-4">
            {Items.map((item) => (
              <li key={item.id} className="flex justify-between items-center p-2 border-b border-gray-300">
                <div>
                  <h3 className="font-semibold text-gray-500">Tên sản phẩm: {item.product.name}</h3>
                  <p className="text-gray-500">Giá: {item.product.price} VND</p>
                  <p className="text-gray-500">Số lượng: {item.amount}</p>
                </div>
                <button
                  onClick={() => handleRemoveFromCart(item.id)} // Đảm bảo onRemoveFromCart đã được định nghĩa trong component cha
                  className="text-red-600 hover:text-red-800 transition-colors duration-200"
                >
                  Xóa
                </button>
              </li>
            ))}
          </ul>
        )}
        
        {Items.length > 0 && (
          <div className="mt-4 flex justify-between">
            <p className="font-semibold text-gray-500">
              Tổng cộng: {Items.reduce((acc, item) => acc + item.product.price * item.amount, 0).toLocaleString('vi-VN')} VND
            </p>
            <button
              onClick={handleCheckout}
              className="bg-primary text-white px-6 py-3 rounded-full hover:bg-primary/80 transition duration-200"
            >
              Thanh toán
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default ProductListDialog;
