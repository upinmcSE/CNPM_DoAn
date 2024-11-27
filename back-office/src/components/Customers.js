import { 
    TableContainer, 
    Typography, 
    Table, 
    TableHead, 
    TableBody, 
    TableRow, 
    TableCell, 
    IconButton, 
} from "@mui/material";
import { getCustomers } from "../apis/customerService";
import ArrowUpwardIcon from '@mui/icons-material/ArrowUpward';
import { useState, useEffect } from "react";
import Pagination from "@mui/material/Pagination";
import { updateLevel } from "../apis/customerService";


function Customers() {
    const [listCustomers, setListCustomers] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1); 

    // Load danh sách sản phẩm theo trang
    useEffect(() => {
        loadProducts(currentPage);
    }, [currentPage]);

    const loadProducts = async (page) => {
        try {
            const res = await getCustomers(page, 10); // Gọi API với trang và kích thước trang
            setListCustomers(res.data);
            setTotalPages(res.totalPages);
        } catch (error) {
            console.error("Error fetching products:", error);
        }
    };

    const handlePageChange = (event, value) => {
        setCurrentPage(value);
    }

    const handleUpdateLevel = async (customer) => {
        try {
            const res = await updateLevel(customer.id);
            if(res.code == '1000'){
                loadProducts(currentPage);
            }
        } catch (error) {
            console.error("Error update level: ", error);
        }
    }

    return ( 
        <>
            <Typography variant="h4">List Customers</Typography>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Full Name</TableCell>
                            <TableCell>Email</TableCell>
                            <TableCell>Phone Number</TableCell>
                            <TableCell>Age</TableCell>
                            <TableCell>Point</TableCell>
                            <TableCell>Level</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {listCustomers.map(c => 
                            <TableRow key={c.id}>
                                <TableCell>{c.fullName}</TableCell>
                                <TableCell>{c.email}</TableCell>
                                <TableCell>{c.username}</TableCell>
                                <TableCell>{c.age}</TableCell>
                                <TableCell>{c.point}</TableCell>
                                <TableCell>{c.menberLV}</TableCell>
                                {/* <TableCell>
                                    <IconButton onClick={() => handleUpdateLevel(c)}>
                                        <ArrowUpwardIcon  />
                                    </IconButton>
                                </TableCell> */}
                            </TableRow>
                        )}
                    </TableBody>
                </Table>
            </TableContainer>

            <Pagination 
                count={totalPages} 
                page={currentPage} 
                onChange={handlePageChange}
                color="primary" 
                sx={{ mt: 2, display: 'flex', justifyContent: 'center' }} 
            />
        </>
     );
}

export default Customers;