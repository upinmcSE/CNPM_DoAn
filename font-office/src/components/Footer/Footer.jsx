import styles from "./styles.module.scss"

function Footer() {
    const {container, containerContent} = styles
    return ( 
        <div className={container}>
            <div className={containerContent}>
                <div>
                    <h3>Chăm sóc khách hàng</h3>
                    <p>Trung tâm trợ giúp</p>
                    <p>Hướng dẫn mua hàng</p>
                    <p>Thanh toán</p>
                    <p>Vận chuyển</p>
                    <p>Trả hàng & hoàn tiền</p>
                    <p>Chăm sóc khách</p>    
                </div>
                <div>
                    <h3>Về chúng tôi</h3>
                    <p>Giới thiệu về Coffe XMAN</p>
                    <p>Điều khoản Coffe XMAN</p>
                    <p>Chính sách bảo mật</p>
                    <p>Chính hãng</p>
                    <p>Flash sales</p>
                    <p>Liên hệ truyền thông</p>
                </div>
                <div>
                    <h3>Thanh toán</h3>
                    <img src="" alt="" />
                </div>
                <div>
                    <h3>Theo dõi chúng tôi</h3>
                    <p>Facebook</p>
                    <p>Instagram</p>
                    <p>Linkedln</p>
                    <p>CoffeeShop@gmail.com</p>
                </div>
            </div>
        </div>
     );
}

export default Footer;