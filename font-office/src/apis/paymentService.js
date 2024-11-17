import axiosClient from "./axiosClient";

const payOrder = async (paymentMethod, paymentInfo) => {
    try{
        const token = localStorage.getItem("Token");
        const url = `/payment/${paymentMethod}?phone=${paymentInfo.phone}&address=${paymentInfo.address}`;
        const response = await axiosClient.get(url, {
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            
        })
        return response.data;
    }catch(error){
        console.error("Error paying order: ", error);
        throw error;
    }
}

const payOrderCash = async ( paymentInfo) => {
    try{
        const token = localStorage.getItem("Token");
        const url = `/payment/cash?phone=${paymentInfo.phone}&address=${paymentInfo.address}`;
        const response = await axiosClient.post(url, {
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json'
            },
            
        })
        return response.data;
    }catch(error){
        console.error("Error paying order: ", error);
        throw error;
    }
}

export { payOrder, payOrderCash };