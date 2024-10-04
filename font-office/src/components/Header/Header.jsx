import styles from './styles.module.scss'
import Menu from './Menu/Menu';
import { dataMenu } from './constants';
import cartIcon from '@icons/svgs/cart-icon.svg';
import userIcon from '@icons/svgs/userIcon.svg';
import searchIcon from '@icons/svgs/searchIcon.svg';
import Logo from '@icons/images/logo.jpg'

function Header() {
    const { container, containerHeader, containerBox, containerMenu, containerBoxIcon } = styles
    return ( 
        <div className={container}>
            <div className={containerHeader}>
                <div>
                <img
                    src={Logo}
                    alt='Logo'
                    style={{ width: '85px', height: '85px', borderRadius: '50%' }}
                />
                </div>
                <div className={containerBox}>
                    <div className={containerMenu}>
                        {dataMenu.map(item => {
                            return <Menu content={item.content} href={item.href} />;
                        })}
                    </div>
                </div>
                <div className={containerBox}>
                    <div className={containerBoxIcon}>
                    <img
                        width={26}
                        height={26}
                        src={cartIcon}
                        alt='#'
                    />
                    <img
                        width={26}
                        height={26}
                        src={userIcon}
                        alt='#'
                    />
                    <img
                        width={26}
                        height={26}
                        src={searchIcon}
                        alt='#'
                    />
                    </div> 
                </div> 
            </div>    
        </div>
     );
}

export default Header;
