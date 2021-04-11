package ar.edu.unahur.obj2.vendedores

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldNotContainExactlyInAnyOrder
import io.kotest.matchers.comparables.shouldBeEqualComparingTo

class CentroDistribucionTest : DescribeSpec ({
    val staFe = Provincia(5000000)
    val cordoba = Provincia(7000000)
    val mendoza = Provincia(6000000)
    val misiones = Provincia(4000000)
    val rosario = Ciudad(staFe)
    val rioCuarto = Ciudad(cordoba)
    val sanRafael = Ciudad(mendoza)
    val obera = Ciudad(misiones)
    val vendedorFijo1 = VendedorFijo(rosario)
    val vendedorFijo2 = VendedorFijo(rioCuarto)
    val vendedorViajante = Viajante(listOf(staFe, cordoba, mendoza))
    val certificacionDeProductoFull = Certificacion(true, 100)
    val certificacionDeProductoPro = Certificacion(true, 75)
    val certificacionBuena = Certificacion(false, 40)
    val certificacionMedia = Certificacion(false, 25)
    vendedorFijo1.agregarCertificacion(certificacionDeProductoFull)
    vendedorFijo1.agregarCertificacion(certificacionDeProductoPro)
    vendedorFijo2.agregarCertificacion(certificacionBuena)
    vendedorFijo2.agregarCertificacion(certificacionMedia)
    vendedorViajante.agregarCertificacion(certificacionBuena)
    vendedorViajante.agregarCertificacion(certificacionDeProductoFull)
    class CentroDistribucionInterior : CentroDistribucion()
    val centroDistribucionInterior = CentroDistribucionInterior()
    centroDistribucionInterior.agregarVendedoresNuevoYGenericoOError(vendedorViajante)
    centroDistribucionInterior.agregarVendedoresNuevoYGenericoOError(vendedorFijo1)
    centroDistribucionInterior.agregarVendedoresNuevoYGenericoOError(vendedorFijo2)

    describe("vendedorEstrella") {
        val centroDistribucionStaFe = CentroDistribucionInterior()

        centroDistribucionStaFe.agregarVendedoresNuevoYGenericoOError(vendedorFijo1)
        centroDistribucionStaFe.agregarVendedoresNuevoYGenericoOError(vendedorFijo2)

        it("Vendedor con mayor puntaje") {
            centroDistribucionStaFe.vendedorEstrella().shouldBeSameInstanceAs(vendedorFijo1)
        }
        it("Vendedor con menor puntaje") {
            centroDistribucionStaFe.vendedorEstrella().shouldNotBeSameInstanceAs(vendedorFijo2)
        }
    }
    describe("puedeCubrirCiudad") {
        it("puede cubrir ciudad") {
            centroDistribucionInterior.puedeCubrirCiudad(sanRafael).shouldBeTrue()
        }
        it("No puede cubrir ciudad") {
            centroDistribucionInterior.puedeCubrirCiudad(obera).shouldBeFalse()
        }
    }
    describe("coleccion de genericos") {
        it("la coleccion de genericos debe contener a") {
            centroDistribucionInterior.genericos.shouldContainExactlyInAnyOrder(vendedorFijo2, vendedorViajante)
        }
        it("la coleccion de genericos NO debe contener a") {
            centroDistribucionInterior.genericos.shouldNotContainExactlyInAnyOrder(vendedorFijo1)
        }
    }
    describe("esRobusto") {
        it("tres de sus vendedores tienen un puntaje mayor a 30") {
            centroDistribucionInterior.esRobusto().shouldBeTrue()
        }
        it("menos de 3 vendedores con puntaje superior a 30") {

        }
    }
})