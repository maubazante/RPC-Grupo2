export interface Catalogo {
    id: number;
    nombre: string;
    descripcion: string;
    habilitado: boolean;
    fechaCreacion: Date;
    productos: CatalogoProducto[]; // Relación con la lista de productos en el catálogo
  }

  export interface CatalogoProducto {
    id: number;
    nombre: string;
    codigo: string;
    color: string;
    talle: string;
    cantidad: number;
    habilitado: boolean;
    catalogoId: number; // Relación con el ID del catálogo al que pertenece el producto
  }
  

  export interface CatalogoSOAP {
    idTienda: number
    id: number
    nombre: string
    productosIds: number[]
  }
  