import styles from '../styles.module.scss';

function Menu({ content, href }) {
    const { menu } = styles;
    return (
        <div className={menu}>
            {content} <a href={href}></a>
        </div>
    );
}

export default Menu;
