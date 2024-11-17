import {Route, Routes} from 'react-router-dom'
import Dashboard from './components/Dashboard'
import Products from './components/Products'
import Customers from './components/Customers'
import Employees from './components/Employees'
import Attendance from './components/Attendance'
import Inventory from './components/Inventory'
import Orders from './components/Orders'

export default function AppRoutes() {
    return(
        <Routes>
            <Route exact path='/' element={<Dashboard />} />
            <Route exact path='/dashboard' element={<Dashboard />} />
            <Route exact path='/products' element={<Products />} />
            <Route exact path='/customers' element={<Customers />} />
            <Route exact path='/employees' element={<Employees />} />
            <Route exact path='/attendance' element={<Attendance />} />
            <Route exact path='/inventory' element={<Inventory />} />
            <Route exact path='/orders' element={<Orders />} />

        </Routes>
    )
}