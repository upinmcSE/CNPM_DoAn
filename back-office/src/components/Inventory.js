import React, { useState, useEffect } from 'react'
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
import EditIcon from '@mui/icons-material/Edit';
import Pagination from "@mui/material/Pagination";
import { getQuantity, upQuantity } from '../apis/inventoryService';
import InventoryEditDialog from './InventoryEditDialog';
import * as Yup from 'yup';


const validationSchemaEdit = Yup.object().shape({
    quantity: Yup.number().required('This field is required'),
});


const Inventory = () => {
    const [products, setListProducts] = useState([])
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1); 
    const [openEditDialog, setOpenEditDialog] = useState(false);

    const [initialValues, setInitialValues] = useState({
        id: -1,
        quantity: 0,
    });

    // Load danh sách sản phẩm theo trang
    useEffect(() => {
        loadProducts(currentPage);
    }, [currentPage]);

    const loadProducts = async (page) => {
        try {
            const res = await getQuantity(page, 10); // Gọi API với trang và kích thước trang
            setListProducts(res.data);
            setTotalPages(res.totalPages);
        } catch (error) {
            console.error("Error fetching products:", error);
        }
    };

    const handlePageChange = (event, value) => {
        setCurrentPage(value);
    }

    const handleUpdate = (value) => {
        console.log('data: ', value)
        setInitialValues({
            id: value.productId,
            quantity: value.quantity,
        })
        setOpenEditDialog(true);
    }

    const handleCloseEditDialog = () => {
        setOpenEditDialog(false);
    };

    const handleSubmitEdit = async (values, { resetForm }) => {
        console.log(values)
        try {
            const res = await upQuantity(values.id, values.quantity);
            
            console.log("Update Quantity successfully: ", res);
            loadProducts(currentPage);
            resetForm();
            setOpenEditDialog(false);
            alert("Thêm số lượng sản phẩm thành công !")
        } catch (error) {
            console.error("Error Update Quantity: ", error);
        }
    };



  return (
    <>
        <Typography variant="h4">List </Typography>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Mã sản phẩm</TableCell>
                            <TableCell>Tên sản phẩm</TableCell>
                            <TableCell>Số lượng</TableCell>
                            <TableCell>Thêm số lượng</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {products.map(c => 
                            <TableRow key={c.id}>
                                <TableCell>{c.productId}</TableCell>
                                <TableCell>{c.productName}</TableCell>
                                <TableCell>{c.quantity}</TableCell>
                                <TableCell>
                                    <IconButton onClick={() => handleUpdate(c)}>
                                        <EditIcon />
                                    </IconButton>
                                </TableCell>
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
             <InventoryEditDialog
                open={openEditDialog}
                onClose={handleCloseEditDialog}
                onSubmit={handleSubmitEdit}
                initialValues={initialValues}
                validationSchema={validationSchemaEdit}
            />
    </>
  )
}

export default Inventory