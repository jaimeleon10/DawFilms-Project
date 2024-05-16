package org.example.dawfilmsinterface.productos.services.productos

import com.github.michaelbull.result.*
import org.example.dawfilmsinterface.productos.cache.ProductosCache
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.models.producto.Producto
import org.example.dawfilmsinterface.productos.repositories.butacas.ButacaRepository
import org.example.dawfilmsinterface.productos.repositories.complementos.ComplementoRepository
import org.example.dawfilmsinterface.productos.validators.ButacaValidator
import org.example.dawfilmsinterface.productos.validators.ComplementoValidator
import org.lighthousegames.logging.logging

private val logger = logging()

class ProductoServiceImpl(
    private val butacaRepository: ButacaRepository,
    private val complementoRepository: ComplementoRepository,
    private val productosCache: ProductosCache,
    private val butacaValidator: ButacaValidator,
    private val complementoValidator: ComplementoValidator
) : ProductoService{
    override fun getAllProductos(): Result<List<Producto>, ProductoError> {
        logger.debug { "Obteniendo todos los productos" }
        val productos: MutableList<Producto> = mutableListOf()
        butacaRepository.findAll().forEach { productos.add(it) }
        complementoRepository.findAll().forEach { productos.add(it) }
        return Ok(productos)
    }

    override fun getAllButacas(): Result<List<Butaca>, ProductoError> {
        logger.debug { "Obteniendo todas las butacas" }
        return Ok(butacaRepository.findAll())
    }

    override fun getAllComplementos(): Result<List<Complemento>, ProductoError> {
        logger.debug { "Obteniendo todos los complementos" }
        return Ok(complementoRepository.findAll())
    }

    override fun getButacaById(id: String): Result<Butaca, ProductoError> {
        logger.debug { "Obteniendo butaca con id: $id" }
        return productosCache.get(id).mapBoth(
            success = {
                logger.debug { "Producto encontrado en cache" }
                Ok(it as Butaca)
            },
            failure = {
                logger.debug { "Producto no encontrado en cache" }
                butacaRepository.findById(id)
                    ?.let { Ok(it) }
                    ?: Err(ProductoError.ProductoNoEncontrado("Producto no encontrado con id: $id"))
            }
        )
    }

    override fun getComplementoById(id: String): Result<Complemento, ProductoError> {
        logger.debug { "Obteniendo complemento con id: $id" }
        return productosCache.get(id).mapBoth(
            success = {
                logger.debug { "Producto encontrado en cache" }
                Ok(it as Complemento)
            },
            failure = {
                logger.debug { "Producto no encontrado en cache" }
                complementoRepository.findById(id)
                    ?.let { Ok(it) }
                    ?: Err(ProductoError.ProductoNoEncontrado("Producto no encontrado con id: $id"))
            }
        )
    }

    override fun getComplementoByNombre(nombre: String): Result<Complemento, ProductoError> {
        logger.debug { "Obteniendo complemento con nombre: $nombre" }
        return complementoRepository.findByNombre(nombre)
            ?.let { Ok(it) }
            ?: Err(ProductoError.ProductoNoEncontrado("Producto no encontrado con nombre: $nombre"))
    }

    override fun saveButaca(item: Butaca): Result<Butaca, ProductoError> {
        logger.debug { "Guardando butaca: $item" }
        return butacaValidator.validate(item).andThen {
            Ok(butacaRepository.save(it))
        }.andThen { p ->
            println("Guardando en cache")
            productosCache.put(p.id, p) as Result<Butaca, ProductoError>
        }
    }

    override fun saveComplemento(item: Complemento): Result<Complemento, ProductoError> {
        logger.debug { "Guardando complemento: $item" }
        return complementoValidator.validate(item).andThen {
            Ok(complementoRepository.save(it))
        }.andThen { p ->
            println("Guardando en cache")
            productosCache.put(p.id, p) as Result<Complemento, ProductoError>
        }
    }

    override fun updateButaca(id: String, item: Butaca): Result<Butaca, ProductoError> {
        logger.debug { "Actualizando butaca con id: $id" }
        return butacaValidator.validate(item).andThen { p ->
            butacaRepository.update(id, p)
                ?.let { Ok(it) }
                ?: Err(ProductoError.ProductoNoActualizado("Butaca no actualizada con id: $id"))
        }.andThen {
            productosCache.put(id, it) as Result<Butaca, ProductoError>
        }
    }

    override fun updateComplemento(id: String, item: Complemento): Result<Complemento, ProductoError> {
        logger.debug { "Actualizando complemento con id: $id" }
        return complementoValidator.validate(item).andThen { p ->
            complementoRepository.update(id, p)
                ?.let { Ok(it) }
                ?: Err(ProductoError.ProductoNoActualizado("Complemento no actualizado con id: $id"))
        }.andThen {
            productosCache.put(id, it) as Result<Complemento, ProductoError>
        }
    }

    override fun deleteButaca(id: String): Result<Butaca, ProductoError> {
        logger.debug { "Borrando butaca con id: $id" }
        return butacaRepository.delete(id)
            ?.let {
                productosCache.remove(id)
                Ok(it)
            }
            ?: Err(ProductoError.ProductoNoEliminado("Butaca no eliminada con id: $id"))
    }

    override fun deleteComplemento(id: String): Result<Complemento, ProductoError> {
        logger.debug { "Borrando complemento por id: $id" }
        return complementoRepository.delete(id)
            ?.let {
                productosCache.remove(id)
                Ok(it)
            }
            ?: Err(ProductoError.ProductoNoEliminado("complemento no eliminado con id: $id"))
    }
}