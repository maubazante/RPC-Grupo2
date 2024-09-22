import { Rol } from "../types/Rol";
import { Usuario } from "../types/Usuario";
import { TIENDAS_MOCK } from "./tienda-mock";

export const USUARIOS_MOCK: Usuario[] = [
    {
      id: 1,
      nombre: 'Juan',
      apellido: 'Pérez',
      username: 'juanp',
      password: 'password123',
      habilitado: true,
      rol: Rol.ADMIN,
      tienda: TIENDAS_MOCK[0]
    },
    {
      id: 2,
      nombre: 'María',
      apellido: 'Gómez',
      username: 'mariag',
      password: 'password123',
      habilitado: false,
      rol: Rol.USER,
      tienda: null  // Usuario sin tienda
    },
    {
      id: 3,
      nombre: 'Carlos',
      apellido: 'Fernández',
      username: 'carlosf',
      password: 'password123',
      habilitado: true,
      rol: Rol.MANAGER,
      tienda: TIENDAS_MOCK[1]
    }
  ];
  