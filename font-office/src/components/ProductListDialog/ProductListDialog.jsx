import React from 'react';

const ProductListDialog = ({ cartItems, onClose, onCheckout, onRemoveFromCart }) => {
  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
      <div className="p-6 bg-white rounded-lg shadow-lg w-11/12 max-w-3xl relative"> {/* Đã tăng max-w thành max-w-3xl */}
        <button 
          className="absolute top-2 right-2 text-gray-500 hover:text-gray-700 text-2xl"
          onClick={onClose}
        >
          &times;
        </button>
        <h2 className="text-2xl font-bold mb-4">Giỏ hàng của bạn</h2> {/* Tăng kích thước chữ tiêu đề */}
        {cartItems.length === 0 ? (
          <p className="text-gray-500">Giỏ hàng của bạn đang trống.</p>
        ) : (
          <ul className="space-y-4">
            {cartItems.map((item) => (
              <li key={item.id} className="flex justify-between items-center p-2 border-b border-gray-300">
                <div>
                  <h3 className="font-semibold text-gray-500">Tên sản phẩm: {item.name}</h3>
                  <p className="text-gray-500">Giá : {item.price} VND</p>
                  <p className="text-gray-500">Số lương : {item.quantity}</p>
                </div>
                <button
                  onClick={() => onRemoveFromCart(item.id)} // Cần định nghĩa onRemoveFromCart trong Navbar nếu cần
                  className="text-red-600 hover:text-red-800 transition-colors duration-200"
                >
                  Xóa
                </button>
              </li>
            ))}
          </ul>
        )}
        
        {cartItems.length > 0 && (
          <div className="mt-4 flex justify-between">
            <button
              onClick={onCheckout}
              className="bg-primary text-white px-6 py-3 rounded-full hover:bg-primary/80 transition duration-200" // Tăng kích thước nút thanh toán
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
