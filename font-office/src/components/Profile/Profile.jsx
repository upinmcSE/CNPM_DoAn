import React, { useState, useEffect } from "react";
import { getById, update } from "../../apis/customerService";

const Profile = ({ isOpen, onClose, onUpdate }) => {
  const [formData, setFormData] = useState({
    fullName: "",
    gender: "",
    age: "",
    email: "",
    memberLevel: "",
    purchasePoints: 0,
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchCustomer = async () => {
      if (!isOpen) return;
      setLoading(true);
      try {
        const data = await getById();
        console.log("data111: ", data.point);
        setFormData({
          fullName: data.fullName || "",
          gender: data.gender === true ? "male" : "female",
          age: data.age || "",
          email: data.email || "",
          memberLevel: data.menberLV || "",  
          purchasePoints: data.point || 0,  
        });
      } catch (err) {
        setError("Không thể tải thông tin khách hàng.");
      } finally {
        setLoading(false);
      }
    };

    fetchCustomer();
  }, [isOpen]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleUpdate = async () => {
    const updateRequest = {
      ...formData,
      gender: formData.gender === "male",
    };
  
    try {
      const res = await update(updateRequest);
      console.log(res) // Gọi API cập nhật
      onUpdate(updateRequest);
      onClose();
    } catch (err) {
      console.error("Cập nhật thất bại:", err);
    }
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50">
      <div className="bg-white rounded-lg shadow-lg p-6 w-96">
        <h2 className="text-xl font-bold mb-4">Profile</h2>

        {loading ? (
          <p>Đang tải...</p>
        ) : error ? (
          <p className="text-red-500">{error}</p>
        ) : (
          <>
            <div className="mb-4">
              <label className="block text-gray-700">Họ và tên</label>
              <input
                type="text"
                name="fullName"
                value={formData.fullName}
                onChange={handleChange}
                className="border text-black border-gray-300 rounded w-full p-2"
                placeholder="Nhập họ và tên"
              />
            </div>

            <div className="mb-4">
              <label className="block text-gray-700">Giới tính</label>
              <select
                name="gender"
                value={formData.gender}
                onChange={handleChange}
                className="border text-black border-gray-300 rounded w-full p-2"
              >
                <option value="">Chọn giới tính</option>
                <option value="male">Nam</option>
                <option value="female">Nữ</option>
              </select>
            </div>

            <div className="mb-4">
              <label className="block text-gray-700">Tuổi</label>
              <input
                type="number"
                name="age"
                value={formData.age}
                onChange={handleChange}
                className="border text-black border-gray-300 rounded w-full p-2"
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
                className="border text-black border-gray-300 rounded w-full p-2"
                placeholder="Nhập email"
              />
            </div>

            <div className="mb-4">
              <label className="block text-gray-700">Cấp độ thành viên</label>
              <span className="block border text-black border-gray-300 rounded w-full p-2 bg-gray-100">
                {formData.memberLevel}
              </span>
            </div>

            <div className="mb-4">
              <label className="block text-gray-700">Điểm mua hàng</label>
              <span className="block border text-black border-gray-300 rounded w-full p-2 bg-gray-100">
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
          </>
        )}
      </div>
    </div>
  );
};

export default Profile;
