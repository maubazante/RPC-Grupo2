export interface IProductoRequest {
    nombre: string
    codigo: string
    color: string
    talle: string
    habilitado: boolean
    tiendaIds: number[]
    foto: string //url 
}