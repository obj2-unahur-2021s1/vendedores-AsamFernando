package ar.edu.unahur.obj2.vendedores

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldNotContainExactlyInAnyOrder

class CentroDistribucionTest : DescribeSpec ({
    //provincias y ciudades
    val staFe = Provincia(5000000)
    val cordoba = Provincia(7000000)
    val mendoza = Provincia(6000000)
    val misiones = Provincia(4000000)
    val rosario = Ciudad(staFe)
    val rioCuarto = Ciudad(cordoba)
    val sanRafael = Ciudad(mendoza)
    val obera = Ciudad(misiones)
    //vendedores
    val vendedorFijo1 = VendedorFijo(rosario)
    val vendedorFijo2 = VendedorFijo(rioCuarto)
    val vendedorViajante1 = Viajante(listOf(staFe, cordoba, mendoza))
    val vendedorViajante2 = Viajante(listOf(staFe, misiones))
    val comercio1 = ComercioCorresponsal(listOf(rosario, sanRafael))
    //certificaciones
    val certificacionDeProductoFull = Certificacion(true, 100)
    val certificacionDeProductoPro = Certificacion(true, 75)
    val certificacionDeProductoBaja = Certificacion(true, 15)
    val certificacionBuena = Certificacion(false, 40)
    val certificacionMedia = Certificacion(false, 25)
    val certificacionBaja = Certificacion(false, 10)
    //agregar certificaciones
    vendedorFijo1.agregarCertificacion(certificacionDeProductoFull)
    vendedorFijo1.agregarCertificacion(certificacionDeProductoPro)
    vendedorFijo2.agregarCertificacion(certificacionBuena)
    vendedorFijo2.agregarCertificacion(certificacionMedia)
    vendedorViajante1.agregarCertificacion(certificacionBuena)
    vendedorViajante1.agregarCertificacion(certificacionDeProductoFull)
    vendedorViajante2.agregarCertificacion(certificacionBaja)
    vendedorViajante2.agregarCertificacion(certificacionBaja)
    comercio1.agregarCertificacion(certificacionBaja)
    comercio1.agregarCertificacion(certificacionDeProductoBaja)
    //centro de distribucion, agregar vendedores
    val centroDistribucionInterior = CentroDistribucionInterior()
    centroDistribucionInterior.agregarVendedoresNuevoYGenericoOError(vendedorViajante1)
    centroDistribucionInterior.agregarVendedoresNuevoYGenericoOError(vendedorFijo1)
    centroDistribucionInterior.agregarVendedoresNuevoYGenericoOError(vendedorFijo2)

    describe("vendedorEstrella") {
        val centroDistribucionStaFeCordoba = CentroDistribucionInterior()
        centroDistribucionStaFeCordoba.agregarVendedoresNuevoYGenericoOError(vendedorFijo1)
        centroDistribucionStaFeCordoba.agregarVendedoresNuevoYGenericoOError(vendedorFijo2)

        it("vendedor con mayor puntaje") {
            centroDistribucionStaFeCordoba.vendedorEstrella().shouldBeSameInstanceAs(vendedorFijo1)
        }
        it("vendedor con menor puntaje") {
            centroDistribucionStaFeCordoba.vendedorEstrella().shouldNotBeSameInstanceAs(vendedorFijo2)
        }
    }
    describe("puedeCubrirCiudad") {
        it("al menos 1 vendedor puede trabajar en la ciudad dada") {
            centroDistribucionInterior.puedeCubrirCiudad(sanRafael).shouldBeTrue()
        }
        it("ningun vendedor puede trabajar en la ciudad dada") {
            centroDistribucionInterior.puedeCubrirCiudad(obera).shouldBeFalse()
        }
    }
    describe("coleccion de genericos") {
        it("la coleccion de genericos debe contener a: vendedorFijo2 y vendedorViajante1") {
            centroDistribucionInterior.genericos.shouldContainExactlyInAnyOrder(vendedorFijo2, vendedorViajante1)
        }
        it("la coleccion de genericos NO debe contener a: vendedorFijo1") {
            centroDistribucionInterior.genericos.shouldNotContainExactlyInAnyOrder(vendedorFijo1)
        }
    }
    describe("esRobusto") {
        it("tres de sus vendedores tienen un puntaje mayor a 30") {
            centroDistribucionInterior.esRobusto().shouldBeTrue()
        }
        it("menos de 3 vendedores con puntaje superior a 30") {
            centroDistribucionInterior.vendedores.clear()
            centroDistribucionInterior.agregarVendedor(comercio1)
            centroDistribucionInterior.agregarVendedor(vendedorFijo1)
            centroDistribucionInterior.agregarVendedor(vendedorFijo2)
            centroDistribucionInterior.agregarVendedor(vendedorViajante2)
            centroDistribucionInterior.esRobusto().shouldBeFalse()
        }
    }
})