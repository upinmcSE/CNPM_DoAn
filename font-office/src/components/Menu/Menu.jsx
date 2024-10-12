import MainLayout from '@components/Layout/Layout';
import styles from './styles.module.scss';
import ProductCard from '@components/ProductCard/ProductCard'

const Menu = ({ data }) => {
    const { container } = styles;
    return (
        <>
            <MainLayout>
                <div className={container}>
                    {data.map((item) => (
                        <ProductCard
                        key={item.id}
                        src={item.urlImage} // Thay đổi thành urlImage
                        name={item.name}
                        price={item.price}
                        />
                    ))}
                </div>
            </MainLayout>
        </>
    );
};

export default Menu;