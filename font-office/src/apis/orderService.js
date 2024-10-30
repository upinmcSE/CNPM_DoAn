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

const addProductToOrder = async (orderline, orderId) => {
    try{
        const token = localStorage.getItem('authToken')
        const res = await axiosClient.post(`/orders/add-line/${orderId}`, {orderline}, {
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })

        console.log("res: ", res);
        return res.data;

    }catch(error){
        console.error("Error adding product to order: ", error);
        throw error;
    }
}

const removeItemFromOrder = async (orderId, productId) => {
    try{
        const token = localStorage.getItem('authToken');
    }catch(error){
        console.error("Error removing product from order: ", error);
        throw error;
    }
}

export { createOrder }
