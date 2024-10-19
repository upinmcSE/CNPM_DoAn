import axios from "axios";

const axiosClient = axios.create({
    baseURL: 'http://localhost:8081/coffee/api/v1',
    timeout: 10000,
    headers: {
        'Content-Type': 'application/json',
    },
})

// Set interceptor cho axiosClient instance
axiosClient.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Thêm response interceptor để xử lý lỗi
axiosClient.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        // Log chi tiết lỗi
        console.error('API Error:', error);
        if (error.response) {
            // Log response status và data
            console.error('Status:', error.response.status);
            console.error('Data:', error.response.data);
        }
        return Promise.reject(error);
    }
);

export default axiosClient;