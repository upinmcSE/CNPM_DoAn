import axiosClient from "./axiosClient";

const getEmployees = async (page=1, size = 10) => {
    try{
        const token = localStorage.getItem('token');
        const res = await axiosClient.get(`/employees/getall?page=${page}&size=${size}`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        console.log("Employees: ", res.data);
        return res.data;
    }catch(error){
        console.error("Error get employees: ", error);
        throw error;
    }
}

const addEmployee = async (employee) => {
    try{
        const token = localStorage.getItem('token');
        const res = await axiosClient.post("/employees/add", employee, {
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'json',
            }
        });
        console.log("Employee added successfully: ", res.data);
        return res.data;
    }catch(error){
        console.error("Error adding employee: ", error.response ? error.response.data : error.message);
        throw error;
    }
}

const updateEmployee = async (id, employee) => {    
    try{
        const token = localStorage.getItem('token');
        const res = await axiosClient.put(`/employees/update-info/${id}`, employee, {
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'json',
            }
        });
        console.log("Employee updated successfully: ", res.data);
        return res.data;
    }catch(error){
        console.error("Error updating employee: ", error.response ? error.response.data : error.message);
        throw error;
    }
}

const deleteEmployee = async (id) => {      
    try{
        const token = localStorage.getItem('token');
        const res = await axiosClient.delete(`/employees/delete/${id}`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        console.log("Employee deleted successfully: ");
        alert("Employee deleted successfully");
    }catch(error){
        console.error("Error deleting employee: ", error.response ? error.response.data : error.message);
        throw error;
    }
}

export { getEmployees, addEmployee, updateEmployee, deleteEmployee }