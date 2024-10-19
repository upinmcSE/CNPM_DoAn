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
    TextField,
    MenuItem,
    Select,
    FormControl,
    InputLabel
} from "@mui/material";
import { Colors } from '../styles/theme';
import EditIcon from '@mui/icons-material/Edit';
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';
import { useState, useEffect } from "react";
import * as Yup from 'yup'
import {Formik, Form, Field, ErrorMessage} from 'formik'
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import Grid from '@mui/material/Grid'
import Pagination from "@mui/material/Pagination";
import { getEmployees, addEmployee, updateEmployee, deleteEmployee } from "../apis/employeeService";

const validationSchema = Yup.object().shape({
    fullName: Yup.string().required('This field is required'),
    gender: Yup.boolean().required('This field is required'),
    username: Yup.string().required('This field is required'),
    password: Yup.string().required('This field is required'),
    rePassword: Yup.string().required('This field is required'),
    email:Yup.string().email('Invalid email').required('This field is required'),
    age: Yup.number().required('This field is required'),
    employeeLv: Yup.string().required('This field is required'),
    salary: Yup.number().required('This field is required'),
    workTime: Yup.string().required('This field is required'),
})




function Employees() {
    const [open, setOpen] = useState(false);
    const [listEmployees, setListEmployees] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    
    const [initialValues, setInitialValues] = useState({
        id: -1,
        username: "",
        password: "",
        rePassword: "",
        fullName: "",
        email: "",
        age: 0,
        gender: null,
        employeeLv: "",
        salary: 0,
        workTime: "",
    })

    useEffect(() => {
        loadEmployees(currentPage);
    }, [currentPage]);


    const loadEmployees = async (page) => {
        try {
            const res = await getEmployees(page, 10);
            setListEmployees(res.data);
            setTotalPages(res.totalPages);
        } catch (error) {
            console.error("Error loading employees: ", error);
        }
    }

    const handleAddEmployee = async () => {
        setInitialValues({
            id: -1,
            username: "",
            password: "",
            rePassword: "",
            fullName: "",
            email: "",
            age: 0,
            gender: null,
            employeeLv: "",
            salary: 0,
            workTime: "",
        })
        setOpen(true)
    }

    const handleEditClick = (employee) => {
        setInitialValues({
            id: employee.id,
            salary: employee.salary,
            employeeLv: employee.employeeLv,
            workTime: employee.workTime, 
        })
        setOpen(true)
    }

    const handleDeleteEmployee = async (employee) => {
        try {
            await deleteEmployee(employee.id);
            loadEmployees(currentPage);
        } catch (error) {
            console.error("Error deleting employee: ", error);
        }
    }

    const handleEmployeeEdit = async (values, actions) => {}

    const handleSubmit = async (values, {resetForm}) => {}

    const handlePageChange = (event, value) => {
        setCurrentPage(value);
    }
    return ( 
        <>
            <Typography variant="h4">List Employees</Typography>
            <br />
            <Button variant="contained" color="primary" onClick={handleAddEmployee}>Add Employee</Button>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Name</TableCell>
                            <TableCell>Gender</TableCell>
                            <TableCell>Phone</TableCell>
                            <TableCell>Work</TableCell>
                            <TableCell>Age</TableCell>
                            <TableCell>Salary</TableCell>
                            <TableCell>WorkDay</TableCell>
                            <TableCell>Level</TableCell>
                            <TableCell>Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {listEmployees.map(e => 
                            <TableRow key={e.id}>
                                <TableCell>{e.fullName}</TableCell>
                                <TableCell>{e.gender}</TableCell>
                                <TableCell>{e.username}</TableCell>
                                <TableCell>{e.workTime}</TableCell>
                                <TableCell>{e.age}</TableCell>
                                <TableCell>{e.salary}</TableCell>
                                <TableCell>{e.workDays}</TableCell>
                                <TableCell>{e.employeeLv}</TableCell>
                                <TableCell>
                                    <IconButton onClick={() => handleEditClick(e)}>
                                        <EditIcon />
                                    </IconButton>
                                    <IconButton onClick={() => handleDeleteEmployee(e)}>
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
                <DialogTitle>Add Employee</DialogTitle>
                <Formik
                    initialValues={initialValues}
                    validationSchema={validationSchema}
                    onSubmit={(values, actions) => {
                        if (values.id === -1) { // Kiểm tra nếu là sản phẩm mới
                            handleSubmit(values, actions); // Gọi hàm thêm sản phẩm
                        } else {
                            handleEmployeeEdit(values, actions); // Gọi hàm chỉnh sửa sản phẩm
                        }
                    }}
                >
                {({dirty, isValid, setFieldValue, getFieldProps}) => (
                    <Form>
                        <DialogContent>
                            <Grid container spacing={2}>
                                <Grid item xs={12}>
                                    <Field 
                                        as={TextField}
                                        name='username'
                                        label='Số điện thoại'
                                        required
                                        fullWidth
                                    />
                                    <ErrorMessage name='username' >
                                        {(msg) => <Typography color="red">{msg}</Typography>}
                                    </ErrorMessage>
                                </Grid>
                                <Grid item xs={12}>
                                    <Field 
                                        as={TextField}
                                        name='password'
                                        label='Nhập mật khẩu'
                                        required
                                        fullWidth
                                    />
                                    <ErrorMessage name='password' >
                                        {(msg) => <Typography color="red">{msg}</Typography>}
                                    </ErrorMessage>
                                </Grid>
                                <Grid item xs={12}>
                                    <Field 
                                        as={TextField}
                                        name='rePassword'
                                        label='Nhập lại mật khẩu'
                                        required
                                        fullWidth
                                    />
                                    <ErrorMessage name='rePassword' >
                                        {(msg) => <Typography color="red">{msg}</Typography>}
                                    </ErrorMessage>
                                </Grid>
                                <Grid item xs={12}>
                                    <Field 
                                        as={TextField}
                                        name='fullName'
                                        label='Họ và tên'
                                        required
                                        fullWidth
                                    />
                                    <ErrorMessage name='fullName' >
                                        {(msg) => <Typography color="red">{msg}</Typography>}
                                    </ErrorMessage>
                                </Grid>
                                <Grid item xs={12}>
                                    <Field 
                                        as={TextField}
                                        name='email'
                                        label='Email'
                                        required
                                        fullWidth
                                    />
                                    <ErrorMessage name='email' >
                                        {(msg) => <Typography color="red">{msg}</Typography>}
                                    </ErrorMessage>
                                </Grid>
                                <Grid item xs={12}>
                                    <Field 
                                        as={TextField}
                                        name='age'
                                        label='Tuổi'
                                        required
                                        fullWidth
                                    />
                                    <ErrorMessage name='age' >
                                        {(msg) => <Typography color="red">{msg}</Typography>}
                                    </ErrorMessage>
                                </Grid>
                                <Grid item xs={12}>
                                    <FormControl fullWidth required>
                                        <InputLabel id="gender-label">Giới tính</InputLabel>
                                        <Field
                                            as={Select}
                                            name="gender"
                                            labelId="gender-label"
                                            label="Giới tính"
                                        >
                                            <MenuItem value={0}>Nam</MenuItem>
                                            <MenuItem value={1}>Nữ</MenuItem>
                                        </Field>
                                    </FormControl>
                                    <ErrorMessage name='gender' >
                                        {(msg) => <Typography color="red">{msg}</Typography>}
                                    </ErrorMessage>
                                </Grid>
                                <Grid item xs={12}>
                                    <Field
                                        as={TextField}
                                        name='employeeLv'
                                        label='Level'
                                        required
                                        fullWidth
                                    
                                    />
                                    <ErrorMessage name='employeeLv' >
                                        {(msg) => <Typography color="red">{msg}</Typography>}
                                    </ErrorMessage>
                                </Grid>
                                <Grid item xs={12}>
                                    <Field 
                                        as={TextField}
                                        name='salary'
                                        label='Lương'
                                        required
                                        fullWidth
                                    />
                                    <ErrorMessage name='salary' >
                                        {(msg) => <Typography color="red">{msg}</Typography>}
                                    </ErrorMessage>
                                </Grid>
                                <Grid item xs={12}>
                                    <Field 
                                        as={TextField}
                                        name='workTime'
                                        label='Thời gian làm việc'
                                        required
                                        fullWidth
                                    />
                                    <ErrorMessage name='workTime' >
                                        {(msg) => <Typography color="red">{msg}</Typography>}
                                    </ErrorMessage>
                                </Grid>
                            </Grid>   
                        </DialogContent>
                        <DialogActions>
                                {getFieldProps("id").value ? (
                                <Button
                                    disabled={!dirty || !isValid}
                                    type="submit"
                                    variant="contained"
                                    color="primary"
                                >
                                    Save Edit
                                </Button>
                                ) : (
                                    <Button
                                    disabled={!dirty || !isValid}
                                    type="submit"
                                    variant="contained"
                                    color="primary"
                                >
                                    Save
                                </Button>
                                )}
                                <Button onClick={() => setOpen(false)}>Cancel</Button>
                            </DialogActions>
                    </Form>
                )}    

                </Formik>
            </Dialog>
        </>
     );
}


export default Employees;