import MenuIcon from '@mui/icons-material/Menu';
import Typography from '@mui/material/Typography';
import Toolbar from '@mui/material/Toolbar';
import { Colors, DrawerWidth } from '../styles/theme';
import { styled } from '@mui/material/styles';
import MuiAppBar from '@mui/material/AppBar';
import IconButton from '@mui/material/IconButton';
import InputBase from '@mui/material/InputBase';
import SearchIcon from '@mui/icons-material/Search';


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
      // vertical padding + font size from searchIcon
      paddingLeft: `calc(1em + ${theme.spacing(4)})`,
      transition: theme.transitions.create('width'),
      width: '100%',
      [theme.breakpoints.up('sm')]: {
        width: '20ch',
        '&:focus': {
            width: '50ch'
        }
      },
    },
  }));

function Appbar({open, handleDrawerOpen}) {
    return ( 
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
          {!open && <Typography overflow={'visible'} fontWeight={'bold'} color={Colors.black} variant="h5" noWrap component="div">
            Admin Dashboard
          </Typography>}
          <Search open={open}>
            <SearchIconWrapper>
              <SearchIcon sx={{color: Colors.light}} />
            </SearchIconWrapper>
            <StyledInputBase
              placeholder="Search…"
              inputProps={{ 'aria-label': 'search' }}
            />
          </Search>
        </Toolbar>
      </AppBar>
     );
}

export default Appbar;