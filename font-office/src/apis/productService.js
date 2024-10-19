import axiosClient from "../apis/axiosClient";

const getProductCategory = async (category, page, size) => {
    try{
        console.log(`/products/category?page=${page}&size=${size}&category=${category}`)
        const res = await axiosClient.get(`/products/category?page=${page}&size=${size}&category=${category}`);
        console.log("res: ", res)
        return res.data
    }catch(error){
        console.error("Error fetching products: ", error);
        throw error
    }

}


export {getProductCategory}