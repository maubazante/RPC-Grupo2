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
  foto: string
  idUserAdmin: string
}
