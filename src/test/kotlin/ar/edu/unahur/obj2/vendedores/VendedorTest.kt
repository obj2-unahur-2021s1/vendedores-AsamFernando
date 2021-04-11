package ar.edu.unahur.obj2.vendedores

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue

class VendedorTest : DescribeSpec({
  val misiones = Provincia(1300000)
  val cordoba = Provincia(2000000)
  val santaFe = Provincia(10000000)
  val laPampa = Provincia(5000000)
  val sanIgnacio = Ciudad(misiones)
  val villaDolores = Ciudad(cordoba)
  val rosario = Ciudad(santaFe)
  val staRosa = Ciudad(laPampa)
  val obera = Ciudad(misiones)
  val esperanza = Ciudad(santaFe)
  val sunchales = Ciudad(santaFe)

  val certificacionDeProductoFull = Certificacion(true, 100)
  val certificacionDeProductoPro = Certificacion(true, 75)
  val certificacionDeProductoBaja = Certificacion(true, 15)
  val certificacionBuena = Certificacion(false, 40)
  val certificacionMedia = Certificacion(false, 25)
  val certificacionBaja = Certificacion(false, 10)

  describe("Vendedor fijo") {
    val vendedorFijo = VendedorFijo(obera)

    describe("puedeTrabajarEn") {
      it("su ciudad de origen") {
        vendedorFijo.puedeTrabajarEn(obera).shouldBeTrue()
      }
      it("otra ciudad") {
        vendedorFijo.puedeTrabajarEn(sanIgnacio).shouldBeFalse()
      }
    }
    describe("esInfluyente") {
      it("el vendedor fijo no es influyente") {
        vendedorFijo.esInfluyente().shouldBeFalse()
      }
    }
    describe("esVersatil") {
      it("menos de 3 certificaciones de un solo tipo") {
        vendedorFijo.agregarCertificacion(certificacionBaja)
        vendedorFijo.agregarCertificacion(certificacionBaja)
        vendedorFijo.esVersatil().shouldBeFalse()
      }
      it("menos de 3 certificaciones de ambos tipos") {
        vendedorFijo.certificaciones.clear()
        vendedorFijo.agregarCertificacion(certificacionBaja)
        vendedorFijo.agregarCertificacion(certificacionDeProductoBaja)
        vendedorFijo.esVersatil().shouldBeFalse()
      }
      it("3 o mas certificaciones de un solo tipo") {
        vendedorFijo.certificaciones.clear()
        vendedorFijo.agregarCertificacion(certificacionBaja)
        vendedorFijo.agregarCertificacion(certificacionBaja)
        vendedorFijo.agregarCertificacion(certificacionMedia)
        vendedorFijo.agregarCertificacion(certificacionBuena)
        vendedorFijo.esVersatil().shouldBeFalse()
      }
      it("3 o mas certificaciones de ambos tipos") {
        vendedorFijo.certificaciones.clear()
        vendedorFijo.agregarCertificacion(certificacionBaja)
        vendedorFijo.agregarCertificacion(certificacionMedia)
        vendedorFijo.agregarCertificacion(certificacionDeProductoFull)
        vendedorFijo.agregarCertificacion(certificacionDeProductoPro)
        vendedorFijo.esVersatil().shouldBeTrue()
      }
    }

    describe("esFirme") {
      it("puntaje de certificaciones menor a 30") {
        vendedorFijo.certificaciones.clear()
        vendedorFijo.agregarCertificacion(certificacionBaja)
        vendedorFijo.agregarCertificacion(certificacionDeProductoBaja)
        vendedorFijo.esFirme().shouldBeFalse()
      }
      it("puntaje de certificaciones mayor o igual a 30") {
        vendedorFijo.certificaciones.clear()
        vendedorFijo.agregarCertificacion(certificacionBaja)
        vendedorFijo.agregarCertificacion(certificacionDeProductoBaja)
        vendedorFijo.agregarCertificacion(certificacionBuena)
        vendedorFijo.esFirme().shouldBeTrue()
      }
    }
    describe("esGenerico") {
      it("tiene solo certificaciones de producto") {
        vendedorFijo.certificaciones.clear()
        vendedorFijo.agregarCertificacion(certificacionDeProductoPro)
        vendedorFijo.agregarCertificacion(certificacionDeProductoFull)
        vendedorFijo.esGenerico().shouldBeFalse()
      }
      it("al menos una certificacion que no es de producto") {
        vendedorFijo.certificaciones.clear()
        vendedorFijo.agregarCertificacion(certificacionBuena)
        vendedorFijo.agregarCertificacion(certificacionDeProductoFull)
        vendedorFijo.esGenerico().shouldBeTrue()
      }
    }
  }

  describe("Viajante") {
    val viajante = Viajante(listOf(misiones))
    val viajanteInfluyente = Viajante(listOf(misiones, santaFe))

    describe("puedeTrabajarEn") {
      it("una ciudad que pertenece a una provincia habilitada") {
        viajante.puedeTrabajarEn(sanIgnacio).shouldBeTrue()
      }
      it("una ciudad que no pertenece a una provincia habilitada") {
        viajante.puedeTrabajarEn(villaDolores).shouldBeFalse()
      }
    }
    describe("esInfluyente") {
      it("suma poblacion de provincias habilitadas menor a 10000000") {
        viajante.esInfluyente().shouldBeFalse()
      }
      it("suma poblacion de provincias habilitadas mayor a 10000000") {
        viajanteInfluyente.esInfluyente().shouldBeTrue()
      }
    }
  }
  describe("comercioCorresponsal") {
    val comercioCorresponsal = ComercioCorresponsal(listOf(staRosa, obera, sanIgnacio))
    val comercioCorresponsalInfluyenteProvincia = ComercioCorresponsal(listOf(rosario, villaDolores, obera))
    val comercioCorresponsalInfluyenteCiudad = ComercioCorresponsal(listOf(rosario, obera, esperanza, sunchales, sanIgnacio))

    describe("puedeTrabajarEn") {
      it("ciudad en la que tiene sucursal") {
        comercioCorresponsal.puedeTrabajarEn(sanIgnacio).shouldBeTrue()
      }
      it("ciudad en la que no tiene sucursal") {
        comercioCorresponsal.puedeTrabajarEn(villaDolores).shouldBeFalse()
      }
    }
    describe("esInfluyente") {
      it("sucursales en 3 provincias") {
        comercioCorresponsalInfluyenteProvincia.esInfluyente().shouldBeTrue()
      }
      it("sucursales en 5 ciudades") {
        comercioCorresponsalInfluyenteCiudad.esInfluyente().shouldBeTrue()
      }
      it("menos de 3 provincias con sucursales y menos de 5 ciudades con sucursales") {
        comercioCorresponsal.esInfluyente().shouldBeFalse()
      }
    }
  }
})
