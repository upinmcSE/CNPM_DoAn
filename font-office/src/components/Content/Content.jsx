import Info from '@components/Info/Info';
import styles from './styles.module.scss'
import Menu from '@components/Menu/Menu';
import { useEffect, useState } from 'react';
import { getProducts } from '../../apis/productService';

function Content() {
    const [listProducts, setListProducts] = useState([]);

    debugger
    useEffect(() => {
        getProducts().then((res) => {
            console.log(res);
            setListProducts(res.data);
        });
    }, []);

    const {container, title, des} = styles
    return ( 
        <div className={container}>
            <Info />
            <div className={title}>
                <p className={des}>sản phẩm nổi bật</p>
            </div>
            <Menu data={listProducts} /> 
        </div>
     );
}

export default Content;