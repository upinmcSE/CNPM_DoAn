import {Route, Routes} from 'react-router-dom'
import Dashboard from './components/Dashboard'
import Products from './components/Products'
import Settings from './components/Settings'
import Customers from './components/Customers'
import Employees from './components/Employees'
export default function AppRoutes() {
    return(
        <Routes>
            <Route exact path='/' element={<Dashboard />} />
            <Route exact path='/dashboard' element={<Dashboard />} />
            <Route exact path='/products' element={<Products />} />
            <Route exact path='/customers' element={<Customers />} />
            <Route exact path='/employees' element={<Employees />} />
        </Routes>
    )
}