import React from 'react';
import { useNavigate } from 'react-router-dom';
import { createOrder } from '../../apis/orderService';
const SuccessPage = () => {
  const navigate = useNavigate();

  const handleBackHome = async () => {
    localStorage.removeItem('orderId');
    await createOrder();
    navigate('/');
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="text-center p-8 bg-white rounded-lg shadow-md">
        <div className="mb-6">
          <svg
            className="mx-auto h-16 w-16 text-green-500"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d="M5 13l4 4L19 7"
            />
          </svg>
        </div>
        <h2 className="text-2xl font-bold text-gray-800 mb-4">
          Đặt hàng thành công!
        </h2>
        <p className="text-gray-600 mb-6">
          Cảm ơn bạn đã mua hàng. Chúng tôi sẽ xử lý đơn hàng của bạn trong thời gian sớm nhất.
        </p>
        <button
          onClick={handleBackHome}
          className="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-2 px-6 rounded-lg transition duration-300"
        >
          Về trang chủ
        </button>
      </div>
    </div>
  );
};

export default SuccessPage;