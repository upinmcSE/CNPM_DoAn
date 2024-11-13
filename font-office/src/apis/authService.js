import axiosClient from "./axiosClient";

const login = async (username, password) => {
    
    try{
        const response = await axiosClient.post('/authentication/customer-login', {username, password})
        console.log("response: ", response)
        if( response.data.code == 1000 & response.data.result.authenticated){
            const token = response.data.result.token;
            console.log("token: ", token)
            localStorage.setItem('authToken', token);
            return {
                success: true,
                token: token,
                message: response.data.message
            }
        }else{
            return {
                success: false,
                message: response.data.message || "Đăng nhập thất bại"
            };
        }
    }catch (error) {
        console.error("Login error:", error);
        return {
            success: false,
            message: error.response?.data?.message || "Có lỗi xảy ra khi đăng nhập"
        };
    }

}


const logout = async (token) => {
    try {
        var response = await axiosClient.post('/authentication/logout', { token: `Bearer ${token}` }, {
            headers: {
                'Content-Type': 'application/json',
            }
        });

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

const register = async (username, password, rePassword) => {
    try {
        const response = await axiosClient.post('/customers/add', { username, password, rePassword });

        if (response.data.code === 1000) { // Kiểm tra mã phản hồi
            return {
                success: true,
                message: response.data.message || "Đăng ký thành công"
            };
        } else {
            return {
                success: false,
                message: response.data.message || "Đăng ký thất bại"
            };
        }
    } catch (error) {
        console.error("Register error:", error);
        return {
            success: false,
            message: error.response?.data?.message || "Có lỗi xảy ra khi đăng ký"
        };
    }
};


const changePassword = async (oldPassword, newPassword) => {
    try{
        console.log("oldPassword: ", oldPassword, "newPassword: ", newPassword)
        var response = await axiosClient.post('/authentication/change-password', {oldPassword, newPassword}, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('authToken')}`,
                'Content-Type': 'application/json',
            }
        });
        return response.data
    }catch(error){
        console.error("Change password error: ", error);
        return {
            success: false,
            message: error.response?.data?.message || "Có lỗi xảy ra khi đổi mật khẩu"
        };
    }
}

const introspect = async ({token}) => {
    try{
        var response = await axiosClient.post('/authentication/introspect', { token: `Bearer ${token}` }, {
            headers: {
                'Content-Type': 'application/json',
            }
        });
        return response.data

    }catch(error){
        console.error("Introspect error: ", error);
        return {
            success: false,
            message: error.response?.data?.message || "Có lỗi xảy ra khi introspect"
        };
    }
}

const forgotPassword = async (phoneNumber) => {
    try{
        var response = await axiosClient.post('/authentication/forgot-password',null, {
            params: {
                phoneNumber: phoneNumber
              }
        });
        console.log("response: ", response)
        return response.data
    }catch(error){
        console.error("Forgot password error: ", error);
    }
}

const verifyOTP = async (otp) => {
    try{
        var phone = localStorage.getItem('phoneNumber');
        console.log("phoneNumber: ", phone)
        var response = await axiosClient.post('/authentication/check-otp', {phone, otp},
            {
                headers: {
                    'Content-Type': 'application/json',
                }
            }
        )
        console.log("response: ", response.data)
        return response.data
    }catch(error){
        console.error("Verify OTP error: ", error);
    }
}

const resetPassword = async (newPassword) => {
    try{
        var phone = localStorage.getItem('phoneNumber');
        var response = await axiosClient.post('/authentication/reset-password', {phone, password: newPassword},
            {
                headers: {
                    'Content-Type': 'application/json',
                }
            }
        )
        return response.data
    }catch(error){
        console.error("Reset password error: ", error);
    }
}


export { login, logout, register, changePassword, introspect, forgotPassword, verifyOTP, resetPassword };