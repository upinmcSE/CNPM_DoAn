import React, { useState } from "react";

const HistoryList = ({ isOpen, onClose, orders }) => {
  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 flex items-center justify-center bg-gray-900 bg-opacity-50">
      <div className="bg-white w-full max-w-4xl p-6 rounded-lg shadow-lg">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-xl font-semibold">Order History</h2>
          <button
            onClick={onClose}
            className="text-gray-500 hover:text-gray-700"
          >
            ✖
          </button>
        </div>
        {orders && orders.length > 0 ? (
          <div className="space-y-4">
            {orders.map((order) => (
              <div
                key={order.id}
                className="border border-gray-300 rounded-lg p-4"
              >
                <div className="flex justify-between items-center">
                  <h3 className="text-lg font-semibold">Order #{order.id}</h3>
                  <span className="text-sm text-gray-600">
                    {order.createdDate}
                  </span>
                </div>
                <p className="text-sm text-gray-500">
                  Status:{" "}
                  <span
                    className={`${
                      order.status === "COMPLETED"
                        ? "text-green-500"
                        : "text-yellow-500"
                    } font-semibold`}
                  >
                    {order.status}
                  </span>
                </p>
                <div className="mt-2">
                  <h4 className="font-medium">Order Lines:</h4>
                  <ul className="list-disc list-inside">
                    {order.orderLines.map((line) => (
                      <li key={line.id} className="text-sm text-gray-700">
                        {line.productName} x {line.amount}
                      </li>
                    ))}
                  </ul>
                </div>
                <p className="mt-2 text-sm font-medium">
                  Total Price:{" "}
                  <span className="text-gray-800">
                    {order.totalPrice.toLocaleString("vi-VN")} VND
                  </span>
                </p>
              </div>
            ))}
          </div>
        ) : (
          <p className="text-center text-gray-500">No orders found.</p>
        )}
      </div>
    </div>
  );
}

export default HistoryList