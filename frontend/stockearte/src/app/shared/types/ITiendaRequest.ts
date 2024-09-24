export interface ITiendaRequest {
    codigo: number
    direccion: string
    ciudad: string
    provincia: string
    habilitada: boolean
    usuarioId?: number
}