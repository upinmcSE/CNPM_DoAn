import * as React from 'react';
import { styled, useTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import CssBaseline from '@mui/material/CssBaseline';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Typography from '@mui/material/Typography';
import { Colors, DrawerWidth } from "../styles/theme"
import Appbar from './Appbar';
import DashboardIcon from '@mui/icons-material/Dashboard';
import InventoryIcon from '@mui/icons-material/Inventory';
import { useNavigate } from 'react-router-dom';
import {useEffect} from 'react';
import GroupIcon from '@mui/icons-material/Group';

const MyListItemButton = ({selected, icon, text, handleNavbarItemClicked}) => {
    return(
    <ListItemButton 
            onClick={() => handleNavbarItemClicked(text)}
            sx={{
                ...(selected && {
                    background: Colors.white,
                    borderRadius: 2,
                    fontWeight: 'bold',
                    color: Colors.black
                })
            }} 
            >
        <ListItemIcon sx = {{color: selected && Colors.primary}}>
            {icon}
        </ListItemIcon>
        <ListItemText primary={text} />
    </ListItemButton>
      
    )
}


export const DrawerHeader = styled('div')(({ theme }) => ({
  display: 'flex',
  alignItems: 'center',
  padding: theme.spacing(0, 1),
  ...theme.mixins.toolbar,
  justifyContent: 'flex-end',
}));

export default function NavDrawer({open, setOpen}) {
  const theme = useTheme();
  const [selectedItem, setSelectedItem] = React.useState('')
  const navigate = useNavigate();
  const [isLoggedIn, setIsLoggedIn] = React.useState(false);

  useEffect(() => {
    const token = localStorage.getItem('token');
    console.log("xx:", token)
    if (token) {
      setIsLoggedIn(true); // Cập nhật trạng thái nếu token tồn tại
    }
  }, []);

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };

  const handleNavbarItemClicked = (item) => {
    if (isLoggedIn) {
      setSelectedItem(item);
      navigate(item);
    } else {
      alert('Anh xin em đấy, đăng nhập trước đã');
    }
  }

  return (
    <Box sx={{ display: 'flex' }}>
      <CssBaseline />
      <Appbar open={open} handleDrawerOpen={handleDrawerOpen} />
      <Drawer
        sx={{
          width: DrawerWidth,
          flexShrink: 0,
          '& .MuiDrawer-paper': {
            width: DrawerWidth,
            boxSizing: 'border-box',
          },
        }}
        variant="persistent"
        anchor="left"
        open={open}
      >
        <DrawerHeader>
          {open && <Typography fontWeight={'bold'} color={Colors.black} variant="h6" noWrap component="div">
            Admin Dashboard
          </Typography>}
          <IconButton onClick={handleDrawerClose}>
            {theme.direction === 'ltr' ? <ChevronLeftIcon /> : <ChevronRightIcon />}
          </IconButton>
        </DrawerHeader>
        <Divider />
        <List>
            <ListItem  disablePadding>
              <MyListItemButton
                text={"Dashboard"}
                icon={<DashboardIcon />}
                handleNavbarItemClicked={handleNavbarItemClicked}
                selected={selectedItem.includes('Dashboard')}
              />
            </ListItem>
            <ListItem  disablePadding>
              <MyListItemButton
                text={"Products"}
                icon={<InventoryIcon />}
                handleNavbarItemClicked={handleNavbarItemClicked}
                selected={selectedItem.includes('Products')}
              />
            </ListItem>
            <ListItem  disablePadding>
              <MyListItemButton
                text={"Customers"}
                icon={<GroupIcon />}
                handleNavbarItemClicked={handleNavbarItemClicked}
                selected={selectedItem.includes('Products')}
              />
            </ListItem>
            <ListItem  disablePadding>
              <MyListItemButton
                text={"Employees"}
                icon={<GroupIcon />}
                handleNavbarItemClicked={handleNavbarItemClicked}
                selected={selectedItem.includes('Products')}
              />
            </ListItem>
        </List>
        <Divider />
      </Drawer>
    </Box>
  );
}
