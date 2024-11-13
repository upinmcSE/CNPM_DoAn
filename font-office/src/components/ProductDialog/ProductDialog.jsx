import React, { useState, useContext } from 'react';
import { addProductToOrder, buyNow } from '../../apis/orderService';
import { ToastContext } from '../../context/ToastContext';
import { useNavigate } from 'react-router-dom';
const ProductDialog = ({ product, onClose, onBuy }) => {
  const [quantity, setQuantity] = useState(1);
  const { toast } = useContext(ToastContext); // Sử dụng ToastContext
  const navigate = useNavigate();

  const handleQuantityChange = (event) => {
    const value = Math.max(1, parseInt(event.target.value) || 1);
    setQuantity(value);
  };

  const handleAddToCart = async (product, quantity) => {
    const orderline = {
      productId: product.id,
      amount: quantity,
    };
    await addProductToOrder(orderline, localStorage.getItem('orderId'));
  };

  const handleOnBuy = async (product, quantity) => {
    const orderline = {
      productId: product.id,
      amount: quantity,
    }
    await addProductToOrder(orderline, localStorage.getItem('orderId'));
    navigate('/payment');

  }


  const handleLoginCheck = async (action, product, quantity) => {
    const token = localStorage.getItem('authToken');
    if (token) {
      await action(product, quantity);
    } else {
      toast.error('Vui lòng đăng nhập để thực hiện thao tác này!');
    }
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
      <div className="bg-white rounded-lg p-4 max-w-sm w-full">
        <h2 className="text-xl font-bold mb-2">{product.name}</h2>
        <img
          src={`http://localhost:8081/coffee/api/v1/products/images/${product.urlImage}`}
          alt={product.name}
          className="w-full h-40 object-cover mb-2 rounded-lg"
        />
        <p className="text-gray-500 text-center font-semibold">{product.price} VND</p>

        <div className="mt-4">
          <label className="block mb-1">Số lượng:</label>
          <input
            type="number"
            value={quantity}
            onChange={handleQuantityChange}
            className="border rounded p-2 w-full"
          />
        </div>

        <div className="mt-4 flex justify-between">
          <button
            onClick={() => {
              handleLoginCheck(handleOnBuy, product, quantity);
              onClose();
            }}
            className="px-4 py-2 bg-primary text-white rounded"
          >
            Mua
          </button>
          <button
            onClick={() => {
              handleLoginCheck(handleAddToCart, product, quantity);
              onClose();
            }}
            className="px-4 py-2 bg-gray-300 rounded"
          >
            Thêm vào giỏ hàng
          </button>
          <button onClick={onClose} className="px-4 py-2 bg-red-300 rounded">
            Đóng
          </button>
        </div>
      </div>
    </div>
  );
};

export default ProductDialog;
