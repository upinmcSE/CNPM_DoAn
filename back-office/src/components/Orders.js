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
import CheckCircleSharpIcon from '@mui/icons-material/CheckCircleSharp';
import Pagination from "@mui/material/Pagination";
import {getOrder, completedOrder} from "../apis/orderService"

const ORDER_STATUS = {
  COMPLETED: 'COMPLETED',
  CANCELLED: 'CANCELLED',
};

const Orders = () => {
  const [orders, setListOrders] = useState([])
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1); 

  useEffect(() => {
    loadOrders(currentPage);
  }, [currentPage]);

  const loadOrders = async (page) => {
      try {
          const res = await getOrder(page, 10); 
          setListOrders(res.data);
          setTotalPages(res.totalPages);
      } catch (error) {
          console.error("Error fetching products:", error);
      }
  };

  const handlePageChange = (event, value) => {
      setCurrentPage(value);
  }

  const handleUpdate = async (value) => {
      try {
        const res = await completedOrder(value.id); 
        loadOrders(currentPage);
    } catch (error) {
        console.error("Error fetching products:", error);
    }
  }
  return(
    <>
        <Typography variant="h4">List Order </Typography>
          <TableContainer>
              <Table>
                  <TableHead>
                      <TableRow>
                          <TableCell>Mã order</TableCell>
                          <TableCell>Mã khách hàng</TableCell>
                          <TableCell>Phương thức thành toán</TableCell>
                          <TableCell>Trạng thái đơn hàng</TableCell>
                          <TableCell>Tổng tiền đơn hàng</TableCell>
                          <TableCell>Xác nhận đơn hàng</TableCell>
                      </TableRow>
                  </TableHead>
                  <TableBody>
                      {orders.map(c => 
                          <TableRow key={c.id}>
                              <TableCell>{c.orderId}</TableCell>
                              <TableCell>{c.customerId}</TableCell>
                              <TableCell>{c.paymentMethod}</TableCell>
                              <TableCell>{c.status}</TableCell>
                              <TableCell>{c.totalPrice}</TableCell>
                              <TableCell>
                                {c.status !== ORDER_STATUS.COMPLETED && ( 
                                    <IconButton onClick={() => handleUpdate(c)}>
                                        <CheckCircleSharpIcon />
                                    </IconButton>
                                )}
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
    </>
  )
}

export default Orders