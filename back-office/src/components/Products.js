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



const validationSchema = Yup.object().shape({
    name: Yup.string().required("Name product is required"),
    price: Yup.number().required('Price is required').positive("Price must be "),
    description: Yup.string().required("Description is required"),
    category: Yup.string().required("Category is required")
})



function Products() {
    const [open, setOpen] = useState(false)
    const [listProducts, setListProducts] = useState([])
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1); 


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
        category: "",
        image: null
    })

    const handleAddProduct = () => {
        setInitialValues({
            _id: -1,
            name: "",
            price: "",
            description: "",
            category: "",
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

            if (values.name) {
                formData.append("name", values.name);
            } else {
                throw new Error("Name is required");
            }

            // Chuyển đổi giá trị price từ string sang double
            const price = parseFloat(values.price);
            if (!isNaN(price)) { // Kiểm tra xem giá trị đã được chuyển đổi thành công chưa
                formData.append("price", price);
            } else {
                throw new Error("Price must be a valid number");
            }
    
            if (values.description) {
                formData.append("description", values.description);
            } else {
                throw new Error("Description is required");
            }

            if (values.category) {
                formData.append("category", values.category);
            } else {
                throw new Error("Category is required");
            }
    
            // Thêm file ảnh vào FormData
            if (values.image) {
                console.log("Image:", values.image.name);
                formData.append("file", values.image); // Thêm tệp ảnh vào FormData
            }
    
            console.log("Form data before API call:", {
                name: values.name,
                price: price,
                description: values.description,
                category: values.category,
                image: values.image ? values.image.name : null,
            });
    
            // Gọi API với FormData
            const newProduct = await addProduct(formData); // Đảm bảo addProduct có thể xử lý FormData
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

        <Dialog open={open} fullWidth maxWidth="lg">
                <DialogTitle>Add Product</DialogTitle>
                <Formik
                    initialValues={initialValues}
                    validationSchema={validationSchema}
                    onSubmit={handleSubmit}
                >
                    {({ dirty, isValid, setFieldValue }) => (
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
                                            {(msg) => <Typography color="red">{msg}</Typography>}
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
                                            {(msg) => <Typography color="red">{msg}</Typography>}
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
                                            {(msg) => <Typography color="red">{msg}</Typography>}
                                        </ErrorMessage>
                                    </Grid>
                                    <Grid item xs={12}>
                                        <Field
                                            as={TextField}
                                            name="category"
                                            label="Category"
                                            required
                                            fullWidth
                                        />
                                        <ErrorMessage name="category">
                                            {(msg) => <Typography color="red">{msg}</Typography>}
                                        </ErrorMessage>
                                    </Grid>
                                    <Grid item xs={12}>
                                        <input
                                            accept="image/*"
                                            type="file"
                                            onChange={(event) =>
                                                setFieldValue("image", event.currentTarget.files[0])
                                            }
                                        />
                                    </Grid>
                                </Grid>
                            </DialogContent>

                            <DialogActions>
                                <Button
                                    disabled={!dirty || !isValid}
                                    type="submit"
                                    variant="contained"
                                    color="primary"
                                >
                                    Save
                                </Button>
                                <Button onClick={() => setOpen(false)}>Cancel</Button>
                            </DialogActions>
                        </Form>
                    )}
                </Formik>
            </Dialog>
        </>
     );
}

export default Products;