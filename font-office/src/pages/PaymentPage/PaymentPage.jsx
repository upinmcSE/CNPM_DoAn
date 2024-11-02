import React, { useState, useEffect } from 'react';
import { getCart } from '../../apis/orderService';
import { useNavigate  } from 'react-router-dom';
const PaymentPage = () => {
    const [cartItems, setCartItems] = useState([]);
    const [paymentMethod, setPaymentMethod] = useState('cash'); // thanh toan mac dinh la tien mat
    const [deliveryAddress, setDeliveryAddress] = useState('');
    const navigate = useNavigate();

    // goi api de lay danh sach san pham trong gio hang
    useEffect(() => {
        const loadCartItems = async () => {
            try {
                const items = await getCart();
                setCartItems(items);
            } catch (error) {
                console.error("Error loading cart items: ", error);
            }
        };

        loadCartItems();
    }, []); // chi goi api 1 lan khi component duoc render

    const handlePayment = () => {
        // xu ly thanh toan tuy theo phuong thuc thanh toan
        console.log(`Thanh toán thành công với phương thức: ${paymentMethod}`);
    };
    const handleGoHome = () => {
        navigate('/'); 
    };

    // tinh tong so tien cua gio hang
    const totalAmount = cartItems.reduce((acc, item) => {
        return acc + (item.product.price * item.amount);
    }, 0);

    return (
        <div className="max-w-4xl mx-auto p-6">
            <h1 className="text-2xl font-bold mb-4">Thanh toán đơn hàng</h1>
            <div className="bg-white shadow-md rounded-lg p-4 mb-4">
                <h2 className="text-xl font-semibold">Thông tin đơn hàng</h2>
                <ul className="space-y-2">
                    {cartItems.map((item) => (
                        <li key={item.id} className="flex justify-between">
                            <span>{item.product.name}</span>
                            <span>{(item.product.price).toLocaleString('vi-VN')} VND x {item.amount}</span>
                        </li>
                    ))}
                </ul>
                <div className="mt-4 flex justify-between font-semibold">
                    <span>Tổng cộng:</span>
                    <span>{totalAmount.toLocaleString('vi-VN')} VND</span>
                </div>
            </div>

            {/* Input for delivery address */}
            <div className="mt-4">
                <label htmlFor="deliveryAddress" className="block text-sm font-medium text-gray-700 mb-2">
                    Địa chỉ nhận hàng
                </label>
                <input
                    type="text"
                    id="deliveryAddress"
                    value={deliveryAddress}
                    onChange={(e) => setDeliveryAddress(e.target.value)} // Update state on change
                    className="block w-full border-gray-300 rounded-md shadow-sm focus:border-primary focus:ring focus:ring-primary focus:ring-opacity-50 p-2"
                    placeholder="Nhập địa chỉ nhận hàng"
                />
            </div>

            <h2 className="text-xl font-semibold mb-2">Phương thức thanh toán</h2>
            <div className="flex flex-col space-y-2">
                <label className="inline-flex items-center">
                    <input
                        type="radio"
                        className="form-radio"
                        value="cash"
                        checked={paymentMethod === 'cash'}
                        onChange={() => setPaymentMethod('cash')}
                    />
                    <span className="ml-2">Tiền mặt</span>
                </label>
                <label className="inline-flex items-center">
                    <input
                        type="radio"
                        className="form-radio"
                        value="momo"
                        checked={paymentMethod === 'momo'}
                        onChange={() => setPaymentMethod('momo')}
                    />
                    <span className="ml-2">Ví điện tử Momo</span>
                </label>
                <label className="inline-flex items-center">
                    <input
                        type="radio"
                        className="form-radio"
                        value="vnpay"
                        checked={paymentMethod === 'vnpay'}
                        onChange={() => setPaymentMethod('vnpay')}
                    />
                    <span className="ml-2">Ví điện tử VNPay</span>
                </label>
            </div>

            <div className="mt-6">
                <button
                    onClick={handleGoHome}
                    className="bg-gray-300 text-black px-6 py-3 rounded-full hover:bg-gray-400 transition duration-200 mb-4"
                >
                    Quay về trang chủ
                </button>

                <button
                    onClick={handlePayment}
                    className="bg-primary text-white px-6 py-3 rounded-full hover:bg-primary/80 transition duration-200 mt-4"  // Added mt-4 for space
                >
                    Thanh toán
                </button>
            </div>

        </div>
    );
};

export default PaymentPage;
