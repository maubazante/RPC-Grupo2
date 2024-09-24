export interface TiendaArray {
  tiendas: Tienda[]
}

export interface Tienda {
  id: string
  codigo: string
  direccion: string
  ciudad: string
  provincia: string
  habilitada: boolean
  usuarioId: string
  es_casa_central: boolean
}
