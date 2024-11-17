import axiosClient from "./axiosClient";

const getOrder = async (page = 1, size = 10) => {
    try {
        const token = localStorage.getItem('authToken');
        const res = await axiosClient.get(`/payment/get-all?page=${page}&size=${size}`,
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            }
        );
        return res.data;
    }catch(error){
        console.log(error)
    }
}

const completedOrder = async (paymentId) => {
    console.log(paymentId);
    try {
        const token = localStorage.getItem('authToken');
        const res = await axiosClient.post(
            `/payment/complete?choice=accept&paymentId=${paymentId}`,{},
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            }
        );
        return res.data;
    } catch (error) {
        console.log(error);
    }
};

export { getOrder, completedOrder }