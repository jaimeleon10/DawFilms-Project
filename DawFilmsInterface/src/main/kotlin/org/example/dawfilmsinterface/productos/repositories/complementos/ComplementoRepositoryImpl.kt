package org.example.dawfilmsinterface.productos.repositories.complementos

import org.example.dawfilmsinterface.database.SqlDeLightManager
import org.example.dawfilmsinterface.productos.mappers.toComplemento
import org.example.dawfilmsinterface.productos.mappers.toProducto
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.lighthousegames.logging.logging
import java.time.LocalDate

private val logger = logging()


class ComplementoRepositoryImpl(
    private val dbManager: SqlDeLightManager
) : ComplementoRepository {

    private val db = dbManager.databaseQueries

    override fun findAll(): List<Complemento> {
        logger.debug { "Buscando todos las complementos" }
        return db.selectAllComplementos().executeAsList().map { it.toProducto() as Complemento }
    }

    override fun findById(id: String): Complemento? {
        logger.debug { "Buscando complemento con id: $id" }
        return db.selectComplementoById(id).executeAsOneOrNull()?.toComplemento()
    }

    override fun findByNombre(nombre: String): Complemento? {
        logger.debug { "Buscando complemento con nombre: $nombre" }
        return db.selectComplementoByNombre(nombre).executeAsOneOrNull()?.toComplemento()
    }

    override fun saveAll(complementos: List<Complemento>): List<Complemento> {
        logger.debug { "Guardando complementos" }
        return complementos.map { save(it) }
    }

    override fun save(item: Complemento): Complemento {
        logger.debug { "Guardando complemento: $item" }
        val timeStamp = LocalDate.now().toString()
        db.transaction {
            db.insertComplemento(
                id = item.id,
                tipo_producto = item.tipoProducto,
                imagen = item.imagen,
                precio = item.precio,
                nombre_complemento = item.nombre,
                stock_complemento = item.stock.toLong(),
                categoria_complemento = item.categoria.toString(),
                created_at = timeStamp,
                updated_at = timeStamp,
                is_deleted = 0
            )
        }
        return db.selectComplementoById(item.id).executeAsOne().toComplemento()
    }

    override fun update(id: String, item: Complemento): Complemento? {
        logger.debug { "Actualizando complemento con id: $id" }
        this.findById(id) ?: return null
        val timeStamp = LocalDate.now()

        db.updateComplemento(
            tipo_producto = item.tipoProducto,
            imagen = item.imagen,
            precio = item.precio,
            nombre_complemento = item.nombre,
            stock_complemento = item.stock.toLong(),
            categoria_complemento = item.categoria.toString(),
            updated_at = timeStamp.toString(),
            is_deleted = if (item.isDeleted!!) 1 else 0,
            id = item.id
        )
        return item
    }

    override fun delete(id: String): Complemento? {
        logger.debug { "Borrando complemento con id: $id" }
        this.findById(id) ?: return null
        val timeStamp = LocalDate.now()

        db.deleteLogicoComplemento(
            updated_at = timeStamp.toString(),
            id = id
        )

        return this.findById(id)
    }

    override fun deleteAll() {
        logger.debug { "Borrando todos los complementos" }
        return db.deleteAllComplementos()
    }
}