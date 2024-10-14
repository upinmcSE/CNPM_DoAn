import { Button, TableContainer, Typography, Table, TableBody, TableCell, TableRow, TableHead } from "@mui/material";
import AddIcon from "@mui/icons-material/Add"
function Products() {
    const handleAddProduct = () => {

    }

    return ( 
        <>
            <Typography sx={{md: 1}} variant="h4">Products</Typography>
            <Button startIcon={<AddIcon />} variant="contained" onClick={handleAddProduct}>ADD Product</Button>
            <TableContainer>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>1</TableCell>
                            <TableCell>2</TableCell>
                            <TableCell>3</TableCell>
                            <TableCell>4</TableCell>
                            <TableCell>5</TableCell>
                            <TableCell>6</TableCell>
                            <TableCell>7</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        
                    </TableBody>
                </Table>
            </TableContainer>
        </>
     );
}

export default Products;