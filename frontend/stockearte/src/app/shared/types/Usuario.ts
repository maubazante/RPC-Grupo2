import { Rol } from "./Rol";
import { Tienda } from "./Tienda";

export interface Usuario {
    id: number;
    nombre: string;
    apellido: string;
    username?: string;
    password?: string;
    habilitado: boolean;
    rol: Rol;
    tienda?: Tienda | null; // Puede ser null si no tiene tienda asignada
}
  
 