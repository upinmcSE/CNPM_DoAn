import React, { useState, useEffect } from 'react';
import MenuIcon from '@mui/icons-material/Menu';
import LoginIcon from '@mui/icons-material/Login'; // Thêm biểu tượng đăng nhập
import Typography from '@mui/material/Typography';
import Toolbar from '@mui/material/Toolbar';
import { Colors, DrawerWidth } from '../styles/theme';
import { styled } from '@mui/material/styles';
import MuiAppBar from '@mui/material/AppBar';
import IconButton from '@mui/material/IconButton';
import InputBase from '@mui/material/InputBase';
import SearchIcon from '@mui/icons-material/Search';
import { Dialog, DialogTitle, DialogContent, DialogActions, TextField, Button } from '@mui/material';
import { login, logout } from '../apis/authService';
import PersonIcon from '@mui/icons-material/Person'; 

const AppBar = styled(MuiAppBar, {
    shouldForwardProp: (prop) => prop !== 'open',
})(({ theme, open }) => ({
    transition: theme.transitions.create(['margin', 'width'], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    ...(open && {
        width: `calc(100% - ${DrawerWidth}px)`,
        marginLeft: `${DrawerWidth}px`,
        transition: theme.transitions.create(['margin', 'width'], {
            easing: theme.transitions.easing.easeOut,
            duration: theme.transitions.duration.enteringScreen,
        }),
    }),
}));

const Search = styled('div')(({ open }) => ({
    position: 'relative',
    borderRadius: 25,
    backgroundColor: Colors.white,
    '&:hover': {
        backgroundColor: `1px solid ${Colors.white}`,
    },
    marginLeft: open ? 0 : 10,
}));

const SearchIconWrapper = styled('div')(({ theme }) => ({
    padding: theme.spacing(0, 2),
    height: '100%',
    position: 'absolute',
    pointerEvents: 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
}));

const StyledInputBase = styled(InputBase)(({ theme }) => ({
    fontSize: '1.25rem',
    '& .MuiInputBase-input': {
        padding: theme.spacing(1, 1, 1, 0),
        paddingLeft: `calc(1em + ${theme.spacing(4)})`,
        transition: theme.transitions.create('width'),
        width: '100%',
        [theme.breakpoints.up('sm')]: {
            width: '20ch',
            '&:focus': {
                width: '50ch',
            },
        },
    },
}));

function Appbar({ open, handleDrawerOpen }) {
    const [openLoginDialog, setOpenLoginDialog] = useState(false); 
    const [isLoggedIn, setIsLoggedIn] = useState(false); 

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            setIsLoggedIn(true); // Cập nhật trạng thái nếu token tồn tại
        }
    }, []);

    const handleLoginDialogOpen = () => {
        setOpenLoginDialog(true);
    };

    const handleLoginDialogClose = () => {
        setOpenLoginDialog(false);
    };

    const handleLoginSuccess = () => {
        setIsLoggedIn(true); // Cập nhật trạng thái khi đăng nhập thành công
    };
    
    const handleLoginSubmit = async (event) => {
        event.preventDefault();
        const form = event.target; 
        const formData = new FormData(form);
        const username = formData.get('username');
        const password = formData.get('password');

        console.log('Username:', username);
        console.log('Password:', password);

        try {
            // Gọi hàm login với username và password
            const userData = await login(username, password);
            
            // Giả sử rằng token được trả về trong 'userData'
            const token = userData.token; // Điều này cần kiểm tra theo cấu trúc thực tế của phản hồi
            if (token) {
                console.log(token);
                localStorage.setItem('token', token); // Lưu token vào localStorage
                handleLoginSuccess(); // Cập nhật trạng thái đăng nhập
                handleLoginDialogClose(); // Đóng dialog đăng nhập
            } else {
                throw new Error('Invalid login response');
            }
        } catch (error) {
            console.error('Login failed:', error); // In ra lỗi nếu có
            alert('Đăng nhập không thành công!'); // Hiển thị thông báo lỗi
        }
    };

    const handleLogout = async () => {
        try {
            await logout(); // Gọi hàm logout từ authService
            localStorage.removeItem('token'); // Xóa token khỏi localStorage
            setIsLoggedIn(false); // Cập nhật trạng thái đăng xuất
        } catch (error) {
            console.error('Logout failed:', error); // In ra lỗi nếu có
            alert('Đăng xuất không thành công!'); // Hiển thị thông báo lỗi
        }
    };
    return (
        <>
            <AppBar position="fixed" elevation={0} open={open}>
                <Toolbar>
                    <IconButton
                        color={Colors.black}
                        aria-label="open drawer"
                        onClick={handleDrawerOpen}
                        edge="start"
                        sx={{ mr: 2, ...(open && { display: 'none' }) }}
                    >
                        <MenuIcon />
                    </IconButton>
                    {!open && (
                        <Typography overflow={'visible'} fontWeight={'bold'} color={Colors.black} variant="h5" noWrap component="div">
                            Admin Dashboard
                        </Typography>
                    )}
                    <Search open={open}>
                        <SearchIconWrapper>
                            <SearchIcon sx={{ color: Colors.light }} />
                        </SearchIconWrapper>
                        <StyledInputBase
                            placeholder="Search…"
                            inputProps={{ 'aria-label': 'search' }}
                        />
                    </Search>
                    <IconButton
                        color={Colors.black}
                        onClick={handleLoginDialogOpen}
                        sx={{ marginLeft: 'auto' }} // Đẩy biểu tượng sang bên phải
                    >
                        {isLoggedIn ? <PersonIcon /> : <LoginIcon />} {/* Hiển thị biểu tượng đăng nhập hoặc biểu tượng người dùng */}
                    </IconButton>
                    {isLoggedIn && (
                        <IconButton color={Colors.black} onClick={handleLogout}>
                        <Typography variant="body2">Logout</Typography>
                    </IconButton>
                    )}
                </Toolbar>
            </AppBar>
        
            {/* Dialog đăng nhập */}
            <Dialog open={openLoginDialog} onClose={() => handleLoginDialogClose()}>
                <DialogTitle>Login</DialogTitle>
                <DialogContent>
                    <form onSubmit={handleLoginSubmit}>
                        <TextField
                            name="username" // Đặt name cho field
                            autoFocus
                            margin="dense"
                            label="Username"
                            type="text"
                            fullWidth
                            variant="outlined"
                            required
                        />
                        <TextField
                            name="password" // Đặt name cho field
                            margin="dense"
                            label="Password"
                            type="password"
                            fullWidth
                            variant="outlined"
                            required
                        />
                        <DialogActions>
                            <Button onClick={handleLoginDialogClose}>Cancel</Button>
                            <Button  type="submit" color="primary">Login</Button>
                        </DialogActions>
                    </form>
                </DialogContent>
            </Dialog>
        </>
    );
}

export default Appbar;