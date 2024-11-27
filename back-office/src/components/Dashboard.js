import React from 'react';
import { Container, Grid, Typography, Card, CardContent } from '@mui/material';
import { Bar, Pie } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend, CategoryScale, LinearScale, BarElement } from 'chart.js';
import { useState, useEffect } from 'react';
import { countCustomers } from '../apis/customerService';
import { totalRevenue } from '../apis/orderService';
// Đăng ký các component của Chart.js
ChartJS.register(ArcElement, Tooltip, Legend, CategoryScale, LinearScale, BarElement);

const Dashboard = () => {
  // Dữ liệu mẫu
  const bestSellingProductsData = {
    labels: ['Product A', 'Product B', 'Product C', 'Product D'],
    datasets: [
      {
        label: 'Số lượng bán',
        data: [120, 90, 75, 60],
        backgroundColor: ['#3f51b5', '#ff5722', '#4caf50', '#ff9800'],
      },
    ],
  };

    const [newCustomersData, setNewCustomersData] = useState(0); 
    const [totalRevenueData, setTotalRevenueData] = useState(0);

    const formatCurrency = (amount) => {
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const res = await countCustomers();
                setNewCustomersData(res.data);
            } catch (error) {
                console.error("Error fetching new customers:", error);
            }
        };
        fetchData();
    }, []);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const res = await totalRevenue();
                setTotalRevenueData(res.data);
            } catch (error) {
                console.error("Error fetching total revenue:", error);
            }
        };
        fetchData();
    }, []);

  return (
    <>
    <Typography variant="h4" gutterBottom>
            Thống kê tháng
        </Typography>
    <Container maxWidth="lg" style={{ marginTop: '20px' }}>
      <Grid container spacing={3}>
        {/* Biểu đồ sản phẩm bán chạy */}
        <Grid item xs={12} md={6} lg={4}>
          <Card>
            <CardContent>
              <Typography variant="h6" gutterBottom>
                Sản phẩm bán chạy
              </Typography>
              <Pie data={bestSellingProductsData} />
            </CardContent>
          </Card>
        </Grid>

        {/* Số lượng khách hàng mới */}
        <Grid item xs={12} md={6} lg={4}>
          <Card>
            <CardContent style={{ textAlign: 'center' }}>
              <Typography variant="h6" gutterBottom>
                Khách hàng mới
              </Typography>
              <Typography variant="h3" color="primary">
                {newCustomersData}
              </Typography>
              <Typography variant="body2" color="textSecondary">
                Khách hàng mới trong tháng
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        {/* Tổng doanh thu */}
        <Grid item xs={12} md={6} lg={4}>
          <Card>
            <CardContent style={{ textAlign: 'center' }}>
              <Typography variant="h6" gutterBottom>
                Tổng doanh thu
              </Typography>
              <Typography variant="h3" color="secondary">
                  {formatCurrency(totalRevenueData)}
              </Typography>
              <Typography variant="body2" color="textSecondary">
                Doanh thu trong tháng
              </Typography>
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </Container>
    </>
  );
};

export default Dashboard;