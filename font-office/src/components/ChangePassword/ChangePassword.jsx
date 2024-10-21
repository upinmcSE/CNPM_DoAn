import React, { useState } from "react";
import { changePassword } from "../../apis/authService";

const ChangePassword = ({ isOpen, onClose, onSubmit }) => {
  const [formData, setFormData] = useState({
    oldPassword: "",
    newPassword: "",
    confirmPassword: "",
  });

  const [error, setError] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try{
        // Kiểm tra mật khẩu mới và mật khẩu nhập lại có khớp không
    if (formData.newPassword !== formData.confirmPassword) {
        setError("Mật khẩu mới và xác nhận mật khẩu không khớp.");
        return;
    }
    var response = await changePassword(formData.oldPassword, formData.newPassword);
    onClose();

    }catch (error) {
      setError(error.response?.data?.message || "Có lỗi xảy ra khi đổi mật khẩu");
    }
  };

  if (!isOpen) return null; // Không hiển thị nếu dialog không được mở

  return (
    <div className="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50">
      <div className="bg-white rounded-lg shadow-lg p-6 w-96">
        <h2 className="text-xl font-bold mb-4">Đổi mật khẩu</h2>
        {error && <p className="text-red-500 mb-4">{error}</p>}

        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label className="block text-gray-700">Mật khẩu cũ</label>
            <input
              type="password"
              name="oldPassword"
              value={formData.oldPassword}
              onChange={handleChange}
              className="border text-black border-gray-300 rounded w-full p-2"
              placeholder="Nhập mật khẩu cũ"
              required
            />
          </div>

          <div className="mb-4">
            <label className="block text-gray-700">Mật khẩu mới</label>
            <input
              type="password"
              name="newPassword"
              value={formData.newPassword}
              onChange={handleChange}
              className="border text-black border-gray-300 rounded w-full p-2"
              placeholder="Nhập mật khẩu mới"
              required
            />
          </div>

          <div className="mb-4">
            <label className="block text-gray-700">Nhập lại mật khẩu mới</label>
            <input
              type="password"
              name="confirmPassword"
              value={formData.confirmPassword}
              onChange={handleChange}
              className="border text-black border-gray-300 rounded w-full p-2"
              placeholder="Nhập lại mật khẩu mới"
              required
            />
          </div>

          <div className="flex justify-between">
            <button
              type="button"
              className="bg-gray-300 text-black px-4 py-2 rounded"
              onClick={onClose}
            >
              Hủy
            </button>
            <button
              type="submit"
              className="bg-blue-500 text-white px-4 py-2 rounded"
            >
              Đổi mật khẩu
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ChangePassword;
