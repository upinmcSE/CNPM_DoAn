import { Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField, Grid, Typography, FormControl, InputLabel, MenuItem, Select } from "@mui/material";
import { Formik, Form, Field, ErrorMessage } from "formik";

function AddEmployeeDialog({ open, onClose, onSubmit, initialValues, validationSchema }) {
    return (
        <Dialog open={open} fullWidth maxWidth="lg">
            <DialogTitle>Add Employee</DialogTitle>
            <Formik
                initialValues={initialValues}
                validationSchema={validationSchema}
                onSubmit={onSubmit}
            >
                {({ dirty, isValid }) => (
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
                                        type="password"
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
                                        type="password"
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
                            <Button disabled={!dirty || !isValid} type="submit" variant="contained" color="primary">
                                Save
                            </Button>
                            <Button onClick={onClose}>Cancel</Button>
                        </DialogActions>
                    </Form>
                )}
            </Formik>
        </Dialog>
    );
}

export default AddEmployeeDialog;