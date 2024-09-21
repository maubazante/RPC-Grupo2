import { Usuario } from "./Usuario";

export interface Tienda {
    id: number;
    codigo: string;
    direccion?: string;
    ciudad?: string;
    provincia?: string;
    habilitado: boolean;
    usuario: Usuario | null;  // Puede ser null si no hay usuario asignado
  }