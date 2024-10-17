import axiosClient from "./axiosClient";

const getProducts = async (page = 1, size = 10) => {
    try {
        const token = localStorage.getItem('token');
        const res = await axiosClient.get(`/products/getall?page=${page}&size=${size}`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        return res.data;
    } catch (error) {
        console.error("Error fetching products: ", error);
        throw error;
    }
}

const addProduct = async (product) => {
    try {
        console.log("Product:", product);
        const token = localStorage.getItem('token');
        const res = await axiosClient.post("/products/add", product, {
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'multipart/form-data', // Có thể bỏ qua
            }
        });
        console.log("Product added successfully:", res.data);
        return res.data;
    } catch (error) {
        console.error("Error adding product:", error.response ? error.response.data : error.message);
        throw error;
    }
};

export { getProducts, addProduct }