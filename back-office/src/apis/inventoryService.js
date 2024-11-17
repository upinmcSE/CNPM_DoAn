import axiosClient from "./axiosClient";

const getQuantity = async (page = 1, size = 10) => {
    try {
        const token = localStorage.getItem('authToken');
        const res = await axiosClient.get(`/inventory/get-all?page=${page}&size=${size}`, {
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

const upQuantity = async (productId, quantity) => {
    try {
        const token = localStorage.getItem('authToken');
        const res = await axiosClient.post(`/inventory/up-quantity?productId=${productId}&quantity=${quantity}`, {},
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            }
        );
        return res.data;
    } catch (error) {
        console.error("Error fetching products: ", error);
        throw error;
    }
};


export { getQuantity, upQuantity }