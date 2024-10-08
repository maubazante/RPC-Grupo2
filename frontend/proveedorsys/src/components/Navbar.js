import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { AppBar, Toolbar, IconButton, Typography, Drawer, List, ListItem, ListItemIcon, ListItemText, Divider, Box } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import LogoutIcon from '@mui/icons-material/Logout';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import InventoryIcon from '@mui/icons-material/Inventory';

const Navbar = () => {
  const [drawerOpen, setDrawerOpen] = useState(false);

  const toggleDrawer = (open) => (event) => {
    if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
      return;
    }
    setDrawerOpen(open);
  };

  const menuItems = [
    { text: 'Productos', icon: <InventoryIcon />, path: '/productos' },
    { text: 'Órdenes de Compra', icon: <ShoppingCartIcon />, path: '/ordenes-compra' },
  ];

  return (
    <>
      {/* Navbar */}
      <AppBar position="static">
        <Toolbar>
          <IconButton edge="start" color="inherit" aria-label="menu" onClick={toggleDrawer(true)}>
            <MenuIcon />
          </IconButton>
          {/* Nombre con fuentes diferenciadas */}
          <Typography variant="h6" style={{ flexGrow: 1 }}>
            <Box component="span" sx={{ fontFamily: 'Ubuntu', fontSize: '24px'}}>
              proveedorsys{' '}
            </Box>
            <Box component="span" sx={{ fontFamily: 'Roboto Slab', fontWeight: '700' }}>
              by stockearte
            </Box>
          </Typography>
        </Toolbar>
      </AppBar>

      {/* Drawer (Menú hamburguesa) */}
      <Drawer anchor="left" open={drawerOpen} onClose={toggleDrawer(false)}>
        <div role="presentation" onClick={toggleDrawer(false)} onKeyDown={toggleDrawer(false)} style={{ width: 250 }}>
          <List>
            {menuItems.map((item, index) => (
              <ListItem button component={Link} to={item.path} key={index}>
                <ListItemIcon>{item.icon}</ListItemIcon>
                <ListItemText primary={item.text} />
              </ListItem>
            ))}
          </List>
          <Divider />
          <List>
            <ListItem button component={Link} to="/inicio">
              <ListItemText primary="Inicio" />
            </ListItem>
          </List>
        </div>
      </Drawer>
    </>
  );
};

export default Navbar;
