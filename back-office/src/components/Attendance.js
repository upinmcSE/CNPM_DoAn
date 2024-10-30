import React, { useState, useEffect } from 'react';
import { Button, Typography, Paper, Alert } from '@mui/material';

const Attendance = () => {
    const [isCheckedIn, setIsCheckedIn] = useState(false);
    const [message, setMessage] = useState('');

    useEffect(() => {
        // Kiểm tra xem có thông tin đã ghi nhận điểm danh trong localStorage hay không
        const checkInStatus = localStorage.getItem('checkInStatus');
        if (checkInStatus) {
            const { timestamp } = JSON.parse(checkInStatus);
            const currentTime = new Date().getTime();

            // Kiểm tra xem có phải đã ghi nhận trong vòng 24 giờ qua không
            if (currentTime - timestamp < 24 * 60 * 60 * 1000) {
                setIsCheckedIn(true); // Ẩn nút nếu đã ghi nhận trong 24 giờ
            }
        }
    }, []);

    const handleCheckIn = () => {
        setIsCheckedIn(true);
        setMessage('Check-in successful!');

        // Lưu thông tin ghi nhận vào localStorage với timestamp hiện tại
        localStorage.setItem('checkInStatus', JSON.stringify({ timestamp: new Date().getTime() }));
    };

    return (
        <Paper elevation={3} sx={{ padding: 4, maxWidth: 300, margin: 'auto', textAlign: 'center' }}>
            <Typography variant="h5" component="h1" gutterBottom>
                Employee Check-In
            </Typography>
            {!isCheckedIn ? (
                <Button
                    variant="contained"
                    color="primary"
                    onClick={handleCheckIn}
                    fullWidth
                >
                    Check In
                </Button>
            ) : (
                <Alert severity="success">
                    You have already checked in for today!
                </Alert>
            )}
            {message && (
                <Alert severity="success" sx={{ marginTop: 2 }}>
                    {message}
                </Alert>
            )}
        </Paper>
    );
};

export default Attendance;
