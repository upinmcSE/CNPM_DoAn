import { 
    Button, 
    TableContainer, 
    Typography, 
    Table, 
    TableHead, 
    TableBody, 
    TableRow, 
    TableCell, 
    IconButton, 
    TextField
} from "@mui/material";
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';
import AddIcon from "@mui/icons-material/Add";
import EditIcon from '@mui/icons-material/Edit';
import { Colors } from '../styles/theme';
import * as Yup from 'yup'
import {Formik, Form, Field, ErrorMessage} from 'formik'
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import { useEffect, useState } from "react";
import Grid from '@mui/material/Grid'
import { getProducts, addProduct } from "../apis/productService";
import Pagination from "@mui/material/Pagination";



// const products = [{
//     _id: 12345,
//     name: "Sữa chua chuối",
//     price: 30000,
//     description: " Sữa chua và chuối"
// }]


const validationSchema = Yup.object().shape({
    name: Yup.string().required("Name product is required"),
    price: Yup.number().required('Price is required').positive("Price must be "),
    description: Yup.string().required("Description is required")
})



function Products() {
    const [open, setOpen] = useState(false)
    const [listProducts, setListProducts] = useState([])
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1); 
    // // Load danh sach san pham tu backend
    // useEffect(() => {
    //     getProducts().then((res) => {
    //         setListProducts(res.data);
    //     })
    // }, []) // Thêm [] để chỉ load 1 lần khi component mount

    // Load danh sách sản phẩm theo trang
    useEffect(() => {
        loadProducts(currentPage);
    }, [currentPage]);

    const loadProducts = async (page) => {
        try {
            const res = await getProducts(page, 10); // Gọi API với trang và kích thước trang
            setListProducts(res.data);
            setTotalPages(res.totalPages);
        } catch (error) {
            console.error("Error fetching products:", error);
        }
    };


    const [initialValues, setInitialValues] = useState({
        _id: -1,
        name: "",
        price: "",
        description: "",
        image: null
    })

    const handleAddProduct = () => {
        setInitialValues({
            _id: -1,
            name: "",
            price: "",
            description: ""
        })
        setOpen(true)
    }

    const handleProductEdit = (product) => {
        setInitialValues(product)
        setOpen(true)
    }

    const handleDeleteProduct = (product) => {
        setListProducts(prev => prev.filter(p => p._id !== product._id)); // Cập nhật danh sách
    };

    // Thay đổi handleSubmit để gọi API addProduct
    const handleSubmit = async (values, { resetForm }) => {
        try {
            const formData = new FormData();
            
            // Thêm các field vào FormData từ ProductRequest
            formData.append("request.name", values.name);
            formData.append("request.price", values.price);
            formData.append("request.description", values.description);
            
            // Thêm file ảnh vào FormData
            if (values.image) {
                formData.append("file", values.image);
            }
    
            // Gọi API với FormData
            const newProduct = await addProduct(formData);
            setListProducts(prev => [...prev, newProduct]); // Cập nhật danh sách sản phẩm
            resetForm();
            setOpen(false);
        } catch (error) {
            console.error("Error adding product:", error);
        }
    };

    const handlePageChange = (event, value) => {
        setCurrentPage(value);
    }

    return ( 
        <>
            <Typography sx={{md: 1}} variant="h4"></Typography>
            <Button startIcon={<AddIcon />} variant="contained" onClick={handleAddProduct}>ADD Product</Button>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Tên sản phẩm</TableCell>
                            <TableCell>Miêu tả sản phẩm</TableCell>
                            <TableCell>Giá</TableCell>
                            <TableCell>Ảnh sản phẩm</TableCell>
                            <TableCell>Action</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {listProducts.map(p => 
                            <TableRow key={p.id}>
                                <TableCell>{p.name}</TableCell>
                                <TableCell>{p.description}</TableCell>
                                <TableCell>{p.price}</TableCell>
                                <TableCell>
                                    <img 
                                        src={`http://localhost:8081/coffee/api/v1/products/images/${p.urlImage}`} 
                                        alt={p.name} 
                                        style={{ width: 100, height: 100, objectFit: 'cover' }} 
                                    />          
                                </TableCell>
                                <TableCell>
                                    <IconButton onClick={() => handleProductEdit(p)}>
                                        <EditIcon />
                                    </IconButton>
                                    <IconButton onClick={() => handleDeleteProduct(p)}>
                                        <DeleteForeverIcon sx={{ color: Colors.danger}} />
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

            <Dialog 
                open={open}
                fullWidth
                maxWidth='lg'
            >
                <DialogTitle >
                    {"Add Product"}
                </DialogTitle>
                <Formik initialValues={initialValues} validationSchema={validationSchema} onSubmit={handleSubmit}>
                    {({ dirty, isValid, setFieldValue, getFieldProps}) =>
                        <Form>
                            <DialogContent>
                                <Grid container spacing={2}>
                                    <Grid item xs={12}>
                                        <Field
                                            as={TextField}
                                            name="name"
                                            label="Name"
                                            required
                                            fullWidth
                                        />
                                        <ErrorMessage name="name">
                                            {message => <Typography color="red">{message}</Typography>}
                                        </ErrorMessage>
                                    </Grid>

                                    <Grid item xs={12}>
                                        <Field
                                            as={TextField}
                                            name="price"
                                            label="Price"
                                            required
                                            fullWidth
                                        />
                                        <ErrorMessage name="price">
                                            {message => <Typography color="red">{message}</Typography>}
                                        </ErrorMessage>
                                    </Grid>

                                    <Grid item xs={12}>
                                        <Field
                                            as={TextField}
                                            name="description"
                                            label="Description"
                                            required
                                            fullWidth
                                        />
                                        <ErrorMessage name="description">
                                            {message => <Typography color="red">{message}</Typography>}
                                        </ErrorMessage>
                                    </Grid>
                                    <Grid item xs={12}>
                                        <input
                                            accept="image/*"
                                            type="file"
                                            onChange={(event) =>
                                                setFieldValue("image", event.currentTarget.files[0])
                                            }
                                            required
                                        />
                                        <ErrorMessage name="image">
                                            {message => <Typography color="red">{message}</Typography>}
                                        </ErrorMessage>
                                    </Grid>
                                </Grid>
                            </DialogContent>

                            <DialogActions>
                                {getFieldProps("_id").value !== -1 ? (
                                    <Button disabled={!dirty | !isValid} onClick={handleSubmit} type="submit" variant="contained" color="primary">Save edit</Button>)
                                    :<Button disabled={!dirty | !isValid} onClick={handleSubmit} type="submit" variant="contained" color="primary">Save</Button>
                                }
                                <Button onClick={() =>setOpen(false)} autoFocus>Cancel</Button>
                            </DialogActions>
                        </Form>
                    } 
                </Formik>
            </Dialog>
        </>
     );
}

export default Products;