import axiosClient from "./axiosClient";

const getCustomers = async (page=1, size = 10) => {
    try{
        const token = localStorage.getItem('authToken');
        const res = await axiosClient.get(`/customers/getall?page=${page}&size=${size}`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        return res.data;
    }catch(error){
        console.error("Error get customers: ", error);
        throw error;
    }
}

const updateLevel = async (id) => {
    try{
        const token = localStorage.getItem('authToken');
        const res = await axiosClient.put(`/customers/update-level/${id}`, null, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        alert("Update level successfully");
        return res.data;
    }catch(error){
        console.error("Error update level: ", error);
        throw error;
    }
}

export { getCustomers, updateLevel };