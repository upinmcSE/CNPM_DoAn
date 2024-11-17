import { 
    Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField, Grid, Typography
} from "@mui/material";
import { Formik, Form, Field, ErrorMessage } from "formik";


function InventoryEditDialog({ open, onClose, onSubmit, initialValues, validationSchema }) {
    return (
        <Dialog open={open} fullWidth maxWidth="lg">
            <DialogTitle>Thêm số lượng cho sản phẩm</DialogTitle>
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
                                    <Field as={TextField} name='quantity' label='Số lượng sản phẩm muốn thêm' required fullWidth />
                                    <ErrorMessage name='quantity' component={Typography} color="error" />
                                </Grid>
                            </Grid>
                        </DialogContent>
                        <DialogActions>
                            <Button disabled={!dirty || !isValid} type="submit" variant="contained" color="primary">
                                Thêm
                            </Button>
                            <Button onClick={onClose}>Cancel</Button>
                        </DialogActions>
                    </Form>
                )}
            </Formik>
        </Dialog>
    );
}

export default InventoryEditDialog;
