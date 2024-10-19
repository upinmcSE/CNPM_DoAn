import React, { useState } from "react";
import Img2 from "../../assets/coffee2.png";
import ProductDialog from "../ProductDialog/ProductDialog";


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

const BestSellerData = [
  {
    id: 1,
    img: Img2,
    name: "Latte",
    description:
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
    aosDelay: "0",
  },
  {
    id: 2,
    img: Img2,
    name: "Mocha",
    description:
      "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
    aosDelay: "0",
  },
  {
    id: 3,
    img: Img2,
    name: "Macchiato",
    description:
      "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris.",
    aosDelay: "0",
  },
];

const Services = () => {
  const [selectedProduct, setSelectedProduct] = useState(null);

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
            {BestSellerData.map((product) => (
              <div
                key={product.id}
                data-aos="fade-up"
                data-aos-delay={product.aosDelay}
                className="rounded-2xl bg-white hover:bg-primary hover:text-white relative shadow-xl duration-high group max-w-[300px]"
                onClick={() => handleProductClick(product)} // Thêm sự kiện click
              >
                <div className="h-[122px]">
                  <img
                    src={product.img}
                    alt=""
                    className="max-w-[200px] block mx-auto transform -translate-y-14 group-hover:scale-105 group-hover:rotate-6 duration-300"
                  />
                </div>
                <div className="p-4 text-center">
                  <h1 className="text-xl font-bold">{product.name}</h1>
                  <p className="text-gray-500 group-hover:text-white duration-high text-sm line-clamp-2">
                    {product.description}
                  </p>
                </div>
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
