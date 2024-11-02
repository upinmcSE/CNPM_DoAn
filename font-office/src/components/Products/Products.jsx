import { useEffect, useState } from 'react';
import ProductDialog from '../ProductDialog/ProductDialog';
import { getProductCategory } from '../../apis/productService';

const categoryData = [
  { id: 1, name: "COFFEE" },
  { id: 2, name: "MILK" },
  { id: 3, name: "TEA" },
  { id: 4, name: "SMOOTHIE" },
  { id: 5, name: "JUICE" },
];

const Products = () => {
  const [selectedCategory, setSelectedCategory] = useState("COFFEE");
  const [currentPage, setCurrentPage] = useState(1);
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [totalPages, setTotalPages] = useState(1);
  const [productsData, setProductsData] = useState([]);

  const itemsPerPage = 6;
  // Load danh sách sản phẩm theo trang
  useEffect(() => {
    loadProducts(currentPage);
  }, [currentPage]);

  const loadProducts = async (page) => {
    try {
      const res = await getProductCategory(selectedCategory, page, itemsPerPage);
      console.log("res: ", res.totalElements)
      if (res && res.data) {
        setProductsData(res.data);
        setTotalPages(res.totalPages);
        
      } else {
        console.warn("No data returned from API");
      }
    } catch (error) {
      console.error("Error fetching products:", error);
    }
  };

  const handleCategoryChange = (category) => {
    setSelectedCategory(category);
    setCurrentPage(1); // Reset page when changing category
  };

  const handleProductClick = (product) => {
    setSelectedProduct(product);
  };

  const handleCloseDialog = () => {
    setSelectedProduct(null);
  };

  useEffect(() => {
    loadProducts(currentPage);
  }, [selectedCategory, currentPage]);

  const currentItems = productsData;

  const handleBuy = (product, quantity) => {
    console.log(`Buying ${quantity} of ${product.name}`);
    // Implement your buy logic here
  };

  const handleAddToCart = (product, quantity) => {
    console.log(`Adding ${quantity} of ${product.name} to cart`);
    // Implement your add to cart logic here
  };

  return (
    <>
      <span id="products"></span>
      <div className="flex bg-slate-200 ">
        {/* Sidebar */}
        <div className="w-1/4 bg-gray-400 p-4">
          <h2 className="font-bold text-center text-lg text-amber-800 mb-4">Chọn loại sản phẩm</h2>
          {categoryData.map((category) => (
            <button
              key={category.id}
              onClick={() => handleCategoryChange(category.name)}
              className={`w-full h-20 text-center text-lg p-2 mb-10 rounded-xl transition-colors duration-300 ${selectedCategory === category.name ? "bg-primary text-white" : "bg-white text-black"} hover:bg-primary`}
            >
              {category.name}
            </button>
          ))}
        </div>

        {/* Danh sách sản phẩm */}
        <div className="w-3/4 p-4">
          <h2 className="font-bold mb-4">{selectedCategory}</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 place-items-center">
            {currentItems.map((product) => (
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

          {/* Phân trang */}
          <div className="flex justify-center mt-4">
            <button
              onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 1))}
              disabled={currentPage === 1}
              className="px-4 py-2 bg-gray-300 rounded-l disabled:opacity-50 transition-opacity duration-300"
            >
              Trước
            </button>
            <span className="flex items-center px-4">{`Trang ${currentPage} / ${totalPages}`}</span>
            <button
              onClick={() => setCurrentPage((prev) => Math.min(prev + 1, totalPages))}
              disabled={currentPage === totalPages}
              className="px-4 py-2 bg-gray-300 rounded-r disabled:opacity-50 transition-opacity duration-300"
            >
              Sau
            </button>
          </div>
        </div>
      </div>

      {/* Hiển thị dialog nếu có sản phẩm được chọn */}
      {selectedProduct && (
        <ProductDialog
          product={selectedProduct}
          onClose={handleCloseDialog}
          onBuy={handleBuy}
          onAddToCart={handleAddToCart}
        />
      )}
    </>
  );
};

export default Products;
