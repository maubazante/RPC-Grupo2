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
  cantidad: number
  id: string | number
  foto: string
  idUserAdmin: string
}
