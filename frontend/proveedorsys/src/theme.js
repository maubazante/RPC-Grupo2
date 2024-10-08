import { createTheme } from '@mui/material/styles';

// Definir los colores del tema
const granate = '#8B0000';
const grisOscuro = '#2c2c2c';
const grisMedio = '#383838';
const blanco = '#ffffff';

const theme = createTheme({
  palette: {
    mode: 'dark',
    primary: {
      main: granate, // Granate como color primario
    },
    secondary: {
      main: blanco, // Blanco como color secundario para resaltar el texto
    },
    background: {
      default: grisOscuro, // Fondo principal en gris oscuro
      paper: grisMedio,    // Fondo de elementos como Paper y Dialog
    },
    text: {
      primary: blanco,  // Color principal de texto en blanco
      secondary: blanco, // Usar blanco también como color secundario
    },
  },
  typography: {
    h4: {
      fontSize: '2rem',
      color: blanco,
    },
    body1: {
      fontSize: '1rem',
      color: blanco,
    },
    button: {
      textTransform: 'none',
      color: blanco,
    },
  },
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          backgroundColor: granate, // Fondo granate para botones
          '&:hover': {
            backgroundColor: '#6b0000', // Un granate más oscuro en hover
          },
          color: blanco, // Texto en blanco en los botones
        },
      },
    },
    MuiDialog: {
      styleOverrides: {
        paper: {
          backgroundColor: grisMedio, // Fondo de los modales en gris más claro
          color: blanco, // Texto en blanco
        },
      },
    },
    MuiTextField: {
      styleOverrides: {
        root: {
          '& .MuiInputBase-input': {
            color: blanco,  // Texto dentro de los campos de texto en blanco
          },
          '& .MuiInputLabel-root': {
            color: blanco,  // Color del label en blanco
          },
          '& .MuiInputLabel-root.Mui-focused': {
            color: blanco,  // Color del label cuando el campo está enfocado
          },
          '& .MuiOutlinedInput-root .MuiOutlinedInput-notchedOutline': {
            borderColor: blanco,  // Borde de los campos en blanco
          },
          '& .MuiOutlinedInput-root:hover .MuiOutlinedInput-notchedOutline': {
            borderColor: granate,  // Borde en granate al hacer hover
          },
          '& .MuiOutlinedInput-root.Mui-focused .MuiOutlinedInput-notchedOutline': {
            borderColor: granate,  // Borde granate cuando está enfocado
          },
        },
      },
    },
    MuiTableCell: {
      styleOverrides: {
        root: {
          color: blanco, // Color del texto en las tablas
          backgroundColor: grisOscuro, // Fondo gris oscuro en las celdas
        },
        head: {
          backgroundColor: granate,  // Color de fondo de los encabezados de la tabla en granate
          color: blanco,             // Texto en blanco en los encabezados
        },
      },
    },
  },
});

export default theme;
