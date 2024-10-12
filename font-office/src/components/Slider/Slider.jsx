import MainLayout from "@components/Layout/Layout";
import styles from './styles.module.scss'
function Slider() {
    const {container, containerContent, des1, des2} = styles
    return ( 
        <MainLayout>
            <div className={container}>
                <div className={containerContent}>
                    <div className={des1}>về chúng tôi</div>
                    <div className={des2}>Bên cạnh niềm tự hào vể những đồ uống ngon-sạch-tươi, chúng tôi luôn tự tin
                        mang đến khách hàng những trải nghiệm tốt nhất về dịch vụ và không gian
                    </div>
                </div>
            </div>
        </MainLayout>
     );
}

export default Slider;
