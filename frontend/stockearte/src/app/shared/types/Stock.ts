import { Tienda } from "./Tienda";

export interface Stock {
    id: number;
    tienda: Tienda;
    cantidad: number;
}