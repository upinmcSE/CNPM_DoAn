import axiosClient from './axiosClient';

const login = async (username, password) => {
    try {
        const response = await axiosClient.post("/authentication/employee-login", { username, password });
        
        if (response.data.code === 1000 && response.data.result.authenticated) {
            // Đăng nhập thành công
            const token = response.data.result.token;
            // Lưu token vào localStorage hoặc state management system
            localStorage.setItem('authToken', token);
            return {
                success: true,
                token: token,
                message: response.data.message
            };
        } else {
            // Đăng nhập thất bại
            return {
                success: false,
                message: response.data.message || "Đăng nhập thất bại"
            };
        }
    } catch (error) {
        console.error("Login error:", error);
        return {
            success: false,
            message: error.response?.data?.message || "Có lỗi xảy ra khi đăng nhập"
        };
    }
};

const logout = async (token) => {
    try {
        const response = await axiosClient.post('/authentication/logout', { token });

        if (response.data.code !== 1000) {
            throw new Error('Logout failed');
        }

        // Xóa token khỏi localStorage
        localStorage.removeItem('authToken');
        
        return {
            success: true,
            message: 'Đăng xuất thành công'
        };
    } catch (error) {
        console.error("Logout error:", error);
        return {
            success: false,
            message: error.response?.data?.message || "Có lỗi xảy ra khi đăng xuất"
        };
    }
};


export { login, logout };