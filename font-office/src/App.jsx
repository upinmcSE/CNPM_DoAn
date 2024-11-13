import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Hero from "./components/Hero/Hero";
import Navbar from "./components/Navbar/Navbar";
import Services from "./components/Services/Services.jsx";
import Banner from "./components/Banner/Banner.jsx";
import AppStore from "./components/AppStore/AppStore.jsx";
import Testimonials from "./components/Testimonials/Testimonials.jsx";
import Footer from "./components/Footer/Footer.jsx";
import AOS from "aos";
import "aos/dist/aos.css";
import Products from "./components/Products/Products.jsx";
import { ToastProvider } from "./context/ToastContext.jsx";
import PaymentPage from "./pages/PaymentPage/PaymentPage.jsx";
import SuccessPage from "./pages/SuccessPage/SuccessPage.jsx";
import ForgotPasswordPage from "./components/ForgotPassword/ForgotPasswordDialog.jsx";

const App = () => {
  React.useEffect(() => {
    AOS.init({
      offset: 100,
      duration: 700,
      easing: "ease-in",
      delay: 100,
    });
    AOS.refresh();
  }, []);

  return (
    <div className="bg-white dark:bg-gray-900 dark:text-white duration-200 overflow-x-hidden">
      <ToastProvider>
        <Router>
          <Routes>
            <Route path="/" element={
              <>
                <Navbar />
                <Hero />
                <Services />
                <Products />
                <Banner />
                <AppStore />
                <Testimonials />
                <Footer />
              </>
            } />
            <Route path="/payment" element={<PaymentPage />} />
            <Route path="/success" element={<SuccessPage />} />
          </Routes>
        </Router>
      </ToastProvider>
    </div>
  );
};

export default App;
