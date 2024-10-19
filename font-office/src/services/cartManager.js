// cartManager.js
const cart = [];

// Hàm thêm sản phẩm vào giỏ hàng
export const addToCart = (product, quantity) => {
  const existingProduct = cart.find((item) => item.id === product.id);
  
  if (existingProduct) {
    // Nếu sản phẩm đã tồn tại, tăng số lượng
    existingProduct.quantity += quantity;
  } else {
    // Nếu chưa tồn tại, thêm sản phẩm mới vào giỏ
    cart.push({ ...product, quantity });
  }

  console.log(cart);
};

// Hàm lấy danh sách giỏ hàng
export const getCart = () => cart;
