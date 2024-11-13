import React, { useState, useEffect } from "react";
import Img2 from "../../assets/coffee2.png";
import ProductDialog from "../ProductDialog/ProductDialog";
import { getProductOutstanding } from "../../apis/productService";

const ServicesData = [
  {
    id: 1,
    img: Img2,
    name: "Espresso",
    description:
      "Lorem ipsum dolor sit amet ipsum dolor sit ametipsum dolor sit amet ipsum dolor sit amet.",
    aosDelay: "100",
  },
  {
    id: 2,
    img: Img2,
    name: "Americano",
    description:
      "Lorem ipsum dolor sit amet ipsum dolor sit ametipsum dolor sit amet ipsum dolor sit amet",
    aosDelay: "300",
  },
  {
    id: 3,
    img: Img2,
    name: "Cappuccino",
    description:
      "Lorem ipsum dolor sit amet ipsum dolor sit ametipsum dolor sit amet ipsum dolor sit amet",
    aosDelay: "500",
  },
];




const Services = () => {
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [outstandingProducts, setOutstandingProducts] = useState([]);

  useEffect(() => {
    loadProducts();
  },[1]);

  const loadProducts = async () => {
    try {
      const res = await getProductOutstanding();
      console.log("res-outstanding: ", res.totalElements)
      if (res && res.data) {
        setOutstandingProducts(res.data);
      } else {
        console.warn("No data returned from API");
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
            {ServicesData.map((service) => (
              <div
                key={service.id}
                data-aos="fade-up"
                data-aos-delay={service.aosDelay}
                className="rounded-2xl bg-white hover:bg-primary hover:text-white relative shadow-xl duration-high group max-w-[300px]"
                onClick={() => handleProductClick(service)} // Thêm sự kiện click
              >
                <div className="h-[122px]">
                  <img
                    src={service.img}
                    alt=""
                    className="max-w-[200px] block mx-auto transform -translate-y-14 group-hover:scale-105 group-hover:rotate-6 duration-300"
                  />
                </div>
                <div className="p-4 text-center">
                  <h1 className="text-xl font-bold">{service.name}</h1>
                  <p className="text-gray-500 group-hover:text-white duration-high text-sm line-clamp-2">
                    {service.description}
                  </p>
                </div>
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
                <img src={`http://localhost:8081/coffee/api/v1/products/images/${product.urlImage}`} alt={product.name} className="w-full h-40 object-cover mb-2 rounded-lg" />
                <h3 className="font-semibold text-lg text-center group-hover:text-primary">{product.name}</h3>
                <p className="text-gray-500 text-center">{product.description}</p>
                <p className="text-gray-500 text-center group-hover:text-white transition-colors duration-300">{product.price} VNĐ</p>
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
