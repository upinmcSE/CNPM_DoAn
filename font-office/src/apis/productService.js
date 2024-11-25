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

const getProductOutstanding = async () => {
    try{
        const res = await axiosClient.get('/products/outstanding');
        console.log("res: ", res);
        return res.data;
    }catch(error){
        console.error("Error fetching outstanding products: ", error);
        throw error;
    }
}

const getProductRecommend = async () => {
    try{
        const token = localStorage.getItem("Token");
        const res = await axiosClient.get(`/products/recommend?token=${token}`, 
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        console.log("res111: ", res);
        return res.data;
    }catch(error){
        console.error("Error fetching recommend products: ", error);
        throw error;
    }
}


export {getProductCategory, getProductOutstanding, getProductRecommend}