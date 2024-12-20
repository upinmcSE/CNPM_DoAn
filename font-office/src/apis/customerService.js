import axiosClient from "./axiosClient";


const getById = async () => {
    try{
        const token = localStorage.getItem('Token');
        console.log("token11: ", token);
        const response = await axiosClient.get(`/customers/get`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        console.log("response: ", response.data)
        return response.data;

    }catch(error){
        console.error("Error fetching customer: ", error);
        throw error
    }
}

const update = async (data) => {
    try{
        const token = localStorage.getItem('Token');
        const response = await axiosClient.put(`/customers/update`, data, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        console.log("response: ", response.data)
        return response.data;

    }catch(error){
        console.error("Error updating customer: ", error);
        throw error
    }
}


export {getById, update}