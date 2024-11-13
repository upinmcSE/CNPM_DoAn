import React, { useState } from 'react';
import ResetPasswordDialog from '../ResetPassword/ResetPassword';
import { verifyOTP } from '../../apis/authService';

const OTPDialog = ({ isOpen, onClose, phoneNumber }) => {  // Đổi username thành phoneNumber
  const [otp, setOtp] = useState('');
  const [isOpenResetPassword, setIsOpenResetPassword] = useState(false);

  const handleClick = async () => {
    if (!otp.trim()) {
      alert('Vui lòng nhập mã OTP');
      return;
    }

    try {
      // Xử lý logic verify OTP ở đây
      console.log('OTP submitted:', otp);
      const res = await verifyOTP(otp);
      if (res && res.code === 1000) {
        setIsOpenResetPassword(true);
      }else{
        alert('Mã OTP không đúng');
      }
    } catch (error) {
      console.error('Error verifying OTP:', error);
      alert('Có lỗi xảy ra, vui lòng thử lại');
    }
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-[60] overflow-y-auto">
      <div 
        className="fixed inset-0 bg-black bg-opacity-50 transition-opacity"
        onClick={onClose}
      ></div>

      <div className="flex min-h-screen items-center justify-center p-4">
        <div className="relative w-full max-w-md">
          <div className="relative bg-white rounded-lg shadow dark:bg-gray-700">
            <button
              type="button"
              className="absolute top-3 right-3 text-gray-400 hover:text-gray-900 dark:hover:text-white rounded-lg p-1.5"
              onClick={onClose}
            >
              <svg className="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
                <path fillRule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clipRule="evenodd"></path>
              </svg>
            </button>

            <div className="p-6">
              <h3 className="text-xl font-semibold text-gray-900 dark:text-white mb-4">
                Nhập mã OTP
              </h3>
              <p className="text-sm text-gray-600 dark:text-gray-300 mb-4">
                Mã OTP đã được gửi đến số điện thoại {phoneNumber}
              </p>
              <div className="space-y-6">
                <div>
                  <label 
                    htmlFor="otp" 
                    className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
                  >
                    Mã OTP
                  </label>
                  <input
                    type="text"
                    id="otp"
                    className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:text-white"
                    placeholder="Nhập mã OTP"
                    required
                    value={otp}
                    onChange={(e) => setOtp(e.target.value)}
                    maxLength={6}
                  />
                </div>

                <button
                  type="button"
                  onClick={handleClick}
                  className="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                >
                  Xác nhận
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <ResetPasswordDialog 
        isOpen={isOpenResetPassword}
        onClose={() => setIsOpenResetPassword(false)}
        phoneNumber={phoneNumber}
      />
    </div>
  );
};

export default OTPDialog;