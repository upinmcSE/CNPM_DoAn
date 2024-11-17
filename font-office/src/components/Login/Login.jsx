import React, { useContext, useState, useEffect } from "react";
import { useFormik } from "formik";
import * as Yup from "yup";
import { login, register } from "../../apis/authService";
import { ToastContext } from "../../context/ToastContext";
import Cookies from "js-cookie";
import ForgotPasswordDialog from "../ForgotPassword/ForgotPasswordDialog";

const Login = ({ isOpen, onClose, onLoginSuccess }) => {
  const { toast } = useContext(ToastContext);
  const [isLoading, setIsLoading] = useState(false);
  const [isRegister, setIsRegister] = useState(false);
  const [isOpenForgotPassword, setIsOpenForgotPassword] = useState(false);

  const handleForgotPassword = () => {
    setIsOpenForgotPassword(true);
  };

  const formikLogin = useFormik({
    initialValues: {
      username: '',
      password: '',
    },
    validationSchema: Yup.object({
      username: Yup.string().required('Username là bắt buộc'),
      password: Yup.string()
        .min(6, 'Mật khẩu phải có ít nhất 6 ký tự')
        .required('Mật khẩu là bắt buộc'),
    }),
    onSubmit: async (values) => {
      if (isLoading) return;
      setIsLoading(true);

      try {
        const { username, password } = values;
        const response = await login(username, password);

        if (response.success) {
          const { token } = response;
          Cookies.set('Token', token);
          toast.success('Đăng nhập thành công!');
          onLoginSuccess();
          onClose();
        } else {
          toast.error(response.message || 'Đăng nhập thất bại!');
        }
      } catch (error) {
        toast.error('Có lỗi xảy ra khi đăng nhập!');
      } finally {
        setIsLoading(false);
      }
    },
  });

  const formikRegister = useFormik({
    initialValues: {
      username: '',
      email: '',
      password: '',
      repassword: '',
    },
    validationSchema: Yup.object({
      username: Yup.string().required('Username là bắt buộc'),
      email: Yup.string().email('Email không hợp lệ').required('Email là bắt buộc'),
      password: Yup.string()
        .min(6, 'Mật khẩu phải có ít nhất 6 ký tự')
        .required('Mật khẩu là bắt buộc'),
      repassword: Yup.string()
        .oneOf([Yup.ref('password'), null], 'Mật khẩu không khớp')
        .required('Bạn cần xác nhận mật khẩu'),
    }),
    onSubmit: async (values) => {
      if (isLoading) return;
      setIsLoading(true);

      try {
        const { username, email, password } = values;
        const response = await register(username, email, password);

        if (response.success) {
          toast.success('Đăng ký thành công! Bạn có thể đăng nhập ngay bây giờ.');
          setIsRegister(false);
          formikLogin.resetForm();
        } else {
          toast.error(response.message || 'Đăng ký thất bại!');
        }
      } catch (error) {
        toast.error('Có lỗi xảy ra khi đăng ký!');
      } finally {
        setIsLoading(false);
      }
    },
  });

  useEffect(() => {
    if (!isOpen) {
      formikLogin.resetForm();
      formikRegister.resetForm();
      setIsRegister(false);
    }
  }, [isOpen]);

  return (
    <div
      className={`fixed inset-0 z-50 flex items-center justify-center backdrop-blur-sm ${isOpen ? 'block' : 'hidden'}`}
    >
      <div className="bg-white rounded-lg shadow-lg p-6 w-96">
        <h2 className="text-2xl font-bold mb-4">{isRegister ? 'Đăng Ký' : 'Đăng Nhập'}</h2>
        
        <form onSubmit={isRegister ? formikRegister.handleSubmit : formikLogin.handleSubmit}>
          {isRegister ? (
            <>
              {/* Register fields */}
            </>
          ) : (
            <>
              <div className="mb-4">
                <label className="block mb-1 text-gray-700" htmlFor="usernameLogin">
                  Tên Người Dùng
                </label>
                <input
                  id="usernameLogin"
                  type="text"
                  {...formikLogin.getFieldProps('username')}
                  className={`border border-gray-300 rounded w-full px-3 py-2 text-black ${
                    formikLogin.touched.username && formikLogin.errors.username ? 'border-red-500' : ''
                  }`}
                  required
                />
                {formikLogin.touched.username && formikLogin.errors.username && (
                  <p className="text-red-500 text-sm mt-1">{formikLogin.errors.username}</p>
                )}
              </div>

              <div className="mb-4">
                <label className="block mb-1 text-gray-700" htmlFor="passwordLogin">
                  Mật Khẩu
                </label>
                <input
                  id="passwordLogin"
                  type="password"
                  {...formikLogin.getFieldProps('password')}
                  className={`border border-gray-300 rounded w-full px-3 py-2 text-black ${
                    formikLogin.touched.password && formikLogin.errors.password ? 'border-red-500' : ''
                  }`}
                  required
                />
                {formikLogin.touched.password && formikLogin.errors.password && (
                  <p className="text-red-500 text-sm mt-1">{formikLogin.errors.password}</p>
                )}
              </div>

              <div className="mb-4 text-center">
                <button
                  type="button"
                  onClick={handleForgotPassword}
                  className="text-primary hover:underline"
                >
                  Quên Mật Khẩu?
                </button>
              </div>
            </>
          )}

          <div className="flex justify-between items-center">
            <button
              type="button"
              onClick={onClose}
              className="text-gray-500 hover:underline"
            >
              Đóng
            </button>
            <button
              type="submit"
              className="bg-primary text-white px-4 py-2 rounded"
            >
              {isLoading ? 'Loading...' : isRegister ? 'Đăng Ký' : 'Đăng Nhập'}
            </button>
            <button
              type="button"
              onClick={() => setIsRegister(!isRegister)}
              className="text-gray-500 hover:underline"
            >
              {isRegister ? 'Đăng Nhập' : 'Đăng Ký'}
            </button>
          </div>
          
          <ForgotPasswordDialog 
            isOpen={isOpenForgotPassword} 
            onClose={() => setIsOpenForgotPassword(false)} 
          />
        </form>
      </div>
    </div>
  );
};

export default Login;
