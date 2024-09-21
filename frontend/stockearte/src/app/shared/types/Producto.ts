import { Stock } from "./Stock";

export interface Producto {
    id: number;
    nombre: string;
    codigo: string;
    foto: string; // base64 o url
    color: string;
    talle: string;
    tiendas: Stock[]; 
    habilitado: boolean;
}
  