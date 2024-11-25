import React, { useState, useEffect } from "react";
import Img2 from "../../assets/coffee2.png";
import ProductDialog from "../ProductDialog/ProductDialog";
import { getProductOutstanding, getProductRecommend } from "../../apis/productService";

const ServicesData = [
  {
    id: 1,
    img: Img2,
    name: "Espresso",
    description:
      "Lorem ipsum dolor sit amet ipsum dolor sit ametipsum dolor sit amet ipsum dolor sit amet.",
    price: "100",
    category: "COFFEE",
  },
  {
    id: 2,
    img: Img2,
    name: "Americano",
    description:
      "Lorem ipsum dolor sit amet ipsum dolor sit ametipsum dolor sit amet ipsum dolor sit amet",
    price: "300",
    category: "COFFEE",
  },
  {
    id: 3,
    img: Img2,
    name: "Cappuccino",
    description:
      "Lorem ipsum dolor sit amet ipsum dolor sit ametipsum dolor sit amet ipsum dolor sit amet",
    price: "500",
    category: "COFFEE",
  },
];

const Services = () => {
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [outstandingProducts, setOutstandingProducts] = useState([]);
  const [recommendProducts, setRecomendProducts] = useState([]);

  useEffect(() => {
    loadProducts();
  }, []);

  const loadProducts = async () => {
    try {
      // Load các sản phẩm nổi bật mà không cần kiểm tra đăng nhập
      const res = await getProductOutstanding();
      if (res && res.data) {
        setOutstandingProducts(res.data);
      } else {
        console.warn("No data returned from API");
      }

      // Kiểm tra token để gọi API sản phẩm đề xuất nếu người dùng đã đăng nhập
      const token = localStorage.getItem("Token");

      if (token) {
        const recommendedRes = await getProductRecommend();
        console.log("recommendedRes: ", recommendedRes);
        if (recommendedRes && recommendedRes.result) {
          setRecomendProducts(recommendedRes.result.data);
        }
      }
    } catch (error) {
      console.error("Error fetching products:", error);
    }
  };

  const handleProductClick = (product) => {
    setSelectedProduct(product);
  };

  const handleCloseDialog = () => {
    setSelectedProduct(null);
  };

  return (
    <>
      <span id="services"></span>
      {/* Section: Best Coffee For You */}
      <div className="py-10">
        <div className="container">
          <div className="text-center mb-20">
            <h1 className="text-4xl font-bold font-cursive text-gray-800">
              Best Coffee For You
            </h1>
          </div>

          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-14 md:gap-5 place-items-center">
            {recommendProducts.length > 0
              ? recommendProducts.map((service) => (
                <div
                  key={service.id}  
                  className="flex flex-col justify-between w-64 h-80 rounded-2xl bg-white border border-gray-300 p-4 shadow hover:shadow-lg transition-shadow duration-300 group relative cursor-pointer"
                  onClick={() => handleProductClick(service)}  
                  data-aos-delay="100"
                >
                  <img
                    src={`http://localhost:8081/coffee/api/v1/products/images/${service.urlImage}`}  
                    alt={service.name}  
                    className="w-full h-40 object-cover mb-2 rounded-lg"
                  />
                  <h3 className="font-semibold text-lg text-center group-hover:text-primary">
                    {service.name}  
                  </h3>
                  <p className="text-gray-500 text-center">{service.description}</p>
                  <p className="text-gray-500 text-center group-hover:text-white transition-colors duration-300">
                    {service.price} VNĐ  
                  </p>
                </div>
              ))
              : ServicesData.map((service) => (
                <div
                  key={service.id}  
                  className="flex flex-col justify-between w-64 h-80 rounded-2xl bg-white border border-gray-300 p-4 shadow hover:shadow-lg transition-shadow duration-300 group relative cursor-pointer"
                  onClick={() => handleProductClick(service)}  
                  data-aos="fade-up"
                  data-aos-delay="100"
                >
                  <img
                    src={Img2}
                    alt=""
                    className="max-w-[200px] block mx-auto transform -translate-y-14 group-hover:scale-105 group-hover:rotate-6 duration-300"
                  />
                  <h3 className="font-semibold text-lg text-center group-hover:text-primary">
                    {service.name}  
                  </h3>
                  <p className="text-gray-500 text-center">{service.description}</p>
                  <p className="text-gray-500 text-center group-hover:text-white transition-colors duration-300">
                    {service.price} VNĐ  
                  </p>
                </div>
              ))}
          </div>
        </div>
      </div>

      {/* Section: Best Seller */}
      <div className="py-10">
        <div className="container">
          <div className="text-center mb-20">
            <h1 className="text-4xl font-bold font-cursive text-gray-800">
              Best Sellers
            </h1>
          </div>

          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-14 md:gap-5 place-items-center">
            {outstandingProducts.map((product) => (
              <div
                key={product.id}
                className="flex flex-col justify-between w-64 h-80 rounded-2xl bg-white border border-gray-300 p-4 shadow hover:shadow-lg transition-shadow duration-300 group relative cursor-pointer"
                onClick={() => handleProductClick(product)}
                data-aos="fade-up"
                data-aos-delay="100"
              >
                <img
                  src={`http://localhost:8081/coffee/api/v1/products/images/${product.urlImage}`}
                  alt={product.name}
                  className="w-full h-40 object-cover mb-2 rounded-lg"
                />
                <h3 className="font-semibold text-lg text-center group-hover:text-primary">
                  {product.name}
                </h3>
                <p className="text-gray-500 text-center">{product.description}</p>
                <p className="text-gray-500 text-center group-hover:text-white transition-colors duration-300">
                  {product.price} VNĐ
                </p>
              </div>
            ))}
          </div>
        </div>
      </div>

      {/* Product Dialog */}
      {selectedProduct && (
        <ProductDialog product={selectedProduct} onClose={handleCloseDialog} />
      )}
    </>
  );
};

export default Services;
