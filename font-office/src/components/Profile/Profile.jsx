import React, { useState } from "react";

const Profile = ({ isOpen, onClose, userInfo, onUpdate }) => {
  const [formData, setFormData] = useState({
    fullName: userInfo.fullName || "",
    gender: userInfo.gender || "",
    age: userInfo.age || "",
    email: userInfo.email || "",
    memberLevel: userInfo.memberLevel || "",
    purchasePoints: userInfo.purchasePoints || "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleUpdate = () => {
    onUpdate(formData);
    onClose(); // Đóng dialog sau khi cập nhật
  };

  return (
    isOpen && (
      <div className="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50">
        <div className="bg-white rounded-lg shadow-lg p-6 w-96">
          <h2 className="text-xl font-bold mb-4">Profile</h2>
          <div className="mb-4">
            <label className="block text-gray-700">Họ và tên</label>
            <input
              type="text"
              name="fullName"
              value={formData.fullName}
              onChange={handleChange}
              className="border border-gray-300 rounded w-full p-2"
              placeholder="Nhập họ và tên"
            />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700">Giới tính</label>
            <select
              name="gender"
              value={formData.gender}
              onChange={handleChange}
              className="border border-gray-300 rounded w-full p-2"
            >
              <option value="">Chọn giới tính</option>
              <option value="male">Nam</option>
              <option value="female">Nữ</option>
              <option value="other">Khác</option>
            </select>
          </div>
          <div className="mb-4">
            <label className="block text-gray-700">Tuổi</label>
            <input
              type="number"
              name="age"
              value={formData.age}
              onChange={handleChange}
              className="border border-gray-300 rounded w-full p-2"
              placeholder="Nhập tuổi"
            />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700">Email</label>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              className="border border-gray-300 rounded w-full p-2"
              placeholder="Nhập email"
            />
          </div>
          <div className="mb-4">
            <label className="block text-gray-700">Cấp độ thành viên</label>
            <span className="block border border-gray-300 rounded w-full p-2 bg-gray-100">
              {formData.memberLevel}
            </span>
          </div>
          <div className="mb-4">
            <label className="block text-gray-700">Điểm mua hàng</label>
            <span className="block border border-gray-300 rounded w-full p-2 bg-gray-100">
              {formData.purchasePoints}
            </span>
          </div>
          <div className="flex justify-between">
            <button
              className="bg-gray-300 text-black px-4 py-2 rounded"
              onClick={onClose}
            >
              Hủy
            </button>
            <button
              className="bg-blue-500 text-white px-4 py-2 rounded"
              onClick={handleUpdate}
            >
              Cập nhật
            </button>
          </div>
        </div>
      </div>
    )
  );
};

export default Profile;
