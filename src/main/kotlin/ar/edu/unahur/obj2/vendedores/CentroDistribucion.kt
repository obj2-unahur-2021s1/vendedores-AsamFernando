package ar.edu.unahur.obj2.vendedores

abstract class CentroDistribucion {
    val vendedores = mutableListOf<Vendedor>()
    val genericos = mutableListOf<Vendedor>()

    fun agregarVendedoresNuevoYGenericoOError(vendedor: Vendedor) {
        if (vendedores.contains(vendedor)) {
            throw Exception("el vendedor ya esta en el centro")
        }
        else {
            this.agregarVendedor(vendedor)
            this.agregarSiEsGenerico(vendedor)
        }
    }
    fun agregarVendedor(vendedor: Vendedor) { vendedores.add(vendedor) }
    fun agregarSiEsGenerico(vendedor : Vendedor) {
        if(vendedor.esGenerico()) { genericos.add(vendedor) }
    }
    fun vendedorEstrella() = vendedores.maxBy { it.puntajeCertificaciones() }
    fun puedeCubrirCiudad(ciudad: Ciudad) = vendedores.any { it.puedeTrabajarEn(ciudad) }
    fun esRobusto() = vendedores.count { it.esFirme() } >= 3
}

class CentroDistribucionInterior : CentroDistribucion()