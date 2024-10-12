import axiosClient from "./axiosClient";

debugger
const getProducts = async () => {
    try {
        const res = await axiosClient.get("/products/getall");
        return res.data;
    } catch (error) {
        console.error("Error fetching products: ", error);
        throw error;
    }
}

export { getProducts };