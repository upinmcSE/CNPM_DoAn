import axios from "axios";

const axiosClient = axios.create({
    baseURL: 'http://localhost:8081/coffee/api/v1/products',
    timeout: 10000,
    headers: {
        'Content-Type': 'application/json'
    }
})

export default axiosClient