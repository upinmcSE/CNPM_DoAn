import styles from './styles.module.scss';

const ProductCard = ({ src, name, price }) => {

    const {
        productCard,
        discountTag,
        boxImg,
        title,
        priceCl
    } = styles;
    
    return (
        <div className={productCard}>
            <div className={discountTag}>Giảm 2%</div>
            <div className={boxImg}>
                <img src= {`http://localhost:8081/coffee/api/v1/products/images/${src}`} alt='' />
            </div>
            <div className={title}>{name}</div>
            <div className={priceCl}>{price} VNĐ</div>
        </div>
    );
};

export default ProductCard