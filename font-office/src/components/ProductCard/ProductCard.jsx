import styles from './styles.module.scss';

const ProductCard = ({ src, name, price }) => {
    const {
        boxImg,
        title,
        priceCl
    } = styles;
    return (
        <div>
            <div className={boxImg}>
                <img src={src} alt='' />
            </div>
            <div className={title}>{name}</div>
            <div className={priceCl}>{price} VNĐ</div>
        </div>
    );
};

export default ProductCard