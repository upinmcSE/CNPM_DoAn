import React, { useContext, useState, useEffect } from "react";
import { useFormik } from "formik";
import * as Yup from "yup";
import { login, register } from "../../apis/authService";
import { ToastContext } from "../../context/ToastContext";
import Cookies from "js-cookie";

const Login = ({ isOpen, onClose, onLoginSuccess }) => {
  const {toast} = useContext(ToastContext);
  const [isLoading, setIsLoading] = useState(false);
  const [isRegister, setIsRegister] = useState(false);

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
          console.log("token: ", token)
          Cookies.set('authToken', token);
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
      password: '',
      repassword: '',
    },
    validationSchema: Yup.object({
      username: Yup.string().required('Username là bắt buộc'),
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
        const { username, password } = values;
        const response = await register(username, password);

        if (response.success) {
          toast.success('Đăng ký thành công! Bạn có thể đăng nhập ngay bây giờ.');
          setIsRegister(false); // Quay lại form đăng nhập
          formikLogin.resetForm(); // Reset form đăng nhập
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

  // Reset form khi modal đóng
  useEffect(() => {
    if (!isOpen) {
      formikLogin.resetForm();
      formikRegister.resetForm();
      setIsRegister(false); // Reset trạng thái đăng ký khi modal đóng
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
              <div className="mb-4">
                <label className="block mb-1 text-gray-700" htmlFor="username">
                  Tên Người Dùng
                </label>
                <input
                  id="username"
                  type="text"
                  {...formikRegister.getFieldProps('username')}
                  className={`border border-gray-300 rounded w-full px-3 py-2 text-black ${
                    formikRegister.touched.username && formikRegister.errors.username ? 'border-red-500' : ''
                  }`}
                  required
                />
                {formikRegister.touched.username && formikRegister.errors.username && (
                  <p className="text-red-500 text-sm mt-1">{formikRegister.errors.username}</p>
                )}
              </div>

              <div className="mb-4">
                <label className="block mb-1 text-gray-700" htmlFor="password">
                  Mật Khẩu
                </label>
                <input
                  id="password"
                  type="password"
                  {...formikRegister.getFieldProps('password')}
                  className={`border border-gray-300 rounded w-full px-3 py-2 text-black ${
                    formikRegister.touched.password && formikRegister.errors.password ? 'border-red-500' : ''
                  }`}
                  required
                />
                {formikRegister.touched.password && formikRegister.errors.password && (
                  <p className="text-red-500 text-sm mt-1">{formikRegister.errors.password}</p>
                )}
              </div>

              <div className="mb-4">
                <label className="block mb-1 text-gray-700" htmlFor="repassword">
                  Xác Nhận Mật Khẩu
                </label>
                <input
                  id="repassword"
                  type="password"
                  {...formikRegister.getFieldProps('repassword')}
                  className={`border border-gray-300 rounded w-full px-3 py-2 text-black ${
                    formikRegister.touched.repassword && formikRegister.errors.repassword ? 'border-red-500' : ''
                  }`}
                  required
                />
                {formikRegister.touched.repassword && formikRegister.errors.repassword && (
                  <p className="text-red-500 text-sm mt-1">{formikRegister.errors.repassword}</p>
                )}
              </div>
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
              {isLoading ? 'ĐANG XỬ LÝ...' : (isRegister ? 'ĐĂNG KÝ' : 'ĐĂNG NHẬP')}
            </button>
          </div>
        </form>

        {!isRegister && (
          <p className="text-center mt-4 text-gray-600 cursor-pointer" onClick={() => setIsRegister(true)}>
            Đăng ký tài khoản
          </p>
        )}
      </div>
    </div>
  );
};

export default Login;
