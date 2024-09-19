import { Producto } from "../types/Producto";
import { Rol } from "../types/Rol";
import { Tienda } from "../types/Tienda";

export const TIENDAS_MOCK: Tienda[] = [
    {
      id: 1,
      codigo: 'TIENDA001',
      direccion: 'Calle Falsa 123',
      ciudad: 'Buenos Aires',
      provincia: 'Buenos Aires',
      habilitado: true,
      usuario: {
        id: 1,
        nombre: 'Juan',
        apellido: 'Pérez',
        username: 'juanp',
        habilitado: true,
        rol: Rol.ADMIN
      }
    },
    {
      id: 2,
      codigo: 'TIENDA002',
      direccion: 'Avenida Siempreviva 742',
      ciudad: 'Córdoba',
      provincia: 'Córdoba',
      habilitado: false,
      usuario: null  // Sin usuario asignado
    }
  ];
  
  const PRODUCTOS_MOCK: Producto[] = [
    {
      id: 1,
      nombre: 'Camiseta Deportiva',
      codigo: 'CAM123',
      foto: 'https://via.placeholder.com/150',
      color: 'Rojo',
      talle: 'M',
      tiendas: [
        { id: 1, tienda: TIENDAS_MOCK[0], cantidad: 10 },  // Relación con Tienda 1
        { id: 2, tienda: TIENDAS_MOCK[1], cantidad: 5 }     // Relación con Tienda 2
      ],
      habilitado: true
    },
    {
      id: 2,
      nombre: 'Pantalón de Entrenamiento',
      codigo: 'PAN456',
      foto: 'https://via.placeholder.com/150',
      color: 'Negro',
      talle: 'L',
      tiendas: [
        { id: 3, tienda: TIENDAS_MOCK[0], cantidad: 3 }     // Relación con Tienda 1
      ],
      habilitado: true
    },
    {
      id: 3,
      nombre: 'Zapatillas Running',
      codigo: 'ZAP789',
      foto: 'https://via.placeholder.com/150',
      color: 'Blanco',
      talle: '42',
      tiendas: [
        { id: 4, tienda: TIENDAS_MOCK[1], cantidad: 8 }     // Relación con Tienda 2
      ],
      habilitado: false
    }
  ];
  