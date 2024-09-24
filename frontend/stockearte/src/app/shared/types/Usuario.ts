import { Rol } from "./Rol";

export interface Usuario {
    id: string;
    nombre: string;
    apellido: string;
    username?: string;
    password?: string;
    habilitado: boolean;
    rol: Rol;
    tiendaId: string | ""; // Puede ser null si no tiene tienda asignada
}

export interface UsuariosArray { 
    usuarios: Usuario[];
}