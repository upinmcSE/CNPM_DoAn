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
        localStorage.setItem('orderId', res.data)
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
        console.log("orderId: ", orderId);
        console.log("orderline: ", orderline);
        const res = await axiosClient.post(`/orders/add-line/${orderId}`,orderline, {
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

const getCart = async () => {
    try {
        const token = localStorage.getItem('authToken');
        if (!token) {
            console.error("No auth token found");
            return []; // Hoặc có thể xử lý một cách thích hợp
        }

        const response = await axiosClient.get(`/orders/getOrder`, {
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json',
            }
        });

        console.log(response.data.orderLineCaches); 
        return response.data.orderLineCaches; 
    } catch (error) {
        console.error('Error fetching order lines:', error);
        return []; // Trả về một mảng rỗng trong trường hợp có lỗi
    }
};

const removeItemFromOrder = async (orderId, orderLineId) => {
    try{
        const token = localStorage.getItem('authToken');
        const res = await axiosClient.delete(`/orders/remove-line/${orderId}/${orderLineId}`, {
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        })
        console.log("res: ", res);
    }catch(error){
        console.error("Error removing product from order: ", error);
        throw error;
    }
}

export { createOrder, addProductToOrder, removeItemFromOrder, getCart }
