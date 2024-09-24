export interface ProductoArray {
    productos: Producto[]
  }
  
  export interface Producto {
    tiendaIds: any[]
    nombre: string
    codigo: string
    color: string
    talle: string
    habilitado: boolean
    stock: string
    id: string
    foto: Foto
  }
  
  export interface Foto {
    type: string
    data: any[]
  }
  