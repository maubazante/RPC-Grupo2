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
  idUserAdmin: string
}

export interface Foto {
  type: string
  data: any[]
}
