import axiosClient from "./axiosClient";

const createOrder = async () => {
    try {
        const token = localStorage.getItem('authToken');
        console.log("tokenxxx: ", token);

        
        const res = await axiosClient.post(`/orders/create`, {}, 
            {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });

        console.log("res: ", res);
        return res.data;

    } catch (error) {
        console.error("Error creating order: ", error);
        throw error;
    }
}

export { createOrder }
