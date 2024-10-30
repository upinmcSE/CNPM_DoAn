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
} from "@mui/material";
import { Colors } from '../styles/theme';
import EditIcon from '@mui/icons-material/Edit';
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';
import { useState, useEffect } from "react";
import * as Yup from 'yup';
import Pagination from "@mui/material/Pagination";
import { getEmployees, addEmployee, updateEmployee, deleteEmployee } from "../apis/employeeService";
import AddEmployeeDialog from "./EmployeeAddDialog";
import EditEmployeeDialog from "./EmployeeEditDialog";

const validationSchemaAdd = Yup.object().shape({
    fullName: Yup.string().required('This field is required'),
    gender: Yup.boolean().required('This field is required'),
    username: Yup.string().required('This field is required'),
    password: Yup.string().required('This field is required'),
    rePassword: Yup.string().required('This field is required'),
    email: Yup.string().email('Invalid email').required('This field is required'),
    age: Yup.number().required('This field is required'),
    employeeLv: Yup.string().required('This field is required'),
    salary: Yup.number().required('This field is required'),
    workTime: Yup.string().required('This field is required'),
});

const validationSchemaEdit = Yup.object().shape({
    fullName: Yup.string().required('This field is required'),
    employeeLv: Yup.string().required('This field is required'),
    salary: Yup.number().required('This field is required'),
    workTime: Yup.string().required('This field is required'),
});

function Employees() {
    const [listEmployees, setListEmployees] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const [openAddDialog, setOpenAddDialog] = useState(false);
    const [openEditDialog, setOpenEditDialog] = useState(false);

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
    });

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
    };

    const handleAddEmployee = () => {
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
        });
        setOpenAddDialog(true);
    };

    const handleEditClick = (employee) => {
        setInitialValues({
            id: employee.id,
            fullName: employee.fullName,
            salary: employee.salary,
            employeeLv: employee.employeeLv,
            workTime: employee.workTime,
        })
        setOpenEditDialog(true);
    }
    const handleCloseAddDialog = () => {
        setOpenAddDialog(false);
    };

    const handleCloseEditDialog = () => {
        setOpenEditDialog(false);
    };

    const handleSubmitAdd = async (values, { resetForm }) => {
        try {
            const employeeData = {
                ...values,
                age: Number(values.age),           
                salary: Number(values.salary),     
            };
            
            
            delete employeeData.id;
            const res = await addEmployee(employeeData);
            
            console.log("Employee added successfully: ", res);
            loadEmployees(currentPage);
            resetForm();
            setOpenAddDialog(false);
        } catch (error) {
            console.error("Error adding employee: ", error);
        }
    };

    const handleSubmitEdit = async (values, { resetForm }) => {
        try {
            const employeeData = {
                ...values,          
                salary: Number(values.salary),     
            };
            delete employeeData.id;
            const res = await updateEmployee(values.id, employeeData);
            console.log("Employee edit successfully: ", res);
            loadEmployees(currentPage);
            resetForm();
            setOpenEditDialog(false);
        } catch (error) {
            console.error("Error adding employee: ", error);
        }
    };
    const handleDeleteEmployee = async (employee) => {
        try {
            await deleteEmployee(employee.id);
            loadEmployees(currentPage);
        } catch (error) {
            console.error("Error deleting employee: ", error);
        }
    }

    const handlePageChange = (event, value) => {
        setCurrentPage(value);
    };

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
                                    <IconButton>
                                        <EditIcon onClick={() => handleEditClick(e)} />
                                    </IconButton>
                                    <IconButton onClick={() => handleDeleteEmployee(e)}>
                                        <DeleteForeverIcon sx={{ color: Colors.danger }} />
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
            
            
            <AddEmployeeDialog 
                open={openAddDialog}
                onClose={handleCloseAddDialog}
                onSubmit={handleSubmitAdd}
                initialValues={initialValues}
                validationSchema={validationSchemaAdd}
            />
            <EditEmployeeDialog
                open={openEditDialog}
                onClose={handleCloseEditDialog}
                onSubmit={handleSubmitEdit}
                initialValues={initialValues}
                validationSchema={validationSchemaEdit}
            />
        </>
    );
}

export default Employees;
