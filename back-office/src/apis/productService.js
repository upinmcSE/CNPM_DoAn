import axiosClient from "./axiosClient";

const getProducts = async (page = 1, size = 10) => {
    try{
        const res = await axiosClient.get(`/products/getall?page=${page}&size=${size}`)
        return res.data
    }catch(error){
        console.error("Error fetching products: ", error);
        throw error;
    }
}

const addProduct = async (product) => {
    try {
        const res = await axiosClient.post("/products/add", product);
        console.log("Product added successfully:", res.data);
        return res.data;
    } catch (error) {
        console.error("Error adding product:", error);
        throw error;
    }
};

export { getProducts, addProduct }
