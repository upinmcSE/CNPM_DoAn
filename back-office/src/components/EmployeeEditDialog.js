import { 
    Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField, Grid, Typography
} from "@mui/material";
import { Formik, Form, Field, ErrorMessage } from "formik";


function EditEmployeeDialog({ open, onClose, onSubmit, initialValues, validationSchema }) {
    return (
        <Dialog open={open} fullWidth maxWidth="lg">
            <DialogTitle>Chỉnh Sửa Nhân Viên</DialogTitle>
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
                                    <Field as={TextField} name='fullName' label='Họ và Tên' required fullWidth />
                                    <ErrorMessage name='fullName' component={Typography} color="error" />
                                </Grid>
                                <Grid item xs={12}>
                                    <Field as={TextField} name='salary' label='Lương' required fullWidth />
                                    <ErrorMessage name='salary' component={Typography} color="error" />
                                </Grid>
                                <Grid item xs={12}>
                                    <Field as={TextField} name='employeeLv' label='Level' required fullWidth />
                                    <ErrorMessage name='employeeLv' component={Typography} color="error" />
                                </Grid>
                                <Grid item xs={12}>
                                    <Field as={TextField} name='workTime' label='Thời gian làm việc' required fullWidth />
                                    <ErrorMessage name='workTime' component={Typography} color="error" />
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

export default EditEmployeeDialog;
