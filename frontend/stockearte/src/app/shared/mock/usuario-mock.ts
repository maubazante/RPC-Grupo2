import { Rol } from "../types/Rol";
import { Usuario } from "../types/Usuario";

export const USUARIOS_MOCK: Usuario[] = [
    {
      id: "1",
      nombre: 'Juan',
      apellido: 'Pérez',
      username: 'juanp',
      password: 'password123',
      habilitado: true,
      rol: Rol.ADMIN,
      tiendaId: "TIENDAS_MOCK[0]"
    },
    {
      id: "2",
      nombre: 'María',
      apellido: 'Gómez',
      username: 'mariag',
      password: 'password123',
      habilitado: false,
      rol: Rol.USER,
      tiendaId: ""  // Usuario sin tienda
    },
    {
      id: "3",
      nombre: 'Carlos',
      apellido: 'Fernández',
      username: 'carlosf',
      password: 'password123',
      habilitado: true,
      rol: Rol.MANAGER,
      tiendaId: "TIENDAS_MOCK[1]"
    }
  ];
  