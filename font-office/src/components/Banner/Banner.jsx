import styles from './styles.module.scss'

function Banner() {
    const {container, title} = styles
    return ( 
        <div className={container}>
            <div className={title}>
            </div>
        </div>
     );
}

export default Banner;